package com.example.list_dnd

import android.R
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.list_dnd.ui.theme.List_DnDTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            List_DnDTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "kroShot",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {

    var word by rememberSaveable { mutableStateOf("Не нажал") }

    Column(modifier = modifier)
    {
        Text(word, color = Color.Red)
        Text(word, modifier = Modifier.padding(bottom = 20.dp) ,color = Color.Green)

        Button(
            onClick = { word = "Ура! Кнопка работаеaт!" },
            modifier = Modifier.padding(bottom = 20.dp)
        )
        {
            Text("Нажми на кнопку")
        }

        Button(
            onClick = { word = "Тыкни ещё" },
            colors = ButtonDefaults.buttonColors(containerColor = Color.Green, contentColor = Color.Blue))
        {
            Text("Нажми на кнопку")
        }
    }
}



@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    List_DnDTheme {
        Greeting("Android")
    }
}