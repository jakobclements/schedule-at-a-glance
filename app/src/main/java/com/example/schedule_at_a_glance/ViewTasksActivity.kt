package com.example.schedule_at_a_glance

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.core.view.marginTop
import com.example.schedule_at_a_glance.databinding.ActivityViewTasksBinding
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

                val task = document.toObject(Task::class.java)

                val textViewTaskName = TextView(this)
                textViewTaskName.text = task.taskName
                textViewTaskName.textSize = 20f

                val textViewDueDate = TextView(this)
                val dueDateYear = task.dueDate?.year?.plus(1900).toString()
                val dueDateMonth = task.dueDate?.month?.plus(1).toString()
                val dueDateDay = task.dueDate?.date.toString()
                textViewDueDate.text = "$dueDateYear-$dueDateMonth-$dueDateDay"
                textViewDueDate.textSize = 16f

                val textViewCategory = TextView(this)
                textViewCategory.text = task.category
                textViewCategory.textSize = 16f

                val textViewImportance = TextView(this)
                textViewImportance.text = task.importance
                textViewImportance.textSize = 16f

                val textViewWhiteSpace = TextView(this)
                textViewWhiteSpace.textSize = 8f

                binding.linearLayout.addView(textViewTaskName)
                binding.linearLayout.addView(textViewDueDate)
                binding.linearLayout.addView(textViewCategory)
                binding.linearLayout.addView(textViewImportance)
                binding.linearLayout.addView(textViewWhiteSpace)

            }
        }

        binding.fabCreateTask.setOnClickListener{
            startActivity(Intent(this, CreateTaskActivity::class.java))
        }

    }
}