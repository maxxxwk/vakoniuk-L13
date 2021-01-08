package com.maxxxwk.hometasklection13

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.maxxxwk.hometasklection13.databinding.ActivityMainBinding
import java.util.concurrent.Executors

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val waitingExecutor = Executors.newSingleThreadExecutor()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupBinding()
        setupListeners()
    }

    private fun setupBinding() {
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    private fun setupListeners() {
        binding.btnWait.setOnClickListener {
            startProgress()
            waitingExecutor.submit {
                val waitingTime = getWaitingTime()
                runOnUiThread {
                    Toast.makeText(this, "Time: $waitingTime ms", Toast.LENGTH_SHORT).show()
                }
                Thread.sleep(waitingTime)
                runOnUiThread {
                    stopProgress()
                    incrementNumber()
                }
            }

        }
    }

    private fun startProgress() {
        binding.llContainer.visibility = View.GONE
        binding.pbWaiting.visibility = View.VISIBLE
    }

    private fun stopProgress() {
        binding.llContainer.visibility = View.VISIBLE
        binding.pbWaiting.visibility = View.GONE
    }

    private fun getWaitingTime() = (binding.tvNumber.text.toString().toLong() + 1) * 100 // (n + 1) / 10 * 1000

    private fun incrementNumber() {
        binding.tvNumber.text = "${binding.tvNumber.text.toString().toLong() + 1}"
    }
}