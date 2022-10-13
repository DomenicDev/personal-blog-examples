package com.cassisi.book.reserve

import com.cassisi.book.*
import com.cassisi.reader.ReaderId
import java.time.LocalDate

object ReserveBook {

    data class ReserveBookCommand(
        val readerId: ReaderId,
        val reservedAt: LocalDate
    )

    fun handle(command: ReserveBookCommand, current: Book): Result<BookReserved> {
        // validate that book is currently loan
        if (current.state != BookState.BORROWED) {
            return Result.failure(BookNotLent(current.bookId))
        }

        // validate that there is no reservation yet
        if (current.reservation is ActiveReservation) {
            return Result.failure(BookAlreadyReserved(current.bookId))
        }

        // calculate expiration date
        val expiresAt = (current.loan as ActiveLoan).endDate.plusWeeks(1)

        val event = BookReserved(
            current.bookId,
            command.readerId,
            command.reservedAt,
            expiresAt
        )
        return Result.success(event)
    }

}