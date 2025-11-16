package com.example.daggerhilt.task1

/**
 * Zadanie 1 - Krok 2: Stwórz moduł Dagger
 * 
 * TODO: Dodaj adnotację @Module do tej klasy
 * TODO: Dodaj adnotację @Provides do metody provideApiService
 */
/* TODO: Dodaj @Module */
class NetworkModule {

    /* TODO: Dodaj @Provides */
    fun provideApiService(): ApiService {
        return MockApiService()
    }
}

