package com.example.schedule_at_a_glance

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import com.example.schedule_at_a_glance.databinding.ActivityViewTasksRecyclerBinding
import com.firebase.ui.auth.AuthUI

class ViewTasksRecyclerActivity : AppCompatActivity(), ViewTasksRecyclerViewAdapter.TaskItemListener {

    private lateinit var binding : ActivityViewTasksRecyclerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityViewTasksRecyclerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val viewModel : ViewTasksViewModel by viewModels()
        viewModel.getTasks().observe(this, { tasks ->
            var recyclerViewAdapter = ViewTasksRecyclerViewAdapter(this, tasks, this)
            binding.viewTasksRecyclerView.adapter = recyclerViewAdapter
        })

        // Add task button
        binding.fabCreateTask.setOnClickListener{
            startActivity(Intent(this, CreateTaskActivity::class.java))
        }

        // Logout button
        binding.buttonLogout.setOnClickListener{
            AuthUI.getInstance()
                .signOut(this)
                .addOnCompleteListener {
                    startActivity(Intent(this, LoginActivity::class.java))
                }
        }

        // Options button
        binding.buttonOptions.setOnClickListener{
            Toast.makeText(this, "No options yet - check back soon!", Toast.LENGTH_SHORT).show()
        }
    }

    override fun taskSelected(task: Task) {
        val intent = Intent(this, TaskDetailsActivity::class.java)
        intent.putExtra("taskId", task.id)
        intent.putExtra("taskName", task.taskName)
        intent.putExtra("dueDate", task.dueDate.toString())
        intent.putExtra("category", task.category)
        intent.putExtra("importance", task.importance)
        startActivity(intent)
    }
}