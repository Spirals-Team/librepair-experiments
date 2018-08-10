package fr.bigray.utils

import fr.bigray.json.*
import java.math.BigDecimal
import java.math.BigInteger
import kotlin.test.Test
import kotlin.test.assertFailsWith
import kotlin.test.assertTrue

class WrapValueTest {

    @Test
    fun wrap() {
        val jsonObject = KjsonObject.createObject()
        val jsonArray = KjsonArray.createArray()
        val aString = "str value"
        val integer = 1234
        val bigDecimal = BigDecimal(1234)
        val bigInteger = BigInteger("1", 3)
        val aDouble = 12.34
        val aLong = 1L
        val aBoolean = true

        val aValueNotKnown = listOf<Pair<*, *>>()

        assertTrue(WrapValue.wrap(jsonObject) is KjsonObject)
        assertTrue(WrapValue.wrap(jsonArray) is KjsonArray)
        assertTrue(WrapValue.wrap(aString) is KjsonString)
        assertTrue(WrapValue.wrap(integer) is KjsonNumber)
        assertTrue(WrapValue.wrap(bigDecimal) is KjsonNumber)
        assertTrue(WrapValue.wrap(bigInteger) is KjsonNumber)
        assertTrue(WrapValue.wrap(aDouble) is KjsonNumber)
        assertTrue(WrapValue.wrap(aLong) is KjsonNumber)
        assertTrue(WrapValue.wrap(aBoolean) is KjsonBoolean)

        assertFailsWith(Exception::class) { WrapValue.wrap(aValueNotKnown) }

    }

    @Test
    fun wrapStringValue() {
        val stringIsNumber = "12"
        val stringIsTrueBoolean = "true"
        val stringIsFalseBoolean = "false"
        val stringIsNull = "null"

        assertTrue(WrapValue.wrapStringValue(stringIsNumber) is KjsonNumber)
        assertTrue(WrapValue.wrapStringValue(stringIsTrueBoolean) is KjsonBoolean)
        assertTrue(WrapValue.wrapStringValue(stringIsFalseBoolean) is KjsonBoolean)
        assertTrue(WrapValue.wrapStringValue(stringIsNull) is KjsonNull)
    }
}