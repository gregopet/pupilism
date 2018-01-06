package co.petrin.pupilism

/**
 * Represents a group of pupils at a given time.
 */
class PupilGroup(val pupils: List<Pupil>, val timeSlot: TimeSlot) {

    /** Returns the number of pupils in individual classes contained in this group */
    val byKlass: Map<Klass, Int> get() = pupils.groupingBy { it.klass }.eachCount()

    /** Minimum year for the pupils in this group */
    val fromYear = pupils.map { it.klass.year }.min()!!

    /** Maximum year for the pupils in this group */
    val toYear = pupils.map { it.klass.year }.max()!!
}