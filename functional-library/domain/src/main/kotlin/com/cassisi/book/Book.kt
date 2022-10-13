package com.cassisi.book

import java.util.*

data class Book(
    val bookId: BookId,
    val loan: Loan,
    val reservation: Reservation,
    val state: BookState,
) {

    companion object {

        fun empty(): Book = EMPTY_BOOK

        fun apply(event: BookEvent, current: Book): Book {
            return when (event) {
                is BookRegistered -> Book(
                    event.bookId,
                    AvailableForLoan,
                    NoReservation,
                    BookState.AVAILABLE
                )

                is BookBorrowed -> current.copy(
                    loan = ActiveLoan(
                        event.loanId,
                        event.readerId,
                        event.loanDate,
                        event.loanEndDate,
                        0
                    ),
                    reservation = NoReservation,
                    state = BookState.BORROWED
                )

                is BookReturned -> current.copy(
                    loan = AvailableForLoan,
                    state = BookState.AVAILABLE
                )

                is BookReserved -> current.copy(
                    reservation = ActiveReservation(
                        event.readerId,
                        event.reservedAt,
                        event.expiresAt
                    )
                )

                is ReservationCleared -> current.copy(
                    reservation = NoReservation
                )
            }
        }
    }
}

enum class BookState {
    AVAILABLE, BORROWED
}

// Helper object for non-initialized books
val EMPTY_BOOK = Book(
    BookId(UUID.randomUUID()),
    AvailableForLoan,
    NoReservation,
    BookState.AVAILABLE
)


