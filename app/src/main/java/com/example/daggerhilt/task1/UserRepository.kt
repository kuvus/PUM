package com.example.daggerhilt.task1

import javax.inject.Inject

/**
 * Zadanie 1 - Krok 1: Dodaj adnotację @Inject do konstruktora
 * 
 * Ta klasa powinna otrzymywać ApiService przez dependency injection.
 * Dodaj @Inject przed słowem kluczowym constructor.
 */
class UserRepository @Inject constructor(
    private val apiService: ApiService
) {
    fun getUserData(): String {
        val data = apiService.fetchData()
        return "User data from repository: $data"
    }
}

