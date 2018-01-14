package co.petrin.pupilism.ui.utils

import javafx.util.StringConverter

/**
 * Helps convert lists of objects with nice toString implementations into string values.
 */
class ListToStringCoverter<T>: StringConverter<List<T>>() {
    override fun toString(p0: List<T>?): String {
        return p0?.joinToString(", ") ?: ""
    }

    override fun fromString(p0: String?): List<T> {
        TODO("not implemented")
    }
}