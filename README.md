# ComposeProject

A modern Android e-commerce application built with Jetpack Compose, following Clean Architecture principles and MVVM pattern.

## 🏗️ Architecture

### Clean Architecture + MVVM

This project follows Clean Architecture principles with a clear separation of concerns across three main layers:

#### **Presentation Layer**
- **Technology**: Jetpack Compose + MVVM
- **Components**: 
  - `HomeViewModel`, `BasketViewModel`, `DetailViewModel`
  - Compose UI screens and components
  - Navigation using Compose Navigation
- **Responsibilities**: UI logic, user interactions, state management

#### **Domain Layer**
- **Components**: Use Cases, Domain Models, Repository Interfaces
- **Responsibilities**: Business logic, data transformation, domain rules
- **Key Features**:
  - `HomeUseCases`: Product listing and management
  - `BasketUseCases`: Shopping cart operations
  - Domain models for UI representation

#### **Data Layer**
- **Components**: Repository Implementations, Data Sources, Mappers
- **Responsibilities**: Data access, API calls, local storage
- **Key Features**:
  - `HomeRepositoryImpl`: Remote and local data management
  - `BasketRepositoryImpl`: Local database operations
  - Room database for offline storage

### Core Architecture Decisions

#### **1. Base Classes and Inheritance**
- `CoreViewModel`: Centralized loading and error handling
- `BaseRepository`: Common network request handling
- `BaseMapper`: Standardized data transformation

#### **2. Dependency Injection**
- **Framework**: Hilt for Android
- **Benefits**: 
  - Testability through dependency injection
  - Singleton management
  - Compile-time dependency validation

#### **3. State Management**
- **Pattern**: StateFlow + StateFlow for reactive UI updates
- **Global State**: Network loading states and error handling
- **Local State**: Feature-specific UI states

#### **4. Navigation**
- **Framework**: Compose Navigation with type-safe routes
- **Features**: 
  - Shared element transitions
  - Custom animations
  - Deep linking support

## 🚀 Setup and Run Instructions

### Prerequisites
- Android Studio Hedgehog (2023.1.1) or later
- JDK 11 or higher
- Android SDK 35 (API Level 35)
- Minimum SDK: 24 (Android 7.0)

### Installation Steps

1. **Clone the repository**
   ```bash
   git clone <repository-url>
   cd ComposeProject
   ```

2. **Open in Android Studio**
   - Open Android Studio
   - Select "Open an existing project"
   - Navigate to the project directory and select it

3. **Sync Gradle**
   - Wait for the initial Gradle sync to complete
   - If prompted, update Gradle wrapper

4. **Configure Build Variants**
   - **Debug**: Development environment with debugging enabled
   - **Release**: Production environment with optimizations
   - **Dev**: Custom development variant with `.dev` suffix

5. **Run the Application**
   - Select your target device (emulator or physical device)
   - Click the "Run" button or press `Shift + F10`

### Build Configuration

The project uses Gradle Version Catalogs for dependency management:

```kotlin
// Top-level build.gradle.kts
plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.stack.hilt.plugin)
    alias(libs.plugins.stack.ksp)
}
```



## 🧪 Test Execution Guidelines

### Unit Tests

Run unit tests using:
```bash
./gradlew test
```

**Test Structure:**
- **Location**: `app/src/test/java/`
- **Framework**: JUnit 4 + MockK + Turbine
- **Coverage**: ViewModels, Use Cases, Repositories

**Example Test:**
```kotlin
@Test
fun `onRefresh should call getVerticalProducts and getSuggestedProducts`() = runTest {
    // Given
    coEvery { homeUseCases.getVerticalProducts() } returns flowOf(RestResult.Success(mockData))
    
    // When
    homeViewModel.onAction(HomeAction.OnRefresh)
    
    // Then
    coVerify { homeUseCases.getVerticalProducts() }
}
```

### Instrumented Tests

Run instrumented tests using:
```bash
./gradlew connectedAndroidTest
```

**Test Structure:**
- **Location**: `app/src/androidTest/java/`
- **Framework**: Espresso + Compose Testing
- **Coverage**: UI components, screen interactions

**Example Test:**
```kotlin
@Test
fun homeScreen_shouldDisplayTitle() {
    composeTestRule.setContent {
        HomeScreen(state = state, onAction = onAction)
    }
    composeTestRule.onNodeWithText("Ürünler").assertIsDisplayed()
}
```

