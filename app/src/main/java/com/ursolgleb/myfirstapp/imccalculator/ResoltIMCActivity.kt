package com.ursolgleb.myfirstapp.imccalculator

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.ursolgleb.myfirstapp.R
import com.ursolgleb.myfirstapp.imccalculator.ImcCalculatorActivity.Companion.IMC_KEY

class ResoltIMCActivity : AppCompatActivity() {

    private lateinit var tvResult:TextView
    private lateinit var tvICM:TextView
    private lateinit var tvDiscript:TextView
    private lateinit var btnRecalc:Button
    private var imc: Double = -1.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_resolt_imcactivity)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        initComponents()
        initUI()
        initListeners()
    }



    private fun initUI() {
        setResultYDiscript()
        setICM()

    }

    private fun initListeners() {
        btnRecalc.setOnClickListener {
            val intent = Intent(this, ImcCalculatorActivity::class.java)
            startActivity(intent)
        }

    }

    private fun setICM() {
        tvICM.text = imc.toString()
    }

    private fun setResultYDiscript() {
        if (imc<18.5){
            tvResult.text = getString(R.string.underweight)
            tvDiscript.text = getString(R.string.underweight_advise)
        }
        if (imc>=18.5 && imc<=24.9){
            tvResult.text = getString(R.string.normal_weight)
            tvDiscript.text = getString(R.string.normal_weight_advise)
        }
        if (imc>24.9 && imc<=29.9){
            tvResult.text = getString(R.string.overweight)
            tvDiscript.text = getString(R.string.overweight_advise)
        }
        if (imc>29.9 && imc<=34.9){
            tvResult.text = getString(R.string.grade_1_obesity)
            tvDiscript.text = getString(R.string.grade_1_obesity_advise)
        }
        if (imc>34.9 && imc<=39.9){
            tvResult.text = getString(R.string.grade_2_obesity)
            tvDiscript.text = getString(R.string.grade_2_obesity_advise)
        }
        if (imc>39.9){
            tvResult.text = getString(R.string.grade_3_obesity_extrema)
            tvDiscript.text = getString(R.string.grade_3_obesity_extrema_advise)
        }
    }

    private fun initComponents() {
        imc = intent.extras?.getDouble(IMC_KEY) ?: -1.0
        tvResult = findViewById(R.id.tvResult)
        tvICM = findViewById(R.id.tvICM)
        tvDiscript = findViewById(R.id.tvDiscript)
        btnRecalc = findViewById(R.id.btnRecalc)
    }
}