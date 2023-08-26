package com.example.ihsanstask.ui.date

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.akexorcist.localizationactivity.core.LanguageSetting
import com.example.ihsanstask.R
import com.example.ihsanstask.bases.BaseFragment
import com.example.ihsanstask.databinding.FragmentDateBinding
import com.example.ihsanstask.utils.LocaleHelper
import com.example.ihsanstask.utils.Results
import com.example.ihsanstask.utils.showDatePicker
import com.example.ihsanstask.utils.snack
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Locale
import javax.inject.Inject

@AndroidEntryPoint
class DateFragment : BaseFragment<FragmentDateBinding>() {

    private val viewModel: DateViewModel by viewModels()


    @Inject
    lateinit var localeHelper: LocaleHelper

    override fun getLayoutId(): Int = R.layout.fragment_date

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.handleClicks()
        binding.initBinding()
        collectDate()
    }

    private fun FragmentDateBinding.initBinding() {
        changeLanguage.text = if (localeHelper.isEnglish) "Ar" else "En"
    }

    private fun FragmentDateBinding.handleClicks() {
        changeLanguage.setOnClickListener {
            changeLanguage(if (localeHelper.isEnglish) "ar" else "en")
        }

        pickDateButton.setOnClickListener {
            parentFragmentManager.showDatePicker(endYear = 2025) { year, month, day ->
                viewModel.getHijriDate("$day-$month-$year")
            }
        }

        assignEvent.setOnClickListener {
            viewModel.dateFlow.value.data?.data?.let {
                val date = it.gregorian?.date ?: ""
                val hijriDate = it.hijri?.date ?: ""

                findNavController().navigate(
                    DateFragmentDirections.actionDateFragmentToAddEventFragment(date, hijriDate)
                )
            }
        }

        showEvents.setOnClickListener {
            findNavController().navigate(
                DateFragmentDirections.actionDateFragmentToEventsFragment()
            )
        }
    }

    private fun collectDate() {
        lifecycleScope.launch(Dispatchers.Main) {
            viewModel.dateFlow.collect {
                when (it.status) {
                    Results.Status.LOADING -> {
                        binding.progressBar.isVisible = true
                        binding.pickDateButton.isClickable = false
                        binding.assignEvent.isVisible = false
                    }

                    Results.Status.SUCCESS -> {
                        binding.progressBar.isVisible = false
                        binding.pickDateButton.isClickable = true
                        binding.date.text = it.data?.data?.hijri?.date ?: "No date received"
                        binding.assignEvent.isVisible = true
                    }

                    Results.Status.ERROR -> {
                        binding.progressBar.isVisible = false
                        binding.pickDateButton.isClickable = true
                        binding.root.snack(it.message ?: "No date received") {}
                        binding.assignEvent.isVisible = false
                    }

                    Results.Status.START -> {
                        binding.progressBar.isVisible = false
                        binding.pickDateButton.isClickable = true
                        binding.assignEvent.isVisible = false
                    }
                }
            }
        }
    }

    private fun changeLanguage(lang: String) {
        requireActivity().intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or
                Intent.FLAG_ACTIVITY_CLEAR_TASK or
                Intent.FLAG_ACTIVITY_NEW_TASK
        requireActivity().intent.flags = Intent.FLAG_ACTIVITY_NO_HISTORY
        requireActivity().intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(requireActivity().intent)
        LanguageSetting.setLanguage(requireContext(), Locale(lang))
        localeHelper.onAttach(requireContext(), lang)
    }
}
