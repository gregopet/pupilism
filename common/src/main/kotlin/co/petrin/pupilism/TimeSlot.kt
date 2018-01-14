package co.petrin.pupilism


/** A day & time interval in the week */
data class TimeSlot(var day: DayOfWeek, var from: Time, var to: Time) {

    /** Does this time slot touch or overlap another? */
    // https://stackoverflow.com/questions/325933/determine-whether-two-date-ranges-overlap
    fun overlaps(other: TimeSlot) = this.day == other.day && from <= other.to && to >= other.from

    override fun toString() = "$day $from-$to"
}


