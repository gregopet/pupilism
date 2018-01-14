package co.petrin.pupilism

/**
 * Represents an activity the kids can be occupied with.
 * @property id A unique name, acting as the ID for this property
 * @property timeSlots The timeslots this activity occupies
 */
class Activity(var id: String, var timeSlots: List<TimeSlot>) {
    override fun toString(): String = id
}