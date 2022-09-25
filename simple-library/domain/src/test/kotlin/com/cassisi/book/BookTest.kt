package com.cassisi.book

import com.cassisi.reader.ReaderId
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import java.time.LocalDate
import java.util.*


class BookTest {

    @Test
    fun registerBook() {
        val bookId = BookId(UUID.randomUUID())
        val book = BookFactory.registerNewBook(bookId)

        val changes = book.getChanges()
        val registeredEvent = changes.first()

        Assertions.assertEquals(1, changes.size)
        Assertions.assertTrue(registeredEvent is BookRegistered)

        val event = registeredEvent as BookRegistered
        Assertions.assertEquals(bookId, event.bookId)
    }

    @Test
    fun borrowBook() {
        val bookId = BookId(UUID.randomUUID())
        val book = BookFactory.registerNewBook(bookId)

        val readerId = ReaderId(UUID.randomUUID())
        val today = LocalDate.now()
        val endDate = today.plusWeeks(6)
        val policy = BorrowBookPolicy()
        book.borrowBook(readerId, today, policy)

        val event = book.getChanges().last()
        Assertions.assertTrue(event is BookBorrowed)

        val bookBorrowed = event as BookBorrowed
        Assertions.assertEquals(bookId, bookBorrowed.bookId)
        Assertions.assertEquals(readerId, bookBorrowed.readerId)
        Assertions.assertEquals(today, bookBorrowed.loanDate)
        Assertions.assertEquals(endDate, bookBorrowed.loanEndDate)
    }

}