package com.ursolgleb.myfirstapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.ursolgleb.myfirstapp.databinding.ActivityMenuBinding
import com.ursolgleb.myfirstapp.firstapp.primerapp.FirstAppActiviti
import com.ursolgleb.myfirstapp.imccalculator.ImcCalculatorActivity
import com.ursolgleb.myfirstapp.todoapp.TodoActivity

class MenuActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMenuBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.btnSaludApp.setOnClickListener { navigateToSaludApp() }
        binding.btnIMCApp.setOnClickListener { navigateToIMCApp() }
        binding.btnTODO.setOnClickListener { navigateToTODOApp() }

    }

    private fun navigateToTODOApp() {
        val intent = Intent(this, TodoActivity::class.java)
        startActivity(intent)
    }

    private fun navigateToIMCApp() {
        val intent = Intent(this, ImcCalculatorActivity::class.java)
        startActivity(intent)

    }

    private fun navigateToSaludApp(){
        val intent = Intent(this, FirstAppActiviti::class.java)
        startActivity(intent)
    }


}