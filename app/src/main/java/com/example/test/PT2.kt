package com.example.test

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlin.math.sqrt

class PT2 : AppCompatActivity() {

    private lateinit var heSoA: EditText
    private lateinit var heSoB: EditText
    private lateinit var heSoC: EditText
    private lateinit var resultTextView: TextView
    private lateinit var solveButton: Button

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.edit_text)

        // Ánh xạ view
        heSoA = findViewById(R.id.he_so_a)
        heSoB = findViewById(R.id.he_so_b)
        heSoC = findViewById(R.id.he_so_c)
        resultTextView = findViewById(R.id.result)
        solveButton = findViewById(R.id.button_process)

        solveButton.setOnClickListener {
            giaiPhuongTrinh()
        }
    }

    private fun giaiPhuongTrinh() {
        val aStr = heSoA.text.toString()
        val bStr = heSoB.text.toString()
        val cStr = heSoC.text.toString()

        if (aStr.isBlank() || bStr.isBlank() || cStr.isBlank()) {
            resultTextView.text = "Vui lòng nhập đầy đủ hệ số a, b, c."
            return
        }

        try {
            val a = aStr.toDouble()
            val b = bStr.toDouble()
            val c = cStr.toDouble()

            if (a == 0.0) {
                // Phương trình bậc nhất bx + c = 0
                if (b == 0.0) {
                    resultTextView.text = if (c == 0.0) "Phương trình vô số nghiệm." else "Phương trình vô nghiệm."
                } else {
                    val x = -c / b
                    resultTextView.text = "Phương trình bậc nhất, nghiệm x = $x"
                }
                return
            }

            val delta = b * b - 4 * a * c

            val result = when {
                delta < 0 -> "Phương trình vô nghiệm."
                delta == 0.0 -> {
                    val x = -b / (2 * a)
                    "Phương trình có nghiệm kép: \nx1 = x2 = $x"
                }
                else -> {
                    val sqrtDelta = sqrt(delta)
                    val x1 = (-b + sqrtDelta) / (2 * a)
                    val x2 = (-b - sqrtDelta) / (2 * a)
                    "Phương trình có 2 nghiệm phân biệt:\nx1 = $x1\nx2 = $x2"
                }
            }

            resultTextView.text = result

        } catch (e: NumberFormatException) {
            resultTextView.text = "Vui lòng nhập đúng định dạng số."
        }
    }
}
