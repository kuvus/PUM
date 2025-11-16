package com.example.daggerhilt.task2

import android.app.Application

/**
 * Zadanie 2 - Krok 1: Skonfiguruj Application dla Hilt
 * 
 * TODO: Dodaj adnotację @HiltAndroidApp do tej klasy
 * TODO: Zaktualizuj AndroidManifest.xml, aby użyć tej klasy Application
 */
/* TODO: Dodaj @HiltAndroidApp */
class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        // Hilt automatycznie skonfiguruje dependency injection
    }
}

