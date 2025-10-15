package ca.gbc.comp3074_101508910.assignment1

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ca.gbc.comp3074_101508910.assignment1.ui.theme.Assignment1Theme
import ca.gbc.comp3074_101508910.assignment1.AboutActivity


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Assignment1Theme {
                MainScreen(
                    onAboutClick = {
                        startActivity(Intent(this, AboutActivity::class.java))
                    }
                )
            }
        }
    }
}

@Composable
fun MainScreen(onAboutClick: () -> Unit) {
    var hours by remember { mutableStateOf(TextFieldValue("")) }
    var rate by remember { mutableStateOf(TextFieldValue("")) }
    var taxRate by remember { mutableStateOf(TextFieldValue("")) }

    var pay by remember { mutableStateOf(0.0) }
    var overtimePay by remember { mutableStateOf(0.0) }
    var totalPay by remember { mutableStateOf(0.0) }
    var tax by remember { mutableStateOf(0.0) }

    Scaffold { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            // ✅ Custom Header Bar
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.primary)
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Pay Calculator",
                    color = MaterialTheme.colorScheme.onPrimary,
                    style = MaterialTheme.typography.headlineSmall,
                    modifier = Modifier.weight(1f)
                )
                IconButton(onClick = onAboutClick) {
                    Icon(
                        imageVector = Icons.Default.Info,
                        contentDescription = "About",
                        tint = MaterialTheme.colorScheme.onPrimary
                    )
                }
            }

            // ✅ Main content
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                OutlinedTextField(
                    value = hours,
                    onValueChange = { hours = it },
                    label = { Text("Hours Worked") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = rate,
                    onValueChange = { rate = it },
                    label = { Text("Hourly Rate") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = taxRate,
                    onValueChange = { taxRate = it },
                    label = { Text("Tax Rate (e.g. 0.15)") },
                    modifier = Modifier.fillMaxWidth()
                )

                Button(
                    onClick = {
                        val h = hours.text.toDoubleOrNull() ?: 0.0
                        val r = rate.text.toDoubleOrNull() ?: 0.0
                        val t = taxRate.text.toDoubleOrNull() ?: 0.0

                        if (h <= 40) {
                            pay = h * r
                            overtimePay = 0.0
                        } else {
                            pay = 40 * r
                            overtimePay = (h - 40) * r * 1.5
                        }

                        totalPay = pay + overtimePay
                        tax = pay * t
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Calculate Pay")
                }

                Divider()

                Text("Pay: $%.2f".format(pay))
                Text("Overtime Pay: $%.2f".format(overtimePay))
                Text("Total Pay: $%.2f".format(totalPay))
                Text("Tax: $%.2f".format(tax))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewMainScreen() {
    Assignment1Theme {
        MainScreen(onAboutClick = {})
    }
}
