package fr.bigray.json

import kotlin.test.*

class KjsonNullTest {

    private lateinit var actual: KjsonNull

    @BeforeTest
    fun init() {
        actual = KjsonNull.NULL
    }

    @Test
    fun getValue() {
        assertNull(actual.value)
    }

    @Test
    fun toJson() {
        assertEquals("null", actual.toJson())
    }

    @Test
    fun equals() {
        val otherNull: KjsonNull = KjsonNull.NULL
        assertTrue(actual == otherNull)
    }
}