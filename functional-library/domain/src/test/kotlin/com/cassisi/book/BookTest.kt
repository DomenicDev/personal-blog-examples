package com.cassisi.book

import com.cassisi.book.borrow.BorrowBook
import com.cassisi.book.borrow.BorrowBookCommand
import com.cassisi.book.borrow.BorrowBookPolicy
import com.cassisi.book.register.RegisterBook
import com.cassisi.book.register.RegisterBookCommand
import com.cassisi.reader.ReaderId
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import java.time.LocalDate
import java.util.*


class BookTest {

    @Test
    fun rehydrateBook() {
        val bookId = BookId(UUID.randomUUID())
        val readerId = ReaderId(UUID.randomUUID())
        val loanId = LoanId(UUID.randomUUID())
        val startDate = LocalDate.now()
        val endDate = startDate.plusWeeks(4)

        val events = listOf(
            BookRegistered(bookId),
            BookBorrowed(bookId, readerId, loanId, startDate, endDate)
        )

        val book = events.fold(Book.empty()) { acc, event -> Book.apply(event, acc) }

        Assertions.assertEquals(bookId, book.bookId)
        Assertions.assertEquals(BookState.BORROWED, book.state)
        Assertions.assertEquals(NoReservation, book.reservation)

        val loan = book.loan as ActiveLoan
        Assertions.assertEquals(readerId, loan.readerId)
        Assertions.assertEquals(startDate, loan.startDate)
        Assertions.assertEquals(endDate, loan.endDate)
        Assertions.assertEquals(loanId, loan.loanId)
    }

    @Test
    fun registerBook() {
        val bookId = BookId(UUID.randomUUID())
        val command = RegisterBookCommand(bookId)
        val bookRegistered = RegisterBook.handle(command)

        Assertions.assertEquals(bookId, bookRegistered.bookId)
    }

    @Test
    fun borrowBook() {
        val bookId = BookId(UUID.randomUUID())
        val readerId = ReaderId(UUID.randomUUID())
        val startDate = LocalDate.now()
        val expectedEndDate = startDate.plusWeeks(6)

        val command = BorrowBookCommand(readerId, startDate)
        val current = Book(bookId, AvailableForLoan, NoReservation, BookState.AVAILABLE)
        val policy = BorrowBookPolicy()

        val result = BorrowBook.handle(command, current, policy)

        Assertions.assertTrue(result.isSuccess)

        val event = result.getOrThrow()

        Assertions.assertEquals(bookId, event.bookId)
        Assertions.assertEquals(readerId, event.readerId)
        Assertions.assertEquals(startDate, event.loanDate)
        Assertions.assertEquals(expectedEndDate, event.loanEndDate)
    }

    @Test
    fun borrowBook_alreadyLent() {
        val bookId = BookId(UUID.randomUUID())
        val readerId = ReaderId(UUID.randomUUID())
        val startDate = LocalDate.now()
        val expectedEndDate = startDate.plusWeeks(6)

        val activeLoan = ActiveLoan(
            LoanId(UUID.randomUUID()),
            readerId,
            startDate,
            expectedEndDate,
            0
        )

        val command = BorrowBookCommand(readerId, startDate)
        val current = Book(bookId, activeLoan, NoReservation, BookState.BORROWED)
        val policy = BorrowBookPolicy()

        val result = BorrowBook.handle(command, current, policy)
        Assertions.assertTrue(result.isFailure)

        val exception = result.exceptionOrNull()!!
        Assertions.assertEquals(BookAlreadyLoan::class, exception::class)
    }

}