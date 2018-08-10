package fr.bigray.json

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class KjsonNumberTest {

    @Test
    fun toJson() {
        val actual1 = KjsonNumber(1234)
        val actual2 = KjsonNumber(12.34)
        val actual3 = KjsonNumber(12L)
        val actual4 = KjsonNumber(12.236985)

        assertEquals(1234, actual1.value)
        assertEquals("1234", actual1.toJson())

        assertEquals(12.34, actual2.value)
        assertEquals("12.34", actual2.toJson())

        assertEquals(12L, actual3.value)
        assertEquals("12", actual3.toJson())

        assertEquals(12.236985, actual4.value)
        assertEquals("12.236985", actual4.toJson())
    }

    @Test
    fun equals() {
        val actual1 = KjsonNumber(12.34)
        val actual2 = KjsonNumber(12.34)

        assertTrue(actual1 == actual2)
    }

}