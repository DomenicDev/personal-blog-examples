package com.cassisi.book.register

import com.cassisi.book.BookId
import com.cassisi.book.BookRegistered

object RegisterBook {

    fun handle(command: RegisterBookCommand): BookRegistered {
        return BookRegistered(command.bookId)
    }

}

data class RegisterBookCommand(val bookId: BookId)