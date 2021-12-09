package com.example.schedule_at_a_glance

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import com.example.schedule_at_a_glance.databinding.ActivityCreateTaskBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.util.*

class CreateTaskActivity : AppCompatActivity() {

    private lateinit var binding : ActivityCreateTaskBinding

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateTaskBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Listen for create task button click
        binding.buttonCreateTask.setOnClickListener {

            val currentTime = Calendar.getInstance()
            val dueDateTime = Calendar.getInstance()
            val dueDateYear = binding.datePickerDate.year
            val dueDateMonth = binding.datePickerDate.month
            val dueDateDay = binding.datePickerDate.dayOfMonth
            dueDateTime.set(dueDateYear, dueDateMonth, dueDateDay, 23, 59, 59)

            // If due date is in past notify user and don't create task
            if (dueDateTime < currentTime)
            {
                Toast.makeText(this, "Due date has already past", Toast.LENGTH_LONG).show()
            }
            // check if task name and category have been entered
            else if (binding.editTextTaskName.text.toString().isNotEmpty() &&
                    binding.spinnerCategory.selectedItemPosition > 0)
            {
                // Add the task
                val task = Task()
                task.taskName = binding.editTextTaskName.text.toString()
                task.category = binding.spinnerCategory.selectedItem.toString()

                // Default importance level to low if no selection is made (pos 0)
                if (binding.spinnerImportance.selectedItemPosition == 0) {
                    task.importance = binding.spinnerImportance.getItemAtPosition(1).toString()
                }
                else {
                    task.importance = binding.spinnerImportance.selectedItem.toString()
                }

                // Date formatting
                val dueDateYear = binding.datePickerDate.year
                val dueDateMonth = binding.datePickerDate.month
                val dueDateDay = binding.datePickerDate.dayOfMonth
                val calendar = Calendar.getInstance()
                calendar.set(dueDateYear, dueDateMonth, dueDateDay, 23, 59, 59)
                Log.d("Selected time", calendar.time.toString())
                task.dueDate = calendar.time

                // Set owner
                val currentUser = FirebaseAuth.getInstance().getUid()
                Log.d("Current User", currentUser.toString())
                task.owner = currentUser

                // Get a unique task id from FireStore
                val db = FirebaseFirestore.getInstance().collection("tasks")
                task.id = db.document().id

                // Create record
                db.document(task.id!!).set(task)

                // Redirect back to ViewTasksRecyclerActivity and show toast
                val intent = Intent(this, ViewTasksRecyclerActivity::class.java)
                intent.putExtra("toast", "Task Added to Schedule")
                startActivity(intent)
            }
            else
            {
                // Don't add the task, notify user
                Toast.makeText(this, "Name and Category are required fields", Toast.LENGTH_LONG).show()
            }
        }

        // Listen for cancel button click
        binding.buttonCancel.setOnClickListener{
            // Warn about data loss
            val builder = AlertDialog.Builder(this)
            builder
                .setMessage("Cancel task creation? Info entered will be lost")
                .setCancelable(true)
                .setPositiveButton("Yes I want to cancel") { dialog, id ->

                    // Redirect to main schedule and display toast
                    val intent = Intent(this, ViewTasksRecyclerActivity::class.java)
                    intent.putExtra("toast", "Draft deleted")
                    startActivity(intent)
                }
                .setNegativeButton("No, continue with task") { dialog, id ->
                    // Do nothing
                }
            val alert = builder.create()
            alert.show()
        }
    }
}