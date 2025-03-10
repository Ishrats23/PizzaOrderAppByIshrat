package week1.st991707736.pizzaorderappbyishrat

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun ResultScreen(navController: NavController, name: String, price: Double, deliveryFee: Double) {
    val total = price + deliveryFee

    Column(modifier = Modifier.padding(16.dp)) {
        Text(text = "RESULT", style = MaterialTheme.typography.headlineMedium)
        Text(text = "Client: $name")
        Text(text = "Order price: ${price}$")
        Text(text = "Delivery fee: ${deliveryFee}$")
        Text(text = "Total: ${total}$")

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
            Button(onClick = { navController.popBackStack() }) { Text("BACK") }
            Button(onClick = { System.exit(0) }) { Text("EXIT") }
        }
    }
}
