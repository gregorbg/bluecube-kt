import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("com.android.application")
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
                implementation(project(":library"))

                implementation("com.juul.kable:core:0.10.2")
            }
        }

        getByName("jsMain") {
            dependencies {
                implementation("org.jetbrains.kotlin-wrappers:kotlin-react:17.0.2-pre.259-kotlin-1.5.31")
                implementation("org.jetbrains.kotlin-wrappers:kotlin-react-dom:17.0.2-pre.259-kotlin-1.5.31")
                implementation(npm("react", "17.0.2"))
                implementation(npm("react-dom", "17.0.2"))
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

    buildFeatures {
        viewBinding = true
    }

    lintOptions {
        isAbortOnError = false
    }

    sourceSets {
        all {
            java.srcDirs(project.file("src/android${name.capitalize()}/kotlin"))
            res.srcDirs(project.file("src/android${name.capitalize()}/res"))
            resources.srcDirs(project.file("src/android${name.capitalize()}/resources"))
            manifest.srcFile(project.file("src/android${name.capitalize()}/AndroidManifest.xml"))
        }
    }

    dependencies {
        implementation("androidx.core:core-ktx:1.3.2")
        implementation("androidx.appcompat:appcompat:1.2.0")
        implementation("com.google.android.material:material:1.3.0")
        implementation("androidx.constraintlayout:constraintlayout:2.0.4")
        implementation("androidx.navigation:navigation-fragment-ktx:2.3.5")
        implementation("androidx.navigation:navigation-ui-ktx:2.3.5")
    }
}

configure<JavaPluginExtension> {
    sourceCompatibility = JavaVersion.VERSION_1_8
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

afterEvaluate {
    rootProject.extensions.configure<org.jetbrains.kotlin.gradle.targets.js.nodejs.NodeJsRootExtension> {
        versions.webpackCli.version = "4.9.0"
    }
}
