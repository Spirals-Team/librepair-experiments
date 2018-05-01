package br.com.zup.eventsourcing.core

class RepositoryManager<T : AggregateRoot>(val repositories: List<Repository<T>>) : Repository<T>() {

    override fun getSavedEvents(aggregateId: AggregateId): List<Event> {
        return repositories.first().getSavedEvents(aggregateId)
    }

    override fun save(aggregateRoot: AggregateRoot, metaData: MetaData, lock: OptimisticLock): T {
        repositories.forEach { it.save(aggregateRoot, metaData, lock) }
        val newVersion = aggregateRoot.version.value + aggregateRoot.events.size
        return aggregateRoot.copyWithVersion(AggregateVersion(newVersion)) as T
    }

    override fun save(aggregateRoot: AggregateRoot, lock: OptimisticLock): T {
        repositories.forEach { it.save(aggregateRoot, lock) }
        val newVersion = aggregateRoot.version.value + aggregateRoot.events.size
        return aggregateRoot.copyWithVersion(AggregateVersion(newVersion)) as T
    }

    override fun get(aggregateId: AggregateId): T {
        return repositories.first().get(aggregateId)
    }

    override fun getLastMetaData(aggregateId: AggregateId): MetaData {
        return repositories.first().getLastMetaData(aggregateId)
    }

    override fun find(aggregateId: AggregateId): T? {
        return repositories.first().find(aggregateId)
    }
}
