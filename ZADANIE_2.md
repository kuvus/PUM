# Zadanie 2: Hilt - Migracja do nowoczesnego DI

## Cel zadania

Użyj Hilt do automatycznego wstrzykiwania zależności w ViewModel.

### Główne adnotacje Hilt:

1. **@HiltAndroidApp** - na klasie Application (entry point)
2. **@AndroidEntryPoint** - na Activity/Fragment (wstrzykiwanie)
3. **@HiltViewModel** - na ViewModel (automatyczne tworzenie)
4. **@Inject** - nadal używamy do constructor injection

### Przepływ:

```
@HiltAndroidApp (Application)
        ↓
@AndroidEntryPoint (Activity)
        ↓
@HiltViewModel (ViewModel)
        ↓
@Inject (GreetingService)
```

## Kroki implementacji

### Krok 1: Skonfiguruj Application

Otwórz: `app/src/main/java/com/example/daggerhilt/task2/MyApplication.kt`

Dodaj adnotację `@HiltAndroidApp`:

```kotlin
import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        // Hilt automatycznie skonfiguruje dependency injection
    }
}
```

**Co to robi?**  
Inicjalizuje Hilt i tworzy bazowy graf zależności dla całej aplikacji.

---

#### Krok 1b: Zaktualizuj AndroidManifest.xml

Otwórz: `app/src/main/AndroidManifest.xml`

Dodaj atrybut `android:name` do tagu `<application>`:

```xml

<application android:name=".task2.MyApplication" android:allowBackup="true"
    android:dataExtractionRules="@xml/data_extraction_rules"
    android:fullBackupContent="@xml/backup_rules" android:icon="@mipmap/ic_launcher"
    android:label="@string/app_name" android:roundIcon="@mipmap/ic_launcher_round"
    android:supportsRtl="true" android:theme="@style/Theme.DaggerHilt" tools:targetApi="31">

    <!-- ... aktywności ... -->
</application>
```

**Co to robi?**  
Informuje Android, aby użył `MyApplication` zamiast domyślnej klasy `Application`.

---

### Krok 2: Skonfiguruj Activity

Otwórz: `app/src/main/java/com/example/daggerhilt/task2/Task2MainActivity.kt`

Dodaj adnotację `@AndroidEntryPoint`:

```kotlin
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class Task2MainActivity : ComponentActivity() {
    // ... reszta kodu
}
```

**Co to robi?**  
Pozwala Hilt wstrzykiwać zależności do tej Activity.

---

### Krok 3: Dodaj @Inject do GreetingService

Otwórz: `app/src/main/java/com/example/daggerhilt/task2/GreetingService.kt`

Dodaj `@Inject constructor()`:

```kotlin
import javax.inject.Inject

class GreetingService @Inject constructor() {
    fun getGreeting(name: String): String {
        return "Witaj, $name! Witamy w Hilt."
    }
}
```

**Co to robi?**  
Informuje Hilt, że ta klasa może być automatycznie tworzona i wstrzykiwana.

---

### Krok 4: Stwórz HiltViewModel

Otwórz: `app/src/main/java/com/example/daggerhilt/task2/MainViewModel.kt`

Dodaj adnotacje i wstrzyknij `GreetingService`:

```kotlin
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val greetingService: GreetingService
) : ViewModel() {

    fun getWelcomeMessage(): String {
        return greetingService.getGreeting("Student")
    }
}
```

**Co to robi?**

- `@HiltViewModel` - informuje Hilt, że to ViewModel do wstrzyknięcia
- `@Inject constructor` - Hilt automatycznie wstrzyknie `GreetingService`

---

### Krok 5: Wstrzyknij ViewModel w Activity

Otwórz: `app/src/main/java/com/example/daggerhilt/task2/Task2MainActivity.kt`

Odkomentuj wstrzykiwanie ViewModel i użyj go:

```kotlin
import androidx.activity.viewModels

@AndroidEntryPoint
class Task2MainActivity : ComponentActivity() {

    // Wstrzyknij ViewModel
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            DaggerHiltTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Task2Screen()
                }
            }
        }
    }

    @Composable
    fun Task2Screen() {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Zadanie 2: Hilt",
                style = MaterialTheme.typography.headlineMedium
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Wyświetl wiadomość z ViewModel
            Text(
                text = viewModel.getWelcomeMessage(),
                style = MaterialTheme.typography.bodyLarge
            )

            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = { showToast() }
            ) {
                Text("Pokaż Toast")
            }
        }
    }

    private fun showToast() {
        Toast.makeText(
            this,
            viewModel.getWelcomeMessage(),
            Toast.LENGTH_LONG
        ).show()
    }
}
```

**Co się dzieje?**

- `by viewModels()` - Hilt automatycznie tworzy ViewModel i wstrzykuje zależności
- Nie musisz ręcznie tworzyć factory ani zarządzać lifecycle!

---

### Krok 6: Dodaj Activity do Manifestu

Otwórz: `app/src/main/AndroidManifest.xml`

Dodaj `Task2MainActivity` (jeśli chcesz ją uruchomić bezpośrednio):

```xml

<activity android:name=".task2.Task2MainActivity" android:exported="true"
    android:theme="@style/Theme.DaggerHilt">
    <!-- Opcjonalnie: możesz dodać intent-filter, aby zmieniać aktywność startową -->
</activity>
```

---

### Krok 7: Przebuduj i uruchom

1. **Rebuild Project**:
   ```
   Build → Rebuild Project
   ```

2. **Uruchom aplikację**

3. Możesz uruchomić `Task2MainActivity` bezpośrednio, zmieniając launcher activity w manifeście, lub
   dodaj przycisk w `MainActivity`.

---

### Opcjonalnie: Integracja z MainActivity

Otwórz: `app/src/main/java/com/example/daggerhilt/MainActivity.kt`

Zmień metodę `runTask2()`:

```kotlin
private fun runTask2() {
    // Uruchom Task2MainActivity
    startActivity(Intent(this, Task2MainActivity::class.java))
}
```

**Nie zapomnij dodać import:**

```kotlin
import android.content.Intent
import com.example.daggerhilt.task2.Task2MainActivity
```

---

## Weryfikacja

Jeśli wszystko działa poprawnie:

- Aplikacja się uruchamia bez błędów
- Na ekranie widać tekst: "Witaj, Student! Witamy w Hilt."
- Po kliknięciu przycisku "Pokaż Toast" wyświetla się Toast z tą samą wiadomością
- Nie musisz ręcznie tworzyć żadnych komponentów!

## Troubleshooting

### Problem: "Missing @HiltAndroidApp annotated Application"

**Rozwiązanie**:

1. Sprawdź, czy `MyApplication` ma `@HiltAndroidApp`
2. Sprawdź, czy `AndroidManifest.xml` ma `android:name=".task2.MyApplication"`

### Problem: "Hilt ViewModel must be annotated with @HiltViewModel"

**Rozwiązanie**: Dodaj `@HiltViewModel` przed klasą `MainViewModel`.

### Problem: Build error "Dagger/Hilt annotation processor required"

**Rozwiązanie**:

1. Sprawdź, czy `build.gradle.kts` ma plugin `com.google.dagger.hilt.android`
2. Sprawdź, czy masz `kapt("com.google.dagger:hilt-android-compiler:2.48")`
3. Zrób Clean & Rebuild Project

### Problem: "Expected @HiltAndroidApp to have a value. Did you forget to apply the Gradle plugin?"

**Rozwiązanie**: Upewnij się, że w `build.gradle.kts` (project level) masz:

```kotlin
id("com.google.dagger.hilt.android") version "2.48" apply false
```
