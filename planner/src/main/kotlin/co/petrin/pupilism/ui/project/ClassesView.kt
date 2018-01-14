package co.petrin.pupilism.ui.project

import co.petrin.pupilism.Klass
import javafx.collections.ObservableList
import javafx.scene.control.TextInputDialog
import tornadofx.*
import javafx.scene.layout.Priority

class ClassesView(val classes: ObservableList<Klass>): View() {
    override val root = vbox {
        val tableView = tableview(classes) {
            vgrow = Priority.ALWAYS
            maxHeight = Double.MAX_VALUE
            isEditable = true
            column("Leto", Klass::year)
            column("Oddelek", Klass::name)
        }
        button("Dodaj") {
            setOnAction {
                TextInputDialog().apply {
                    title = "Ime novega razreda"
                    headerText = null
                    contentText = "Prosim, vpišite ime novega razreda, npr. 3A"
                    showAndWait().ifPresent { input ->
                        val newKlass = Klass(input[0].toString().toInt(), input.toUpperCase()[1]) //error handling!
                        if (classes.contains(newKlass)) {
                            warning("Razred $input že obstaja!")
                        } else {
                            classes.add(newKlass)
                        }
                    }
                }
            }
        }
    }
}