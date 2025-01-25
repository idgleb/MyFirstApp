package com.ursolgleb.myfirstapp.todoapp

import com.ursolgleb.myfirstapp.R

sealed class TaskCategory(var isSelected: Boolean = true) {
    abstract val color: Int
    abstract val name: CharSequence?

    object Business : TaskCategory(){
        override val color: Int
            get() = R.color.todo_business_category
        override val name: CharSequence
            get() = "Negocios"
    }
    object Personal : TaskCategory(){
        override val color: Int
            get() = R.color.todo_personal_category
        override val name: CharSequence
            get() = "Personal"
    }
    object Other : TaskCategory(){
        override val color: Int
            get() = R.color.todo_other_category
        override val name: CharSequence
            get() = "Otros"

    }
}