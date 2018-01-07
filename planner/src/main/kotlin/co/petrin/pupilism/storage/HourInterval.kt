package co.petrin.pupilism.storage
import co.petrin.pupilism.Time

/** A time interval without a day attached */
data class TimeInterval(val from: Time, val to: Time) {
    override fun toString(): String = "$from - $to"
}