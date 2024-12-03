import org.gradle.internal.impldep.org.apache.http.client.ResponseHandler
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse
import kotlin.io.path.ExperimentalPathApi
import kotlin.io.path.OnErrorResult
import kotlin.io.path.copyToRecursively
import kotlin.io.path.name
import kotlin.io.path.readLines
import kotlin.io.path.readText
import kotlin.io.path.writeLines
import kotlin.io.path.writeText

plugins {
    kotlin("jvm") version "2.1.0"
}

subprojects {
    apply(plugin = "org.jetbrains.kotlin.jvm")

    dependencies {
        if (project.name != "util") implementation(project(":util"))
    }
}

@ExperimentalPathApi
val task = tasks.register("newDay") {
    val projectDir = project.projectDir.toPath()
    val path = projectDir.resolve("previous-day")
    val dayToCreate = path.readText().toInt() + 1

    val parent = projectDir.resolve("day")
    val day = projectDir.resolve("day$dayToCreate")


    parent.copyToRecursively(day, { _, _, _ -> OnErrorResult.SKIP_SUBTREE }, true, true)

    exec {
        commandLine("git", "add", day.name)
    }

    val client = HttpClient.newHttpClient()
    val inputFile = day.resolve("src/main/resources/input.txt")
    client.send(
        HttpRequest.newBuilder(URI("https://adventofcode.com/2024/day/$dayToCreate/input")).GET()
            .headers(
                "Cookie",
                "session=${System.getenv("AOC_SESSION")}"
            )
            .build(),
        HttpResponse.BodyHandlers.ofFile(inputFile)
    )

    path.writeText(dayToCreate.toString())
}
