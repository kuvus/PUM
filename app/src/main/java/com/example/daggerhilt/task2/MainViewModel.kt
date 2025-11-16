package com.example.daggerhilt.task2

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * Zadanie 2 - Krok 4: Stwórz HiltViewModel
 * 
 * TODO: Dodaj adnotację @HiltViewModel do klasy
 * TODO: Dodaj adnotację @Inject do konstruktora
 * TODO: Wstrzyknij GreetingService jako parametr konstruktora
 */
@HiltViewModel
class MainViewModel @Inject constructor(
    private val greetingService: GreetingService
) : ViewModel() {

    fun getWelcomeMessage(): String {
        return greetingService.getGreeting("Student")
    }
}

