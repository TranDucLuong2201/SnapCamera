# 🛠️ Gradle Setup Guide - SnapCamera

## 📦 Version Catalogs (libs.versions.toml)

```toml
[versions]
# Android & Kotlin
agp = "8.2.0"
kotlin = "1.9.20"
kotlinCompilerExtension = "1.5.6"
kotlinSerialization = "1.6.0"

# Compose
composeBom = "2024.01.00"
composeCompiler = "1.5.6"
composeMaterial3 = "1.1.2"
composeNavigation = "2.7.6"
composeViewModel = "2.7.0"
composeHiltNavigation = "1.1.0"

# Core Android
coreKtx = "1.12.0"
lifecycle = "2.7.0"
activityCompose = "1.8.2"
fragmentKtx = "1.6.2"

# Database
room = "2.6.1"
datastore = "1.0.0"

# Networking
retrofit = "2.9.0"
okhttp = "4.12.0"
kotlinxSerialization = "1.6.0"

# Dependency Injection
hilt = "2.48.1"
hiltCompiler = "1.1.0"

# Firebase
firebaseBom = "32.7.0"

# Camera & Media
camerax = "1.3.1"
media3 = "1.2.0"
exifinterface = "1.3.6"

# UI/UX
coil = "2.5.0"
lottie = "6.3.0"
accompanist = "0.32.0"
materialIconsExtended = "1.6.0"

# Background Processing
workManager = "2.9.0"

# Testing
junit = "4.13.2"
androidxTestExt = "1.1.5"
androidxTestEspresso = "3.5.1"
composeTest = "1.5.4"
mockk = "1.13.8"
coroutinesTest = "1.7.3"

# Code Quality
spotless = "6.23.3"
detekt = "1.23.4"
ktlint = "0.50.0"

[libraries]
# Android Core
androidx-core-ktx = { group = "androidx.core", name = "core-ktx", version.ref = "coreKtx" }
androidx-lifecycle-runtime-ktx = { group = "androidx.lifecycle", name = "lifecycle-runtime-ktx", version.ref = "lifecycle" }
androidx-lifecycle-viewmodel-ktx = { group = "androidx.lifecycle", name = "lifecycle-viewmodel-ktx", version.ref = "lifecycle" }
androidx-lifecycle-viewmodel-compose = { group = "androidx.lifecycle", name = "lifecycle-viewmodel-compose", version.ref = "lifecycle" }
androidx-activity-compose = { group = "androidx.activity", name = "activity-compose", version.ref = "activityCompose" }
androidx-fragment-ktx = { group = "androidx.fragment", name = "fragment-ktx", version.ref = "fragmentKtx" }

# Compose
compose-bom = { group = "androidx.compose", name = "compose-bom", version.ref = "composeBom" }
compose-ui = { group = "androidx.compose.ui", name = "ui" }
compose-ui-graphics = { group = "androidx.compose.ui", name = "ui-graphics" }
compose-ui-tooling = { group = "androidx.compose.ui", name = "ui-tooling" }
compose-ui-tooling-preview = { group = "androidx.compose.ui", name = "ui-tooling-preview" }
compose-ui-test-manifest = { group = "androidx.compose.ui", name = "ui-test-manifest" }
compose-ui-test-junit4 = { group = "androidx.compose.ui", name = "ui-test-junit4" }
compose-material3 = { group = "androidx.compose.material3", name = "material3", version.ref = "composeMaterial3" }
compose-material-icons-extended = { group = "androidx.compose.material", name = "material-icons-extended", version.ref = "materialIconsExtended" }

# Navigation
compose-navigation = { group = "androidx.navigation", name = "navigation-compose", version.ref = "composeNavigation" }
compose-hilt-navigation = { group = "androidx.hilt", name = "hilt-navigation-compose", version.ref = "composeHiltNavigation" }

# Database
room-runtime = { group = "androidx.room", name = "room-runtime", version.ref = "room" }
room-ktx = { group = "androidx.room", name = "room-ktx", version.ref = "room" }
room-compiler = { group = "androidx.room", name = "room-compiler", version.ref = "room" }
datastore-preferences = { group = "androidx.datastore", name = "datastore-preferences", version.ref = "datastore" }

# Networking
retrofit = { group = "com.squareup.retrofit2", name = "retrofit", version.ref = "retrofit" }
retrofit-kotlinx-serialization = { group = "com.jakewharton.retrofit", name = "retrofit2-kotlinx-serialization-converter", version = "1.0.0" }
okhttp = { group = "com.squareup.okhttp3", name = "okhttp", version.ref = "okhttp" }
okhttp-logging = { group = "com.squareup.okhttp3", name = "logging-interceptor", version.ref = "okhttp" }
kotlinx-serialization-json = { group = "org.jetbrains.kotlinx", name = "kotlinx-serialization-json", version.ref = "kotlinxSerialization" }

# Dependency Injection
hilt-android = { group = "com.google.dagger", name = "hilt-android", version.ref = "hilt" }
hilt-compiler = { group = "com.google.dagger", name = "hilt-compiler", version.ref = "hilt" }
hilt-work = { group = "androidx.hilt", name = "hilt-work", version = "1.1.0" }

# Firebase
firebase-bom = { group = "com.google.firebase", name = "firebase-bom", version.ref = "firebaseBom" }
firebase-auth = { group = "com.google.firebase", name = "firebase-auth" }
firebase-firestore = { group = "com.google.firebase", name = "firebase-firestore" }
firebase-storage = { group = "com.google.firebase", name = "firebase-storage" }
firebase-messaging = { group = "com.google.firebase", name = "firebase-messaging" }
firebase-analytics = { group = "com.google.firebase", name = "firebase-analytics" }
firebase-crashlytics = { group = "com.google.firebase", name = "firebase-crashlytics" }

# Camera & Media
camerax-core = { group = "androidx.camera", name = "camera-core", version.ref = "camerax" }
camerax-camera2 = { group = "androidx.camera", name = "camera-camera2", version.ref = "camerax" }
camerax-lifecycle = { group = "androidx.camera", name = "camera-lifecycle", version.ref = "camerax" }
camerax-view = { group = "androidx.camera", name = "camera-view", version.ref = "camerax" }
media3-exoplayer = { group = "androidx.media3", name = "media3-exoplayer", version.ref = "media3" }
exifinterface = { group = "androidx.exifinterface", name = "exifinterface", version.ref = "exifinterface" }

# UI/UX
coil-compose = { group = "io.coil-kt", name = "coil-compose", version.ref = "coil" }
lottie-compose = { group = "com.airbnb.android", name = "lottie-compose", version.ref = "lottie" }
accompanist-permissions = { group = "com.google.accompanist", name = "accompanist-permissions", version.ref = "accompanist" }
accompanist-navigation-animation = { group = "com.google.accompanist", name = "accompanist-navigation-animation", version.ref = "accompanist" }
accompanist-systemuicontroller = { group = "com.google.accompanist", name = "accompanist-systemuicontroller", version.ref = "accompanist" }

# Background Processing
work-runtime-ktx = { group = "androidx.work", name = "work-runtime-ktx", version.ref = "workManager" }

# Testing
junit = { group = "junit", name = "junit", version.ref = "junit" }
androidx-test-ext = { group = "androidx.test.ext", name = "junit", version.ref = "androidxTestExt" }
androidx-test-espresso = { group = "androidx.test.espresso", name = "espresso-core", version.ref = "androidxTestEspresso" }
compose-test = { group = "androidx.compose.ui", name = "ui-test-junit4", version.ref = "composeTest" }
mockk = { group = "io.mockk", name = "mockk", version.ref = "mockk" }
coroutines-test = { group = "org.jetbrains.kotlinx", name = "kotlinx-coroutines-test", version.ref = "coroutinesTest" }

# Code Quality
spotless = { group = "com.diffplug.spotless", name = "spotless-plugin-gradle", version.ref = "spotless" }
detekt = { group = "io.gitlab.arturbosch.detekt", name = "detekt", version.ref = "detekt" }
ktlint = { group = "org.jlleitschuh.gradle", name = "ktlint-gradle", version.ref = "ktlint" }

[plugins]
android-application = { id = "com.android.application", version.ref = "agp" }
android-library = { id = "com.android.library", version.ref = "agp" }
kotlin-android = { id = "org.jetbrains.kotlin.android", version.ref = "kotlin" }
kotlin-serialization = { id = "org.jetbrains.kotlin.plugin.serialization", version.ref = "kotlin" }
kotlin-kapt = { id = "org.jetbrains.kotlin.kapt", version.ref = "kotlin" }
hilt = { id = "com.google.dagger.hilt.android", version.ref = "hilt" }
firebase-crashlytics = { id = "com.google.firebase.crashlytics", version = "2.9.9" }
firebase-perf = { id = "com.google.firebase.firebase-perf", version = "1.4.2" }
spotless = { id = "com.diffplug.spotless", version.ref = "spotless" }
detekt = { id = "io.gitlab.arturbosch.detekt", version.ref = "detekt" }
ktlint = { id = "org.jlleitschuh.gradle.ktlint", version.ref = "ktlint" }
```

