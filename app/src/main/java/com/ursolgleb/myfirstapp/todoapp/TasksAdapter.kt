package com.ursolgleb.myfirstapp.todoapp

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.ursolgleb.myfirstapp.R

class TasksAdapter(
    var tasks: MutableList<Task>,
    private val onTaskSelected: (Int) -> Unit
) :
    RecyclerView.Adapter<TaskViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_task, parent, false)
        return TaskViewHolder(view)
    }

    override fun getItemCount() = tasks.size

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {

        holder.render(tasks[position])

        holder.itemView.setOnClickListener {
            onTaskSelected(tasks[position].idTask)
        }
        holder.itemView.findViewById<CheckBox>(R.id.cbTask).setOnClickListener {
            onTaskSelected(tasks[position].idTask)
        }
    }


}
