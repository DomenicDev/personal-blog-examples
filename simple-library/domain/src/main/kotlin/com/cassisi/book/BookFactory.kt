package com.cassisi.book

object BookFactory {

    fun registerNewBook(bookId: BookId): Book {
        val book = BookAggregate(bookId)
        val event = BookRegistered(bookId)
        book.registerEvent(event)
        return book
    }

}