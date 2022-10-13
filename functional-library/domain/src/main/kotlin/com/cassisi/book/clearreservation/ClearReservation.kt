package com.cassisi.book.clearreservation

import com.cassisi.book.*

object ClearReservation {

    fun handle(current: Book): Result<ReservationCleared> {
        if (current.reservation !is ActiveReservation) {
            return Result.failure(BookNotReserved(current.bookId))
        }
        // create and return reservation cleared event
        val event = ReservationCleared(current.bookId)
        return Result.success(event)
    }

}