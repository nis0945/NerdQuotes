package com.eposro.apps.nerdquotes.presentation

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.eposro.apps.nerdquotes.R
import com.eposro.apps.nerdquotes.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()
    private val bottomSheetAdapter = RecentQuotesAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            btnNextQuote.setOnClickListener { viewModel.loadQuote() }
        }
        binding.bottomSheet.rvRecentQuotes.adapter = bottomSheetAdapter
        binding.bottomSheet.rvRecentQuotes.layoutManager = LinearLayoutManager(this@MainActivity)

        viewModel.uiState.observe(this) { state ->
            if (state.hasError) {
                Snackbar.make(binding.root, state.error!!, Snackbar.LENGTH_INDEFINITE).show()
            } else {
                if (!state.isLoading) viewModel.recentQuotes.last().let {
                    binding.tvQuote.text = it.en
                    binding.tvAuthor.text = getString(
                        R.string.author_with_name,
                        it.author
                    )
                    bottomSheetAdapter.submitList(viewModel.recentQuotes)
                } else {
                    binding.tvQuote.text = getString(R.string.loading_txt)
                    binding.tvAuthor.text =
                        getString(R.string.author_with_name, getString(R.string.loading_txt))
                }
                binding.btnNextQuote.isEnabled = !state.isLoading
            }
        }
    }
}