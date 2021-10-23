buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath("com.android.tools.build:gradle:4.2.2")
    }
}

plugins {
    kotlin("multiplatform") version "1.5.31" apply false
}

group = "net.gregorbg"
version = "1.0-SNAPSHOT"

subprojects {
    repositories {
        google()
        mavenCentral()
    }
}