## 🏗️ Project Structure

```
SnapCamera/
├── app/
│   ├── build.gradle.kts
│   └── src/
│       ├── main/
│       │   ├── java/com/snapcamera/
│       │   │   ├── core/
│       │   │   │   ├── common/
│       │   │   │   ├── data/
│       │   │   │   ├── domain/
│       │   │   │   └── presentation/
│       │   │   ├── features/
│       │   │   │   ├── auth/
│       │   │   │   ├── onboarding/
│       │   │   │   ├── timetable/
│       │   │   │   ├── camera/
│       │   │   │   ├── gallery/
│       │   │   │   ├── dashboard/
│       │   │   │   ├── exam/
│       │   │   │   └── export/
│       │   │   └── shared/
│       │   │       ├── ui/
│       │   │       ├── utils/
│       │   │       └── resources/
│       │   ├── res/
│       │   └── AndroidManifest.xml
│       ├── test/
│       └── androidTest/
├── buildSrc/
│   ├── build.gradle.kts
│   └── src/main/kotlin/
│       ├── BuildConfig.kt
│       ├── Dependencies.kt
│       └── Versions.kt
├── gradle/
│   └── libs.versions.toml
├── build.gradle.kts
├── settings.gradle.kts
└── gradle.properties
```

## ⚙️ Root build.gradle.kts

