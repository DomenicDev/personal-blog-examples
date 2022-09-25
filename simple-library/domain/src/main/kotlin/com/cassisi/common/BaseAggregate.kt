package com.cassisi.common

abstract class BaseAggregate<ID, EventType> (private val id: ID): EventSourcedAggregate<ID, EventType> {

    /**
     * The list changes (events) stored as a mutable list.
     */
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

    /**
     * Adds the specified event to the list
     * of changes and invokes the handleEvent()
     * method for applying that event.
     */
    fun registerEvent(event: EventType) {
        changes.add(event)
        handleEvent(event)
    }

    /**
     * This method is invoked whenever a new event is
     * registered. Implement logic here to change current
     * state of the aggregate.
     */
    protected abstract fun handleEvent(event: EventType)

}