package co.petrin.pupilism

class Time (time: String): Comparable<Time> {
    var hour: Int
    var minute: Int

    init {
        // Kotlin compiler complains otherwise
        hour = 0
        minute = 0

        fromString(time)
    }

    fun fromString(src: String) {
        val components = src.split(':')
        require(components.size == 2) { "Invalid time specification: $src" }
        hour = components[0].toInt()
        minute = components[1].toInt()
    }
    override fun toString() = "$hour:${minute.toString().padStart(2, '0')}"
    override fun equals(other: Any?) = other is Time && other.hour == this.hour && other.minute == this.minute
    override fun hashCode() = hour * 60 + minute
    override fun compareTo(other: Time) = when {
        this.hour == other.hour -> this.minute.compareTo(other.minute)
        else                    -> this.hour.compareTo(other.hour)
    }
}