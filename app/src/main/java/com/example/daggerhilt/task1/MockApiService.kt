package com.example.daggerhilt.task1

/**
 * Mockowa implementacja ApiService do celów demonstracyjnych
 */
class MockApiService : ApiService {
    override fun fetchData(): String {
        return "Mock data from API"
    }
}

