package com.ursolgleb.myfirstapp.firstapp.primerapp

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.ursolgleb.myfirstapp.R


class FirstAppActiviti : AppCompatActivity() {


    lateinit var but2: AppCompatButton
    lateinit var edText: AppCompatEditText


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_first_app_activiti)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        edText = findViewById(R.id.edText)

        but2 = findViewById<AppCompatButton>(R.id.but2)

        but2.setOnClickListener {
            if (edText.text.toString().isNotEmpty()){
                val intent = Intent(this, ResaltActivity::class.java)
                intent.putExtra("CLAVE", edText.text.toString())
                startActivity(intent)
            }

        }


    }

}