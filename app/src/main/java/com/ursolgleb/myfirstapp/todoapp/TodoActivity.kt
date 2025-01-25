package com.ursolgleb.myfirstapp.todoapp

import android.app.Dialog
import android.content.Context
import android.graphics.Rect
import android.os.Bundle
import android.os.SystemClock
import android.util.Log
import android.view.Gravity
import android.view.MotionEvent
import android.view.ViewTreeObserver
import android.view.WindowManager
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.ursolgleb.myfirstapp.R
import com.ursolgleb.myfirstapp.databinding.ActivityTodoBinding
import com.ursolgleb.myfirstapp.databinding.DialogTaskBinding
import com.ursolgleb.myfirstapp.todoapp.TaskCategory.*

class TodoActivity : AppCompatActivity() {
    private lateinit var todoBinding: ActivityTodoBinding

    private val categories = listOf(
        Business,
        Personal,
        Other
    )
    private lateinit var categoriesAdapter: CategoriesAdapter

    private val tasksList = mutableListOf(
        Task("PruebaBusiness", Business),
        Task("PruebaPersonal", Personal),
        Task("PruebaOther", Other, true)
    )

    private lateinit var tasksAdapter: TasksAdapter
    private lateinit var bindindDialog: DialogTaskBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        todoBinding = ActivityTodoBinding.inflate(layoutInflater)
        setContentView(todoBinding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        initUI()
        initListeners()
    }

    private fun initListeners() {
        todoBinding.fabAddTask.setOnClickListener {
            showDialog()
            showKeyBoard(bindindDialog)
        }

    }

    private fun showDialog() {
        var dialog = Dialog(this, R.style.DialogStyle)
        dialog.setContentView(R.layout.dialog_task)
        bindindDialog = DialogTaskBinding.bind(dialog.findViewById(R.id.cardTask))

        dialog.window?.apply {
            setGravity(Gravity.BOTTOM or Gravity.CENTER_HORIZONTAL)
        }

        dialog.show()

        ViewCompat.setOnApplyWindowInsetsListener(todoBinding.root) { _, insets ->
            val isKeyboardVisible = insets.isVisible(WindowInsetsCompat.Type.ime())
            if (isKeyboardVisible) {
                Log.d("Keyboard2", "Teclado abierto")
            } else {
                Log.d("Keyboard2", "Teclado cerrado")
                dialog.hide()
            }
            insets
        }


        bindindDialog.etTask.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                Log.d("Keyboard2", "Teclado cerrado por el botón Done")
                bindindDialog.btnTask.performClick()
                true // Indicar que manejaste el evento
            } else {
                false // Permitir el comportamiento predeterminado
            }
        }


        bindindDialog.btnTask.setOnClickListener {
            val currentTask = bindindDialog.etTask.text.toString()
            if (currentTask.isNotEmpty()) {
                val selectedButtonId = bindindDialog.rgCategories.checkedRadioButtonId

                val currentCategory: TaskCategory = when (selectedButtonId) {
                    R.id.rbBusiness -> Business
                    R.id.rbPersonal -> Personal
                    R.id.rbOther -> Other
                    else -> Business

                }
                tasksList.add(Task(currentTask, currentCategory))
                updateTask()
                todoBinding.rvTasks.smoothScrollToPosition(tasksList.size - 1)
                dialog.hide()
            } else {
                bindindDialog.etTask.error = getString(R.string.todo_no_puede_estar_vacio)
                showKeyBoard(bindindDialog)

            }
        }


    }

    private fun showKeyBoard(bindindDialog: DialogTaskBinding) {
        bindindDialog.etTask.requestFocus()
        val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(bindindDialog.etTask, InputMethodManager.SHOW_IMPLICIT)
        Log.d("Keyboard2", "Teclado abrrr---")
    }


    private fun initUI() {
        categories.get(0).isSelected = false

        categoriesAdapter = CategoriesAdapter(categories, ::onItemCategorySelected)
        todoBinding.rvCategories.adapter = categoriesAdapter
        todoBinding.rvCategories.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)


        var tasksFiltered = filtrarTasks()

        tasksAdapter = TasksAdapter(tasksFiltered.toMutableList(), ::onItemTaskSelected)

        todoBinding.rvTasks.adapter = tasksAdapter
        todoBinding.rvTasks.layoutManager =
            LinearLayoutManager(this)

    }

    private fun onItemCategorySelected(posicion: Int) {
        categories[posicion].isSelected = !categories[posicion].isSelected
        categoriesAdapter.notifyItemChanged(posicion)

        updateTask()

    }

    private fun onItemTaskSelected(idTask: Int) {
        Toast.makeText(this, "idTask: $idTask", Toast.LENGTH_SHORT).show()
        val posicion = tasksList.indexOfFirst { it.idTask == idTask }
        tasksList[posicion].isSelected = !tasksList[posicion].isSelected
        updateTask()
    }

    private fun updateTask() {
        var tasksFiltered = filtrarTasks()
        tasksAdapter.tasks = tasksFiltered.toMutableList()
        tasksAdapter.notifyDataSetChanged()
    }

    private fun filtrarTasks(): List<Task> {
        var categoriesFiltered = categories.filter { it.isSelected }
        var tasksFiltered = tasksList.filter { it.category in categoriesFiltered }
        return tasksFiltered
    }


}


