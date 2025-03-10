package week1.st991707736.pizzaorderappbyishrat

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicText
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun OrderScreen(navController: NavController, name: String, phone: String) {
    var pizzaPrice by remember { mutableStateOf(9.0) }
    var toppingsPrice by remember { mutableStateOf(0.0) }
    var deliveryFee by remember { mutableStateOf(0.0) }

    Column(modifier = Modifier.padding(16.dp)) {
        Text(text = "ORDER", style = MaterialTheme.typography.headlineMedium)
        Text(text = "Client: $name, $phone")

        // Pizza Size Selection
        Text("Select the size:")
        Row {
            RadioButton(selected = pizzaPrice == 9.0, onClick = { pizzaPrice = 9.0 })
            BasicText("Small - 9$")
        }
        Row {
            RadioButton(selected = pizzaPrice == 10.0, onClick = { pizzaPrice = 10.0 })
            BasicText("Medium - 10$")
        }
        Row {
            RadioButton(selected = pizzaPrice == 11.0, onClick = { pizzaPrice = 11.0 })
            BasicText("Large - 11$")
        }

        // Toppings Selection
        Text("Select toppings:")
        Row {
            Checkbox(checked = toppingsPrice >= 2.0, onCheckedChange = { if (it) toppingsPrice += 2.0 else toppingsPrice -= 2.0 })
            BasicText("Meat - 2$")
        }
        Row {
            Checkbox(checked = toppingsPrice >= 4.0, onCheckedChange = { if (it) toppingsPrice += 2.0 else toppingsPrice -= 2.0 })
            BasicText("Cheese - 2$")
        }
        Row {
            Checkbox(checked = toppingsPrice >= 6.0, onCheckedChange = { if (it) toppingsPrice += 2.0 else toppingsPrice -= 2.0 })
            BasicText("Veggies - 2$")
        }

        // Delivery Selection
        Row {
            Checkbox(checked = deliveryFee == 5.5, onCheckedChange = { if (it) deliveryFee = 5.5 else deliveryFee = 0.0 })
            BasicText("Delivery required - 5.5$")
        }

        // Navigation Buttons
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
            Button(onClick = { navController.popBackStack() }) { Text("BACK") }
            Button(onClick = {
                val totalPrice = pizzaPrice + toppingsPrice
                navController.navigate("result/$name/$totalPrice/$deliveryFee")
            }) {
                Text("NEXT")
            }
        }
    }
}
