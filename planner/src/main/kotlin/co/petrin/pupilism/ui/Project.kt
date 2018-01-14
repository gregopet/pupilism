package co.petrin.pupilism.ui

import co.petrin.pupilism.storage.SavedProject
import co.petrin.pupilism.ui.project.ActivitiesView
import co.petrin.pupilism.ui.project.ClassesView
import co.petrin.pupilism.ui.project.PeriodsView
import co.petrin.pupilism.ui.project.PupilsView
import co.petrin.pupilism.ui.viewmodel.ActivityModel
import javafx.collections.FXCollections
import javafx.collections.ObservableList
import javafx.scene.Parent
import javafx.scene.control.TabPane
import tornadofx.*

class Project(val name: String, val project: SavedProject): View("Podaljšano bivanje učencev") {
    override val root: Parent

    val classes = FXCollections.observableArrayList(project.classes)
    val pupils = FXCollections.observableArrayList(project.pupils)
    val activities = FXCollections.observableArrayList(project.activities.map(::ActivityModel))
    val periods = FXCollections.observableArrayList(project.times)

    init {
        root = vbox {
            menubar {
                menu(name) {
                    item("Shrani") {
                        setOnAction {
                            save()
                        }
                    }
                    item("Shrani in zapri") {
                        setOnAction {
                            save()
                            replaceWith(ProjectSelector::class)
                        }
                    }
                    item("Samo zapri") {
                        setOnAction {
                            replaceWith(ProjectSelector::class)
                        }
                    }
                }
            }
            tabpane {
                tabClosingPolicy = TabPane.TabClosingPolicy.UNAVAILABLE
                tab("Razredi") {
                    content = ClassesView(classes).root
                }
                tab("Učenci") {
                    content = PupilsView(pupils, classes).root
                }
                tab("Ure") {
                    content = PeriodsView(periods).root
                }
                tab("Krožki") {
                    content = ActivitiesView(activities).root
                }
            }
        }
    }


    private fun save() {
        TODO("Save not implemented yet")
    }
}