### Test Configuration

**Hilt Test Setup:**
```kotlin
@HiltAndroidTest
class HomeScreenIntegrationTest {
    @get:Rule
    val hiltRule = HiltAndroidRule(this)
}
```

**Test Dependencies:**
- `mockk`: Mocking framework
- `turbine`: Flow testing
- `coroutines-test`: Coroutine testing utilities
- `androidx.core-testing`: Architecture components testing

## 📚 Libraries Used and Reasoning

### Core Android Libraries

| Library | Version | Purpose | Reasoning |
|---------|---------|---------|-----------|
| **AndroidX Core KTX** | 1.16.0 | Core Android utilities | Provides Kotlin extensions for Android APIs |
| **Lifecycle Runtime** | 2.9.1 | Lifecycle management | Handles component lifecycle events |
| **Activity Compose** | 1.10.1 | Compose integration | Enables Compose in Activities |

### UI and Compose

| Library | Version | Purpose | Reasoning |
|---------|---------|---------|-----------|
| **Jetpack Compose BOM** | 2025.06.01 | Compose dependency management | Ensures compatible Compose library versions |
| **Material 3** | Latest | Material Design components | Modern Material Design implementation |
| **Compose Navigation** | 2.9.1 | Navigation framework | Type-safe navigation with Compose |
| **Accompanist** | 0.28.0 | Compose utilities | Additional Compose utilities (shimmer, system UI) |

### Dependency Injection

| Library | Version | Purpose | Reasoning |
|---------|---------|---------|-----------|
| **Hilt** | 2.48 | Dependency injection | Google's recommended DI solution for Android |
| **Hilt Navigation Compose** | 1.2.0 | Compose integration | Seamless DI integration with Compose Navigation |

### Networking

| Library | Version | Purpose | Reasoning |
|---------|---------|---------|-----------|
| **Retrofit** | 2.11.0 | HTTP client | Type-safe HTTP client with excellent Kotlin support |
| **OkHttp** | 4.12.0 | HTTP client engine | Efficient HTTP client with interceptors |
| **Kotlinx Serialization** | 1.7.3 | JSON serialization | Kotlin-first JSON serialization |

### Local Storage

| Library | Version | Purpose | Reasoning |
|---------|---------|---------|-----------|
| **Room** | 2.5.2 | Local database | Android's recommended local database solution |
| **DataStore** | 1.0.0 | Preferences storage | Modern replacement for SharedPreferences |

### Image Loading

| Library | Version | Purpose | Reasoning |
|---------|---------|---------|-----------|
| **Coil** | 2.6.0 | Image loading | Kotlin-first image loading library with Compose support |

### Testing

| Library | Version | Purpose | Reasoning |
|---------|---------|---------|-----------|
| **JUnit** | 4.13.2 | Unit testing | Standard Java testing framework |
| **MockK** | 1.13.8 | Mocking | Kotlin-first mocking library |
| **Turbine** | 1.0.0 | Flow testing | Testing utilities for Kotlin Flows |
| **Espresso** | 3.6.1 | UI testing | Android's standard UI testing framework |

### Utilities

| Library | Version | Purpose | Reasoning |
|---------|---------|---------|-----------|
| **Coroutines** | 1.6.4 | Asynchronous programming | Kotlin's coroutines for async operations |
| **Gson** | 2.10.1 | JSON parsing | Legacy JSON parsing (consider migration to Kotlinx Serialization) |
| **Moshi** | 1.13.0 | JSON parsing | Alternative JSON parser |

## 🔧 Decisions Made and Trade-offs

### Architecture Decisions

#### **1. Clean Architecture vs Simple Architecture**
- **Decision**: Clean Architecture with clear layer separation
- **Trade-off**: More boilerplate code but better maintainability and testability
- **Benefit**: Easier to test, maintain, and scale

#### **2. MVVM vs MVI**
- **Decision**: MVVM with StateFlow
- **Trade-off**: Simpler than MVI but less predictable state management
- **Benefit**: Familiar pattern, easier onboarding for new developers

#### **3. Compose Navigation vs Traditional Navigation**
- **Decision**: Compose Navigation with type-safe routes
- **Trade-off**: Learning curve but better type safety
- **Benefit**: Compile-time navigation safety, better integration with Compose

