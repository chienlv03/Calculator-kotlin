package com.example.test

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var operationTextView: TextView
    private lateinit var resultTextView: TextView
    private var currentNumber = "0"
    private var savedNumber = ""
    private var currentOperation = ""
    private var isNewOperation = true
    private var operationHistory = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        operationTextView = findViewById(R.id.operation)
        resultTextView = findViewById(R.id.result)

        setNumberButtonListeners()
        setFunctionButtonListeners()
    }

    private fun setNumberButtonListeners() {
        val numberButtonIds = intArrayOf(
            R.id.button0, R.id.button1, R.id.button2, R.id.button3,
            R.id.button4, R.id.button5, R.id.button6, R.id.button7,
            R.id.button8, R.id.button9, R.id.buttonDot
        )

        for (buttonId in numberButtonIds) {
            findViewById<Button>(buttonId).setOnClickListener(this)
        }
    }

    private fun setFunctionButtonListeners() {
        val functionButtonIds = intArrayOf(
            R.id.buttonAC, R.id.buttonC, R.id.buttonBS, R.id.buttonDevide, R.id.buttonEqual,
            R.id.buttonAdd, R.id.buttonSubtract, R.id.buttonMultiply, R.id.buttonPlusMinus,
        )

        for (buttonId in functionButtonIds) {
            findViewById<Button>(buttonId).setOnClickListener(this)
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.button0, R.id.button1, R.id.button2, R.id.button3, R.id.button4,
            R.id.button5, R.id.button6, R.id.button7, R.id.button8, R.id.button9 -> {
                // Lấy văn bản của nút số và xử ly
                onNumberButtonClick((v as Button).text.toString())
            }

            R.id.buttonDot -> onDotButtonClick()


            R.id.buttonAdd, R.id.buttonSubtract, R.id.buttonMultiply, R.id.buttonDevide -> {
                onOperatorButtonClick((v as Button).text.toString())
            }

            R.id.buttonEqual -> onEqualButtonClick()

            R.id.buttonAC -> onAllClearButtonClick()

            R.id.buttonC -> onClearButtonClick()

            R.id.buttonBS -> onBackspaceButtonClick()

            R.id.buttonPlusMinus -> onPlusMinusButtonClick()

        }
        updateDisplay()
    }

    private fun onNumberButtonClick(digit: String) {
        if (isNewOperation) {
            if (operationHistory.contains("=")) {
                // Reset hoàn toàn nếu là phép tính mới sau khi có kết quả
                currentNumber = digit
                operationHistory = ""
                savedNumber = ""
                currentOperation = ""
            } else {
                // Nếu đang trong phép tính (đã có toán tử)
                currentNumber = digit
            }
            isNewOperation = false
        } else {
            // Tiếp tục nhập số bình thường
            if (currentNumber == "0") {
                currentNumber = digit
            } else {
                currentNumber += digit
            }
        }
        updateDisplay()
    }

    private fun onDotButtonClick() {
        if (isNewOperation) {
            currentNumber = "0."
            isNewOperation = false
        } else if (!currentNumber.contains(".")) {
            currentNumber += "."

        }
    }

    private fun onOperatorButtonClick(operator: String) {
        if (operationHistory.contains("=")) {
            // Nếu đã hoàn thành phép tính trước đó, sử dụng kết quả làm số đầu tiên
            operationHistory = "$currentNumber $operator "
            savedNumber = currentNumber
            currentOperation = operator
            currentNumber = savedNumber ///
            isNewOperation = true
        } else {
            // Xử lý bình thường
            if (savedNumber.isNotEmpty() && !isNewOperation) {
                calculateResult()
            }

            operationHistory = if (savedNumber.isEmpty()) {
                "$currentNumber $operator "
            } else {
                "$operationHistory$currentNumber $operator "
            }

            savedNumber = currentNumber
            currentOperation = operator
            currentNumber = savedNumber ///
            isNewOperation = true
        }
    }

    private fun onEqualButtonClick() {
        if (savedNumber.isNotEmpty() && !isNewOperation) {
            operationHistory = "$operationHistory$currentNumber = "
            calculateResult()
//            operationHistory = ""
            savedNumber = ""
            currentOperation = ""
            isNewOperation = true
        }
    }

    private fun onAllClearButtonClick() {
        currentNumber = "0"
        savedNumber = ""
        currentOperation = ""
        operationHistory = ""
        isNewOperation = true
    }

    private fun onClearButtonClick() {
        currentNumber = "0"
        isNewOperation = true
    }

    private fun onBackspaceButtonClick() {
        currentNumber = if (currentNumber.length > 1) {
            currentNumber.substring(0, currentNumber.length - 1)
        } else {
            "0"
        }
    }

    private fun onPlusMinusButtonClick() {
        if (currentNumber != "0") {
            currentNumber = if (currentNumber.startsWith("-")) {
                currentNumber.substring(1)
            } else {
                "-$currentNumber"
            }
        }
    }

    private fun calculateResult() {
        val num1 = savedNumber.toDouble()
        val num2 = currentNumber.toDouble()
        val result: Double = when (currentOperation) {
            "+" -> num1 + num2
            "-" -> num1 - num2
            "x" -> num1 * num2
            "/" -> {
                if (num2 != 0.0) {
                    num1 / num2
                } else {
                    currentNumber = "Error"
                    return
                }
            }
            else -> 0.0
        }

        currentNumber = if (result == result.toLong().toDouble()) {
            result.toLong().toString()
        } else {
            result.toString()
        }
    }

    private fun updateDisplay() {
        // Hiển thị phép tính đang thực hiện
        operationTextView.text = operationHistory

        // Hiển thị số hiện tại hoặc kết quả
        resultTextView.text = currentNumber
    }

}