```kotlin
buildscript {
    dependencies {
        classpath(libs.firebase.crashlytics)
        classpath(libs.firebase.perf)
    }
}

plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.serialization) apply false
    alias(libs.plugins.kotlin.kapt) apply false
    alias(libs.plugins.hilt) apply false
    alias(libs.plugins.spotless) apply false
    alias(libs.plugins.detekt) apply false
    alias(libs.plugins.ktlint) apply false
}

allprojects {
    repositories {
        google()
        mavenCentral()
        maven { url = uri("https://jitpack.io") }
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}
```

## 📱 App build.gradle.kts

```kotlin
plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.kotlin.kapt)
    alias(libs.plugins.hilt)
    alias(libs.plugins.firebase.crashlytics)
    alias(libs.plugins.firebase.perf)
    alias(libs.plugins.spotless)
    alias(libs.plugins.detekt)
    alias(libs.plugins.ktlint)
}

android {
    namespace = "com.snapcamera"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.snapcamera"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }

        // Room schema location
        javaCompileOptions {
            annotationProcessorOptions {
                arguments += mapOf(
                    "room.schemaLocation" to "$projectDir/schemas",
                    "room.incremental" to "true",
                    "room.expandProjection" to "true"
                )
            }
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig = signingConfigs.getByName("debug") // Change to release config
        }
        debug {
            isMinifyEnabled = false
            isShrinkResources = false
            applicationIdSuffix = ".debug"
            versionNameSuffix = "-debug"
        }
        create("staging") {
            initWith(getByName("debug"))
            applicationIdSuffix = ".staging"
            versionNameSuffix = "-staging"
            isDebuggable = false
        }
    }

    buildFeatures {
        compose = true
        buildConfig = true
        viewBinding = true
        dataBinding = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.kotlinCompilerExtension.get()
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = "17"
        freeCompilerArgs += listOf(
            "-opt-in=kotlin.RequiresOptIn",
            "-opt-in=kotlinx.coroutines.ExperimentalCoroutinesApi",
            "-opt-in=androidx.compose.material3.ExperimentalMaterial3Api"
        )
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }

    testOptions {
        unitTests {
            isIncludeAndroidResources = true
        }
    }
}

dependencies {
    // Android Core
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.fragment.ktx)

    // Compose
    implementation(platform(libs.compose.bom))
    implementation(libs.compose.ui)
    implementation(libs.compose.ui.graphics)
    implementation(libs.compose.ui.tooling.preview)
    implementation(libs.compose.material3)
    implementation(libs.compose.material.icons.extended)
    debugImplementation(libs.compose.ui.tooling)
    debugImplementation(libs.compose.ui.test.manifest)

    // Navigation
    implementation(libs.compose.navigation)
    implementation(libs.compose.hilt.navigation)

    // Database
    implementation(libs.room.runtime)
    implementation(libs.room.ktx)
    kapt(libs.room.compiler)
    implementation(libs.datastore.preferences)

    // Networking
    implementation(libs.retrofit)
    implementation(libs.retrofit.kotlinx.serialization)
    implementation(libs.okhttp)
    implementation(libs.okhttp.logging)
    implementation(libs.kotlinx.serialization.json)

    // Dependency Injection
    implementation(libs.hilt.android)
    implementation(libs.hilt.work)
    kapt(libs.hilt.compiler)

    // Firebase
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.auth)
    implementation(libs.firebase.firestore)
    implementation(libs.firebase.storage)
    implementation(libs.firebase.messaging)
    implementation(libs.firebase.analytics)
    implementation(libs.firebase.crashlytics)

    // Camera & Media
    implementation(libs.camerax.core)
    implementation(libs.camerax.camera2)
    implementation(libs.camerax.lifecycle)
    implementation(libs.camerax.view)
    implementation(libs.media3.exoplayer)
    implementation(libs.exifinterface)

    // UI/UX
    implementation(libs.coil.compose)
    implementation(libs.lottie.compose)
    implementation(libs.accompanist.permissions)
    implementation(libs.accompanist.navigation.animation)
    implementation(libs.accompanist.systemuicontroller)

    // Background Processing
    implementation(libs.work.runtime.ktx)

    // Testing
    testImplementation(libs.junit)
    testImplementation(libs.androidx.test.ext)
    testImplementation(libs.androidx.test.espresso)
    testImplementation(libs.compose.test)
    testImplementation(libs.mockk)
    testImplementation(libs.coroutines.test)
    androidTestImplementation(libs.androidx.test.ext)
    androidTestImplementation(libs.androidx.test.espresso)
    androidTestImplementation(platform(libs.compose.bom))
    androidTestImplementation(libs.compose.test)
}

// Code Quality Configuration
spotless {
    kotlin {
        target("**/*.kt")
        targetExclude("**/build/**/*.kt")
        ktlint(libs.versions.ktlint.get())
        licenseHeaderFile(rootProject.file("spotless/copyright.kt"))
    }
    kotlinGradle {
        target("**/*.gradle.kts")
        ktlint(libs.versions.ktlint.get())
    }
    java {
        target("**/*.java")
        targetExclude("**/build/**/*.java")
        licenseHeaderFile(rootProject.file("spotless/copyright.java"))
    }
    format("xml") {
        target("**/*.xml")
        targetExclude("**/build/**/*.xml")
    }
}

detekt {
    config = files("$projectDir/config/detekt/detekt.yml")
    buildUponDefaultConfig = true
    allRules = false
    autoCorrect = true
    checkExhaustiveness = true
    parallel = true
}

ktlint {
    android.set(true)
    ignoreFailures.set(false)
    reporters {
        reporter(org.jlleitschuh.gradle.ktlint.reporter.ReporterType.PLAIN)
        reporter(org.jlleitschuh.gradle.ktlint.reporter.ReporterType.CHECKSTYLE)
    }
    filter {
        exclude { element -> element.file.path.contains("build/") }
    }
}
```

