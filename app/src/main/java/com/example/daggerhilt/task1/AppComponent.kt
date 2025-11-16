package com.example.daggerhilt.task1

import dagger.Component

/**
 * Zadanie 1 - Krok 3: Stwórz Component
 * 
 * TODO: Dodaj adnotację @Component(modules = [NetworkModule::class])
 * TODO: Dodaj metodę getUserRepository(): UserRepository
 * 
 * Po dodaniu adnotacji przebuduj projekt (Build > Rebuild Project),
 * aby Dagger wygenerował implementację (DaggerAppComponent).
 */
@Component(modules = [NetworkModule::class])
interface AppComponent {
    fun getUserRepository(): UserRepository
}

