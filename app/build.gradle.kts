plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    id("androidx.navigation.safeargs")
}

android {
    namespace = "com.example.palindromeapp"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.palindromeapp"
        minSdk = 22
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }

    buildFeatures {
        viewBinding = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.swiperefreshlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    // Navigation Component
    implementation(libs.androidx.navigation.fragment.ktx.v235)
    implementation(libs.androidx.navigation.ui.ktx)

    // Retrofit untuk API
    implementation(libs.retrofit)
    implementation(libs.retrofit2.converter.gson)

    // RecyclerView
    implementation(libs.androidx.recyclerview)
    // viewmodel
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.lifecycle.livedata.ktx)
    // glide
    implementation(libs.glide)
    // paging 3
    implementation(libs.androidx.paging.runtime.ktx)
}