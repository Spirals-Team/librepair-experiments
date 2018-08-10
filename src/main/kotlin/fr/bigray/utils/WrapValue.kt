package fr.bigray.utils

import fr.bigray.json.*

object WrapValue {
    fun wrap(value: Any?): KjsonValue {
        return when (value) {
            is KjsonValue -> value
            is String -> KjsonString(value)
            is Number -> KjsonNumber(value)
            is Boolean -> KjsonBoolean(value)
            null -> KjsonNull.NULL
            else -> throw Exception("Unknown json type.")
        }
    }

    fun wrapStringValue(value: String): KjsonValue {
        return when {
            isBoolean(value) -> KjsonBoolean(value.toBoolean())
            isNull(value) -> KjsonNull()
            isNumber(value) -> KjsonNumber(value.toBigDecimal())
            else -> KjsonString(value)
        }
    }

    private val isNumber = { value: String -> value.toBigDecimal().let { true } }
    private val isBoolean = { value: String -> value.toBoolean() || java.lang.Boolean.FALSE.toString() == value }
    private val isNull = { value: String? -> value.isNullOrEmpty() || value == "null" }

}