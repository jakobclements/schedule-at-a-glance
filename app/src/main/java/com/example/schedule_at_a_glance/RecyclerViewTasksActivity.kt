package com.example.schedule_at_a_glance

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.example.schedule_at_a_glance.databinding.ActivityRecyclerViewTasksBinding
import com.google.firebase.auth.FirebaseAuth

class RecyclerViewTasksActivity : AppCompatActivity() {

    private lateinit var binding : ActivityRecyclerViewTasksBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRecyclerViewTasksBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val viewModel : ViewTasksViewModel by viewModels()
        viewModel.getTasks().observe(this, { tasks ->
            var recyclerViewAdapter = RecyclerViewAdapter(this, tasks)
            binding.viewTasksRecyclerView.adapter = recyclerViewAdapter
        })

    }
}