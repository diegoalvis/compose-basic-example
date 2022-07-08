package com.diegoalvis.composeexample

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.Red
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.diegoalvis.composeexample.ui.theme.ComposeExampleTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeExampleTheme {
                // A surface container using the 'background' color from the theme
                Scaffold {
                    Column {
                        WaterCounter()
                        TodoList(
                            Modifier.padding(all = 16.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun WaterCounter(modifier: Modifier = Modifier) {
    var count by rememberSaveable { mutableStateOf(0) }
    Column(
        modifier = modifier,
    ) {
        Text(text = "You have drunk $count glasses of water")
        Spacer(modifier = Modifier.padding(vertical = 8.dp))
        Button(onClick = {
            count++
        }) {
            Text(text = "Add glass of water")
        }
    }
}


@Composable
fun TodoList(modifier: Modifier = Modifier) {
    val todos = remember { mutableStateListOf<ItemTask>() }
    Column(modifier = modifier) {
        LazyColumn {
            items(todos) { item ->
                ItemTaskWidget(item, onRemoved = {
                    todos.remove(item)
                })
            }
        }
        Button(
            onClick = {
                todos.add(ItemTask(name = "Task #${todos.size + 1}"))
            },
            enabled = todos.size < 10,
        ) {
            Text(text = "Add new task")
        }
    }
}

@Composable
private fun ItemTaskWidget(
    item_: ItemTask,
    onRemoved: () -> Unit,
) {
    var item by remember { mutableStateOf(item_.isCompleted) }
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = item_.name)
        Checkbox(
            checked = item,
            onCheckedChange = {
                item = it
            },
        )
        IconButton(onClick = { onRemoved() }) {
            Icon(
                Icons.Filled.Close,
                contentDescription = "Localized description",
                tint = Red
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ComposeExampleTheme {
        TodoList()
    }
}


data class ItemTask(
    val name: String,
    var isCompleted: Boolean = false
)