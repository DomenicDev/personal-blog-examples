package com.cassisi.book.register

import com.cassisi.book.BookId
import com.cassisi.book.BookRegistered
import com.cassisi.book.register.RegisterBookRepository
import com.cassisi.eventstore.SimpleEventStore

class RegisterBookEventStoreRepository : RegisterBookRepository {

    override fun save(bookId: BookId, book: BookRegistered) {
        SimpleEventStore.appendEvents(bookId, listOf(book))
    }
}