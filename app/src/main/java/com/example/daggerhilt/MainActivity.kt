package com.example.daggerhilt

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.daggerhilt.ui.theme.DaggerHiltTheme

/**
 * MainActivity - Punkt wejścia aplikacji
 *
 * ZADANIE 1: Użyj Dagger 2 do wstrzyknięcia UserRepository ZADANIE 2: Użyj Hilt do wstrzyknięcia
 * ViewModel
 *
 * Przełączaj się między zadaniami, komentując/odkomentowując odpowiednie sekcje.
 */
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            DaggerHiltTheme {
                Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = MaterialTheme.colorScheme.background
                ) { MainScreen() }
            }
        }
    }

    @Composable
    fun MainScreen() {
        var message by remember { mutableStateOf("Wybierz zadanie do wykonania") }

        Column(
                modifier = Modifier.fillMaxSize().padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
        ) {
            Text(text = "Dagger 2 & Hilt Workshop", style = MaterialTheme.typography.headlineMedium)

            Spacer(modifier = Modifier.height(32.dp))

            // ZADANIE 1: Dagger 2
            Button(onClick = { runTask1() }, modifier = Modifier.fillMaxWidth()) {
                Text("Zadanie 1: Dagger 2")
            }

            Spacer(modifier = Modifier.height(16.dp))

            // ZADANIE 2: Hilt
            Button(onClick = { runTask2() }, modifier = Modifier.fillMaxWidth()) {
                Text("Zadanie 2: Hilt")
            }

            Spacer(modifier = Modifier.height(32.dp))

            Text(text = message, style = MaterialTheme.typography.bodyLarge)
        }
    }

    /**
     * Zadanie 1 - Krok 4: Użyj Dagger Component
     *
     * TODO: Odkomentuj poniższy kod i uzupełnij implementację
     */
    private fun runTask1() {
        // Utwórz instancję DaggerAppComponent
        val component = com.example.daggerhilt.task1.DaggerAppComponent.create()

        // Pobierz UserRepository z komponentu
        val repository = component.getUserRepository()

        // Wyświetl dane z repository w Toast
        val data = repository.getUserData()
        Toast.makeText(this, data, Toast.LENGTH_LONG).show()
    }

    /** Zadanie 2 - Do zaimplementowania w następnym kroku */
    private fun runTask2() {
        Toast.makeText(this, "TODO: Zaimplementuj Zadanie 2", Toast.LENGTH_SHORT).show()
    }
}
