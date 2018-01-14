package co.petrin.pupilism.ui.project

import co.petrin.pupilism.DayOfWeek
import co.petrin.pupilism.Klass
import co.petrin.pupilism.Pupil
import co.petrin.pupilism.Time
import co.petrin.pupilism.ui.utils.ListToStringCoverter
import javafx.beans.value.ObservableValue
import javafx.collections.ObservableList
import javafx.scene.control.TableColumn
import javafx.scene.control.cell.ChoiceBoxTableCell
import javafx.scene.control.cell.TextFieldTableCell
import javafx.scene.layout.Priority
import tornadofx.*

class PupilsView(val pupils: ObservableList<Pupil>, val classes: ObservableList<Klass>): View() {
    override val root = vbox {
        val tableView = tableview(pupils) {
            isEditable = true
            column("Učenec", Pupil::name).also { sortOrder.add(it) }
            column("Razred", Pupil::klass) {
                cellFactory = ChoiceBoxTableCell.forTableColumn(classes)
            }.also { sortOrder.add(0, it) }
            nestedColumn("Odhodi domov") {
                column("Ponedeljek", observeDayLeave(DayOfWeek.Ponedeljek))
                column("Torek", observeDayLeave(DayOfWeek.Torek))
                column("Sreda", observeDayLeave(DayOfWeek.Sreda))
                column("Četrtek", observeDayLeave(DayOfWeek.Četrtek))
                column("Petek", observeDayLeave(DayOfWeek.Petek))
            }
            column("Aktivnosti", Pupil::activities) {
                cellFactory = TextFieldTableCell.forTableColumn(ListToStringCoverter())
            }
        }
    }


    /** Creates an observable property from a pupil's leave time for a [day] */
    private fun observeDayLeave(day: DayOfWeek): (TableColumn.CellDataFeatures<Pupil, Time>) -> ObservableValue<Time> = { col ->
        col.value.leaveTimes.observable().toProperty(day, { it.toProperty() })
    }

}