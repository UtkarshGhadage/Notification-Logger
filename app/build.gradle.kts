plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.jetbrains.kapt.android)
    alias(libs.plugins.ksp.android)
    alias(libs.plugins.hilt.android.gradle)
    alias(libs.plugins.google.services)
    alias(libs.plugins.google.firebase.crashlytics)

}

hilt {
    enableAggregatingTask = true
}

dependencies {
    implementation(libs.hilt.android)
    androidTestImplementation("junit:junit:4.12")
    ksp(libs.hilt.android.compiler)

    implementation(libs.androidx.hilt.work)
    ksp(libs.androidx.hilt.compiler)


    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.constraintlayout)
    implementation("com.google.android.material:material:1.11.0")
//    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.lifecycle.viewmodel)
    implementation(libs.androidx.lifecycle.livedata)
    implementation(libs.androidx.lifecycle.compose)
    implementation(libs.androidx.browser.browser)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.datastore.core)
    implementation(libs.androidx.hilt.compiler)
    implementation(libs.androidx.hilt.work)
//    implementation(libs.androidx.room.ktx)
//    implementation(libs.androidx.room.compiler)
//    implementation(libs.androidx.room.runtime)


    implementation(libs.androidx.work.runtime.ktx)
//    implementation(libs.google.firebase.bom)
    implementation(platform("com.google.firebase:firebase-bom:32.7.0"))
    implementation("com.google.firebase:firebase-analytics")
    implementation(libs.google.play.services.auth)
    implementation(libs.gson)
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation(libs.hilt.android)
    implementation(libs.hilt.android.compiler)
    implementation(libs.material)
    implementation(libs.squareup.converter.moshi)
    implementation(libs.squareup.logging.interceptor)
    implementation(libs.squareup.moshi.kotlin)
    implementation(libs.test.androidx.work)
    implementation(libs.test.androidx.core)
    implementation(libs.test.androidx.core.ktx)
    implementation(libs.test.kotlinx.coroutines)
    implementation(libs.test.mockito.kotlin)

//    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:1.9.22")



    dependencies {
        val room_version = "2.6.1"

        implementation("androidx.room:room-runtime:$room_version")
        annotationProcessor("androidx.room:room-compiler:$room_version")

        // To use Kotlin annotation processing tool (kapt)
//        kapt("androidx.room:room-compiler:$room_version")
        // To use Kotlin Symbol Processing (KSP)
        ksp("androidx.room:room-compiler:$room_version")

        // optional - Kotlin Extensions and Coroutines support for Room
        implementation("androidx.room:room-ktx:$room_version")

        // optional - RxJava2 support for Room
        implementation("androidx.room:room-rxjava2:$room_version")

        // optional - RxJava3 support for Room
        implementation("androidx.room:room-rxjava3:$room_version")

        // optional - Guava support for Room, including Optional and ListenableFuture
        implementation("androidx.room:room-guava:$room_version")

        // optional - Test helpers
        testImplementation("androidx.room:room-testing:$room_version")

        // optional - Paging 3 Integration
        implementation("androidx.room:room-paging:$room_version")
    }

}

android {

    packaging {
        resources {
            excludes += "/META-INF/*"
            excludes += "/META-INF/gradle/incremental.annotation.processors"
        }
    }

    namespace = "com.example.notificationlogger"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.notificationlogger"
        minSdk = 33
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        javaCompileOptions {
            annotationProcessorOptions {
                arguments += mapOf(
                    "room.schemaLocation" to "$projectDir/schemas",
                    "room.incremental" to "true"
                )
            }
        }

        defaultConfig {
            multiDexEnabled = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
//            isShrinkResources = false
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

}
