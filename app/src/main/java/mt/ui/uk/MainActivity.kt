package mt.ui.uk

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import mt.ui.theme.CalculatorTheme
import java.text.DecimalFormat

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CalculatorTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    CalculatorScreen()
                }
            }
        }
    }
}

@Composable
fun CalculatorScreen() {
    var display by remember { mutableStateOf("0") }
    var currentNumber by remember { mutableStateOf("") }
    var operation by remember { mutableStateOf("") }
    var previousNumber by remember { mutableStateOf("") }
    var shouldResetDisplay by remember { mutableStateOf(false) }

    fun handleNumber(number: String) {
        if (shouldResetDisplay) {
            display = number
            currentNumber = number
            shouldResetDisplay = false
        } else {
            if (display == "0") {
                display = number
                currentNumber = number
            } else {
                display += number
                currentNumber += number
            }
        }
    }

    fun calculate() {
        if (previousNumber.isNotEmpty() && currentNumber.isNotEmpty() && operation.isNotEmpty()) {
            val prev = previousNumber.toDoubleOrNull() ?: 0.0
            val curr = currentNumber.toDoubleOrNull() ?: 0.0

            val result = when (operation) {
                "+" -> prev + curr
                "-" -> prev - curr
                "×" -> prev * curr
                "÷" -> if (curr != 0.0) prev / curr else 0.0
                else -> curr
            }

            val formatter = DecimalFormat("#.##########")
            display = formatter.format(result)
            currentNumber = display
            previousNumber = ""
            operation = ""
            shouldResetDisplay = true
        }
    }

    fun handleOperation(op: String) {
        if (currentNumber.isNotEmpty() && previousNumber.isNotEmpty() && operation.isNotEmpty()) {
            calculate()
        }
        operation = op
        previousNumber = currentNumber
        currentNumber = ""
        shouldResetDisplay = true
    }

    fun clear() {
        display = "0"
        currentNumber = ""
        previousNumber = ""
        operation = ""
        shouldResetDisplay = false
    }

    fun handleDecimal() {
        if (shouldResetDisplay) {
            display = "0."
            currentNumber = "0."
            shouldResetDisplay = false
        } else if (!currentNumber.contains(".")) {
            display += "."
            currentNumber += "."
        }
    }

    fun handleBackspace() {
        if (display.length > 1) {
            display = display.dropLast(1)
            currentNumber = currentNumber.dropLast(1)
        } else {
            display = "0"
            currentNumber = ""
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp)
    ) {
        // Display
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.3f),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface
            ),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp),
                contentAlignment = Alignment.CenterEnd
            ) {
                Text(
                    text = display,
                    fontSize = 48.sp,
                    fontWeight = FontWeight.Light,
                    color = MaterialTheme.colorScheme.onSurface,
                    textAlign = TextAlign.End,
                    maxLines = 1
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Button Grid
        Column(
            modifier = Modifier.weight(0.7f),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Row 1: Clear, Backspace, ÷
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                CalculatorButton(
                    text = "C",
                    modifier = Modifier.weight(1f),
                    backgroundColor = MaterialTheme.colorScheme.error,
                    textColor = MaterialTheme.colorScheme.onError,
                    onClick = { clear() }
                )
                CalculatorButton(
                    text = "⌫",
                    modifier = Modifier.weight(1f),
                    backgroundColor = MaterialTheme.colorScheme.secondary,
                    textColor = MaterialTheme.colorScheme.onSecondary,
                    onClick = { handleBackspace() }
                )
                CalculatorButton(
                    text = "÷",
                    modifier = Modifier.weight(1f),
                    backgroundColor = MaterialTheme.colorScheme.primary,
                    textColor = MaterialTheme.colorScheme.onPrimary,
                    onClick = { handleOperation("÷") }
                )
            }

            // Row 2: 7, 8, 9, ×
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                CalculatorButton(
                    text = "7",
                    modifier = Modifier.weight(1f),
                    onClick = { handleNumber("7") }
                )
                CalculatorButton(
                    text = "8",
                    modifier = Modifier.weight(1f),
                    onClick = { handleNumber("8") }
                )
                CalculatorButton(
                    text = "9",
                    modifier = Modifier.weight(1f),
                    onClick = { handleNumber("9") }
                )
                CalculatorButton(
                    text = "×",
                    modifier = Modifier.weight(1f),
                    backgroundColor = MaterialTheme.colorScheme.primary,
                    textColor = MaterialTheme.colorScheme.onPrimary,
                    onClick = { handleOperation("×") }
                )
            }

            // Row 3: 4, 5, 6, -
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                CalculatorButton(
                    text = "4",
                    modifier = Modifier.weight(1f),
                    onClick = { handleNumber("4") }
                )
                CalculatorButton(
                    text = "5",
                    modifier = Modifier.weight(1f),
                    onClick = { handleNumber("5") }
                )
                CalculatorButton(
                    text = "6",
                    modifier = Modifier.weight(1f),
                    onClick = { handleNumber("6") }
                )
                CalculatorButton(
                    text = "-",
                    modifier = Modifier.weight(1f),
                    backgroundColor = MaterialTheme.colorScheme.primary,
                    textColor = MaterialTheme.colorScheme.onPrimary,
                    onClick = { handleOperation("-") }
                )
            }

            // Row 4: 1, 2, 3, +
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                CalculatorButton(
                    text = "1",
                    modifier = Modifier.weight(1f),
                    onClick = { handleNumber("1") }
                )
                CalculatorButton(
                    text = "2",
                    modifier = Modifier.weight(1f),
                    onClick = { handleNumber("2") }
                )
                CalculatorButton(
                    text = "3",
                    modifier = Modifier.weight(1f),
                    onClick = { handleNumber("3") }
                )
                CalculatorButton(
                    text = "+",
                    modifier = Modifier.weight(1f),
                    backgroundColor = MaterialTheme.colorScheme.primary,
                    textColor = MaterialTheme.colorScheme.onPrimary,
                    onClick = { handleOperation("+") }
                )
            }

            // Row 5: 0, ., =
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                CalculatorButton(
                    text = "0",
                    modifier = Modifier.weight(2f),
                    onClick = { handleNumber("0") }
                )
                CalculatorButton(
                    text = ".",
                    modifier = Modifier.weight(1f),
                    onClick = { handleDecimal() }
                )
                CalculatorButton(
                    text = "=",
                    modifier = Modifier.weight(1f),
                    backgroundColor = MaterialTheme.colorScheme.tertiary,
                    textColor = MaterialTheme.colorScheme.onTertiary,
                    onClick = { calculate() }
                )
            }
        }
    }
}

@Composable
fun CalculatorButton(
    text: String,
    modifier: Modifier = Modifier,
    backgroundColor: Color = MaterialTheme.colorScheme.surfaceVariant,
    textColor: Color = MaterialTheme.colorScheme.onSurfaceVariant,
    onClick: () -> Unit
) {
    Card(
        modifier = modifier
            .aspectRatio(if (text == "0") 2f else 1f)
            .clickable { onClick() },
        colors = CardDefaults.cardColors(
            containerColor = backgroundColor
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        shape = RoundedCornerShape(16.dp)
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = text,
                fontSize = 24.sp,
                fontWeight = FontWeight.Medium,
                color = textColor
            )
        }
    }
}