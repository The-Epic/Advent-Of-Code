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

file(".").listFiles().filter { it.isDirectory }.forEach { file ->
    if (file.resolve("build.gradle.kts").exists()) {
        include(":${file.name}")
    }
}