package fr.bigray.json

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class KjsonBooleanTest {

    @Test
    fun toJson() {
        val actual = KjsonBoolean(false)

        assertEquals("false", actual.toJson())
    }

    @Test
    fun `to string`() {
        val actual = KjsonBoolean(false)

        assertEquals("JsonBoolean(value=false)", actual.toString())
    }

    @Test
    fun equals() {
        val actual1 = KjsonBoolean(false)
        val actual2 = KjsonBoolean(false)

        assertTrue(actual1 == actual2)
    }

}