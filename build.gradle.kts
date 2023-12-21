buildscript {
    dependencies {
        classpath("com.google.gms:google-services:4.4.0")
        classpath(kotlin("gradle-plugin", version = "1.9.21"))
        classpath("com.google.dagger:hilt-android-gradle-plugin:2.44.2")

        repositories {
            mavenCentral()
        }
        dependencies {
            classpath ("com.google.dagger:hilt-android-gradle-plugin:2.44.2")
        }

    }
}
// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") version "8.2.0" apply false
    id("org.jetbrains.kotlin.android") version "1.9.10" apply false
    kotlin("kapt") version "1.9.21"
    kotlin("jvm") version "1.9.21" apply false
    id("com.google.dagger.hilt.android") version "2.44" apply false


}

