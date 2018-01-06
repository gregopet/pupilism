package co.petrin.pupilism

/**
 * Represents a pupil.
 * @property name Name of the pupil
 * @property klass The klass/class this pupil is enrolled in
 * @property leaveTimes The known leave time of kids
 */
class Pupil(val name: String, val klass: Klass, val leaveTimes: MutableMap<DayOfWeek, Time> = mutableMapOf()) {
    override fun toString() = "$name, $klass"
}