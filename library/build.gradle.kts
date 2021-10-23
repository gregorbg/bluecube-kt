import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("com.android.library")
    kotlin("multiplatform")
}

group = "net.gregorbg"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    google()
}

kotlin {
    js(IR) {
        browser()
        binaries.executable()
    }

    android()

    sourceSets {
        commonMain {
            dependencies {
                api("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.5.2")
                implementation("com.juul.kable:core:0.10.2")
            }
        }
    }
}

android {
    //buildToolsVersion = "30.0.2"

    compileSdk = 31

    defaultConfig {
        minSdk = 21
    }

    lintOptions {
        isAbortOnError = false
    }

    sourceSets {
        getByName("main") {
            manifest.srcFile("src/androidMain/AndroidManifest.xml")
        }
    }
}

configure<JavaPluginExtension> {
    sourceCompatibility = JavaVersion.VERSION_1_8
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}
