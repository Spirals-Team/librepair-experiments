package com.github.aivancioglo.resttest.mappers

import com.google.gson.Gson
import com.google.gson.JsonElement
import io.restassured.mapper.ObjectMapper
import io.restassured.mapper.ObjectMapperDeserializationContext
import io.restassured.mapper.ObjectMapperSerializationContext
import java.net.URLEncoder
import java.util.*


class URLEncodeMapper : ObjectMapper {
    override fun deserialize(ctx: ObjectMapperDeserializationContext?): Any {
        throw RuntimeException("Deserialize method is not implemented")
    }

    /**
     * Converts an object to serialized HashMap
     *
     * @param ctx The serialization context that contains the object to be converted
     * @return Serialized URI parameters for use
     */
    override fun serialize(ctx: ObjectMapperSerializationContext?): String {
        return convertTree(Gson().toJsonTree(ctx!!.objectToSerialize))
    }

    /**
     * Converts the given JSON tree to serialized URI parameters.
     *
     * @param tree The JSON tree (can be an object or array).
     * @return Serialized URI parameters for use
     */
    private fun convertTree(tree: JsonElement): String {
        val params = HashMap<String, String>()
        when {
            tree.isJsonArray -> for ((i, element) in tree.asJsonArray.withIndex())
                buildObjectParams("$i", element, params)

            tree.isJsonObject -> for ((key, value) in tree.asJsonObject.entrySet())
                buildObjectParams(key, value, params)

            !tree.isJsonNull -> throw IllegalArgumentException("Cannot convert " + tree.toString())
        }
        return params.entries.joinToString("&") { "${it.key}=${URLEncoder.encode(it.value, "UTF-8")}" }
    }

    /**
     * Recursive helper method for [.convertTree].
     *
     * @param prefix The prefix for the parameter names.
     * @param tree   The remaining JSON tree.
     * @param params The params object to write to.
     */
    private fun buildObjectParams(prefix: String, tree: JsonElement, params: MutableMap<String, String>) {
        when {
            tree.isJsonArray -> for ((i, element) in tree.asJsonArray.withIndex())
                buildObjectParams("$prefix[$i]", element, params)

            tree.isJsonObject -> for ((key, value) in tree.asJsonObject.entrySet())
                buildObjectParams("$prefix[$key]", value, params)

            tree.isJsonPrimitive -> params[prefix] = tree.asJsonPrimitive.asString
        }
    }
}