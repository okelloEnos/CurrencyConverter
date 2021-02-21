package com.okellosoftwarez.currencyconverter

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import com.okellosoftwarez.currencyconverter.databinding.ActivityMainBinding
import com.okellosoftwarez.currencyconverter.main.mainViewModel
import com.okellosoftwarez.currencyconverter.util.resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding
    private val viewModel : mainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnConvert.setOnClickListener{
            viewModel.convert(
                binding.etText.text.toString(),
                binding.spFromCurrency.selectedItem.toString(),
                binding.spToCurrency.selectedItem.toString(),
            )
        }

        lifecycleScope.launchWhenStarted {
            viewModel.conversion.collect { event ->

                when (event) {

                    is mainViewModel.CurrencyEvent.Success -> {
                        binding.progressBar.isVisible = false
                        binding.conversion.setTextColor(Color.BLACK)
                        binding.conversion.text = event.resultText

                    }

                    is mainViewModel.CurrencyEvent.Failure -> {
                        binding.progressBar.isVisible = false
                        binding.conversion.setTextColor(Color.RED)
                        binding.conversion.text = event.errorText

                    }

                    is mainViewModel.CurrencyEvent.Loading -> {
                        binding.progressBar.isVisible = true

                    }

                    else -> Unit
                }
            }
        }
    }
}