package br.com.zup.eventsourcing.core.util

import java.lang.reflect.Constructor

object ReflectHelper {

    private val NO_PARAM_SIGNATURE = arrayOfNulls<Class<*>>(0)

    fun <T> getDefaultNoArgsConstructor(clazz: Class<T>): Constructor<T> {
        try {
            return clazz.getDeclaredConstructor(*NO_PARAM_SIGNATURE).apply { isAccessible = true }
        } catch (ex: NoSuchMethodException) {
            throw NoSuchMethodException("Object class [${clazz.name}] is an interface " +
                "or it must declare a default (no-argument) constructor")
        }
    }
}