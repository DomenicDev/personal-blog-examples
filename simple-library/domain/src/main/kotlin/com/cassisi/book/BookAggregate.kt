package com.cassisi.book

import com.cassisi.common.BaseAggregate
import com.cassisi.reader.ReaderId
import java.time.LocalDate
import java.util.*

class BookAggregate(id: BookId) : Book, BaseAggregate<BookId, BookEvent>(id) {

    private var currentLoan: Loan = NoLoan
    private var currentReservation: Reservation = NoReservation

    override fun borrowBook(readerId: ReaderId, startDate: LocalDate, policy: BorrowBookPolicy): Result<Book> {
        // check if this book is already loan
        if (currentLoan is ActiveLoan) {
            return Result.failure(BookAlreadyLoan(getId()))
        }

        // check if there is a reservation that was made by another reader
        if (currentReservation is ActiveReservation) {
            val reservation = (currentReservation as ActiveReservation)
            if (reservation.readerId != readerId) {
                return Result.failure(BookReservedByOtherReader(readerId, reservation.readerId))
            }
        }

        // validate if student borrow policy
        val result = policy.validateIfStudentIsAllowedToBorrowBook(readerId)
        result.onFailure { return Result.failure(it) }


        // the book can be borrowed, thus an event is created
        val loanId = LoanId(UUID.randomUUID())
        val endDate = startDate.plusWeeks(6)
        val event = BookBorrowed(
            getId(),
            readerId,
            loanId,
            startDate,
            endDate
        )
        registerEvent(event)

        // clear reservation if book was reserved
        if (currentReservation is ActiveReservation) {
            clearReservation()
        }

        // return current instance
        return Result.success(this)
    }

    override fun returnBook(returnDate: LocalDate): Result<Book> {
        if (currentLoan is NoLoan) {
            return Result.failure(BookNotLent(getId()))
        }
        val loan = currentLoan as ActiveLoan

        val event = BookReturned(
            getId(),
            loan.loanId,
            returnDate
        )
        registerEvent(event)
        return Result.success(this)
    }

    override fun reserveBook(readerId: ReaderId, reservationDate: LocalDate): Result<Book> {
        if (currentLoan !is ActiveLoan) {
            // books can only be reserved if currently lent
            return Result.failure(BookNotLent(getId()))
        }
        if (currentReservation is ActiveReservation) {
            return Result.failure(BookAlreadyReserved(getId()))
        }

        val expirationDate = (currentLoan as ActiveLoan).endDate.plusWeeks(1)

        val event = BookReserved(
            getId(),
            readerId,
            reservationDate,
            expirationDate
        )
        registerEvent(event)
        return Result.success(this)
    }

    override fun clearReservation(): Result<Book> {
        return if (currentReservation is ActiveReservation) {
            val event = ReservationCleared(getId())
            registerEvent(event)
            Result.success(this)
        } else {
            Result.failure(BookNotReserved(getId()))
        }
    }

    override fun handleEvent(event: BookEvent) {
        when (event) {
            is BookRegistered -> handle(event)
            is BookBorrowed -> handle(event)
            is BookReturned -> handle(event)
            is BookReserved -> handle(event)
            is ReservationCleared -> handle(event)
        }
    }

    private fun handle(event: BookRegistered) {
        this.currentLoan = NoLoan
        this.currentReservation = NoReservation
    }

    private fun handle(event: BookBorrowed) {
        this.currentLoan = ActiveLoan(
            event.loanId,
            event.readerId,
            event.loanDate,
            event.loanEndDate,
            0
        )
    }

    private fun handle(event: BookReturned) {
        this.currentLoan = NoLoan
    }

    private fun handle(event: BookReserved) {
        this.currentReservation = ActiveReservation(
            event.readerId,
            event.reservedAt,
            event.expiresAt
        )
    }

    private fun handle(event: ReservationCleared) {
        this.currentReservation = NoReservation
    }

}