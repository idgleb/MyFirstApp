package com.ursolgleb.myfirstapp.todoapp

import android.content.res.ColorStateList
import android.graphics.Paint
import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.ursolgleb.myfirstapp.databinding.ItemTaskBinding

class TaskViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val bindTask = ItemTaskBinding.bind(view)

    fun render(task: Task) {

        bindTask.tvTask.text = task.name + " - " + task.idTask

        // Cambiar el color del CheckBox según la categoría
        bindTask.cbTask.buttonTintList = ColorStateList.valueOf(
            ContextCompat.getColor(
                bindTask.tvTask.context,
                task.category.color
            )
        )

        aplicarTachado(task)

    }

    private fun aplicarTachado(task: Task) {
        bindTask.cbTask.isChecked = task.isSelected
        if (task.isSelected) {
            bindTask.tvTask.paintFlags = bindTask.tvTask.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
        } else {
            bindTask.tvTask.paintFlags =
                bindTask.tvTask.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
        }
    }


}