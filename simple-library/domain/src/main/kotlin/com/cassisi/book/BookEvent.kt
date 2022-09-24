package com.cassisi.book

import com.cassisi.reader.ReaderId
import java.time.LocalDate

sealed interface BookEvent

data class BookRegistered(
    val bookId: BookId,
): BookEvent

data class BookBorrowed(
    val bookId: BookId,
    val readerId: ReaderId,
    val loanId: LoanId,
    val loanDate: LocalDate,
    val loanEndDate: LocalDate
): BookEvent

data class ReservationCleared(
    val bookId: BookId
): BookEvent

data class BookReturned(
    val bookId: BookId,
    val loanId: LoanId,
    val returnDate: LocalDate
): BookEvent

data class BookReserved(
    val bookId: BookId,
    val readerId: ReaderId,
    val reservedAt: LocalDate,
    val expiresAt: LocalDate
): BookEvent