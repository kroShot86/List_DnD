package com.example.list_dnd

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.list_dnd.ui.theme.List_DnDTheme

enum class CharState { HEALTHY, UNCONSCIOUS, DEAD }

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            List_DnDTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    CharacterScreen(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun CharacterScreen(modifier: Modifier = Modifier) {
    var strength by rememberSaveable { mutableStateOf("10") }
    var dexterity by rememberSaveable { mutableStateOf("10") }
    var constitution by rememberSaveable { mutableStateOf("10") }
    var intelligence by rememberSaveable { mutableStateOf("10") }
    var wisdom by rememberSaveable { mutableStateOf("10") }
    var charisma by rememberSaveable { mutableStateOf("10") }

    var stateString by rememberSaveable { mutableStateOf(CharState.HEALTHY.name) }
    var currentHp by rememberSaveable { mutableStateOf(30) }

    val state = CharState.valueOf(stateString)
    val conValue = constitution.toIntOrNull() ?: 10
    val calculatedMaxHp = 10 + (conValue * 2)

    val backgroundColor = when (state) {
        CharState.HEALTHY -> Color(0xFFE8F5E9)
        CharState.UNCONSCIOUS -> Color(0xFFFFF3E0)
        CharState.DEAD -> Color(0xFFEF5350)
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(backgroundColor)
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(120.dp)
                .background(Color.LightGray, shape = RoundedCornerShape(16.dp))
                .border(2.dp, Color.Gray, shape = RoundedCornerShape(16.dp)),
            contentAlignment = Alignment.Center
        ) {
            val avatarEmoji = when (state) {
                CharState.HEALTHY -> "🧙‍♂️"
                CharState.UNCONSCIOUS -> "😵"
                CharState.DEAD -> "💀"
            }
            Text(text = avatarEmoji, fontSize = 64.sp)
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (state != CharState.HEALTHY) {
            Card(
                colors = CardDefaults.cardColors(containerColor = Color.Red.copy(alpha = 0.2f)),
                modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)
            ) {
                Text(
                    text = when (state) {
                        CharState.UNCONSCIOUS -> "💀 ВЫ БЕЗ СОЗНАНИЯ!"
                        CharState.DEAD -> "☠️ ВЫ МЕРТВЫ."
                        else -> ""
                    },
                    color = Color.Black,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(16.dp),
                    textAlign = TextAlign.Center
                )
            }
        }

        Text("Характеристики", fontSize = 18.sp, fontWeight = FontWeight.Bold, modifier = Modifier.align(Alignment.Start))
        Spacer(modifier = Modifier.height(8.dp))

        Row(horizontalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.fillMaxWidth()) {
            StatInputField("СИЛА", strength, { strength = it }, Modifier.weight(1f))
            StatInputField("ЛОВ", dexterity, { dexterity = it }, Modifier.weight(1f))
            StatInputField("ТЕЛО", constitution, { constitution = it }, Modifier.weight(1f))
        }
        Spacer(modifier = Modifier.height(8.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.fillMaxWidth()) {
            StatInputField("ИНТ", intelligence, { intelligence = it }, Modifier.weight(1f))
            StatInputField("МУД", wisdom, { wisdom = it }, Modifier.weight(1f))
            StatInputField("ХАР", charisma, { charisma = it }, Modifier.weight(1f))
        }

        Spacer(modifier = Modifier.height(16.dp))

        Card(
            modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            Column(modifier = Modifier.padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                Text("Хиты: $currentHp / $calculatedMaxHp", fontSize = 24.sp, fontWeight = FontWeight.Bold)
            }
        }

        Text("Действия:", fontSize = 18.sp, modifier = Modifier.align(Alignment.Start))
        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = {
                currentHp = (currentHp - 5).coerceAtLeast(0)
                if (currentHp == 0) {
                    stateString = if (state == CharState.UNCONSCIOUS) CharState.DEAD.name else CharState.UNCONSCIOUS.name
                }
            },
            modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp),
            enabled = state != CharState.DEAD,
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFD32F2F))
        ) { Text("Получить урон (-5 HP)") }

        Button(
            onClick = {
                currentHp = (currentHp + 10).coerceAtMost(calculatedMaxHp)
                if (state == CharState.UNCONSCIOUS && currentHp > 0) stateString = CharState.HEALTHY.name
            },
            modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp),
            enabled = state != CharState.DEAD
        ) { Text("Лечение (+10 HP)") }

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedButton(
            onClick = {
                strength = "10"; dexterity = "10"; constitution = "10"
                intelligence = "10"; wisdom = "10"; charisma = "10"
                currentHp = 30
                stateString = CharState.HEALTHY.name
            },
            modifier = Modifier.fillMaxWidth()
        ) { Text("Сбросить всё") }
    }
}

@Composable
fun StatInputField(title: String, value: String, onValueChange: (String) -> Unit, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFE0E0E0))
    ) {
        Column(modifier = Modifier.fillMaxWidth().padding(8.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            Text(title, style = MaterialTheme.typography.labelSmall, color = Color.DarkGray)
            OutlinedTextField(
                value = value,
                onValueChange = { newValue ->
                    if (newValue.all { it.isDigit() } && newValue.length <= 2) {
                        onValueChange(newValue)
                    }
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                textStyle = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold, textAlign = TextAlign.Center),
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CharacterScreenPreview() {
    List_DnDTheme {
        CharacterScreen()
    }
}