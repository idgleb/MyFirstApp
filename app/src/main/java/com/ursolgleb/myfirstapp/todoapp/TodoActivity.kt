package com.ursolgleb.myfirstapp.todoapp

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.ViewTreeObserver
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
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
    private lateinit var todoBind: ActivityTodoBinding
    private lateinit var dialogBind: DialogTaskBinding
    private lateinit var tasksAdapter: TasksAdapter
    private lateinit var categoriesAdapter: CategoriesAdapter

    private val categories = listOf(
        Alta,
        Media,
        Baja
    )

    private val tasksList = mutableListOf(
        Task("Comprar leche", Alta),
        Task("Reparar bicicleta", Media),
        Task("Llamar al dentista", Baja, true)
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        todoBind = ActivityTodoBinding.inflate(layoutInflater)
        setContentView(todoBind.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        initUI()
        initListeners()
    }

    private fun initListeners() {
        todoBind.fabAddTask.setOnClickListener {
            showDialog()
            showKeyBoard(dialogBind)
        }
    }

    private fun showDialog() {
        var dialog = Dialog(this, R.style.DialogStyle)
        dialog.setContentView(R.layout.dialog_task)
        dialogBind = DialogTaskBinding.bind(dialog.findViewById(R.id.cardTask))

        dialog.window?.apply {
            setGravity(Gravity.BOTTOM or Gravity.CENTER_HORIZONTAL)
        }

        dialog.show()

        val globalLayoutListener = ViewTreeObserver.OnGlobalLayoutListener {
            showKeyBoard(dialogBind)
        }
        dialog.window?.decorView?.viewTreeObserver?.addOnGlobalLayoutListener(globalLayoutListener)

        ViewCompat.setOnApplyWindowInsetsListener(todoBind.root) { _, insets ->
            val isKeyboardVisible = insets.isVisible(WindowInsetsCompat.Type.ime())
            if (isKeyboardVisible) {
                Log.d("Keyboard2", "Teclado abierto")
                dialog.window?.decorView?.viewTreeObserver?.removeOnGlobalLayoutListener(globalLayoutListener)
            } else {
                Log.d("Keyboard2", "Teclado cerrado")
            }
            insets
        }

        dialogBind.etTask.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                Log.d("Keyboard2", "Teclado cerrado por el botón Done")
                dialogBind.btnTask.performClick()
                true // Indicar que manejaste el evento
            } else {
                false // Permitir el comportamiento predeterminado
            }
        }

        dialogBind.btnTask.setOnClickListener {
            val currentTask = dialogBind.etTask.text.toString()
            if (currentTask.isNotEmpty()) {
                val selectedButtonId = dialogBind.rgCategories.checkedRadioButtonId
                val currentCategory: TaskCategory = when (selectedButtonId) {
                    R.id.rbBusiness -> Alta
                    R.id.rbPersonal -> Media
                    R.id.rbOther -> Baja
                    else -> Alta
                }
                tasksList.add(Task(currentTask, currentCategory))
                updateTask()
                todoBind.rvTasks.smoothScrollToPosition(tasksList.size - 1)
                dialog.hide()
            } else {
                dialogBind.etTask.error = getString(R.string.todo_no_puede_estar_vacio)
                showKeyBoard(dialogBind)
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
        categoriesAdapter = CategoriesAdapter(categories, ::onItemCategorySelected)
        todoBind.rvCategories.adapter = categoriesAdapter
        todoBind.rvCategories.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        var tasksFiltered = filtrarTasks()

        tasksAdapter = TasksAdapter(tasksFiltered.toMutableList(), ::onItemTaskSelected)

        todoBind.rvTasks.adapter = tasksAdapter
        todoBind.rvTasks.layoutManager =
            LinearLayoutManager(this)

    }

    private fun onItemCategorySelected(posicion: Int) {
        categories[posicion].isSelected = !categories[posicion].isSelected
        categoriesAdapter.notifyItemChanged(posicion)
        updateTask()
    }

    private fun onItemTaskSelected(idTask: Int) {
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


