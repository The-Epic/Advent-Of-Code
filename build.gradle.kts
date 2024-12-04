import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse
import java.nio.file.StandardCopyOption
import kotlin.io.path.ExperimentalPathApi
import kotlin.io.path.OnErrorResult
import kotlin.io.path.copyTo
import kotlin.io.path.copyToRecursively
import kotlin.io.path.name
import kotlin.io.path.readLines
import kotlin.io.path.readText
import kotlin.io.path.writeLines
import kotlin.io.path.writeText

plugins {
    kotlin("jvm") version "2.1.0"
}

@ExperimentalPathApi
val task = tasks.create("newDay") {
    doLast {
        val projectDir = project.projectDir.toPath()
        val path = projectDir.resolve("previous-day")
        val dayToCreate = path.readText().toInt() + 1

        val days = projectDir.resolve("2024/src/main")
        val parent = days.resolve("resources/Day_.kt")
        val dayFile = days.resolve("kotlin/days/Day$dayToCreate.kt")


        parent.copyTo(dayFile, StandardCopyOption.REPLACE_EXISTING)

        // modify files
        val lines = dayFile.readLines().toMutableList()
        lines[2] = lines[2].replace("_", dayToCreate.toString())
        dayFile.writeLines(lines)

        val main = days.resolve("kotlin/Main.kt")

        val mainLines = main.readLines().toMutableList()
        mainLines.add(6, "        Day$dayToCreate(),")
        main.writeLines(mainLines)

        exec {
            commandLine("git", "add", dayFile.name)
        }

        val client = HttpClient.newHttpClient()
        val inputFile = days.resolve("resources/input-$dayToCreate.txt")
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
}
