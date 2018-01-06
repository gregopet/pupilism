package co.petrin.pupilism


/** A day & time interval in the week */
class TimeSlot(val day: DayOfWeek, val from: Time, val to: Time) {

    /** Does this time slot touch or overlap another? */
    // https://stackoverflow.com/questions/325933/determine-whether-two-date-ranges-overlap
    fun overlaps(other: TimeSlot) = this.day == other.day && from <= other.to && to >= other.from


}


