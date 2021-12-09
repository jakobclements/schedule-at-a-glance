package com.example.schedule_at_a_glance

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import com.example.schedule_at_a_glance.databinding.ActivityTaskDetailsBinding
import com.google.firebase.firestore.FirebaseFirestore

class TaskDetailsActivity : AppCompatActivity() {
    private lateinit var binding : ActivityTaskDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTaskDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Get task info from intent
        binding.textViewTaskName.text = intent.getStringExtra("taskName")
        binding.textViewDueDate.text = intent.getStringExtra("dueDate")
        binding.textViewCategory.text = intent.getStringExtra("category")
        binding.textViewImportance.text = intent.getStringExtra("importance")

        // Return to the main schedule if the return button is clicked
        binding.buttonReturnToSchedule.setOnClickListener{
            startActivity(Intent(this, ViewTasksRecyclerActivity::class.java))
        }

        // Delete the task if delete button is clicked then redirect to main schedule
        binding.buttonDelete.setOnClickListener{

            val builder = AlertDialog.Builder(this)
            builder
                .setMessage("Delete forever?")
                .setCancelable(true)
                .setPositiveButton("Delete") { dialog, id ->
                    // DELETE
                    val db = FirebaseFirestore.getInstance()
                    db
                        .collection("tasks")
                        .document(intent.getStringExtra("taskId").toString())
                        .delete()
                        .addOnSuccessListener { Log.d("DB Response", "Success! Document was deleted.") }
                        .addOnFailureListener { e -> Log.w("DB Response", "Error deleting document", e) }
                    Log.i("DB Response", "Task was deleted")

                    // Redirect to main schedule and display toast
                    val intent = Intent(this, ViewTasksRecyclerActivity::class.java)
                    intent.putExtra("toast", "Task Deleted")
                    startActivity(intent)
                }
                .setNegativeButton("Cancel") { dialog, id ->
                    Log.i("DB Response", "Task was not deleted")
                }
            val alert = builder.create()
            alert.show()
        }
    }
}