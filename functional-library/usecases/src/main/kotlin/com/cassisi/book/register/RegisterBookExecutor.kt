package com.cassisi.book.register

import com.cassisi.book.BookId


class RegisterBookExecutor(private val repository: RegisterBookRepository) : IRegisterBook {

    override fun execute(bookId: BookId): Result<Unit> {
        val command = RegisterBookCommand(bookId)
        val book = RegisterBook.handle(command)
        repository.save(bookId, book)
        return Result.success(Unit)
    }

}