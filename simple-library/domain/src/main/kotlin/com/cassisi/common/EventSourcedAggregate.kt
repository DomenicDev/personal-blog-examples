package com.cassisi.common

interface EventSourcedAggregate<ID, EventType> : Aggregate<ID> {

    /**
     * Builds the current state of that aggregate
     * from the history of previously stored events.
     */
    fun loadFromHistory(events: List<EventType>)

    /**
     * Returns a list of events that have occurred
     * after the aggregate got initialized.
     */
    fun getChanges(): List<EventType>

}