package com.example.daggerhilt.task1

import dagger.Module
import dagger.Provides
import dagger.hilt.migration.DisableInstallInCheck

/**
 * Zadanie 1 - Krok 2: Stwórz moduł Dagger
 * 
 * TODO: Dodaj adnotację @Module do tej klasy
 * TODO: Dodaj adnotację @Provides do metody provideApiService
 */
@Module
@DisableInstallInCheck
class NetworkModule {

    @Provides
    fun provideApiService(): ApiService {
        return MockApiService()
    }
}

