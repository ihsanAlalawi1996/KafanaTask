package com.example.ihsanstask.ui.main

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.akexorcist.localizationactivity.ui.LocalizationActivity
import com.example.ihsanstask.R
import com.example.ihsanstask.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : LocalizationActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

    }
}
