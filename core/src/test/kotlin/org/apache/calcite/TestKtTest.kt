package org.apache.calcite

import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.junit.JUnitAsserter

class TestKtTest {
    @Test
    fun `a test to verify how Kotlin test fails in CI`() {
        JUnitAsserter.hashCode() // make maven-dependency-plugin happy
        assertEquals("Hello, world", "42", "43")
    }
}
