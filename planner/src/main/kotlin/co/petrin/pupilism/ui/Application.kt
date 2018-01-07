package co.petrin.pupilism.ui

import co.petrin.pupilism.storage.getDataFolder
import tornadofx.App

class Application: App(ProjectSelector::class) {
}

fun main(args: Array<String>) {
    println("The app is alive, its project folder is located at ${getDataFolder()}")
    javafx.application.Application.launch(Application::class.java, *args)
}