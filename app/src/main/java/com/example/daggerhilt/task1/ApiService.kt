package com.example.daggerhilt.task1

/**
 * Zadanie 1 - Interfejs ApiService
 * 
 * Ten interfejs reprezentuje serwis API, który będzie dostarczany przez Dagger 2.
 */
interface ApiService {
    fun fetchData(): String
}

