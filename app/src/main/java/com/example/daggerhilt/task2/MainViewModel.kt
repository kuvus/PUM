package com.example.daggerhilt.task2

import androidx.lifecycle.ViewModel

/**
 * Zadanie 2 - Krok 4: Stwórz HiltViewModel
 * 
 * TODO: Dodaj adnotację @HiltViewModel do klasy
 * TODO: Dodaj adnotację @Inject do konstruktora
 * TODO: Wstrzyknij GreetingService jako parametr konstruktora
 */
/* TODO: Dodaj @HiltViewModel */
class MainViewModel /* TODO: Dodaj @Inject constructor */ (
    // TODO: Wstrzyknij GreetingService
) : ViewModel() {

    fun getWelcomeMessage(): String {
        // TODO: Użyj GreetingService do wygenerowania wiadomości
        // return greetingService.getGreeting("Student")
        return "TODO: Zaimplementuj wstrzykiwanie GreetingService"
    }
}

