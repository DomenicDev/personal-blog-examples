package com.cassisi.book

import com.cassisi.common.EventSourcedAggregate
import com.cassisi.reader.ReaderId
import java.time.LocalDate

sealed interface Book : EventSourcedAggregate<BookId, BookEvent> {

    fun borrowBook(readerId: ReaderId, startDate: LocalDate, policy: BorrowBookPolicy): Result<Book>

    fun returnBook(returnDate: LocalDate): Result<Book>

    fun reserveBook(readerId: ReaderId, reservationDate: LocalDate): Result<Book>

    fun clearReservation(): Result<Book>

}