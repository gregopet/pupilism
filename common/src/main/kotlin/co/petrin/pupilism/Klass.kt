package co.petrin.pupilism

/**
 * Represents a Klass of kids (class is a really difficuly word to work with :P)
 * @property year The school year of this klass (e.g. 1-9)
 * @property name The name of this klass, uppercased, e.g. A, B, C..
 */
data class Klass(val year: Int, val name: Char) {
    override fun toString() = "$year.$name"
}