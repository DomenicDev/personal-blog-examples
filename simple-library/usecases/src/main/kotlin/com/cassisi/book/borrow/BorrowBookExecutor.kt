package com.cassisi.book.borrow

import com.cassisi.book.BorrowBookPolicy

class BorrowBookExecutor(
    private val repository: BorrowBookRepository,
    private val policy: BorrowBookPolicy
) : BorrowBook {

    override fun execute(command: BorrowBookCommand): Result<Unit> {
        val book = repository.get(command.bookId)
        val result = book.borrowBook(command.readerId, command.loanAt, policy)
        return result.fold({
            // on success
            repository.save(it)
            Result.success(Unit)
        }, {
            // on failure
            Result.failure(it)
            }
        )
    }

}