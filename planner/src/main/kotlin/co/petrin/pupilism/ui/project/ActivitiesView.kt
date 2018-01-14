package co.petrin.pupilism.ui.project
import co.petrin.pupilism.*
import co.petrin.pupilism.ui.utils.ListToStringCoverter
import co.petrin.pupilism.ui.viewmodel.ActivityModel
import co.petrin.pupilism.ui.viewmodel.SlotModel
import javafx.beans.value.ObservableValue
import javafx.collections.ObservableList
import javafx.scene.control.cell.TextFieldTableCell
import javafx.scene.input.KeyCode
import javafx.util.StringConverter
import tornadofx.*


class ActivitiesView(val activities: ObservableList<ActivityModel>): View() {
    override val root = vbox {
        tableview(activities) {
            column("Naziv", ActivityModel::name)
            column<ActivityModel, ObservableList<SlotModel>>("Termini", ActivityModel::slots) {
                autosize()
                cellFactory = TextFieldTableCell.forTableColumn(object: StringConverter<ObservableList<SlotModel>>() {
                    override fun toString(p0: ObservableList<SlotModel>): String {
                        return p0.sortedBy { it.day.value }. map { it.toString() }.joinToString()
                    }

                    override fun fromString(p0: String?): ObservableList<SlotModel> {
                        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                    }
                })
            }

            fun open() {
                if (selectedItem != null) {
                    val form = ActivityForm(selectedItem!!)
                    form.openModal(block = true)
                }
            }

            setOnMouseClicked {
                if (it.clickCount == 2) open()
            }
            setOnKeyPressed {
                if (it.code == KeyCode.ENTER) open()
            }
        }

        button("Nov krožek") {
            setOnAction {
                val form = ActivityForm(ActivityModel(Activity("", emptyList())))
                form.openModal(block = true)
                isDefaultButton
                println("Modal dialog netted us ${form.activity}")
            }
        }
    }


}