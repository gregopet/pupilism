package co.petrin.pupilism

/**
 * Represents an activity the kids can be occupied with.
 * @property id A unique ID for this property
 * @property timeSlots The timeslots this activity occupies
 */
class Activity(val id: String, val timeSlots: List<TimeSlot>) {
    override fun toString(): String = id
}