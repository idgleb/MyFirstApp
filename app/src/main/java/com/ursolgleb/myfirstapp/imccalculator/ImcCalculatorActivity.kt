package com.ursolgleb.myfirstapp.imccalculator

import android.content.Intent
import android.icu.text.DecimalFormat
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.slider.RangeSlider
import com.ursolgleb.myfirstapp.R

class ImcCalculatorActivity : AppCompatActivity() {

    private var isMaleSelected = true
    private var isFamaleSelected = false
    private var currentWeight: Int = 60
    private var currentAge: Int = 30
    private var currentHeight: Int = 120

    private lateinit var viewMail: CardView
    private lateinit var viewFamail: CardView
    private lateinit var tvHeight: TextView
    private lateinit var rsHeight: RangeSlider
    private lateinit var tvWeight: TextView
    private lateinit var btnSubtractWeight: FloatingActionButton
    private lateinit var btnPlusWeight: FloatingActionButton
    private lateinit var tvAge: TextView
    private lateinit var btnSubstractAge: FloatingActionButton
    private lateinit var btnPlusAge: FloatingActionButton
    private lateinit var btnCalculate: Button

    companion object{
        const val IMC_KEY = "IMC_RESOLT"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_imc_calculator)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        initComponents()
        initUI()
        initListeners()

    }

    private fun initComponents() {
        viewMail = findViewById(R.id.viewMail)
        viewFamail = findViewById(R.id.viewFamail)
        tvHeight = findViewById(R.id.tvHeight)
        rsHeight = findViewById(R.id.rsHeight)
        tvWeight = findViewById(R.id.tvWeight)
        btnSubtractWeight = findViewById(R.id.btnSubtractWeight)
        btnPlusWeight = findViewById(R.id.btnPlusWeight)
        tvAge = findViewById(R.id.tvAge)
        btnSubstractAge = findViewById(R.id.btnSubstractAge)
        btnPlusAge = findViewById(R.id.btnPlusAge)
        btnCalculate = findViewById(R.id.btnCalculate)

    }

    private fun initUI() {
        setGenderColor()
        setWeight()
        setAge()
    }



    @RequiresApi(Build.VERSION_CODES.N)
    private fun initListeners() {
        viewMail.setOnClickListener {
            changeGender(true)
            setGenderColor()
        }
        viewFamail.setOnClickListener {
            changeGender(false)
            setGenderColor()
        }
        rsHeight.addOnChangeListener { _, value, _ ->
            currentHeight = value.toInt()
            tvHeight.text = getString(R.string.height_display, currentHeight.toString(), getString(R.string.sm))
        }
        btnSubtractWeight.setOnClickListener {
            currentWeight--
            setWeight()
        }
        btnPlusWeight.setOnClickListener {
            currentWeight++
            setWeight()
        }

        btnSubstractAge.setOnClickListener {
            currentAge--
            setAge()
        }
        btnPlusAge.setOnClickListener {
            currentAge++
            setAge()
        }
        btnCalculate.setOnClickListener {

            navigateToReslt(calculateIMC())
        }

    }

    private fun navigateToReslt(imc: Double) {
        val intent = Intent(this, ResoltIMCActivity::class.java)
        intent.putExtra(IMC_KEY, imc)
        startActivity(intent)

    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun calculateIMC(): Double {
        var imc = currentWeight / (currentHeight.toDouble() / 100 * currentHeight.toDouble() / 100)
        imc = DecimalFormat("#.##").format(imc).toDouble()
        return imc
    }

    private fun setWeight() {
        tvWeight.text = currentWeight.toString()
    }

    private fun setAge() {
        tvAge.text = currentAge.toString()
    }

    private fun changeGender(bool: Boolean) {
        isMaleSelected = bool
        isFamaleSelected = !isMaleSelected
    }

    private fun setGenderColor() {
        viewMail.setCardBackgroundColor(getGenderColor(isMaleSelected))
        viewFamail.setCardBackgroundColor(getGenderColor(isFamaleSelected))
    }

    private fun getGenderColor(isSelected: Boolean): Int {
        if (isSelected) return ContextCompat.getColor(this, R.color.background_component_selected)
        return ContextCompat.getColor(this, R.color.background_component)
    }


}