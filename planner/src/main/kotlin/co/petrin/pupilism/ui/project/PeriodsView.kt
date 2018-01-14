package co.petrin.pupilism.ui.project

import co.petrin.pupilism.storage.TimeInterval
import javafx.collections.FXCollections
import javafx.collections.ObservableList
import tornadofx.View
import tornadofx.column
import tornadofx.tableview
import tornadofx.vbox

class PeriodsView(val periods: ObservableList<TimeInterval>): View() {
    override val root = vbox {
        tableview(periods) {
            column("Od", TimeInterval::from).also { sortOrder.add(it) }
            column("Do", TimeInterval::to)
        }
    }
}