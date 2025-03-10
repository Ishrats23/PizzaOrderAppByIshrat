package week1.st991707736.pizzaorderappbyishrat

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable
fun AppNavigation(navController: NavHostController) {
    NavHost(navController, startDestination = "home") {
        composable("home") { HomeScreen(navController) }
        composable("order/{name}/{phone}") { backStackEntry ->
            val name = backStackEntry.arguments?.getString("name") ?: ""
            val phone = backStackEntry.arguments?.getString("phone") ?: ""
            OrderScreen(navController, name, phone)
        }
        composable("result/{name}/{price}/{deliveryFee}") { backStackEntry ->
            val name = backStackEntry.arguments?.getString("name") ?: ""
            val price = backStackEntry.arguments?.getString("price")?.toDoubleOrNull() ?: 0.0
            val deliveryFee = backStackEntry.arguments?.getString("deliveryFee")?.toDoubleOrNull() ?: 0.0
            ResultScreen(navController, name, price, deliveryFee)
        }
    }
}
