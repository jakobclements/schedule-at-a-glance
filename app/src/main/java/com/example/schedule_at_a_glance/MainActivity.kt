package com.example.schedule_at_a_glance

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.schedule_at_a_glance.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // On button press
        binding.button.setOnClickListener {
            // check if task name and category have been entered
            if (binding.editTextTaskName.text.toString().isNotEmpty() &&
                    binding.spinnerCategory.selectedItemPosition > 0)
            {
                // Add the task

                // Default priority level to low if no selection is made
            }
            else
            {
                // Don't add the task
            }
        }

    }
}