package com.ursolgleb.myfirstapp.todoapp

import com.ursolgleb.myfirstapp.R

sealed class TaskCategory(var isSelected: Boolean = true) {
    abstract val color: Int
    abstract val name: CharSequence?

    object Alta : TaskCategory(){
        override val color: Int
            get() = R.color.todo_business_category
        override val name: CharSequence
            get() = "Alta"
    }
    object Media : TaskCategory(){
        override val color: Int
            get() = R.color.todo_personal_category
        override val name: CharSequence
            get() = "Media"
    }
    object Baja : TaskCategory(){
        override val color: Int
            get() = R.color.todo_other_category
        override val name: CharSequence
            get() = "Baja"

    }
}