## 🔧 Gradle Properties

```properties
# Project-wide Gradle settings
org.gradle.jvmargs=-Xmx4096m -XX:MaxMetaspaceSize=1024m -XX:+HeapDumpOnOutOfMemoryError
org.gradle.parallel=true
org.gradle.caching=true
org.gradle.configuration-cache=true

# Android
android.useAndroidX=true
android.enableJetifier=false
android.nonTransitiveRClass=true
android.enableR8.fullMode=true

# Kotlin
kotlin.code.style=official
kotlin.incremental=true
kotlin.incremental.useClasspathSnapshot=true

# Compose
android.enableComposeCompiler=true
android.compose.compiler.plugins.kotlin.experimental.tryK2=true

# Build Features
android.enableBuildConfigAsBytecode=true
android.enableResourceOptimizations=true

# Testing
android.testInstrumentationRunner=androidx.test.runner.AndroidJUnitRunner
```

## 🚀 Build Variants Configuration

```kotlin
// build.gradle.kts
android {
    buildTypes {
        release {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig = signingConfigs.getByName("debug")
            manifestPlaceholders["appName"] = "SnapCamera"
            manifestPlaceholders["allowBackup"] = "false"
            manifestPlaceholders["usesCleartextTraffic"] = "false"
        }
        debug {
            isMinifyEnabled = false
            isShrinkResources = false
            applicationIdSuffix = ".debug"
            versionNameSuffix = "-debug"
            manifestPlaceholders["appName"] = "SnapCamera Debug"
            manifestPlaceholders["allowBackup"] = "true"
            manifestPlaceholders["usesCleartextTraffic"] = "true"
        }
        create("staging") {
            initWith(getByName("debug"))
            applicationIdSuffix = ".staging"
            versionNameSuffix = "-staging"
            isDebuggable = false
            manifestPlaceholders["appName"] = "SnapCamera Staging"
            manifestPlaceholders["allowBackup"] = "false"
            manifestPlaceholders["usesCleartextTraffic"] = "false"
        }
    }

    flavorDimensions += "environment"
    productFlavors {
        create("production") {
            dimension = "environment"
            buildConfigField("String", "BASE_URL", "\"https://api.snapcamera.com\"")
            buildConfigField("String", "FIREBASE_PROJECT_ID", "\"snapcamera-prod\"")
        }
        create("staging") {
            dimension = "environment"
            buildConfigField("String", "BASE_URL", "\"https://staging-api.snapcamera.com\"")
            buildConfigField("String", "FIREBASE_PROJECT_ID", "\"snapcamera-staging\"")
        }
        create("development") {
            dimension = "environment"
            buildConfigField("String", "BASE_URL", "\"https://dev-api.snapcamera.com\"")
            buildConfigField("String", "FIREBASE_PROJECT_ID", "\"snapcamera-dev\"")
        }
    }
}
```

