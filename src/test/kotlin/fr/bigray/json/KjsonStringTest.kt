package fr.bigray.json

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue


class KjsonStringTest {

    @Test
    fun toJson() {
        val actual1 = KjsonString()
        val actual2 = KjsonString(" test JsonString ")

        assertEquals("\"null\"", actual1.toJson())
        assertEquals("\"test JsonString\"", actual2.toJson())

    }

    @Test
    fun equals() {
        val actual1 = KjsonString("value")
        val actual2 = KjsonString("value")

        assertTrue(actual1 == actual2)
    }

}