package com.cassisi.book.borrow

import com.cassisi.book.BookId
import com.cassisi.reader.ReaderId
import java.time.LocalDate

sealed interface BorrowBook {

    fun execute(command: BorrowBookCommand): Result<Unit>

}

data class BorrowBookCommand(
    val bookId: BookId,
    val readerId: ReaderId,
    val loanAt: LocalDate
)