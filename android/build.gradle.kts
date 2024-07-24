// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") version "8.2.2" apply false
    id("org.jetbrains.kotlin.android") version "1.9.0" apply false
    id("org.jlleitschuh.gradle.ktlint") version "11.4.0" apply true
    id("org.jetbrains.kotlin.jvm") version "1.9.0" apply false

    id("com.google.dagger.hilt.android") version "2.48" apply false
    id("org.jetbrains.kotlin.plugin.serialization") version "1.8.20" apply false
}

allprojects {
    apply(plugin = "org.jlleitschuh.gradle.ktlint")
}