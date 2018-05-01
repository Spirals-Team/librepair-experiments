package br.com.zup.eventsourcing.core

import java.lang.reflect.ParameterizedType

abstract class Repository<T : AggregateRoot> {
    abstract fun save(aggregateRoot: AggregateRoot, lock: OptimisticLock = OptimisticLock.ENABLED): T
    abstract fun save(aggregateRoot: AggregateRoot, metaData: MetaData, lock: OptimisticLock = OptimisticLock.ENABLED): T
    abstract fun get(aggregateId: AggregateId): T
    abstract fun getSavedEvents(aggregateId: AggregateId): List<Event>
    abstract fun getLastMetaData(aggregateId: AggregateId): MetaData
    abstract fun find(aggregateId: AggregateId): T?


    protected fun getGenericClass(): Class<T> =
            (javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[0] as Class<T>

    protected fun getGenericName(): String =
            getGenericClass().simpleName

    protected fun getGenericCanonicalName(): String =
            getGenericClass().canonicalName

    class NotFoundException : RuntimeException()

    enum class OptimisticLock {
        DISABLED,
        ENABLED
    }
}
