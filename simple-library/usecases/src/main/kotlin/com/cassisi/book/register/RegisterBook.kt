package com.cassisi.book.register

import com.cassisi.book.BookId

sealed interface RegisterBook {

    fun execute(bookId: BookId): Result<Unit>

}