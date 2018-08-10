package fr.bigray.json

import fr.bigray.parser.KjsonParser
import fr.bigray.utils.WrapValue

class KjsonObject private constructor() : LinkedHashMap<String, KjsonValue>(), KjsonValue {
    override val value: KjsonObject
        get() = this

    override fun toJson(): String = this.entries.joinToString(prefix = "{", postfix = "}", separator = ",")
    { "\"${it.key}\":${it.value.toJson()}" }

    companion object {
        @JvmStatic fun createObject(): KjsonObject = KjsonObject()
        @JvmStatic fun fromJson(json: String): KjsonObject = KjsonParser.parse(json) as KjsonObject
    }

    fun en(key: String, entry: Any?): KjsonObject {
        this[key] = WrapValue.wrap(entry)
        return this
    }

    fun allEn(values: KjsonObject): KjsonObject {
        this.putAll(values)
        return this
    }

}
