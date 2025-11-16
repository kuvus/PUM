package com.example.daggerhilt.task2

import javax.inject.Inject

/**
 * Zadanie 2 - Krok 3: Dodaj constructor injection
 *
 * TODO: Dodaj adnotację @Inject do konstruktora
 */
class GreetingService @Inject constructor() {
    fun getGreeting(name: String): String {
        return "Witaj, $name! Witamy w Hilt."
    }
}
