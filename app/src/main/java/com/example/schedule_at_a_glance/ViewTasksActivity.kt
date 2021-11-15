package com.example.schedule_at_a_glance

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import com.example.schedule_at_a_glance.databinding.ActivityViewTasksBinding
import com.firebase.ui.auth.AuthUI
import com.google.firebase.firestore.FirebaseFirestore

class ViewTasksActivity : AppCompatActivity() {

    private lateinit var binding : ActivityViewTasksBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityViewTasksBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Connect to db
        val db = FirebaseFirestore.getInstance().collection("tasks").orderBy("dueDate")

        // Query db
        val query = db.get().addOnSuccessListener { documents ->

            // Iterate through records
            for (document in documents)
            {
                Log.d("DB Response", "${document.data}")

                // Get task object from db
                val task = document.toObject(Task::class.java)

                // Init textViews to display task info
                // TODO: Make this better with RecyclerView

                // Task name
                val textViewTaskName = TextView(this)
                textViewTaskName.text = task.taskName
                textViewTaskName.textSize = 20f

                // Due Date
                val textViewDueDate = TextView(this)
                // Temporary date formatting
                // TODO: make this not terrible
                val dueDateYear = task.dueDate?.year?.plus(1900).toString()
                val dueDateMonth = task.dueDate?.month?.plus(1).toString()
                val dueDateDay = task.dueDate?.date.toString()
                textViewDueDate.text = "$dueDateYear-$dueDateMonth-$dueDateDay"
                textViewDueDate.textSize = 16f

                // Category
                val textViewCategory = TextView(this)
                textViewCategory.text = task.category
                textViewCategory.textSize = 16f

                // Importance
                val textViewImportance = TextView(this)
                textViewImportance.text = task.importance
                textViewImportance.textSize = 16f

                // Temporary formatting lol :~)
                val textViewWhiteSpace = TextView(this)
                textViewWhiteSpace.textSize = 8f

                // Display task info textViews inside linear layout
                binding.linearLayout.addView(textViewTaskName)
                binding.linearLayout.addView(textViewDueDate)
                binding.linearLayout.addView(textViewCategory)
                binding.linearLayout.addView(textViewImportance)
                binding.linearLayout.addView(textViewWhiteSpace)

            }
        }

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
}