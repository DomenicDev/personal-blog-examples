package com.cassisi.book.borrow

import com.cassisi.book.BookId
import com.cassisi.reader.ReaderId
import java.time.LocalDate

sealed interface IBorrowBook {

    fun execute(bookId: BookId, readerId: ReaderId, loanAt: LocalDate): Result<Unit>

}
