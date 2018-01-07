package co.petrin.pupilism.storage

import co.petrin.pupilism.ui.Application
import java.io.File

const val PROJECT_FOLDER_NAME = "projekti"

/** Returns the path to the PROJECT_FOLDER_NAME which will be a subfolder in the application's install folder */
fun getDataFolder(): File {
    val binFolderPath = Application::class.java.protectionDomain.codeSource.location.file
    val storageFolder = File(binFolderPath, PROJECT_FOLDER_NAME)
    if (!storageFolder.exists()) storageFolder.mkdir()
    return storageFolder
}

/** Returns the list of projects stored in the given folder */
fun getStoredProjects(dataFolder: File): List<String> {
    return dataFolder.listFiles().map { it.name }
}

/** Returns the file pointing to a named [project] */
fun getStoredProject(project: String, dataFolder: File): File = File(dataFolder, project)