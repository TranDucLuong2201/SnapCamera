plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.google.gms.google.services)
    id("com.google.devtools.ksp")
    alias(libs.plugins.hilt)
}

android {
    namespace = "org.campusquest.android"
    compileSdk = 35

    defaultConfig {
        applicationId = "org.campusquest.android"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        isCoreLibraryDesugaringEnabled = true
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
            excludes += "/META-INF/gradle/incremental.annotation.processors"
            excludes += "/META-INF/DEPENDENCIES"
            excludes += "/META-INF/LICENSE"
            excludes += "/META-INF/LICENSE.txt"
            excludes += "/META-INF/license.txt"
            excludes += "/META-INF/NOTICE"
            excludes += "/META-INF/NOTICE.txt"
            excludes += "/META-INF/notice.txt"
            excludes += "/META-INF/ASL2.0"
            excludes += "/META-INF/*.kotlin_module"
            excludes += "/META-INF/annotations.kotlin_module"
            // Fix Room compiler processing license conflict
            excludes += "/META-INF/androidx/room/room-compiler-processing/LICENSE.txt"
        }
    }
}
configurations.all {
    resolutionStrategy.eachDependency {
        if (requested.group == "com.intellij" && requested.name == "annotations") {
            useTarget("org.jetbrains:annotations:23.0.0")
            because("Avoid duplicate classes from com.intellij and org.jetbrains")
        }
    }
}


dependencies {
    implementation(libs.firebase.auth)
    implementation(libs.androidx.credentials)
    implementation(libs.androidx.credentials.play.services.auth)
    implementation(libs.googleid)
    implementation(libs.play.services.auth)
//    implementation(libs.androidx.work.runtime.ktx)
    implementation(libs.androidx.datastore.core.android)
    implementation(libs.androidx.media3.common.ktx)
    implementation(libs.androidx.room.compiler)
    implementation(libs.androidx.databinding.adapters)
    implementation(libs.firebase.messaging)
    apply(plugin = "com.google.gms.google-services")
    implementation("androidx.core:core-splashscreen:1.0.0-beta02")
    implementation("androidx.datastore:datastore-preferences-core:1.1.7")
    implementation("androidx.datastore:datastore-preferences:1.1.7")
    implementation ("androidx.compose.animation:animation:1.8.3")
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
    implementation ("androidx.compose.material:material-icons-extended:1.7.8")
    implementation(libs.retrofit)
    implementation(libs.coil)
    implementation(libs.okhttp)
    implementation(libs.moshi)
    implementation("com.google.code.gson:gson:2.13.1")
    implementation("androidx.credentials:credentials:1.6.0-alpha03")
    implementation("androidx.credentials:credentials-play-services-auth:1.6.0-alpha03")
    coreLibraryDesugaring ("com.android.tools:desugar_jdk_libs:2.1.5")
    // Dagger Hilt Bundle
    implementation("androidx.media3:media3-exoplayer:1.7.1")
    implementation("androidx.media3:media3-ui:1.7.1")
    implementation(libs.bundles.dagger.hilt)
    ksp(libs.dagger.hilt.compiler)
    implementation ("com.google.android.exoplayer:exoplayer:2.19.1")

    implementation(libs.room.runtime)
    implementation(libs.okhttp.logging.interceptor)
    val work_version = "2.10.2"
    implementation("androidx.work:work-runtime-ktx:${work_version}")
    // If this project uses any Kotlin source, use Kotlin Symbol Processing (KSP)
    // See Add the KSP plugin to your project
    ksp(libs.androidx.room.compiler)
// JSON parsing
//    implementation ("com.google.code.gson:gson:2.12.1")
}