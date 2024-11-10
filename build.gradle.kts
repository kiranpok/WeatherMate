// Project-level build.gradle.kts file
plugins {
    id("com.android.application") version "8.6.1" apply false
    id("com.android.library") version "8.6.1" apply false
    id("org.jetbrains.kotlin.android") version "1.9.0" apply false
    id("com.google.dagger.hilt.android") version "2.51.1" apply false
}
buildscript {
    repositories {
        google()
        mavenCentral()
    }


    dependencies {
        classpath("com.android.tools.build:gradle:7.0.4")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.5.31")
        classpath("com.google.dagger:hilt-android-gradle-plugin:2.51.1")
        // NOTE: Do not place your application dependencies here; they belong
        // in the individual module build.gradle files
    }
tasks.register("clean", Delete::class) {
    delete(layout.buildDirectory) // Updated to avoid deprecation warning

}
}


