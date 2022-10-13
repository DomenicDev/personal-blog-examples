package com.cassisi.eventstore

import com.cassisi.book.BookEvent
import com.cassisi.book.BookId

object SimpleEventStore {

    val store = mutableMapOf<BookId, List<BookEvent>>()

    fun getEvents(bookId: BookId): List<BookEvent> {
        return store.getOrDefault(bookId, emptyList())
    }

    fun appendEvents(bookId: BookId, changes: List<BookEvent>) {
        val current = store.getOrDefault(bookId, emptyList())
        val complete = current.plus(changes)
        store[bookId] = complete
    }



}