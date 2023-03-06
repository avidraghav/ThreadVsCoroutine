package com.example.threadvscoroutine

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.threadvscoroutine.databinding.ActivityThreadBinding

// Activity which contains a button to spawn 100_000 Threads
class ThreadActivity : AppCompatActivity() {

    private lateinit var binding: ActivityThreadBinding
    private val handler: Handler = object : Handler(Looper.getMainLooper()) {
        override fun handleMessage(message: Message) {
            super.handleMessage(message)
            "thread number - ${message.obj}".also { binding.textview.text = it }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityThreadBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        // Application will crash
        binding.button.setOnClickListener {
            val thread = ExampleThread()
            thread.start()
        }
    }

    // I am using another thread to start 100,000 threads
    // as so the UI can be updated.
    inner class ExampleThread : Thread() {
        override fun run() {
            startThreads()
        }
    }

    // creates and starts 100,000 threads
    private fun startThreads() {
        repeat(100_000) {
            val thread = Thread {
                Thread.sleep(5000)
                Log.d(TAG, Thread.currentThread().name)
                val message = Message.obtain()
                message.obj = it
                handler.sendMessage(message)
            }
            thread.start()
        }
    }

    companion object {
        private const val TAG = "ThreadActivity"
    }
}
