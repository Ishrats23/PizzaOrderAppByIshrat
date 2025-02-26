package week1.st991707736.pizzaorderappbyishrat

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch // Correct import
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import week1.st991707736.pizzaorderappbyishrat.ui.theme.PizzaOrderAppByIshratTheme

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PizzaOrderAppByIshratTheme {
                Scaffold(topBar = {
                    TopAppBar(
                        title = { Text("Ishrat Vohra  991707736", style = MaterialTheme.typography.headlineSmall) },
                        colors = TopAppBarDefaults.topAppBarColors(
                            containerColor = MaterialTheme.colorScheme.primary,
                            titleContentColor = MaterialTheme.colorScheme.onPrimary
                        )
                    )
                }, modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                    ) {
                        PizzaOrderLayout(
                            modifier = Modifier.padding(innerPadding))
                    }
                }
            }
        }
    }
}

@Composable
fun PizzaOrderLayout(modifier: Modifier = Modifier) {
    val sizeOptions = listOf("Small($9)", "Medium($10)", "Large($11)")
    val (selectedSize, onSizeSelected) = rememberSaveable { mutableStateOf(sizeOptions[0]) }
    var isMeatSelected by rememberSaveable { mutableStateOf(false) }
    var isCheeseSelected by rememberSaveable { mutableStateOf(false) }
    var isVeggiesSelected by rememberSaveable { mutableStateOf(false) }
    var isDeliveryRequired by rememberSaveable { mutableStateOf(false) }
    var showOrderSummary by rememberSaveable { mutableStateOf(false) }
    var showHelpInfo by rememberSaveable { mutableStateOf(false) }

    val totalCost = calculateTotalCost(selectedSize, isMeatSelected, isCheeseSelected, isVeggiesSelected, isDeliveryRequired)

    Column(
        modifier = Modifier
            .statusBarsPadding()
            .padding(horizontal = 40.dp)
            .verticalScroll(rememberScrollState())
            .safeDrawingPadding(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.pizza),
            contentDescription = stringResource(R.string.pizza_image_description),
            contentScale = ContentScale.FillHeight,
            modifier = Modifier
                .fillMaxWidth()
                .height(375.dp)
                .padding(bottom = 25.dp)
        )

        Row(
            horizontalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text("Select Size:")
                SizeRadioGroup(
                    options = sizeOptions,
                    selectedOption = selectedSize,
                    onOptionSelected = onSizeSelected
                )
            }

            Column(modifier = Modifier.weight(1f)) {
                Text("Select Toppings:")
                ToppingOption(
                    modifier = Modifier,
                    text = "Meat($2)",
                    checked = isMeatSelected,
                    onCheckedChange = { isMeatSelected = it }
                )
                ToppingOption(
                    modifier = Modifier,
                    text = "Cheese($2)",
                    checked = isCheeseSelected,
                    onCheckedChange = { isCheeseSelected = it }
                )
                ToppingOption(
                    modifier = Modifier,
                    text = "Veggies($2)",
                    checked = isVeggiesSelected,
                    onCheckedChange = { isVeggiesSelected = it }
                )
            }
        }

        DeliveryOption(
            isDeliveryRequired = isDeliveryRequired,
            onDeliveryRequiredChange = { isDeliveryRequired = it },
            modifier = Modifier.padding(top = 10.dp)
        )

        Text(
            text = "Price: $$totalCost",
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.padding(top = 16.dp)
        )
        if (isDeliveryRequired) {
            Text(
                text = "Delivery Required",
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(top = 8.dp)
            )
        }

        Row(
            modifier = Modifier.padding(top = 20.dp),
            horizontalArrangement = Arrangement.spacedBy(20.dp)
        ) {

            Button(onClick = { showOrderSummary = true }) {
                Text("Order")
            }

            Button(onClick = { showHelpInfo = true }) {
                Text("Help")
            }
        }


        if (showOrderSummary) {
            AlertDialog(
                onDismissRequest = { showOrderSummary = false },
                title = { Text("Order Summary") },
                text = {
                    Column {
                        Text("Size: $selectedSize")
                        if (isMeatSelected) Text("Topping: Meat ($2)")
                        if (isCheeseSelected) Text("Topping: Cheese ($2)")
                        if (isVeggiesSelected) Text("Topping: Veggies ($2)")
                        Text("Delivery: ${if (isDeliveryRequired) "Required" else "Not Required"}")
                        Text("Total Price: $$totalCost")
                    }
                },
                confirmButton = {
                    Button(onClick = {
                        showOrderSummary = false
                        onSizeSelected(sizeOptions[0])
                        isMeatSelected = false
                        isCheeseSelected = false
                        isVeggiesSelected = false
                        isDeliveryRequired = false
                    }) {
                        Text("OK")
                    }
                }
            )
        }

        
        if (showHelpInfo) {
            AlertDialog(
                onDismissRequest = { showHelpInfo = false },
                title = { Text("Pizza Recommendations") },
                text = {
                    Column {
                        Text("Not sure what to order? Try these popular choices:")
                        Spacer(modifier = Modifier.height(8.dp))
                        Text("1. Margherita - Classic and cheesy!")
                        Spacer(modifier = Modifier.height(4.dp))
                        Text("2. Pepperoni - A crowd favorite!")
                        Spacer(modifier = Modifier.height(4.dp))
                        Text("3. Veggie Delight - Fresh and healthy!")
                        Spacer(modifier = Modifier.height(4.dp))
                        Text("4. BBQ Chicken - Smoky and delicious!")
                    }
                },
                confirmButton = {
                    Button(onClick = { showHelpInfo = false }) {
                        Text("Got It!")
                    }
                }
            )
        }
    }
}

@Composable
fun SizeRadioGroup(
    options: List<String>,
    selectedOption: String,
    onOptionSelected: (String) -> Unit
) {
    Column {
        options.forEach { text ->
            Row(
                Modifier
                    .fillMaxWidth()
                    .size(50.dp)
                    .selectable(
                        selected = (text == selectedOption),
                        onClick = { onOptionSelected(text) }
                    ),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    selected = (text == selectedOption),
                    onClick = { onOptionSelected(text) }
                )
                Text(
                    text = text,
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(start = 5.dp)
                )
            }
        }
    }
}

@Composable
fun ToppingOption(modifier: Modifier, text: String, checked: Boolean, onCheckedChange: (Boolean) -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.primaryContainer)
    ) {
        Checkbox(
            checked = checked,
            onCheckedChange = onCheckedChange
        )
        Text("$text")
    }
}

@Composable
fun DeliveryOption(
    isDeliveryRequired: Boolean,
    onDeliveryRequiredChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = stringResource(R.string.delivery_required))
        Switch(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentWidth(Alignment.End),
            checked = isDeliveryRequired,
            onCheckedChange = onDeliveryRequiredChange
        )
    }
}

private fun calculateTotalCost(
    selectedSize: String,
    isMeatSelected: Boolean,
    isCheeseSelected: Boolean,
    isVeggiesSelected: Boolean,
    isDeliveryRequired: Boolean
): Int {
    val basePrice = when (selectedSize) {
        "Small($9)" -> 9
        "Medium($10)" -> 10
        "Large($11)" -> 11
        else -> 0
    }
    val toppingsPrice =
        (if (isMeatSelected) 2 else 0) +
                (if (isCheeseSelected) 2 else 0) +
                (if (isVeggiesSelected) 2 else 0)
    val deliveryPrice = if (isDeliveryRequired) 5 else 0

    return (basePrice + toppingsPrice + deliveryPrice)
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    PizzaOrderAppByIshratTheme {
        PizzaOrderLayout()
    }
}