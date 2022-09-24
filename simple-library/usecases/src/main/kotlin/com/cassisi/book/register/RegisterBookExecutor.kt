package com.cassisi.book.register

import com.cassisi.book.BookFactory
import com.cassisi.book.BookId

class RegisterBookExecutor(private val repository: RegisterBookRepository) : RegisterBook {

    override fun execute(bookId: BookId): Result<Unit> {
        val book = BookFactory.registerNewBook(bookId)
        repository.save(book)
        return Result.success(Unit)
    }
}