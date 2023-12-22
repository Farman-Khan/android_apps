package com.fkhan.android_apps

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.PopupProperties
import com.fkhan.android_apps.ui.theme.Android_appsTheme

class MainActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            Android_appsTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val data = getItems()
                    CurrencyView(data)
                }
            }
        }
    }
}

//this method should return the actual data
fun getItems(): List<String> {
    val data = mutableListOf<String>()
    repeat(100) {
        data.add("Item - $it")
    }

    return data
}

@Composable
fun CurrencyView(data: List<String>) {
    val enteredValue = remember { mutableStateOf("") }

    Column(modifier = Modifier.padding(10.dp)) {
        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .border(),
            value = enteredValue.value, onValueChange = {
                enteredValue.value = it
            }
        )

        DropdownSelector(options = getItems())

        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
        ) {

            data.forEach {
                item {
                    Column(
                        modifier = Modifier
                            .height(100.dp)
                            .padding(10.dp)
                            .border(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally

                    ) {
                        Text(text = it)
                    }
                }
            }
        }

    }
}

@Composable
fun DropdownSelector(options: List<String>) {
    var expanded by remember { mutableStateOf(false) }
    var selectedIndex by remember { mutableIntStateOf(0) }

    Row(modifier = Modifier.padding(top = 5.dp, bottom = 10.dp))
    {
        Spacer(modifier = Modifier.weight(1f))
        Column(modifier = Modifier
            .width(150.dp)
            .height(40.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        )
        {

            AssistChip(
                modifier = Modifier.fillMaxSize(),
                onClick = { expanded = true},
                label = {  Text(text = options[selectedIndex]) },
                trailingIcon = {
                    Spacer(modifier = Modifier.weight(1f))
                    Icon(
                        Icons.Filled.ArrowDropDown,
                        contentDescription = "Dropdown",
                        Modifier.size(AssistChipDefaults.IconSize)
                    )
                }
            )

            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
            ) {
                options.forEachIndexed { index, s ->
                    DropdownMenuItem(
                        text = { Text(text = s) },
                        onClick = {
                            selectedIndex = index
                            expanded = false
                        }
                    )
                }
            }
        }
    }
}

fun Modifier.border() = this.border(width = 1.dp, color = Color.Gray, shape = RectangleShape)

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Android_appsTheme {
        CurrencyView(getItems())
    }
}