package com.ursolgleb.myfirstapp.todoapp

data class Task(
    val name: String,
    val category: TaskCategory,
    var isSelected: Boolean = false,
    val idTask: Int = nextId()
) {
    companion object {
        private var lastId = 0

        // Generar un nuevo ID automáticamente
        private fun nextId(): Int {
            lastId++
            return lastId
        }
    }
}
