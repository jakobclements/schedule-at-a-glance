package com.example.schedule_at_a_glance

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import com.example.schedule_at_a_glance.databinding.ActivityScheduleBinding
import com.google.firebase.firestore.FirebaseFirestore

class ScheduleActivity : AppCompatActivity() {

    private lateinit var binding : ActivityScheduleBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityScheduleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Connect to db
        val db = FirebaseFirestore.getInstance().collection("tasks")

        // Query db
        val query = db.get().addOnSuccessListener { documents ->

            // Iterate through records
            for (document in documents)
            {
                Log.d("DB Response", "${document.data}")

                val task = document.toObject(Task::class.java)

                val textViewTasks = TextView(this)
                textViewTasks.text = task.taskName

                binding.linearLayout.addView(textViewTasks)
            }
        }
    }
}