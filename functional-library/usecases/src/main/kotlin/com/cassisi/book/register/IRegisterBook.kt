package com.cassisi.book.register

import com.cassisi.book.BookId

sealed interface IRegisterBook {

    fun execute(bookId: BookId): Result<Unit>

}