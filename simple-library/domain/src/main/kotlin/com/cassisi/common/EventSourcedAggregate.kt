package com.cassisi.common

interface EventSourcedAggregate<ID, EventType> : Aggregate<ID> {

    fun loadFromHistory(events: List<EventType>)

    fun getChanges(): List<EventType>

}