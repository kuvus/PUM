package com.example.daggerhilt.task1

/**
 * Zadanie 1 - Krok 1: Dodaj adnotację @Inject do konstruktora
 * 
 * Ta klasa powinna otrzymywać ApiService przez dependency injection.
 * Dodaj @Inject przed słowem kluczowym constructor.
 */
class UserRepository /* TODO: Dodaj @Inject */ constructor(
    private val apiService: ApiService
) {
    fun getUserData(): String {
        val data = apiService.fetchData()
        return "User data from repository: $data"
    }
}