### Technology Decisions

#### **1. Hilt vs Koin**
- **Decision**: Hilt (Google's official DI solution)
- **Trade-off**: More opinionated but better integration with Android ecosystem
- **Benefit**: Compile-time validation, better tooling support

#### **2. Room vs SQLite**
- **Decision**: Room with Kotlin coroutines
- **Trade-off**: Abstraction layer but better developer experience
- **Benefit**: Type-safe queries, compile-time validation

#### **3. Retrofit vs Ktor**
- **Decision**: Retrofit with OkHttp
- **Trade-off**: More mature but less Kotlin-native
- **Benefit**: Extensive ecosystem, proven reliability

### Performance Decisions

#### **1. StateFlow vs LiveData**
- **Decision**: StateFlow for reactive programming
- **Trade-off**: Kotlin-specific but better coroutine integration
- **Benefit**: Better integration with Compose and coroutines

#### **2. Coil vs Glide**
- **Decision**: Coil for image loading
- **Trade-off**: Newer library but Kotlin-first design
- **Benefit**: Better Compose integration, smaller APK size

### Testing Decisions

#### **1. MockK vs Mockito**
- **Decision**: MockK for mocking
- **Trade-off**: Kotlin-specific but better Kotlin support
- **Benefit**: Better syntax for Kotlin, coroutine support

#### **2. Turbine vs RxJava TestScheduler**
- **Decision**: Turbine for Flow testing
- **Trade-off**: Newer library but Flow-native
- **Benefit**: Better integration with Kotlin Flows

## 📱 Features

### Core Features
- **Product Listing**: Horizontal and vertical product displays
- **Shopping Cart**: Add/remove items, quantity management
- **Product Details**: Detailed product information
- **Offline Support**: Local database caching
- **Pull-to-Refresh**: Data refresh functionality

### UI/UX Features
- **Material Design 3**: Modern Material Design implementation
- **Dark/Light Theme**: Dynamic theme support
- **Smooth Animations**: Shared element transitions
- **Loading States**: Multiple loading indicators
- **Error Handling**: Global error management

### Technical Features
- **Type-Safe Navigation**: Compile-time navigation safety
- **Dependency Injection**: Hilt-based DI
- **Reactive Programming**: StateFlow-based state management
- **Offline First**: Local database with remote sync
- **Test Coverage**: Comprehensive unit and UI tests

## 🏗️ Project Structure

```
app/src/main/java/com/example/composeproject/
├── core/                           # Core utilities and base classes
│   ├── BaseMapper.kt
│   ├── BaseRepository.kt
│   ├── CoreViewModel.kt
│   ├── extension/                  # Extension functions
│   ├── model/                      # Core data models
│   ├── network/                    # Network layer
│   ├── qualifiers/                 # Hilt qualifiers
│   └── ui/                         # Core UI utilities
├── designsysytem/                  # Design system components
│   ├── components/                 # Reusable UI components
│   ├── sections/                   # UI sections
│   └── theme/                      # Theme and styling
├── di/                            # Dependency injection modules
├── feature/                       # Feature modules
│   ├── basket/                    # Shopping cart feature
│   ├── detail/                    # Product detail feature
│   └── home/                      # Home screen feature
├── navigation/                    # Navigation configuration
├── util/                          # Utility classes
├── CoreApp.kt                     # Application class
├── MainActivity.kt                # Main activity
└── MainScreen.kt                  # Main screen composable
```

## 🚀 Future Improvements

### Planned Enhancements
1. **Migration to Kotlinx Serialization**: Replace Gson with Kotlinx Serialization
2. **Enhanced Error Handling**: More granular error types and user-friendly messages
3. **Performance Optimization**: Image caching, lazy loading
4. **Accessibility**: Screen reader support, content descriptions
5. **Internationalization**: Multi-language support

### Technical Debt
1. **Test Coverage**: Increase test coverage to 90%+
2. **Documentation**: Add KDoc comments for public APIs
3. **Code Quality**: Implement stricter linting rules
4. **Performance**: Add performance monitoring and optimization

## 📄 License

This project is licensed under the MIT License - see the LICENSE file for details.

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add some amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

## 📞 Support

For support and questions, please contact the development team or create an issue in the repository. 