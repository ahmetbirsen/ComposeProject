# Test Dokümantasyonu

Bu proje için kapsamlı test yapısı oluşturulmuştur. Testler aynı mimari yapıda (Data, Domain, Presentation) organize edilmiştir.

## Test Yapısı

### 1. Unit Tests (`app/src/test/`)

#### Data Layer Tests
- **HomeRepositoryImplTest**: Repository implementasyonunun testleri
  - API çağrılarının doğru yapıldığını kontrol eder
  - Database işlemlerinin doğru çağrıldığını doğrular
  - Mapping işlemlerinin doğru çalıştığını test eder

#### Domain Layer Tests
- **HomeUseCasesTest**: Use case'lerin testleri
  - Repository metodlarının doğru çağrıldığını kontrol eder
  - Business logic'in doğru çalıştığını doğrular

#### Presentation Layer Tests
- **HomeViewModelTest**: ViewModel'in testleri
  - State management'in doğru çalıştığını kontrol eder
  - User action'ların doğru handle edildiğini test eder
  - Event emission'ların doğru yapıldığını doğrular

### 2. UI Tests (`app/src/androidTest/`)

#### Component Tests
- **HomeScreenTest**: Compose UI component'lerinin testleri
  - UI elementlerinin doğru display edildiğini kontrol eder
  - User interaction'ların doğru çalıştığını test eder

#### Integration Tests
- **HomeScreenIntegrationTest**: End-to-end testler
  - Tüm katmanların birlikte çalıştığını doğrular
  - Real user scenario'larını test eder

## Test Dependencies

### Unit Test Dependencies
- **JUnit 4**: Test framework
- **MockK**: Mocking library
- **Coroutines Test**: Coroutines testing utilities
- **Turbine**: Flow testing
- **Architecture Core Testing**: Architecture components testing

### UI Test Dependencies
- **Compose UI Test**: Compose UI testing
- **Espresso**: UI automation
- **Hilt Testing**: Dependency injection testing

## Test Çalıştırma

### Unit Tests
```bash
# Tüm unit testleri çalıştır
./gradlew test

# Sadece home feature testlerini çalıştır
./gradlew test --tests "*Home*"
```

### UI Tests
```bash
# Tüm UI testleri çalıştır
./gradlew connectedAndroidTest

# Sadece home feature UI testlerini çalıştır
./gradlew connectedAndroidTest --tests "*Home*"
```

### Coverage Raporu
```bash
# Test coverage raporu oluştur
./gradlew createDebugCoverageReport
```

## Test Best Practices

### 1. Test Naming Convention
- Test metodları `should_expectedBehavior_when_condition` formatında yazılır
- Örnek: `shouldReturnSuccess_whenApiCallSucceeds`

### 2. Test Structure (AAA Pattern)
- **Arrange**: Test verilerini hazırla
- **Act**: Test edilecek metodu çağır
- **Assert**: Sonuçları doğrula

### 3. Mocking Strategy
- External dependencies mock'lanır
- Internal dependencies real implementation kullanır
- Test isolation sağlanır

### 4. Test Data
- Test data'ları her test için ayrı oluşturulur
- Realistic test data kullanılır
- Edge case'ler test edilir

## Test Coverage Hedefleri

- **Unit Tests**: %80+ coverage
- **UI Tests**: Critical user flows
- **Integration Tests**: End-to-end scenarios

## Test Maintenance

### 1. Test Güncelleme
- Code değişikliklerinde testler güncellenir
- Breaking changes'de testler revize edilir

### 2. Test Performance
- Testler hızlı çalışmalı
- Unnecessary setup'lar kaldırılır
- Parallel execution kullanılır

### 3. Test Reliability
- Flaky testler düzeltilir
- Deterministic testler yazılır
- Environment dependencies minimize edilir

## Örnek Test Senaryoları

### 1. Success Scenarios
- API çağrısı başarılı olduğunda data doğru display edilir
- User action başarılı olduğunda UI güncellenir

### 2. Error Scenarios
- Network error durumunda error state gösterilir
- Invalid data durumunda validation çalışır

### 3. Edge Cases
- Empty data durumları
- Loading states
- Network timeout scenarios

## Test Debugging

### 1. Unit Test Debugging
```kotlin
@Test
fun debugTest() {
    // Debug point koy
    println("Debug: $variable")
    // Test logic
}
```

### 2. UI Test Debugging
```kotlin
@Test
fun debugUITest() {
    composeTestRule.onNodeWithText("Debug Text").assertIsDisplayed()
    // Screenshot al
    composeTestRule.onRoot().captureToImage()
}
```

Bu test yapısı, uygulamanın kalitesini artırır ve regression'ları önler. 