package fr.bigray.json

import kotlin.test.*

class KjsonObjectTest {

    private lateinit var actual: KjsonObject

    @BeforeTest
    fun init() {
        val address = KjsonObject.createObject()
                .en("number", 4)
                .en("zipCode", KjsonNumber(17540))
                .en("street", "Chemin de la gare")
                .en("city", KjsonString("Le Gué d'Alleré"))
                .en("digiCode", null)

        actual = KjsonObject.createObject()
                .en("firstName", KjsonString("John"))
                .en("lastName", KjsonString("Doe"))
                .en("age", KjsonNumber(40))
                .en("isStrong", KjsonBoolean(true))
                .en("address", KjsonObject.createObject()
                        .allEn(address))
                .en("hobbies", KjsonArray.createArray()
                        .el("F1")
                        .el("Rally")
                        .el("Music"))
    }

    @Test
    fun toJson() {

        val expectedJson = "{\"firstName\":\"John\",\"lastName\":\"Doe\",\"age\":40,\"isStrong\":true,\"address\":{\"number\":4,\"zipCode\":17540,\"street\":\"Chemin de la gare\",\"city\":\"Le Gué d'Alleré\",\"digiCode\":null},\"hobbies\":[\"F1\",\"Rally\",\"Music\"]}"

        assertEquals(expectedJson, actual.toJson())

    }

}