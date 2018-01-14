package co.petrin.pupilism.ui.viewmodel

import co.petrin.pupilism.Time
import co.petrin.pupilism.TimeSlot
import tornadofx.ViewModel
import tornadofx.observable

class SlotModel(var slot: TimeSlot): ViewModel() {
    val day = bind { slot.observable(TimeSlot::day) }
    val from = bind { slot.from.observable(Time::toString, Time::fromString) }
    val to = bind {  slot.to.observable(Time::toString, Time::fromString) }

    override fun toString(): String = slot.toString()

    fun clone() = SlotModel(TimeSlot(
            day = this.slot.day,
            from = Time(this.slot.from.toString()),
            to = Time(this.slot.to.toString())
    ))
}