package com.cassisi.book.returnbook

import com.cassisi.book.*
import java.time.LocalDate

object ReturnBook {

    data class ReturnBookCommand(
        val returnDate: LocalDate
    )

    fun handle(command: ReturnBookCommand, current: Book): Result<BookReturned> {
        return if (current.loan is ActiveLoan) {
            val bookReturnedEvent = BookReturned(
                current.bookId,
                current.loan.loanId,
                command.returnDate
            )
            Result.success(bookReturnedEvent)
        } else {
            Result.failure(BookNotLent(current.bookId))
        }

    }

}