## 📊 Build Performance Optimization

### 1. Gradle Daemon
```properties
org.gradle.daemon=true
org.gradle.jvmargs=-Xmx4096m -XX:MaxMetaspaceSize=1024m
```

### 2. Parallel Execution
```properties
org.gradle.parallel=true
org.gradle.caching=true
org.gradle.configuration-cache=true
```

### 3. Build Cache
```kotlin
// settings.gradle.kts
buildCache {
    local {
        directory = File(rootDir, "build-cache")
        removeUnusedEntriesAfterDays = 30
    }
}
```

### 4. Module Parallelization
```kotlin
// build.gradle.kts
android {
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    
    kotlinOptions {
        jvmTarget = "17"
        freeCompilerArgs += listOf(
            "-Xjvm-default=all",
            "-Xopt-in=kotlin.RequiresOptIn"
        )
    }
}
```

## 🔒 ProGuard Rules

```proguard
# Keep Compose
-keep class androidx.compose.** { *; }
-keepclassmembers class androidx.compose.** { *; }

# Keep Room
-keep class * extends androidx.room.RoomDatabase
-keep @androidx.room.Entity class *
-dontwarn androidx.room.paging.**

# Keep Retrofit
-keepattributes Signature
-keepattributes *Annotation*
-keep class retrofit2.** { *; }
-keepclasseswithmembers class * {
    @retrofit2.http.* <methods>;
}

# Keep OkHttp
-dontwarn okhttp3.**
-dontwarn okio.**
-dontwarn javax.annotation.**
-dontwarn org.conscrypt.**

# Keep Firebase
-keep class com.google.firebase.** { *; }
-keep class com.google.android.gms.** { *; }

# Keep Hilt
-keep class dagger.hilt.** { *; }
-keep class javax.inject.** { *; }
-keep class * extends dagger.hilt.android.internal.managers.ViewComponentManager { *; }

# Keep CameraX
-keep class androidx.camera.** { *; }

# Keep Kotlin Serialization
-keepattributes *Annotation*, InnerClasses
-dontnote kotlinx.serialization.AnnotationsKt
-keepclassmembers class kotlinx.serialization.json.** {
    *** Companion;
}
-keepclasseswithmembers class kotlinx.serialization.json.** {
    kotlinx.serialization.KSerializer serializer(...);
}

# Keep WorkManager
-keep class androidx.work.impl.** { *; }
```

