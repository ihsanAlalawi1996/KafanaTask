package com.example.ihsanstask.bases

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.akexorcist.localizationactivity.core.LocalizationActivityDelegate

abstract class BaseFragment<VB : ViewDataBinding> : Fragment() {

    private lateinit var _binding: VB
    open val binding get() = _binding
    private val localizationDelegate: LocalizationActivityDelegate by lazy {
        LocalizationActivityDelegate(
            requireActivity()
        )
    }

    @LayoutRes
    abstract fun getLayoutId(): Int

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DataBindingUtil.inflate(inflater, getLayoutId(), container, false)
        localizationDelegate.onCreate()
        localizationDelegate.setLanguage(
            requireContext(),
            localizationDelegate.getLanguage(requireContext())
        )
        return _binding.root
    }
}
