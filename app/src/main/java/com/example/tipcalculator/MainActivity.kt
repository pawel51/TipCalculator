package com.example.tipcalculator

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import com.example.tipcalculator.databinding.ActivityMainBinding
import java.text.NumberFormat
import kotlin.math.ceil

class MainActivity : AppCompatActivity() {




    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.calcButton.setOnClickListener { calculateTips(it) }
        binding.textInputEditText.setOnKeyListener { v, keyCode, _ ->  handleKeyEvent(v, keyCode)}
    }

    @SuppressLint("StringFormatInvalid")
    private fun calculateTips(view: View) {
        val cost = binding.textInputEditText.text.toString().toDoubleOrNull()

        if (cost == null){
            binding.tipAmount.text = ""
            return
        }

        val tipPercent = when(binding.radioGroup.checkedRadioButtonId){
            R.id.amazing_radioBtn -> 0.20
            R.id.good_radioBtn -> 0.18
            else -> 0.15
        }
        var tip = cost * tipPercent
        if(binding.roundUpSwitch.isChecked)
            tip = ceil(tip)

        val formattedTip = NumberFormat.getCurrencyInstance().format(tip)
        binding.tipAmount.text = getString(R.string.tip_amount, formattedTip)
        handleKeyEvent(view, KeyEvent.KEYCODE_ENTER )
    }

    private fun handleKeyEvent(view: View, keyCode: Int): Boolean {
        if (keyCode == KeyEvent.KEYCODE_ENTER) {
            // Hide the keyboard
            val inputMethodManager =
                getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
            return true
        }
        return false
    }
}