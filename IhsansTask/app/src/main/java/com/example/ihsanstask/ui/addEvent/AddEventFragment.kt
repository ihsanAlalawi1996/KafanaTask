package com.example.ihsanstask.ui.addEvent

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.example.ihsanstask.R
import com.example.ihsanstask.databinding.FragmentAddEventBinding
import com.example.ihsanstask.ui.events.EventsViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AddEventFragment : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentAddEventBinding

    private val viewModel: AddEventViewModel by viewModels()
    private val eventsViewModel: EventsViewModel by activityViewModels()

    val args: AddEventFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_add_event, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (args.id == -1) setDates()
        else viewModel.getEvent(args.id)

        initBinding()
        collectResponse()
    }

    private fun setDates() {
        viewModel.setDates(args.date, args.hijriDate)
    }

    private fun initBinding() {
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel
        binding.model = viewModel.model
    }

    private fun collectResponse() {
        lifecycleScope.launch(Dispatchers.Main) {
            viewModel.responseFlow.collect {
                eventsViewModel.getEvents()
                if (it) dismiss()
            }
        }
    }
}
