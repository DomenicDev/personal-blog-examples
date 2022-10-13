package com.cassisi.book.borrow

import com.cassisi.book.BookId
import com.cassisi.reader.ReaderId
import java.time.LocalDate

class BorrowBookExecutor(
    private val repository: BorrowBookRepository,
    private val policy: BorrowBookPolicy
) : IBorrowBook {

    override fun execute(bookId: BookId, readerId: ReaderId, loanAt: LocalDate): Result<Unit> {
        val currentBook = repository.get(bookId)
        val command = BorrowBookCommand(readerId, loanAt)
        val result = BorrowBook.handle(command, currentBook, policy)
        return result.fold({
            repository.save(bookId, it)
            Result.success(Unit)
        }, { Result.failure(it) })
    }

}