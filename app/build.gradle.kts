//plugins {
//    alias(libs.plugins.android.application)
//    alias(libs.plugins.kotlin.android)
//    alias(libs.plugins.google.gms.google.services)
//}
//
//android {
//    namespace = "com.example.soundnova"
//    compileSdk = 35
//
//    defaultConfig {
//        applicationId = "com.example.soundnova"
//        minSdk = 24
//        targetSdk = 35
//        versionCode = 1
//        versionName = "1.0"
//
//
//        testInstrumentationRunner = "android.support.test.runner.AndroidJUnitRunner"
//        vectorDrawables {
//            useSupportLibrary = true
//        }
//    }
//
//    buildTypes {
//        release {
//            isMinifyEnabled = false
//            proguardFiles(
//                getDefaultProguardFile("proguard-android-optimize.txt"),
//                "proguard-rules.pro"
//            )
//        }
//    }
//    compileOptions {
//        sourceCompatibility = JavaVersion.VERSION_1_8
//        targetCompatibility = JavaVersion.VERSION_1_8
//    }
//    kotlinOptions {
//        jvmTarget = "1.8"
//    }
//    buildFeatures {
//        compose = true
//        viewBinding = true
//    }
//    composeOptions {
//        kotlinCompilerExtensionVersion = "1.5.1"
//    }
//    packaging {
//        resources {
//            excludes += "/META-INF/{AL2.0,LGPL2.1}"
//        }
//    }
//}
//
//dependencies {
//
//    implementation(libs.androidx.core.ktx)
//    implementation(libs.androidx.lifecycle.runtime.ktx)
//    implementation(libs.androidx.activity.compose)
//    implementation(platform(libs.androidx.compose.bom))
//    implementation(libs.androidx.ui)
//    implementation(libs.androidx.ui.graphics)
//    implementation(libs.androidx.ui.tooling.preview)
//    implementation(libs.androidx.material3)
//    implementation(libs.firebase.auth)
//    implementation(libs.androidx.constraintlayout)
//    testImplementation(libs.junit)
//    androidTestImplementation(libs.androidx.junit)
//    androidTestImplementation(libs.androidx.espresso.core)
//    androidTestImplementation(platform(libs.androidx.compose.bom))
//    androidTestImplementation(libs.androidx.ui.test.junit4)
//    debugImplementation(libs.androidx.ui.tooling)
//    debugImplementation(libs.androidx.ui.test.manifest)
//    implementation(kotlin("script-runtime"))
//    testImplementation("junit:junit:4.13.2") // JUnit cho test đơn vị
//
//    // Android Instrumentation Test
//    androidTestImplementation("androidx.test.ext:junit:1.1.5") // JUnit cho Android
//    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1") // Espresso cho UI test
//
//    // AndroidX Test Core
//    androidTestImplementation("androidx.test:core:1.5.0")
//
//    // AndroidX Test Rules
//    androidTestImplementation("androidx.test:rules:1.5.0")
//
//    // AndroidX Test Runner
//    androidTestImplementation("androidx.test:runner:1.5.2")
//
//    // Mockito (Nếu bạn muốn sử dụng cho mock test)
//    testImplementation("org.mockito:mockito-core:4.2.0")
//}

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.google.gms.google.services)
}

android {
    namespace = "com.example.soundnova"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.soundnova"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        // Sử dụng `androidx.test.runner.AndroidJUnitRunner` thay vì `android.support.test.runner.AndroidJUnitRunner`
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        vectorDrawables {
            useSupportLibrary = true
        }
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }

    buildFeatures {
        compose = true
        viewBinding = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    // Các thư viện AndroidX và Firebase
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.firebase.auth)
    implementation(libs.androidx.constraintlayout)

    // Thư viện kiểm thử
    testImplementation(libs.junit)
    testImplementation("junit:junit:4.13.2") // JUnit cho test đơn vị
    testImplementation("org.mockito:mockito-core:4.2.0") // Mockito cho mock test

    // Thư viện kiểm thử Android Instrumentation
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)

    // Thư viện dành cho chế độ debug
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    // AndroidX Test Core
    androidTestImplementation("androidx.test:core:1.5.0")
    // AndroidX Test Rules
    androidTestImplementation("androidx.test:rules:1.5.0")
    // AndroidX Test Runner
    androidTestImplementation("androidx.test:runner:1.5.2")

    // Chạy tập lệnh Kotlin
    implementation(kotlin("script-runtime"))
}
