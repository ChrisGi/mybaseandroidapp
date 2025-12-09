# MVI Architecture & Code Standards

## Table of Contents
1. [What is MVI?](#what-is-mvi)
2. [MVI Flow Diagram](#mvi-flow-diagram)
3. [Project Architecture Overview](#project-architecture-overview)
4. [Core Components](#core-components)
5. [Code Standards](#code-standards)
6. [Static Code Analysis with Detekt](#static-code-analysis-with-detekt)
7. [Implementation Guide](#implementation-guide)
8. [Testing Guidelines](#testing-guidelines)
9. [Best Practices](#best-practices)

---

## What is MVI?

**MVI (Model-View-Intent)** is a unidirectional data flow architecture pattern that provides:

- **Predictable state management**: Single source of truth for UI state
- **Immutable states**: State changes are explicit and traceable
- **Testability**: Business logic isolated from UI
- **Reactive programming**: Leverages Kotlin Flows for reactive updates

### MVI Terminology

- **Model (State)**: Immutable data class representing the entire UI state
- **View**: UI layer (Jetpack Compose) that renders the state and emits intents
- **Intent (Event)**: User actions or system events that trigger state changes
- **Effect (Side Effect)**: One-time events like navigation, toasts, or dialogs

---

## MVI Flow Diagram

```
┌─────────────────────────────────────────────────────────────┐
│                                                             │
│  User Interaction (View)                                    │
│         │                                                    │
│         ▼                                                    │
│  Event/Intent                                               │
│         │                                                    │
│         ▼                                                    │
│  EventHandler.obtainEvent()                                 │
│         │                                                    │
│         ▼                                                    │
│  ViewModel processes event                                  │
│         │                                                    │
│         ├──────► UseCase (Domain Layer)                     │
│         │              │                                     │
│         │              ▼                                     │
│         │        Repository (Data Layer)                    │
│         │              │                                     │
│         │              ▼                                     │
│         │        Network/DB/Preferences                     │
│         │              │                                     │
│         │              ▼                                     │
│         │        Result returned                            │
│         │                                                    │
│         ▼                                                    │
│  New State created (via Factory)                            │
│         │                                                    │
│         ▼                                                    │
│  StateFlow emits new state                                  │
│         │                                                    │
│         ▼                                                    │
│  View observes state & recomposes                           │
│         │                                                    │
│         └────────────────────────────────────────────────►  │
│                                                             │
└─────────────────────────────────────────────────────────────┘

Side Effects Flow:
┌──────────────────────────────────────────┐
│ ViewModel emits Effect                   │
│       │                                   │
│       ▼                                   │
│ SharedFlow<Effect>                       │
│       │                                   │
│       ▼                                   │
│ View collects in LaunchedEffect          │
│       │                                   │
│       ▼                                   │
│ One-time action (navigate, toast, etc.)  │
└──────────────────────────────────────────┘
```

---

## Project Architecture Overview

### Layered Architecture

This project follows **Clean Architecture** principles with three main layers:

```
┌─────────────────────────────────────────────────────┐
│  Presentation Layer (Feature Modules)               │
│  - ViewModels, States, Events, Effects              │
│  - Jetpack Compose UI                               │
│  - Navigation                                        │
└─────────────────────────────────────────────────────┘
                        ▼
┌─────────────────────────────────────────────────────┐
│  Domain Layer                                        │
│  - UseCases (Business Logic)                        │
│  - Domain Models                                     │
│  - Repository Interfaces                            │
└─────────────────────────────────────────────────────┘
                        ▼
┌─────────────────────────────────────────────────────┐
│  Data Layer                                          │
│  - Repository Implementations                       │
│  - Data Sources (Remote, Local, Preferences)       │
│  - DTOs & Mappers                                   │
└─────────────────────────────────────────────────────┘
                        ▼
┌─────────────────────────────────────────────────────┐
│  Infrastructure (Libs)                              │
│  - Network, Database, Preferences                   │
│  - Common utilities & extensions                    │
└─────────────────────────────────────────────────────┘
```

### Module Structure

```
WeatherTomorrow/
├── feature/                    # Feature modules (Presentation)
│   ├── feature-weather-forecast/
│   ├── feature-search-location/
│   └── feature-app-settings/
├── domain/                     # Business logic
│   ├── domain-weather-forecast/
│   ├── domain-location/
│   └── domain-app-settings/
├── data/                       # Data sources
│   ├── data-weather-forecast/
│   ├── data-location/
│   └── data-app-settings/
└── libs/                       # Shared infrastructure
    ├── ui/                     # Base UI components
    ├── network/                # HTTP client
    ├── preferences/            # DataStore
    └── common/                 # Utilities
```

---

## Core Components

### 1. State

**Location**: `feature-*/presentation/model/*State.kt`

Immutable data class representing the complete UI state.

```kotlin
data class SearchLocationViewState(
    val toolbarTitle: UiString,
    val locationSearchBarState: LocationSearchBarState,
    val displayState: LceState<List<SearchLocation>>?
) {
    // Computed properties for derived state
    val isLoading: Boolean
        get() = displayState == LceState.Loading

    val content: List<SearchLocation>
        get() = (displayState as? LceState.Content)?.content ?: emptyList()

    val isEmpty: Boolean
        get() = (displayState as? LceState.Content) != null && content.isEmpty()
}
```

**Key Principles**:
- Immutable data classes
- All UI information in one place
- Computed properties for derived state
- No business logic in state classes

---

### 2. Events (Intents)

**Location**: `feature-*/presentation/model/*Event.kt`

Sealed interface representing all possible user actions.

```kotlin
sealed interface SearchLocationEvent : Event {
    data class Search(val query: String) : SearchLocationEvent
    data class ShowLocationWeather(val location: SearchLocation) : SearchLocationEvent
    data object ClearSearch : SearchLocationEvent
    data object NavigateToNetworkSettings : SearchLocationEvent
    data object RetrySearch : SearchLocationEvent
}
```

**Base Interface** (`libs/ui/src/commonMain/kotlin/gi/aera/ui/Event.kt`):
```kotlin
interface Event
```

**Key Principles**:
- Sealed interface for type safety
- `data class` for events with parameters
- `data object` for simple actions
- Descriptive names in imperative form

---

### 3. Effects (Side Effects)

**Location**: `feature-*/presentation/model/*Effect.kt`

One-time events that don't modify state (navigation, toasts, dialogs).

```kotlin
sealed interface SearchLocationEffect : Effect {
    data class ShowLocationWeather(val location: SearchLocation) : SearchLocationEffect
    data class ShowError(val message: String) : SearchLocationEffect
    data object NavigateBack : SearchLocationEffect
}
```

**Base Interface** (`libs/ui/src/commonMain/kotlin/gi/aera/ui/Effect.kt`):
```kotlin
interface Effect
```

**Key Principles**:
- For one-time actions only
- Use `SharedFlow` instead of `StateFlow`
- Consumed by UI in `LaunchedEffect`
- Should not modify state

---

### 4. ViewModel

**Location**: `feature-*/presentation/*ViewModel.kt`

Handles events and manages state.

```kotlin
class SearchLocationViewModel(
    private val searchLocationUseCase: SearchLocationUseCase,
    private val stateFactory: SearchLocationStateFactory
) : ViewModel(), EventHandler<SearchLocationEvent> {

    // State
    private val _viewState = MutableStateFlow(initialState())
    val viewState: StateFlow<SearchLocationViewState> = _viewState
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = initialState()
        )

    // Effects
    private val _effects = MutableSharedFlow<SearchLocationEffect>(
        extraBufferCapacity = 1
    )
    val effects: SharedFlow<SearchLocationEffect> = _effects.asSharedFlow()

    // Event Handler
    override fun obtainEvent(event: SearchLocationEvent) {
        when (event) {
            is SearchLocationEvent.Search -> handleSearch(event.query)
            is SearchLocationEvent.ShowLocationWeather -> {
                _effects.tryEmit(
                    SearchLocationEffect.ShowLocationWeather(event.location)
                )
            }
            SearchLocationEvent.ClearSearch -> clearSearch()
            SearchLocationEvent.RetrySearch -> retrySearch()
        }
    }

    private fun handleSearch(query: String) {
        viewModelScope.launch {
            _viewState.update { it.copy(displayState = LceState.Loading) }

            searchLocationUseCase(query)
                .onSuccess { locations ->
                    _viewState.update {
                        stateFactory.createState(locations)
                    }
                }
                .onFailure { error ->
                    _viewState.update {
                        it.copy(displayState = LceState.Error(error))
                    }
                }
        }
    }
}
```

**Key Principles**:
- Implements `EventHandler<Event>` interface
- Exposes state as `StateFlow` (read-only)
- Exposes effects as `SharedFlow` (for one-time events)
- Uses `WhileSubscribed` with timeout for lifecycle awareness
- All business logic delegated to UseCases
- State creation delegated to StateFactories

---

### 5. EventHandler Interface

**Location**: `libs/ui/src/commonMain/kotlin/gi/aera/ui/EventHandler.kt`

```kotlin
interface EventHandler<T : Event> {
    fun obtainEvent(event: T)
}
```

**Usage in UI**:
```kotlin
ForecastScreen(
    currentWeatherState = currentWeatherViewState,
    forecastState = forecastViewState,
    event = viewModel::obtainEvent  // Pass as method reference
)
```

---

### 6. StateFactory

**Location**: `feature-*/presentation/model/*StateFactory.kt`

Transforms domain models into UI state.

```kotlin
class WeatherLocationStateFactory {
    fun createState(
        response: RealtimeWeatherResponse,
        location: SearchLocation
    ): WeatherLocationState.WeatherLocation {
        return WeatherLocationState.WeatherLocation(
            name = location.name,
            temperatureData = TemperatureData(
                currentTemperature = response.current.tempC.toString(),
                feelsLike = response.current.feelslikeC.toString()
            ),
            weatherIcon = weatherConditionToIcon(response.current.condition.code)
        )
    }
}
```

**Key Principles**:
- Injected into ViewModel via constructor
- Pure functions (no side effects)
- Domain model → UI model mapping
- Testable independently

---

### 7. UseCase

**Location**: `domain-*/domain/usecase/*UseCase.kt`

Encapsulates business logic.

```kotlin
class SearchLocationUseCase internal constructor(
    private val searchLocationRepository: SearchLocationRepository,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) {
    suspend operator fun invoke(query: String) = withContext(dispatcher) {
        searchLocationRepository.searchLocation(query)
            .map { response ->
                response.results.map { it.toDomain() }
            }
    }
}
```

**Key Principles**:
- Single responsibility
- `operator fun invoke()` for clean call syntax
- `suspend` functions for async operations
- IO dispatcher for background execution
- Returns domain models (not DTOs)

---

### 8. Repository

**Interface Location**: `domain-*/domain/repository/*Repository.kt`
**Implementation Location**: `data-*/data/*RepositoryImpl.kt`

```kotlin
// Domain layer (interface)
interface SearchLocationRepository {
    suspend fun searchLocation(query: String): ApiResponse<SearchLocationResponse>
}

// Data layer (implementation)
class SearchLocationRepositoryImpl(
    private val remoteDataSource: SearchLocationRemoteDataSource
) : SearchLocationRepository {
    override suspend fun searchLocation(
        query: String
    ): ApiResponse<SearchLocationResponse> {
        return remoteDataSource.searchLocation(query)
    }
}
```

**Key Principles**:
- Interface in domain, implementation in data
- Returns `ApiResponse<T>` for error handling
- Abstracts data sources (network, cache, DB)
- No business logic (just data operations)

---

### 9. LCE State Pattern

**Location**: `libs/ui/src/commonMain/kotlin/gi/aera/ui/LceState.kt`

**L**oading, **C**ontent, **E**rror state wrapper.

```kotlin
sealed class LceState<out T> {
    data object Loading : LceState<Nothing>()
    data class Refreshing<T>(val content: T) : LceState<T>()
    data class Content<T>(val content: T) : LceState<T>()
    data class Error(val appError: AppError) : LceState<Nothing>()
}
```

**Usage in ViewModel**:
```kotlin
private val _viewState = MutableStateFlow<LceState<MyData>>(LceState.Loading)

fun loadData(forceRefresh: Boolean = false) {
    viewModelScope.launch {
        // Show loading or refreshing based on current state
        _viewState.update { currentState ->
            if (currentState is LceState.Content && forceRefresh) {
                LceState.Refreshing(currentState.content)
            } else {
                LceState.Loading
            }
        }

        when (val result = getDataUseCase()) {
            is ApiResponse.Success -> {
                _viewState.value = LceState.Content(result.data)
            }
            is ApiResponse.Error -> {
                _viewState.value = LceState.Error(result.error)
            }
        }
    }
}
```

**Usage in UI**:
```kotlin
@Composable
fun MyScreen(state: LceState<MyData>) {
    LceViewState(
        state = state,
        loading = { CircularProgressIndicator() },
        errorContent = { error -> ErrorView(error) },
        content = { data -> DataView(data) }
    )
}
```

---

## Code Standards

### Naming Conventions

| Component | Naming Pattern | Example |
|-----------|---------------|---------|
| State | `*State` or `*ViewState` | `SearchLocationViewState` |
| Event | `*Event` | `SearchLocationEvent` |
| Effect | `*Effect` | `SearchLocationEffect` |
| ViewModel | `*ViewModel` | `SearchLocationViewModel` |
| UseCase | `<Verb><Noun>UseCase` | `SearchLocationUseCase` |
| Repository | `*Repository` | `SearchLocationRepository` |
| StateFactory | `*StateFactory` | `SearchLocationStateFactory` |

### File Organization

```
feature-my-feature/
└── src/
    └── commonMain/
        └── kotlin/
            └── com/example/feature/myfeature/
                ├── presentation/
                │   ├── MyFeatureViewModel.kt
                │   ├── MyFeatureScreen.kt
                │   ├── MyFeatureNavScreen.kt
                │   └── model/
                │       ├── MyFeatureViewState.kt
                │       ├── MyFeatureEvent.kt
                │       ├── MyFeatureEffect.kt
                │       └── MyFeatureStateFactory.kt
                └── di/
                    └── KoinModule.kt
```

### Package Structure

Follow this package organization:

```
com.example.project/
├── feature/                # Features (Presentation Layer)
│   └── [feature-name]/
│       └── presentation/
│           ├── *.ViewModel.kt
│           ├── *Screen.kt
│           └── model/
├── domain/                 # Domain Layer
│   └── [domain-name]/
│       ├── usecase/
│       ├── repository/
│       └── model/
└── data/                   # Data Layer
    └── [data-name]/
        ├── *RepositoryImpl.kt
        └── datasource/
```

---

## Static Code Analysis with Detekt

### What is Detekt?

**Detekt** is a static code analysis tool for Kotlin that helps maintain code quality, consistency, and best practices. It analyzes your codebase and reports:

- Code smells
- Complexity issues
- Formatting violations
- Potential bugs
- Performance issues
- Style violations

### Why Use Detekt?

- **Consistency**: Enforces coding standards across the team
- **Quality**: Catches potential bugs before runtime
- **Maintainability**: Identifies complex code that needs refactoring
- **Best Practices**: Ensures Kotlin idioms and conventions
- **CI/CD Integration**: Can fail builds on violations

---

### Project Configuration

**Version**: 1.23.8

**Configuration File**: `config/detekt/detekt.yml`

**Plugins**:
- `detekt-formatting`: Kotlin formatting rules
- `detekt-compose`: Jetpack Compose-specific rules (v0.4.12)

**Setup in `build.gradle.kts`**:
```kotlin
plugins {
  alias(libs.plugins.detekt.plugin) apply true
}

dependencies {
  detektPlugins(libs.detekt.formatting)
  detektPlugins(libs.detekt.compose)
}

detekt {
  toolVersion = "1.23.8"
  config.setFrom(file("config/detekt/detekt.yml"))
  buildUponDefaultConfig = true
  source.from(
    "composeApp/src/",
    "data/**/src/",
    "feature/**/src/",
    "libs/**/src/",
    "shared/src/"
  )
}
```

---

### Running Detekt

#### Analyze Code
```bash
./gradlew detekt
```

#### Auto-Fix Issues (where possible)
```bash
./gradlew detektFormat
```

#### Generate Reports
Detekt generates reports in `build/reports/detekt/`:
- `detekt.html` - HTML report (human-readable)
- `detekt.xml` - XML report (for CI tools)
- `detekt.txt` - Text report
- `detekt.sarif` - SARIF format (for GitHub integration)

---

### Key Rules Enabled

#### 1. Complexity Rules

| Rule | Threshold | Description |
|------|-----------|-------------|
| `CyclomaticComplexMethod` | 15 | Maximum cyclomatic complexity per method |
| `ComplexCondition` | 4 | Maximum boolean conditions in expressions |
| `LongMethod` | 60 lines | Maximum method length |
| `LongParameterList` | 6 params (function)<br>12 params (constructor) | Maximum parameters |
| `LargeClass` | 600 lines | Maximum class size |
| `TooManyFunctions` | 11 | Maximum functions per class/file |
| `NestedBlockDepth` | 4 | Maximum nesting depth |

**Compose Exceptions**:
- `@Composable` functions are excluded from `LongParameterList` checks

---

#### 2. Coroutines Rules

| Rule | Description |
|------|-------------|
| `InjectDispatcher` | Enforces dispatcher injection (prevents hardcoded dispatchers) |
| `RedundantSuspendModifier` | Detects unnecessary suspend modifiers |
| `SleepInsteadOfDelay` | Prevents Thread.sleep in coroutines |
| `SuspendFunWithFlowReturnType` | Warns about suspend functions returning Flow |

**Example - Dispatcher Injection**:
```kotlin
// BAD
class MyUseCase {
    suspend fun execute() = withContext(Dispatchers.IO) { /* ... */ }
}

// GOOD
class MyUseCase(
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) {
    suspend fun execute() = withContext(dispatcher) { /* ... */ }
}
```

---

#### 3. Potential Bugs

| Rule | Description |
|------|-------------|
| `AvoidReferentialEquality` | Warns about using `==` with String (use `.equals()`) |
| `DoubleMutabilityForCollection` | Prevents `var list = mutableListOf()` |
| `HasPlatformType` | Detects exposed platform types from Java |
| `IgnoredReturnValue` | Warns about ignored return values (e.g., Flow) |
| `UnsafeCallOnNullableType` | Detects unsafe `!!` operators |
| `UnsafeCast` | Warns about unsafe casts |
| `UnnecessaryNotNullOperator` | Detects redundant `!!` |
| `UnnecessarySafeCall` | Detects redundant `?.` |

---

#### 4. Exception Handling

| Rule | Description |
|------|-------------|
| `TooGenericExceptionCaught` | Prevents catching generic exceptions |
| `TooGenericExceptionThrown` | Prevents throwing generic exceptions |
| `SwallowedException` | Detects empty catch blocks |
| `ThrowingExceptionsWithoutMessageOrCause` | Requires exception messages |
| `PrintStackTrace` | Warns about printStackTrace() usage |

**Example**:
```kotlin
// BAD
try {
    riskyOperation()
} catch (e: Exception) { // Too generic
    e.printStackTrace() // Don't use printStackTrace
}

// GOOD
try {
    riskyOperation()
} catch (e: IOException) { // Specific exception
    logger.error("Failed to read file", e) // Proper logging
}
```

---

#### 5. Naming Conventions

| Rule | Pattern | Example |
|------|---------|---------|
| `ClassNaming` | PascalCase | `MyViewModel` |
| `FunctionNaming` | camelCase | `handleEvent()` |
| `VariableNaming` | camelCase | `viewState` |
| `PackageNaming` | lowercase | `com.example.feature` |
| `EnumNaming` | UPPER_SNAKE_CASE | `ERROR_STATE` |
| `ObjectPropertyNaming` | UPPER_SNAKE_CASE (const)<br>camelCase (other) | `MAX_SIZE`, `myValue` |

---

#### 6. Performance Rules

| Rule | Description |
|------|-------------|
| `ArrayPrimitive` | Recommends IntArray instead of Array<Int> |
| `ForEachOnRange` | Warns about forEach on ranges (use for loop) |
| `SpreadOperator` | Warns about spread operator in loops |
| `UnnecessaryTemporaryInstantiation` | Detects unnecessary object creation |

---

#### 7. Style Rules

| Rule | Description |
|------|-------------|
| `MagicNumber` | Detects hard-coded numbers (except -1, 0, 1, 2) |
| `MaxLineLength` | 170 characters max |
| `ReturnCount` | Max 2 return statements per function |
| `ThrowsCount` | Max 2 throws per function |
| `ForbiddenComment` | Prevents FIXME, TODO, STOPSHIP comments |
| `WildcardImport` | Prevents wildcard imports (except java.util.*) |
| `VarCouldBeVal` | Suggests val instead of var |
| `ExplicitItLambdaParameter` | Requires explicit lambda parameter names |

**Ignored Numbers** (not flagged as magic):
```kotlin
val numbers = listOf(-1, 0, 1, 2) // These are OK
val ratio = 0.5 // Flagged as magic number
val percentage = 100 // Flagged as magic number
```

---

#### 8. Empty Blocks

All empty blocks are flagged:
- `EmptyCatchBlock` (except `_` or `ignored` exception names)
- `EmptyFunctionBlock`
- `EmptyIfBlock`
- `EmptyWhenBlock`
- etc.

**Allowed**:
```kotlin
try {
    operation()
} catch (_: IOException) {
    // OK - exception name is "_"
} catch (ignored: NetworkException) {
    // OK - exception name is "ignored"
}
```

---

#### 9. Formatting Rules (auto-fixable)

Enabled with **autoCorrect: true**:

| Rule | Description |
|------|-------------|
| `FinalNewline` | Ensures files end with newline |
| `NoConsecutiveBlankLines` | Removes multiple blank lines |
| `NoTrailingSpaces` | Removes trailing whitespace |
| `NoSemicolons` | Removes unnecessary semicolons |
| `NoUnusedImports` | Removes unused imports |
| `NoWildcardImports` | Expands wildcard imports |
| `ChainWrapping` | Wraps chained calls properly |
| `CommentSpacing` | Ensures space after // |
| `SpacingAroundColon` | Enforces colon spacing |
| `SpacingAroundComma` | Enforces comma spacing |
| `TrailingCommaOnCallSite` | Adds trailing commas in function calls |
| `TrailingCommaOnDeclarationSite` | Adds trailing commas in declarations |

**Auto-fix these with**:
```bash
./gradlew detektFormat
```

---

#### 10. Compose-Specific Rules

Provided by `detekt-compose` plugin:

| Rule | Description |
|------|-------------|
| `ComposableNaming` | @Composable functions should be PascalCase |
| `CompositionLocalNaming` | CompositionLocals should start with "Local" |
| `ModifierMissing` | Ensures Composables accept Modifier parameter |
| `ModifierReused` | Prevents modifier reuse |
| `ModifierWithoutDefault` | Modifier parameter should have default |
| `MultipleContentEmitters` | One content emitter per Composable |
| `MutableParams` | Prevents mutable parameters in Composables |
| `ComposableEventParameterNaming` | Event params should start with "on" |
| `PreviewPublic` | @Preview functions should be private |
| `UnstableCollections` | Warns about unstable collection params |

**Example**:
```kotlin
// BAD
@Composable
fun myScreen() { /* ... */ } // Should be PascalCase

// GOOD
@Composable
fun MyScreen(
    modifier: Modifier = Modifier, // Default modifier
    onEvent: (Event) -> Unit // Event parameter naming
) {
    /* ... */
}
```

---

### Suppressing Warnings

#### 1. Suppress at Declaration Level
```kotlin
@Suppress("MagicNumber", "LongMethod")
fun complexCalculation() {
    val threshold = 42 // Magic number allowed here
    // Long function allowed here
}
```

#### 2. Suppress at File Level
```kotlin
@file:Suppress("MagicNumber")

package com.example.constants

const val MAX_ITEMS = 100 // All magic numbers allowed in this file
```

#### 3. Suppress for Tests
Many rules are automatically disabled for test files:
- `**/test/**`
- `**/androidTest/**`
- `**/commonTest/**`
- `**/jvmTest/**`

#### 4. Suppress via Configuration
Edit `config/detekt/detekt.yml`:
```yaml
style:
  MagicNumber:
    active: false  # Disable rule entirely
    ignoreNumbers:
      - '-1'
      - '0'
      - '1'
      - '2'
      - '100'  # Add custom ignore
```

---

### CI/CD Integration

#### GitHub Actions Example
```yaml
- name: Run Detekt
  run: ./gradlew detekt

- name: Upload Detekt Report
  if: always()
  uses: actions/upload-artifact@v3
  with:
    name: detekt-report
    path: build/reports/detekt/
```

#### Fail Build on Issues
In `detekt.yml`:
```yaml
build:
  maxIssues: 0  # Fail if any issues found
  warningsAsErrors: true  # Treat warnings as errors
```

---

### Best Practices

#### 1. Run Before Committing
```bash
./gradlew detektFormat detekt
```

#### 2. Fix Issues Incrementally
- Start with high-priority rules (bugs, complexity)
- Gradually enable stricter rules
- Use suppression sparingly

#### 3. Team Agreement
- Agree on which rules to enable/disable
- Document suppressions with comments
- Review configuration regularly

#### 4. IDE Integration

**Android Studio / IntelliJ IDEA**:
1. Install "Detekt" plugin
2. Configure: `Settings > Tools > Detekt`
3. Set config file: `config/detekt/detekt.yml`
4. Enable "Run Detekt on Save" (optional)

---

### Common Issues & Solutions

#### Issue: Too Many Findings
**Solution**:
- Create a baseline: `./gradlew detektBaseline`
- This creates `detekt-baseline.xml` with current issues
- Only new issues will be reported

#### Issue: False Positives
**Solution**: Use targeted suppression
```kotlin
@Suppress("ComplexMethod") // Justification: UI logic is inherently complex
fun renderComplexUi() { /* ... */ }
```

#### Issue: Compose Functions Flagged
**Solution**: Already configured in this project
```yaml
complexity:
  LongParameterList:
    ignoreAnnotated: ['Composable']
```

---

### Detekt Reports

After running `./gradlew detekt`, check:

```
build/reports/detekt/
├── detekt.html      # Open this in browser
├── detekt.xml       # For CI tools
├── detekt.txt       # Quick text overview
└── detekt.sarif     # For GitHub Code Scanning
```

**HTML Report shows**:
- Total issues by severity
- Issues grouped by rule set
- File locations with line numbers
- Rule descriptions and fixes

---

### Custom Rules (Optional)

You can write custom Detekt rules:

1. Create a Gradle module
2. Implement `Rule` interface
3. Register in `resources/META-INF/services`
4. Add as detekt plugin

**Example**:
```kotlin
class NoTodoCommentsRule : Rule(Config.empty) {
    override val issue = Issue(
        javaClass.simpleName,
        Severity.Warning,
        "TODO comments should not be committed",
        Debt.FIVE_MINS
    )

    override fun visitComment(comment: PsiComment) {
        if (comment.text.contains("TODO", ignoreCase = true)) {
            report(CodeSmell(...))
        }
    }
}
```

---

### Quick Reference

| Command | Purpose |
|---------|---------|
| `./gradlew detekt` | Run analysis |
| `./gradlew detektFormat` | Auto-fix formatting |
| `./gradlew detektBaseline` | Create baseline |
| `./gradlew detekt --auto-correct` | Run with auto-fix |

**Config File**: `config/detekt/detekt.yml`
**Reports**: `build/reports/detekt/`
**Baseline**: `detekt-baseline.xml` (if created)

---

### Integration with Pre-commit Hooks

Add to `.git/hooks/pre-commit`:
```bash
#!/bin/bash
./gradlew detektFormat detekt
if [ $? -ne 0 ]; then
    echo "Detekt found issues. Please fix before committing."
    exit 1
fi
```

Make executable:
```bash
chmod +x .git/hooks/pre-commit
```

---

## Implementation Guide

### Step 1: Define State

```kotlin
// feature-*/presentation/model/MyFeatureViewState.kt
data class MyFeatureViewState(
    val title: String = "",
    val items: LceState<List<Item>>? = null,
    val selectedItem: Item? = null
) {
    val isLoading: Boolean
        get() = items == LceState.Loading

    val hasItems: Boolean
        get() = (items as? LceState.Content)?.content?.isNotEmpty() == true
}
```

### Step 2: Define Events

```kotlin
// feature-*/presentation/model/MyFeatureEvent.kt
sealed interface MyFeatureEvent : Event {
    data class LoadItems(val forceRefresh: Boolean = false) : MyFeatureEvent
    data class SelectItem(val item: Item) : MyFeatureEvent
    data object DeleteSelectedItem : MyFeatureEvent
    data object NavigateBack : MyFeatureEvent
}
```

### Step 3: Define Effects

```kotlin
// feature-*/presentation/model/MyFeatureEffect.kt
sealed interface MyFeatureEffect : Effect {
    data class ShowToast(val message: String) : MyFeatureEffect
    data object NavigateBack : MyFeatureEffect
    data class NavigateToDetail(val itemId: String) : MyFeatureEffect
}
```

### Step 4: Create StateFactory

```kotlin
// feature-*/presentation/model/MyFeatureStateFactory.kt
class MyFeatureStateFactory {
    fun createState(items: List<ItemDomain>): MyFeatureViewState {
        return MyFeatureViewState(
            items = LceState.Content(
                items.map { it.toUiModel() }
            )
        )
    }

    fun createErrorState(error: AppError): MyFeatureViewState {
        return MyFeatureViewState(
            items = LceState.Error(error)
        )
    }
}
```

### Step 5: Create UseCase

```kotlin
// domain-*/domain/usecase/GetItemsUseCase.kt
class GetItemsUseCase internal constructor(
    private val repository: MyRepository,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) {
    suspend operator fun invoke(forceRefresh: Boolean = false) =
        withContext(dispatcher) {
            repository.getItems(forceRefresh)
        }
}
```

### Step 6: Create ViewModel

```kotlin
// feature-*/presentation/MyFeatureViewModel.kt
class MyFeatureViewModel(
    private val getItemsUseCase: GetItemsUseCase,
    private val stateFactory: MyFeatureStateFactory
) : ViewModel(), EventHandler<MyFeatureEvent> {

    private val _viewState = MutableStateFlow(MyFeatureViewState())
    val viewState: StateFlow<MyFeatureViewState> = _viewState
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = MyFeatureViewState()
        )

    private val _effects = MutableSharedFlow<MyFeatureEffect>(
        extraBufferCapacity = 1
    )
    val effects = _effects.asSharedFlow()

    init {
        loadItems()
    }

    override fun obtainEvent(event: MyFeatureEvent) {
        when (event) {
            is MyFeatureEvent.LoadItems -> loadItems(event.forceRefresh)
            is MyFeatureEvent.SelectItem -> selectItem(event.item)
            MyFeatureEvent.DeleteSelectedItem -> deleteSelectedItem()
            MyFeatureEvent.NavigateBack -> {
                _effects.tryEmit(MyFeatureEffect.NavigateBack)
            }
        }
    }

    private fun loadItems(forceRefresh: Boolean = false) {
        viewModelScope.launch {
            _viewState.update {
                it.copy(items = LceState.Loading)
            }

            when (val result = getItemsUseCase(forceRefresh)) {
                is ApiResponse.Success -> {
                    _viewState.update {
                        stateFactory.createState(result.data)
                    }
                }
                is ApiResponse.Error -> {
                    _viewState.update {
                        stateFactory.createErrorState(result.error)
                    }
                }
            }
        }
    }

    private fun selectItem(item: Item) {
        _viewState.update { it.copy(selectedItem = item) }
        _effects.tryEmit(MyFeatureEffect.NavigateToDetail(item.id))
    }

    private fun deleteSelectedItem() {
        viewModelScope.launch {
            _viewState.value.selectedItem?.let { item ->
                // Delete logic here
                _effects.tryEmit(
                    MyFeatureEffect.ShowToast("Item deleted")
                )
            }
        }
    }
}
```

### Step 7: Create UI (Composable)

```kotlin
// feature-*/presentation/MyFeatureScreen.kt
@Composable
fun MyFeatureScreen(
    state: MyFeatureViewState,
    event: (MyFeatureEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(state.title) },
                navigationIcon = {
                    IconButton(onClick = { event(MyFeatureEvent.NavigateBack) }) {
                        Icon(Icons.Default.ArrowBack, "Back")
                    }
                }
            )
        }
    ) { padding ->
        LceViewState(
            state = state.items ?: LceState.Loading,
            loading = { CircularProgressIndicator() },
            errorContent = { error ->
                ErrorView(
                    error = error,
                    onRetry = { event(MyFeatureEvent.LoadItems(true)) }
                )
            },
            content = { items ->
                ItemsList(
                    items = items,
                    onItemClick = { event(MyFeatureEvent.SelectItem(it)) }
                )
            }
        )
    }
}
```

### Step 8: Create Navigation Screen

```kotlin
// feature-*/presentation/MyFeatureNavScreen.kt
fun NavGraphBuilder.myFeatureNavScreen(
    onNavigateBack: () -> Unit
) {
    composable<Route.MyFeature> {
        val viewModel: MyFeatureViewModel = koinViewModel()

        val state by viewModel.viewState.collectAsStateWithLifecycle()

        LaunchedEffect(Unit) {
            viewModel.effects.collect { effect ->
                when (effect) {
                    is MyFeatureEffect.NavigateBack -> onNavigateBack()
                    is MyFeatureEffect.ShowToast -> {
                        // Show toast
                    }
                    is MyFeatureEffect.NavigateToDetail -> {
                        // Navigate to detail
                    }
                }
            }
        }

        MyFeatureScreen(
            state = state,
            event = viewModel::obtainEvent
        )
    }
}
```

### Step 9: Setup Dependency Injection

```kotlin
// feature-*/di/KoinModule.kt
val myFeatureModule = module {
    factoryOf(::MyFeatureStateFactory)
    viewModelOf(::MyFeatureViewModel)
}

// domain-*/di/KoinModule.kt
val myDomainModule = module {
    single { GetItemsUseCase(get(), get(named(IoDispatcher))) }
}

// data-*/di/KoinModule.kt
val myDataModule = module {
    single<MyRepository> { MyRepositoryImpl(get()) }
}
```

---

## Testing Guidelines

### ViewModel Testing

```kotlin
class MyFeatureViewModelTest : KoinTest {

    private lateinit var viewModel: MyFeatureViewModel
    private lateinit var getItemsUseCase: GetItemsUseCase

    @BeforeTest
    fun setup() {
        getItemsUseCase = mock()
        viewModel = MyFeatureViewModel(
            getItemsUseCase = getItemsUseCase,
            stateFactory = MyFeatureStateFactory()
        )
    }

    @Test
    fun `when ViewModel starts, loading state is emitted`() = runTest {
        viewModel.viewState.test {
            val state = awaitItem()
            assertEquals(LceState.Loading, state.items)
        }
    }

    @Test
    fun `when loadItems succeeds, content state is emitted`() = runTest {
        // Given
        val items = listOf(/* test items */)
        every { getItemsUseCase() } returns ApiResponse.Success(items)

        // When
        viewModel.obtainEvent(MyFeatureEvent.LoadItems())

        // Then
        viewModel.viewState.test {
            val state = awaitItem()
            assertTrue(state.items is LceState.Content)
            assertEquals(items.size, (state.items as LceState.Content).content.size)
        }
    }

    @Test
    fun `when SelectItem event is sent, effect is emitted`() = runTest {
        // Given
        val item = /* test item */

        // When
        viewModel.obtainEvent(MyFeatureEvent.SelectItem(item))

        // Then
        viewModel.effects.test {
            val effect = awaitItem()
            assertTrue(effect is MyFeatureEffect.NavigateToDetail)
        }
    }
}
```

### StateFactory Testing

```kotlin
class MyFeatureStateFactoryTest {

    private val factory = MyFeatureStateFactory()

    @Test
    fun `createState maps domain items to UI items`() {
        // Given
        val domainItems = listOf(/* domain items */)

        // When
        val state = factory.createState(domainItems)

        // Then
        assertTrue(state.items is LceState.Content)
        assertEquals(domainItems.size, state.items.content.size)
    }

    @Test
    fun `createErrorState creates error state`() {
        // Given
        val error = AppError.Network.NoInternet

        // When
        val state = factory.createErrorState(error)

        // Then
        assertTrue(state.items is LceState.Error)
        assertEquals(error, (state.items as LceState.Error).appError)
    }
}
```

### UseCase Testing

```kotlin
class GetItemsUseCaseTest {

    private lateinit var repository: MyRepository
    private lateinit var useCase: GetItemsUseCase

    @BeforeTest
    fun setup() {
        repository = mock()
        useCase = GetItemsUseCase(
            repository = repository,
            dispatcher = StandardTestDispatcher()
        )
    }

    @Test
    fun `invoke calls repository and returns items`() = runTest {
        // Given
        val expectedItems = listOf(/* items */)
        every { repository.getItems(any()) } returns ApiResponse.Success(expectedItems)

        // When
        val result = useCase(forceRefresh = false)

        // Then
        assertTrue(result is ApiResponse.Success)
        assertEquals(expectedItems, result.data)
    }
}
```

---

## Best Practices

### 1. State Management

**DO:**
- Keep states immutable
- Use `data class` for states
- Include all UI information in state
- Use computed properties for derived state
- Use `LceState` for async data

**DON'T:**
- Mutate state directly
- Store ViewModels or callbacks in state
- Include business logic in state classes
- Use nullable state when not needed

### 2. Events

**DO:**
- Use sealed interface for type safety
- Name events as user actions (imperative)
- Keep events simple and focused
- Include all necessary data in event

**DON'T:**
- Include business logic in events
- Use events for internal ViewModel communication
- Name events as states (use verbs, not nouns)

### 3. Effects

**DO:**
- Use for one-time events only
- Use `SharedFlow` with `extraBufferCapacity`
- Collect effects in `LaunchedEffect`
- Name effects descriptively

**DON'T:**
- Use effects to update UI state
- Store effects in state
- Collect effects multiple times
- Use effects for continuous data

### 4. ViewModel

**DO:**
- Implement `EventHandler<Event>`
- Expose state as `StateFlow` (immutable)
- Use `WhileSubscribed(5000)` for lifecycle awareness
- Delegate business logic to UseCases
- Use StateFactories for state creation
- Handle errors gracefully

**DON'T:**
- Expose `MutableStateFlow` directly
- Include network/DB calls directly
- Create states manually (use factories)
- Leak coroutines (use `viewModelScope`)

### 5. Reactive Patterns

**DO:**
```kotlin
// Combine multiple flows
val combinedState = combine(
    flow1,
    flow2,
    flow3
) { data1, data2, data3 ->
    createState(data1, data2, data3)
}.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), initialState)

// Transform flows
val transformedState = sourceFlow
    .map { data -> transformData(data) }
    .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), initialState)
```

### 6. Error Handling

**DO:**
```kotlin
when (val result = useCase()) {
    is ApiResponse.Success -> {
        _viewState.update {
            stateFactory.createSuccessState(result.data)
        }
    }
    is ApiResponse.Error -> {
        _viewState.update {
            stateFactory.createErrorState(result.error)
        }
    }
}
```

**DON'T:**
```kotlin
// Don't ignore errors
val result = useCase()
_viewState.update { createState(result.data!!) } // Can crash!
```

### 7. Loading & Refreshing

**DO:**
```kotlin
private fun loadData(forceRefresh: Boolean = false) {
    viewModelScope.launch {
        val currentContent = (_viewState.value.data as? LceState.Content)?.content

        _viewState.update {
            it.copy(
                data = if (currentContent != null && forceRefresh) {
                    LceState.Refreshing(currentContent)
                } else {
                    LceState.Loading
                }
            )
        }

        // Load data...
    }
}
```

### 8. Debouncing (Search)

**DO:**
```kotlin
private val searchQuery = MutableStateFlow("")

val searchResults = searchQuery
    .debounce(500) // Wait 500ms after user stops typing
    .filter { it.length >= 3 } // Only search if 3+ chars
    .flatMapLatest { query ->
        flow {
            emit(LceState.Loading)
            val result = searchUseCase(query)
            emit(LceState.Content(result))
        }
    }
    .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), LceState.Loading)
```

### 9. Navigation

**DO:**
```kotlin
// Emit navigation effect
override fun obtainEvent(event: MyEvent) {
    when (event) {
        MyEvent.NavigateToDetail -> {
            _effects.tryEmit(MyEffect.NavigateToDetail)
        }
    }
}

// Handle in UI
LaunchedEffect(Unit) {
    viewModel.effects.collect { effect ->
        when (effect) {
            MyEffect.NavigateToDetail -> navController.navigate(...)
        }
    }
}
```

**DON'T:**
```kotlin
// Don't pass NavController to ViewModel
class MyViewModel(
    private val navController: NavController // BAD!
)
```

### 10. Dependency Injection

**DO:**
```kotlin
// Module definition
val myModule = module {
    factoryOf(::MyStateFactory)
    single { MyUseCase(get(), get(named(IoDispatcher))) }
    viewModelOf(::MyViewModel)
}

// ViewModel injection
val viewModel: MyViewModel = koinViewModel()
```

### 11. Testing

**DO:**
- Test ViewModels with different scenarios
- Test StateFactories in isolation
- Use Turbine for Flow testing
- Mock dependencies (UseCases, Repositories)
- Test error cases

**DON'T:**
- Test implementation details
- Test framework code (Compose, Flow)
- Use real repositories in ViewModel tests

---

## Common Patterns

### Pull-to-Refresh

```kotlin
val isRefreshing = MutableStateFlow(false)

fun refresh() {
    viewModelScope.launch {
        isRefreshing.value = true
        loadData(forceRefresh = true)
        isRefreshing.value = false
    }
}
```

### Pagination

```kotlin
data class PaginatedState<T>(
    val items: List<T> = emptyList(),
    val isLoading: Boolean = false,
    val hasMore: Boolean = true,
    val error: AppError? = null
)

fun loadNextPage() {
    if (state.value.isLoading || !state.value.hasMore) return

    viewModelScope.launch {
        _state.update { it.copy(isLoading = true) }

        when (val result = getPageUseCase(page = currentPage)) {
            is ApiResponse.Success -> {
                _state.update {
                    it.copy(
                        items = it.items + result.data,
                        isLoading = false,
                        hasMore = result.data.isNotEmpty()
                    )
                }
                currentPage++
            }
            is ApiResponse.Error -> {
                _state.update {
                    it.copy(isLoading = false, error = result.error)
                }
            }
        }
    }
}
```

### Swipe-to-Delete

```kotlin
sealed interface ListEvent : Event {
    data class DeleteItem(val item: Item) : ListEvent
    data class UndoDelete(val item: Item) : ListEvent
}

override fun obtainEvent(event: ListEvent) {
    when (event) {
        is ListEvent.DeleteItem -> {
            viewModelScope.launch {
                deleteItemUseCase(event.item)
                _effects.tryEmit(
                    ListEffect.ShowSnackbar(
                        message = "Item deleted",
                        action = "Undo"
                    )
                )
            }
        }
        is ListEvent.UndoDelete -> {
            viewModelScope.launch {
                restoreItemUseCase(event.item)
            }
        }
    }
}
```

---

## Checklist for New Features

- [ ] Define State data class
- [ ] Define Events sealed interface
- [ ] Define Effects sealed interface (if needed)
- [ ] Create StateFactory
- [ ] Create UseCase(s) in domain layer
- [ ] Create Repository interface in domain
- [ ] Implement Repository in data layer
- [ ] Create ViewModel with EventHandler
- [ ] Create Composable UI
- [ ] Create Navigation screen
- [ ] Setup Koin modules
- [ ] Write ViewModel tests
- [ ] Write StateFactory tests
- [ ] Write UseCase tests
- [ ] Document any special behavior

---

## References

### Key Files

- Event interface: `libs/ui/src/commonMain/kotlin/gi/aera/ui/Event.kt`
- EventHandler interface: `libs/ui/src/commonMain/kotlin/gi/aera/ui/EventHandler.kt`
- Effect interface: `libs/ui/src/commonMain/kotlin/gi/aera/ui/Effect.kt`
- LceState: `libs/ui/src/commonMain/kotlin/gi/aera/ui/LceState.kt`
- ApiResponse: `libs/common/src/commonMain/kotlin/gi/aera/common/model/ApiResponse.kt`
- AppError: `libs/common/src/commonMain/kotlin/gi/aera/common/model/AppError.kt`

### Example Implementations

- Simple feature: `feature-app-settings/`
- Complex feature: `feature-weather-forecast/`
- Search with debounce: `feature-search-location/`

---

## Conclusion

This MVI architecture provides:

- **Predictability**: Unidirectional data flow
- **Testability**: Isolated components
- **Scalability**: Clear separation of concerns
- **Maintainability**: Consistent patterns
- **Type Safety**: Sealed interfaces and immutable states

Follow these guidelines to maintain consistency and quality across the codebase.