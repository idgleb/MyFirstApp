package com.ursolgleb.myfirstapp.todoapp

import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.ursolgleb.myfirstapp.R
import com.ursolgleb.myfirstapp.databinding.ItemTaskCategoryBinding

class CategoriesViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val binding = ItemTaskCategoryBinding.bind(view)

    fun render(taskCategory: TaskCategory, onItemSelected: (Int) -> Unit) {

        binding.tvCategoryName.text = taskCategory.name

        binding.vDivider.setBackgroundColor(
            ContextCompat.getColor(
                binding.vDivider.context,
                taskCategory.color
            )
        )

        var color = ContextCompat.getColor(
            binding.cardTask.context,
            if (taskCategory.isSelected) R.color.todo_background_card else R.color.todo_background_disabled
        )
        binding.cardTask.setCardBackgroundColor(color)

        itemView.setOnClickListener {
            onItemSelected(layoutPosition)
        }

    }


}