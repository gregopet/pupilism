package co.petrin.pupilism.ui

import co.petrin.pupilism.storage.deserialize
import co.petrin.pupilism.storage.getDataFolder
import co.petrin.pupilism.storage.getStoredProject
import co.petrin.pupilism.storage.getStoredProjects
import javafx.collections.FXCollections
import javafx.geometry.Pos
import javafx.scene.Parent
import javafx.scene.control.TextInputDialog
import javafx.scene.layout.Priority
import javafx.scene.text.Font
import tornadofx.*

/** Special name of the sample project that can be loaded from app's resources for demo purposes */
private val SAMPLE_PROJECT = "Vzorec"

/** Special name of the simple project that can be loaded from app's resources for demo purposes */
private val SIMPLE_PROJECT = "Preprost vzorec"

class ProjectSelector : View("Podaljšano bivanje učencev") {
    override val root: Parent

    /** Folder in which projects are stored */
    val projectsFolder = getDataFolder()

    /** Lists existing projects */
    val existingProjects = FXCollections.observableArrayList<String>()

    init {
        root = vbox {
            spacing = 1.5
            minWidth = 300.0

            label("Izberi projekt") {
                alignment = Pos.BASELINE_CENTER
                font = Font(30.0)
                maxWidth = Double.MAX_VALUE

            }
            listview<String>(existingProjects) {
                vgrow = Priority.ALWAYS
                maxHeight = Double.MAX_VALUE
                onUserSelect { proj ->
                    val projectFileReader = if (proj === SAMPLE_PROJECT) {
                        ProjectSelector::class.java.getResourceAsStream("/sampleProject.yaml").reader()
                    } else if (proj === SIMPLE_PROJECT) {
                        ProjectSelector::class.java.getResourceAsStream("/simpleProject.yaml").reader()
                    } else {
                        getStoredProject(proj, projectsFolder).reader()
                    }
                    val project = projectFileReader.use {
                        deserialize(it)
                    }
                    val projectView = Project(proj, project)
                    replaceWith(projectView)
                }
            }
            button("Nov projekt") {
                hgrow = Priority.ALWAYS
                maxWidth = Double.MAX_VALUE
                setOnAction {
                    TextInputDialog().apply {
                        title = "Ime novega projekta"
                        headerText = null
                        contentText = "Prosim, vpišite ime novega projekta"
                        showAndWait().ifPresent { input ->
                            if (existingProjects.contains(input)) {
                                warning("Projekt $input že obstaja!")
                            } else {
                                existingProjects.add(input)
                            }
                        }
                    }
                }
            }
        }

        // Load names of projects that exist in the project folder
        existingProjects.addAll(getStoredProjects(projectsFolder) + SIMPLE_PROJECT + SAMPLE_PROJECT)
    }
}