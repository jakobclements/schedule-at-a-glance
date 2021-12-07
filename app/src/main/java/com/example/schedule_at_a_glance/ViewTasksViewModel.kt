package com.example.schedule_at_a_glance

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

class ViewTasksViewModel : ViewModel() {

    // Mutable list to store Task objects
    private val tasks = MutableLiveData<List<Task>>()

    // Init viewmodel
    init{
        loadTasks()
    }

    // Loads Task objects from FireStore
    private fun loadTasks()
    {
        // Connect to db, init query
        val db = FirebaseFirestore.getInstance().collection("tasks")
            .orderBy("dueDate", Query.Direction.ASCENDING)

        // Create snapshot listener for tasks
        db.addSnapshotListener{ documents, e ->
            // Check for exception and log if found
            e?.let {
                Log.w("DB Response", "Listen failed.", e)
                return@addSnapshotListener
            }

            // Snapshot listener connected successfully
            Log.d("DB Response", "Current data: ${documents?.size()} elements found")

            // ArrayList to hold Task objects from DB and populate MutableLiveData list
            val taskList = ArrayList<Task>()

            // Check if there are documents
            documents?.let{
                // Iterate through the documents
                for (document in documents)
                {
                    try {
                        // Get Task object from db
                        val task = document.toObject(Task::class.java)
                        // Add task object to ArrayList
                        taskList.add(task)
                    }catch(e : Exception)
                    {
                        Log.e("Error", "Exception Thrown", e)
                    }
                }
            }
            // Populate MutableLiveData List
            tasks.value = taskList
        }
    }

    // Returns MutableLiveData List of tasks
    fun getTasks() : LiveData<List<Task>>
    {
        return tasks
    }

}