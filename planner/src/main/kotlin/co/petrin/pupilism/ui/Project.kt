package co.petrin.pupilism.ui

import co.petrin.pupilism.storage.SavedProject
import javafx.scene.Parent
import javafx.scene.control.TabPane
import tornadofx.*

class Project(val name: String, val project: SavedProject): View("Podaljšano bivanje učencev") {
    override val root: Parent


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
                    label(project.classes.joinToString())
                }
                tab("Učenci") {
                    label(project.pupils.joinToString())
                }
                tab("Ure") {
                    label(project.times.joinToString())
                }
                tab("Aktivnosti") {
                    label(project.activities.joinToString())
                }
            }
        }
    }


    private fun save() {
        TODO("Save not implemented yet")
    }
}