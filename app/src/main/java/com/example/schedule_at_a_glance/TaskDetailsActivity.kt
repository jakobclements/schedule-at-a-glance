package com.example.schedule_at_a_glance

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.schedule_at_a_glance.databinding.ActivityTaskDetailsBinding

class TaskDetailsActivity : AppCompatActivity() {
    private lateinit var binding : ActivityTaskDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTaskDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.textViewTaskName.text = intent.getStringExtra("taskName")
        binding.textViewDueDate.text = intent.getStringExtra("dueDate")
        binding.textViewCategory.text = intent.getStringExtra("category")
        binding.textViewImportance.text = intent.getStringExtra("importance")

    }
}