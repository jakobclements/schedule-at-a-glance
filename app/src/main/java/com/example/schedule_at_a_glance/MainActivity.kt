package com.example.schedule_at_a_glance

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.annotation.RequiresApi
import com.example.schedule_at_a_glance.databinding.ActivityMainBinding
import com.google.firebase.firestore.FirebaseFirestore
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // On button press
        binding.buttonCreateTask.setOnClickListener {
            // check if task name and category have been entered
            if (binding.editTextTaskName.text.toString().isNotEmpty() &&
                    binding.spinnerCategory.selectedItemPosition > 0)
            {
                // Add the task
                val task = Task()
                task.taskName = binding.editTextTaskName.text.toString()
                task.category = binding.spinnerCategory.selectedItem.toString()
                // Default importance level to low if no selection is made (pos 0)
                if (binding.spinnerImportance.selectedItemPosition == 0)
                {
                    task.importance = binding.spinnerImportance.getItemAtPosition(1).toString()
                }
                else
                {
                    task.importance = binding.spinnerImportance.selectedItem.toString()
                }

                // Date formatting
                val dueDateYear = binding.datePickerDate.year
                val dueDateMonth = binding.datePickerDate.month
                val dueDateDay = binding.datePickerDate.dayOfMonth
                val calendar = Calendar.getInstance()
                calendar.set(dueDateYear, dueDateMonth, dueDateDay)
                Log.d("Selected time", calendar.time.toString())
                task.dueDate = calendar.time

                // Get id from FireStore
                val db = FirebaseFirestore.getInstance().collection("tasks")
                task.id = db.document().id

                // Create record
                db.document(task.id!!).set(task)


            }
            else
            {
                // Don't add the task
            }
        }

    }
}