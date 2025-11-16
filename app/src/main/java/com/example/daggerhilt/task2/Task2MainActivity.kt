package com.example.daggerhilt.task2

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.daggerhilt.ui.theme.DaggerHiltTheme
import dagger.hilt.android.AndroidEntryPoint

/**
 * Zadanie 2 - Krok 2 i 5: Skonfiguruj Activity dla Hilt
 * 
 * TODO: Dodaj adnotację @AndroidEntryPoint do tej klasy
 * TODO: Wstrzyknij MainViewModel przez viewModels()
 */
@AndroidEntryPoint
class Task2MainActivity : ComponentActivity() {
    
    // Wstrzyknij ViewModel
    private val viewModel: MainViewModel by viewModels()
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        setContent {
            DaggerHiltTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Task2Screen()
                }
            }
        }
    }
    
    @Composable
    fun Task2Screen() {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Zadanie 2: Hilt",
                style = MaterialTheme.typography.headlineMedium
            )
            
            Spacer(modifier = Modifier.height(32.dp))
            
            // Wyświetl wiadomość z ViewModel
            Text(
                text = viewModel.getWelcomeMessage(),
                style = MaterialTheme.typography.bodyLarge
            )
            
            Spacer(modifier = Modifier.height(32.dp))
            
            Button(
                onClick = { showToast() }
            ) {
                Text("Pokaż Toast")
            }
        }
    }
    
    private fun showToast() {
        Toast.makeText(
            this,
            viewModel.getWelcomeMessage(),
            Toast.LENGTH_LONG
        ).show()
    }
}

