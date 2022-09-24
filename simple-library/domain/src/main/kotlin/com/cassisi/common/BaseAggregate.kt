package com.cassisi.common

abstract class BaseAggregate<ID, EventType> (private val id: ID): EventSourcedAggregate<ID, EventType> {

    private val changes = mutableListOf<EventType>()

    override fun getId(): ID {
        return this.id
    }

    override fun loadFromHistory(events: List<EventType>) {
        events.forEach { handleEvent(it) }
    }

    override fun getChanges(): List<EventType> {
        return this.changes.toList()
    }

    fun registerEvent(event: EventType) {
        changes.add(event)
        handleEvent(event)
    }

    protected abstract fun handleEvent(event: EventType)

}