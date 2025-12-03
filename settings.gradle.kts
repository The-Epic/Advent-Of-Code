rootProject.name = "AdventOfCode"

gradle.rootProject {
    group = "xyz.epicebic.aoc"
    version = "1.0-SNAPSHOT"
}

dependencyResolutionManagement {
    repositories {
        mavenCentral()
    }
}

include("util", "2024", "2023", "2025")