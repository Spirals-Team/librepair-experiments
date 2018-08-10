package fr.bigray.json

import java.math.BigDecimal
import java.math.BigInteger
import kotlin.test.Test
import kotlin.test.assertEquals

class KjsonArrayTest {

    @Test
    fun toJson() {

        val arr1 = KjsonArray.createArray()
                .el("arr1")
                .el(12)

        val obj1 = KjsonObject.createObject()
                .en("firstName", "Mick")
                .en("lastName", "Tyson")
                .en("age", 55)
                .en("hobbies", KjsonArray.createArray()
                        .el("Boxe")
                        .el("Catch")
                        .el("Movies"))

        val obj2 = KjsonObject.createObject()
                .en("firstName", "Alain")
                .en("lastName", "Prost")
                .en("age", 65)
                .en("hobbies", KjsonArray.createArray()
                        .el("F1")
                        .el("Rally")
                        .el("Music"))

        val obj3 = KjsonObject.createObject()
                .en("firstName", "Big")
                .en("lastName", "Ray")
                .en("age", 40)
                .en("hobbies", KjsonArray.createArray()
                        .el("guitar")
                        .el("basket ball"))

        val actual = KjsonArray.createArray()
                .el(obj1)
                .el(obj2)
                .el(obj3)
                .el("string value")
                .el(BigDecimal(3456))
                .el(BigInteger("1", 3))
                .el(123)
                .el(123.56)
                .el(1234567L)
                .el(null)
                .allEl(arr1)

        val expected = "[{\"firstName\":\"Mick\",\"lastName\":\"Tyson\",\"age\":55,\"hobbies\":[\"Boxe\",\"Catch\",\"Movies\"]},{\"firstName\":\"Alain\",\"lastName\":\"Prost\",\"age\":65,\"hobbies\":[\"F1\",\"Rally\",\"Music\"]},{\"firstName\":\"Big\",\"lastName\":\"Ray\",\"age\":40,\"hobbies\":[\"guitar\",\"basket ball\"]},\"string value\",3456,1,123,123.56,1234567,null,\"arr1\",12]"

        assertEquals(expected, actual.toJson())
    }
}