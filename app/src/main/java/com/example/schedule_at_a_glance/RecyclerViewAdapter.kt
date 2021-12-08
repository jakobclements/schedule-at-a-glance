package com.example.schedule_at_a_glance

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth

class RecyclerViewAdapter (val context : Context,
                           val tasks : List<Task>) : RecyclerView.Adapter<RecyclerViewAdapter.TaskViewHolder>() {

    // Gets Task objects from item_task.xml
    inner class TaskViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        val textViewTaskName = itemView.findViewById<TextView>(R.id.textViewTaskName)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_task, parent, false)
        return TaskViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: TaskViewHolder, position: Int) {
        val task = tasks[position]
        val currentUser = FirebaseAuth.getInstance().getUid()
        if (task.owner == currentUser) {
            viewHolder.textViewTaskName.text = task.taskName
        }
    }

    override fun getItemCount(): Int {
        return tasks.size
    }
}