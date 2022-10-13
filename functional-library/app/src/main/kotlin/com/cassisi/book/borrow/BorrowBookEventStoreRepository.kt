package com.cassisi.book.borrow

import com.cassisi.book.*
import com.cassisi.eventstore.SimpleEventStore

class BorrowBookEventStoreRepository : BorrowBookRepository {

    override fun get(bookId: BookId): Book {
        val events = SimpleEventStore.getEvents(bookId)
        return events.fold(Book.empty()) { acc, event -> Book.apply(event, acc) }
    }

    override fun save(bookId: BookId, changes: BookBorrowed) {
        SimpleEventStore.appendEvents(bookId, listOf(changes))
    }
}