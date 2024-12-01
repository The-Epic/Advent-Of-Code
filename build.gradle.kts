plugins {
    kotlin("jvm") version "2.1.0"
}

subprojects {
    apply(plugin = "org.jetbrains.kotlin.jvm")

    dependencies {
        if (project.name != "util") implementation(project(":util"))
    }
}