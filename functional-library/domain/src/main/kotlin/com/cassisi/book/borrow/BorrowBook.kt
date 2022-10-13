package com.cassisi.book.borrow

import com.cassisi.book.*
import com.cassisi.book.BookReservedByOtherReader
import com.cassisi.reader.ReaderId
import java.time.LocalDate
import java.util.*

object BorrowBook {

    fun handle(command: BorrowBookCommand, current: Book, policy: BorrowBookPolicy): Result<BookBorrowed> {
        // validate if book is available
        if (current.state != BookState.AVAILABLE) {
            return Result.failure(BookAlreadyLoan(current.bookId))
        }

        // validate that book was not reserved by someone else
        if (current.reservation is ActiveReservation) {
            if (current.reservation.readerId != command.readerId) {
                return Result.failure(BookReservedByOtherReader(command.readerId, current.reservation.readerId))
            }
        }

        // validate if student borrow policy
        val result = policy.validateIfStudentIsAllowedToBorrowBook(command.readerId)
        result.onFailure { return Result.failure(it) }

        // the book can be borrowed, so we create the loan data
        val loanId = LoanId(UUID.randomUUID())
        val startDate = command.startDate
        val endDate = startDate.plusWeeks(6)

        // create event
        val bookBorrowedEvent = BookBorrowed(
            current.bookId,
            command.readerId,
            loanId,
            startDate,
            endDate
        )

        // return a result containing the event
        return Result.success(bookBorrowedEvent)
    }
}

data class BorrowBookCommand(
    val readerId: ReaderId,
    val startDate: LocalDate
)
