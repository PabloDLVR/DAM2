package com.example.calculadora

import android.R
import android.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.ArrayAdapter
import com.example.calculadora.databinding.ActivityMainBinding
import com.example.calculadora.databinding.DialogHistoryBinding
import kotlin.math.*

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var currentInput = StringBuilder()
    private var currentOperator = ""
    private var operand1 = 0.0
    private var isNewOperation = true
    private var hasDecimal = false

    private val operationHistory = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupHistoryButton()
        setupBasicCalculator()
        setupScientificCalculator()
        updateDisplay()
    }

    private fun setupBasicCalculator() {
        // Botones numéricos
        binding.btn0.setOnClickListener { appendNumber("0") }
        binding.btn1.setOnClickListener { appendNumber("1") }
        binding.btn2.setOnClickListener { appendNumber("2") }
        binding.btn3.setOnClickListener { appendNumber("3") }
        binding.btn4.setOnClickListener { appendNumber("4") }
        binding.btn5.setOnClickListener { appendNumber("5") }
        binding.btn6.setOnClickListener { appendNumber("6") }
        binding.btn7.setOnClickListener { appendNumber("7") }
        binding.btn8.setOnClickListener { appendNumber("8") }
        binding.btn9.setOnClickListener { appendNumber("9") }

        // Operadores básicos
        binding.btnAdd.setOnClickListener { setOperator("+") }
        binding.btnSubtract.setOnClickListener { setOperator("-") }
        binding.btnMultiply.setOnClickListener { setOperator("×") }
        binding.btnDivide.setOnClickListener { setOperator("/") }

        // Otros botones básicos
        binding.btnDecimal.setOnClickListener { addDecimal() }
        binding.btnEquals.setOnClickListener { calculateResult() }
        binding.btnClear.setOnClickListener { clearAll() }
        binding.btnDelete.setOnClickListener { deleteLast() }
    }

    private fun setupHistoryButton() {
        binding.btnHistory.setOnClickListener {
            showHistoryDialog()
        }
    }

    private fun addToHistory(operation: String) {
        operationHistory.add(0, operation)
        if (operationHistory.size > 50) {
            operationHistory.removeAt(operationHistory.size - 1)
        }
    }

    private fun showHistoryDialog() {
        val dialogBinding = DialogHistoryBinding.inflate(LayoutInflater.from(this))

        val dialog = AlertDialog.Builder(this)
            .setView(dialogBinding.root)
            .setPositiveButton("Cerrar", null)
            .create()

        val adapter = ArrayAdapter(this, R.layout.simple_list_item_1, operationHistory)
        dialogBinding.lvHistory.adapter = adapter

        dialogBinding.btnClearHistory.setOnClickListener {
            operationHistory.clear()
            adapter.notifyDataSetChanged()
        }

        dialogBinding.lvHistory.setOnItemClickListener { parent, view, position, id ->
            val selectedOperation = operationHistory[position]
            val result = selectedOperation.substringAfter("= ")
            if (result != "Error") {
                currentInput.clear()
                currentInput.append(result)
                updateDisplay()
                dialog.dismiss()
            }
        }

        dialog.show()
    }

    private fun setupScientificCalculator() {
        // Verificar si los botones científicos existen (solo en landscape)
        try {
            binding.btnSin?.setOnClickListener { scientificOperation("sin") }
            binding.btnCos?.setOnClickListener { scientificOperation("cos") }
            binding.btnTan?.setOnClickListener { scientificOperation("tan") }
            binding.btnLog?.setOnClickListener { scientificOperation("log") }
            binding.btnLn?.setOnClickListener { scientificOperation("ln") }
            binding.btnSqrt?.setOnClickListener { scientificOperation("sqrt") }
            binding.btnPower?.setOnClickListener { scientificOperation("power2") }
            binding.btnPowerY?.setOnClickListener { setOperator("^") }
            binding.btnPi?.setOnClickListener { appendNumber("3.14159265359") }
            binding.btnE?.setOnClickListener { appendNumber("2.71828182846") }
            binding.btnFactorial?.setOnClickListener { scientificOperation("factorial") }
            binding.btnParenthesis?.setOnClickListener { toggleParenthesis() }
        } catch (e: Exception) {
            // Los botones científicos no existen en portrait
        }
    }

    private fun appendNumber(number: String) {
        if (isNewOperation) {
            currentInput.clear()
            isNewOperation = false
        }
        currentInput.append(number)
        updateDisplay()
    }

    private fun setOperator(operator: String) {
        if (currentInput.isNotEmpty()) {
            operand1 = currentInput.toString().toDouble()
            currentOperator = operator
            currentInput.clear()
            hasDecimal = false
            updateDisplay()
        }
    }

    private fun addDecimal() {
        if (!hasDecimal) {
            if (currentInput.isEmpty() || isNewOperation) {
                currentInput.append("0")
            }
            currentInput.append(".")
            hasDecimal = true
            isNewOperation = false
            updateDisplay()
        }
    }

    private fun calculateResult() {
        if (currentInput.isNotEmpty() && currentOperator.isNotEmpty()) {
            val operand2 = currentInput.toString().toDouble()
            val result = when (currentOperator) {
                "+" -> operand1 + operand2
                "-" -> operand1 - operand2
                "×" -> operand1 * operand2
                "/" -> if (operand2 != 0.0) operand1 / operand2 else Double.NaN
                "^" -> operand1.pow(operand2)
                else -> operand2
            }

            currentInput.clear()
            if (result.isNaN()) {
                currentInput.append("Error")
            } else if (result == result.toLong().toDouble()) {
                currentInput.append(result.toLong().toString())
            } else {
                currentInput.append("%.6f".format(result).trimEnd('0').trimEnd('.'))
            }
            // Agregar al historial
            addToHistory("$operand2 = $result")
            currentOperator = ""
            isNewOperation = true
            hasDecimal = currentInput.toString().contains(".")
            updateDisplay()
        }

    }

    private fun scientificOperation(operation: String) {
        if (currentInput.isNotEmpty()) {
            val value = currentInput.toString().toDouble()
            val result = when (operation) {
                "sin" -> sin(Math.toRadians(value))
                "cos" -> cos(Math.toRadians(value))
                "tan" -> tan(Math.toRadians(value))
                "log" -> log10(value)
                "ln" -> ln(value)
                "sqrt" -> sqrt(value)
                "power2" -> value * value
                "factorial" -> factorial(value.toInt()).toDouble()
                else -> value
            }
            // Agregar al historial
            when (operation) {
                "power2" -> addToHistory("$value² = $result")
                "factorial" -> addToHistory("$value! = $result")
                else -> addToHistory("$operation($value) = $result")
            }

            currentInput.clear()
            if (result.isNaN() || result.isInfinite()) {
                currentInput.append("Error")
            } else if (result == result.toLong().toDouble()) {
                currentInput.append(result.toLong().toString())
            } else {
                currentInput.append("%.6f".format(result).trimEnd('0').trimEnd('.'))
            }

            isNewOperation = true
            hasDecimal = currentInput.toString().contains(".")
            updateDisplay()
        }

    }

    private fun factorial(n: Int): Long {
        return if (n <= 1) 1 else n * factorial(n - 1)
    }

    private fun toggleParenthesis() {
        if (currentInput.isEmpty() || isNewOperation) {
            currentInput.append("(")
        } else if (currentInput.last().isDigit()) {
            currentInput.append(")")
        } else {
            currentInput.append("(")
        }
        updateDisplay()
    }

    private fun clearAll() {
        currentInput.clear()
        currentOperator = ""
        operand1 = 0.0
        isNewOperation = true
        hasDecimal = false
        updateDisplay()
    }

    private fun deleteLast() {
        if (currentInput.length > 0) {
            if (currentInput.last() == '.') {
                hasDecimal = false
            }
            currentInput.deleteCharAt(currentInput.length - 1)
            updateDisplay()
        }
    }

    private fun updateDisplay() {
        // CORRECCIÓN: Usar un enfoque más seguro
        val displayText = if (currentInput.isEmpty()) "0" else currentInput.toString()
        binding.etDisplay.setText(displayText)
    }

    // Función de extensión para verificar si StringBuilder está vacío
    private fun StringBuilder.isEmpty(): Boolean {
        return this.length == 0
    }

    // Función de extensión para verificar si StringBuilder no está vacío
    private fun StringBuilder.isNotEmpty(): Boolean {
        return this.length > 0
    }
}