package co.petrin.pupilism

class Time (time: String): Comparable<Time> {
    val hour: Int
    val minute: Int

    init {
        val components = time.split(':')
        require(components.size == 2) { "Invalid time specification: $time" }
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