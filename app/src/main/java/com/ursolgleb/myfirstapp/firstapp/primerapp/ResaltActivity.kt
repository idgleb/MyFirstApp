package com.ursolgleb.myfirstapp.firstapp.primerapp

import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.ursolgleb.myfirstapp.R

class ResaltActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_resalt)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val texView = findViewById<TextView>(R.id.txtView)

        val name: String = intent.extras?.getString("CLAVE").orEmpty()

        val name2 = intent.getStringExtra("CLAVE").orEmpty()

        texView.text = "Hola $name2"


    }
}