## 📈 Build Metrics

```kotlin
// build.gradle.kts
tasks.register("buildMetrics") {
    doLast {
        val buildDir = project.buildDir
        val apkSize = buildDir.walkTopDown()
            .filter { it.extension == "apk" }
            .map { it.length() }
            .sum()
        
        println("APK Size: ${apkSize / 1024 / 1024} MB")
        
        val buildTime = System.currentTimeMillis() - project.gradle.startParameter.startTime
        println("Build Time: ${buildTime / 1000} seconds")
    }
}
```

## 🎯 CI/CD Integration

```yaml
# .github/workflows/android.yml
name: Android CI

on:
  push:
    branches: [ main, develop ]
  pull_request:
    branches: [ main ]

jobs:
  build:
    runs-on: ubuntu-latest
    steps:
    - uses: actions/checkout@v3
    
    - name: Set up JDK 17
      uses: actions/setup-java@v3
      with:
        java-version: '17'
        distribution: 'temurin'
        cache: gradle
    
    - name: Grant execute permission for gradlew
      run: chmod +x gradlew
    
    - name: Run tests
      run: ./gradlew test
    
    - name: Run lint
      run: ./gradlew lint
    
    - name: Build with Gradle
      run: ./gradlew build
    
    - name: Upload APK
      uses: actions/upload-artifact@v3
      with:
        name: app-debug
        path: app/build/outputs/apk/debug/app-debug.apk
```

---

*This Gradle setup provides a production-ready configuration with optimal performance, code quality tools, and proper build variants for different environments.* 