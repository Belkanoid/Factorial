package com.belkanoid.factorial

import android.opengl.Visibility
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.belkanoid.factorial.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class MainActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private val viewModel by lazy {
        ViewModelProvider(this)[ViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.buttonCalculate.setOnClickListener {
            viewModel.calculate(binding.editTextNumber.text.toString())
        }

        viewModel.state.onEach {
            binding.progressBarLoading.visibility = View.GONE
            binding.buttonCalculate.isEnabled = true

            when(it) {
                is State.Factorial -> {
                    binding.textView.text = it.value
                }
                is State.Loading -> {
                    binding.progressBarLoading.visibility = View.VISIBLE
                    binding.buttonCalculate.isEnabled = false
                }
                is State.Error -> {
                    Snackbar.make(this, binding.root, it.message, Snackbar.LENGTH_SHORT).show()
                }
                is State.Empty -> Unit
            }
        }.launchIn(lifecycleScope)
    }
}