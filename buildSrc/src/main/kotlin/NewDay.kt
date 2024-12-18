import org.gradle.api.DefaultTask
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.TaskAction
import org.gradle.api.tasks.options.Option
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse
import java.nio.file.Path
import kotlin.io.path.absolutePathString
import kotlin.io.path.createDirectories
import kotlin.io.path.notExists
import kotlin.io.path.outputStream
import kotlin.io.path.pathString
import kotlin.io.path.readLines
import kotlin.io.path.writeLines

open class NewDay : DefaultTask() {

    @get:Input
    @set:Option(option = "year", description = "The days year")
    var year: String = "2024"

    @get:Input
    @set:Option(option = "day", description = "The day to create")
    var day: String = "1"

    @TaskAction
    fun run() {
        val parentDir = project.projectDir.toPath()


        val yearDir = parentDir.resolve(year)
        val pkg = yearDir.resolve("src/main")

        val daysPkg = pkg.resolve("kotlin/days")
        val inputFile = pkg.resolve("resources/input-$day.txt")
        if (daysPkg.notExists()) daysPkg.createDirectories()
        if (inputFile.parent.notExists()) inputFile.parent.createDirectories()

        downloadInput(inputFile)

        val dayFile = daysPkg.resolve("Day$day.kt")
        dayFile.outputStream().use { output ->
            Thread.currentThread().contextClassLoader.getResourceAsStream("Day_.kt")!!.copyTo(output)
        }

        val modified = dayFile.readLines().toMutableList()
        modified[2] = modified[2].replace("_", day)
        dayFile.writeLines(modified)

        addGit(dayFile)
        updateMain(pkg.resolve("kotlin/Main.kt"))

        println("Created $year/$day")
    }

    private fun downloadInput(output: Path) {
        val client = HttpClient.newHttpClient()
        client.send(
            HttpRequest.newBuilder(URI("https://adventofcode.com/$year/day/$day/input")).GET()
                .headers(
                    "Cookie",
                    "session=${System.getenv("AOC_SESSION")}"
                )
                .build(),
            HttpResponse.BodyHandlers.ofFile(output)
        )
    }

    private fun addGit(day: Path) {
        val projectDir = project.projectDir.toPath()
        val builder = ProcessBuilder("git", "add", projectDir.relativize(day).pathString)
        builder.directory(projectDir.toFile())
        builder.start().waitFor()
    }

    private fun updateMain(main: Path) {
        val modified = main.readLines().toMutableList()
        modified.add(7, "        Day$day(),")
        main.writeLines(modified)
    }
}