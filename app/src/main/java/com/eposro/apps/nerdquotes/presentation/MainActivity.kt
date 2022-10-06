package com.eposro.apps.nerdquotes.presentation

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.eposro.apps.nerdquotes.R
import com.eposro.apps.nerdquotes.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.loadQuote()

        viewModel.uiState.observe(this) { state ->
            if (state.hasError) {
                Snackbar.make(binding.root, state.error!!, Snackbar.LENGTH_INDEFINITE).show()
            } else {
                binding.tvQuote.text =
                    if (state.isLoading) getString(R.string.loading_txt) else viewModel.quote
                binding.btnNextQuote.isEnabled = !state.isLoading
            }
        }

        binding.apply {
            btnNextQuote.setOnClickListener { viewModel.loadQuote() }
        }
    }

}