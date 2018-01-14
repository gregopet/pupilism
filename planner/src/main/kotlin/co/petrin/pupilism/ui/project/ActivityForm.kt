package co.petrin.pupilism.ui.project

import co.petrin.pupilism.DayOfWeek
import co.petrin.pupilism.Time
import co.petrin.pupilism.TimeSlot
import co.petrin.pupilism.ui.viewmodel.ActivityModel
import co.petrin.pupilism.ui.viewmodel.SlotModel
import javafx.scene.input.KeyCode
import tornadofx.*

class ActivityForm(val activity: ActivityModel): View() {

    var newSlotModel = SlotModel(TimeSlot(DayOfWeek.Ponedeljek, Time("12:00"), Time("13:00")))

    override val root = form {
        title = "Urejanje aktivnosti"

        fieldset {
            field("Naziv") {
                textfield(activity.name).required()
            }
            field("Nov termin") {
                combobox(property = newSlotModel.day, values = DayOfWeek.values().asList()) {
                    makeAutocompletable()
                    minWidth = 200.0
                }
                textfield(newSlotModel.from)
                textfield(newSlotModel.to)
                button("Dodaj") {
                    isDefaultButton = true
                    action { addTimeSlot() }
                }
                setOnKeyPressed {
                    if (it.code == KeyCode.ENTER) {
                        it.consume()
                        addTimeSlot()
                    }
                }
            }
            field("Termini") {
                listview(activity.slots) {
                    setOnMouseClicked {
                        if (it.clickCount == 2) {
                            activity.slots.value.remove(selectedItem)
                        }
                    }
                }
            }
        }

        button("Shrani") {
            isDefaultButton = true
            enableWhen(activity.dirty)
            action {
                activity.commit {
                    println("Save called, we should be returning $activity")
                    close()
                }
            }
        }
    }

    private fun addTimeSlot() {
        newSlotModel.commit { activity.slots.value.add(newSlotModel.clone()) }
    }
}