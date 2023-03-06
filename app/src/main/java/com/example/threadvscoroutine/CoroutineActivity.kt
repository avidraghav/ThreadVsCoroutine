package com.example.threadvscoroutine

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.threadvscoroutine.databinding.ActivityCoroutineBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

// Activity which contains a button to spawn 100_000 Coroutines
class CoroutineActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCoroutineBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityCoroutineBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.button.setOnClickListener {
            launchCoroutines()
        }
    }

    // Application won't crash here
    private fun launchCoroutines() {
        // create a separate coroutine to launch other coroutines
        // as not to block Main Thread
        CoroutineScope(Dispatchers.Default).launch {
            repeat(100_000) {
                launch {
                    delay(5000)
                    withContext(Dispatchers.Main) {
                        "coroutine number- $it".also { binding.textview.text = it }
                    }
                    Log.d(TAG, Thread.currentThread().name)
                }
            }
            Log.d(TAG, "finished")
        }
    }

    companion object {
        private const val TAG = "CoroutineActivity"
    }
}
