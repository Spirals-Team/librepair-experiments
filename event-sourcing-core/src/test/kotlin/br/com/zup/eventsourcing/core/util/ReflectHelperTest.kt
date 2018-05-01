package br.com.zup.eventsourcing.core.util

import org.junit.Test
import kotlin.test.assertEquals

class ReflectHelperTest {

    interface Interface

    abstract class AbstractClass

    class ClassWithNoArgsConstructor(name: String)

    class ClassWithPublicNoArgsConstructor

    class ClassWithPrivateNoArgsConstructor private constructor()

    class ClassWithNoArgsConstructorNotPrimary(name: String) {
        private constructor() : this("foo")
    }

    @Test(expected = NoSuchMethodException::class)
    fun `when calling getDefaultNoArgsConstructor for interface, then should raise NoSuchMethodException`() {
        ReflectHelper.getDefaultNoArgsConstructor(Interface::class.java)
    }

    @Test
    fun `when calling getDefaultNoArgsConstructor for abstract class, then should return the constructor`() {
        ReflectHelper.getDefaultNoArgsConstructor(AbstractClass::class.java)
            .also { assertEquals(0, it.parameterCount) }
    }

    @Test(expected = NoSuchMethodException::class)
    fun `when calling getDefaultNoArgsConstructor for a class when no-args constructor is not defined, then should raise NoSuchMethodException`() {
        ReflectHelper.getDefaultNoArgsConstructor(ClassWithNoArgsConstructor::class.java)
    }

    @Test
    fun `when calling getDefaultNoArgsConstructor for a class where no-args constructor is public, then should return the constructor`() {
        ReflectHelper.getDefaultNoArgsConstructor(ClassWithPublicNoArgsConstructor::class.java)
            .also { assertEquals(0, it.parameterCount) }
    }

    @Test
    fun `when calling getDefaultNoArgsConstructor for a class where no-args constructor is private, then should return the constructor`() {
        ReflectHelper.getDefaultNoArgsConstructor(ClassWithPrivateNoArgsConstructor::class.java)
            .also { assertEquals(0, it.parameterCount) }
    }

    @Test
    fun `when calling getDefaultNoArgsConstructor for a class where no-args constructor is not primary, then should return the constructor`() {
        ReflectHelper.getDefaultNoArgsConstructor(ClassWithNoArgsConstructorNotPrimary::class.java)
            .also { assertEquals(0, it.parameterCount) }
    }
}