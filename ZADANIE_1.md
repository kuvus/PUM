# Zadanie 1: Dagger 2 - Tworzenie podstawowego grafu zależności

## Cel zadania

Stwórz prosty graf zależności używając czystego Dagger 2.

### Główne elementy Dagger 2:

1. **@Inject** - oznacza, które zależności mają być wstrzyknięte
2. **@Module** - klasa dostarczająca zależności
3. **@Provides** - metoda w module, która dostarcza konkretną zależność
4. **@Component** - interfejs łączący moduły z miejscami, które potrzebują zależności

### Przepływ:

```
@Module → @Provides → ApiService
                          ↓
              @Inject → UserRepository
                          ↓
                    @Component → MainActivity
```

## Kroki implementacji

### Krok 1: Dodaj @Inject do UserRepository

Otwórz: `app/src/main/java/com/example/daggerhilt/task1/UserRepository.kt`

Dodaj adnotację `@Inject` przed słowem kluczowym `constructor`:

```kotlin
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val apiService: ApiService
) {
    fun getUserData(): String {
        val data = apiService.fetchData()
        return "User data from repository: $data"
    }
}
```

**Co to robi?**  
Informuje Dagger, że ta klasa może być tworzona automatycznie, wstrzykując `ApiService`.

---

### Krok 2: Stwórz NetworkModule

Otwórz: `app/src/main/java/com/example/daggerhilt/task1/NetworkModule.kt`

Dodaj adnotacje `@Module`, `@DisableInstallInCheck` i `@Provides`:

```kotlin
import dagger.Module
import dagger.Provides
import dagger.hilt.migration.DisableInstallInCheck

@Module
@DisableInstallInCheck
class NetworkModule {

    @Provides
    fun provideApiService(): ApiService {
        return MockApiService()
    }
}
```

**Co to robi?**

- `@Module` - informuje Dagger, że ta klasa dostarcza zależności
- `@DisableInstallInCheck` - informuje Hilt (jeśli jest w projekcie), że ten moduł używa czystego Dagger 2
- `@Provides` - oznacza metodę, która dostarcza konkretną zależność (`ApiService`)

Metoda `provideApiService()` zwraca mockową implementację `ApiService`.

---

### Krok 3: Stwórz AppComponent

Otwórz: `app/src/main/java/com/example/daggerhilt/task1/AppComponent.kt`

Dodaj adnotację `@Component` i metodę:

```kotlin
import dagger.Component

@Component(modules = [NetworkModule::class])
interface AppComponent {
    fun getUserRepository(): UserRepository
}
```

**Co to robi?**  
Component łączy wszystkie moduły i udostępnia metody do pobierania zależności. Dagger automatycznie
wygeneruje klasę `DaggerAppComponent`.

---

### Krok 4: Przebuduj projekt

**WAŻNE**: Dagger generuje kod podczas kompilacji!

W Android Studio:

```
Build → Rebuild Project
```

Lub w terminalu:

```bash
./gradlew clean build
```

Poczekaj, aż Gradle zakończy budowanie. Teraz klasa `DaggerAppComponent` powinna być dostępna.

---

### Krok 5: Użyj Dagger w MainActivity

Otwórz: `app/src/main/java/com/example/daggerhilt/MainActivity.kt`

Znajdź metodę `runTask1()` i odkomentuj/uzupełnij kod:

```kotlin
private fun runTask1() {
    // Utwórz instancję komponentu
    val component = DaggerAppComponent.create()

    // Pobierz UserRepository z komponentu
    val repository = component.getUserRepository()

    // Wyświetl dane z repository w Toast
    val data = repository.getUserData()
    Toast.makeText(this, data, Toast.LENGTH_LONG).show()
}
```

**Co się dzieje?**

1. `DaggerAppComponent.create()` - Dagger tworzy graf zależności
2. `component.getUserRepository()` - Pobiera `UserRepository` (Dagger automatycznie wstrzykuje
   `ApiService`)
3. Toast pokazuje dane z repository

---

### Krok 6: Uruchom aplikację

1. Uruchom aplikację na emulatorze/urządzeniu
2. Kliknij przycisk **"Zadanie 1: Dagger 2"**
3. Powinieneś zobaczyć Toast z tekstem:
   ```
   User data from repository: Mock data from API
   ```

---

## Weryfikacja

Jeśli wszystko działa poprawnie:

- Aplikacja się uruchamia bez błędów
- Po kliknięciu przycisku widzisz Toast z danymi
- Dane pochodzą z `MockApiService` przez `UserRepository`

## Troubleshooting

### Problem: "Cannot resolve symbol 'DaggerAppComponent'"

**Rozwiązanie**: Zrób `Build → Rebuild Project`. Dagger musi wygenerować tę klasę.

### Problem: Build error z KAPT

**Rozwiązanie**: Sprawdź, czy dodałeś plugin `kotlin-kapt` w `build.gradle.kts`.

### Problem: "Missing binding for ApiService"

**Rozwiązanie**: Upewnij się, że:

- `NetworkModule` ma adnotację `@Module`
- Metoda `provideApiService` ma adnotację `@Provides`
- `AppComponent` ma `modules = [NetworkModule::class]`

### Problem: "[Hilt] NetworkModule is missing an @InstallIn annotation"

**Opis problemu**:  
Jeśli w projekcie jest już skonfigurowany Hilt (do użycia w Zadaniu 2), podczas kompilacji może pojawić się błąd:

```
[Hilt] com.example.daggerhilt.task1.NetworkModule is missing an @InstallIn annotation.
```

**Przyczyna**:  
Hilt domyślnie wymaga, aby wszystkie moduły oznaczone `@Module` miały również adnotację `@InstallIn`, która określa, w którym komponencie Hilt mają być zainstalowane. Jednak w Zadaniu 1 używamy czystego Dagger 2 (bez Hilt), więc nasz moduł nie powinien być zarządzany przez Hilt.

**Rozwiązanie**:  
Dodaj adnotację `@DisableInstallInCheck` do `NetworkModule`, aby poinformować Hilt, że ten moduł jest przeznaczony dla czystego Dagger 2:

```kotlin
import dagger.Module
import dagger.Provides
import dagger.hilt.migration.DisableInstallInCheck

@Module
@DisableInstallInCheck  // <- Dodaj tę linię
class NetworkModule {

    @Provides
    fun provideApiService(): ApiService {
        return MockApiService()
    }
}
```

**Co to robi?**  
Adnotacja `@DisableInstallInCheck` informuje procesor Hilt, że ten konkretny moduł jest celowo używany z czystym Dagger 2 i nie powinien być sprawdzany pod kątem wymagań Hilt. Dzięki temu możemy mieć w tym samym projekcie zarówno kod używający czystego Dagger 2 (Zadanie 1), jak i kod używający Hilt (Zadanie 2).
