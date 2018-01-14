package co.petrin.pupilism.ui.viewmodel

import co.petrin.pupilism.Activity
import javafx.beans.property.SimpleListProperty
import javafx.collections.FXCollections
import tornadofx.ViewModel
import tornadofx.observable

class ActivityModel(activity: Activity): ViewModel() {
    val name = bind { activity.observable(Activity::id) }
    val slots = bind { SimpleListProperty<SlotModel>(FXCollections.observableList(activity.timeSlots.map(::SlotModel))) }
}