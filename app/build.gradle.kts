plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android") // <-- ADD THIS LINE
}

android {
    namespace = "com.example.myapplication"
    compileSdk = 34 // Usar la API 34, que es la versión estable actual (Target para Android 14)

    defaultConfig {
        applicationId = "com.example.myapplication"
        minSdk = 24
        targetSdk = 34 // Coincide con compileSdk
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
        sourceCompatibility = JavaVersion.VERSION_1_8 // El estándar más común y compatible
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions { // Necesario si usas Kotlin
        jvmTarget = "1.8"
    }
    buildFeatures { // Buena práctica añadir esto
        viewBinding = true
    }
}

dependencies {
    // Se usan las referencias del archivo libs.versions.toml
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation(libs.volley)
    implementation(libs.glide)

    // Dependencias de Test
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
}
