package com.example.schedule_at_a_glance

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth

class ViewTasksRecyclerViewAdapter (val context : Context,
                                    val tasks : List<Task>,
                                    val itemListener: TaskItemListener) : RecyclerView.Adapter<ViewTasksRecyclerViewAdapter.TaskViewHolder>() {

    // Gets Task objects from item_task.xml
    inner class TaskViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        val textViewTaskName = itemView.findViewById<TextView>(R.id.textViewTaskName)
        val textViewDueDate = itemView.findViewById<TextView>(R.id.textViewDueDate)
    }

    // Connects to a ViewHolder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_task, parent, false)
        return TaskViewHolder(view)
    }

    // Binds a Task object to the ViewHolder (1 task obj to each tile in list)
    override fun onBindViewHolder(viewHolder: TaskViewHolder, position: Int) {
        val task = tasks[position]
        val currentUser = FirebaseAuth.getInstance().getUid()

        if (task.owner == currentUser) {

            viewHolder.textViewTaskName.text = task.taskName

            val dueDateYear = task.dueDate?.year?.plus(1900).toString()
            val dueDateMonth = task.dueDate?.month?.plus(1).toString()
            val dueDateDay = task.dueDate?.date.toString()
            viewHolder.textViewDueDate.text = "$dueDateYear-$dueDateMonth-$dueDateDay"
//            viewHolder.textViewDueDate.text = task.dueDate.toString()
        }

        viewHolder.itemView.setOnClickListener {
            itemListener.taskSelected(task)
        }
    }

    override fun getItemCount(): Int {
        return tasks.size
    }

    interface TaskItemListener {
        fun taskSelected(task : Task)
    }
}