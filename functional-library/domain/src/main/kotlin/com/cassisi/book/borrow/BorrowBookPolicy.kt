package com.cassisi.book.borrow

import com.cassisi.reader.ReaderId

class BorrowBookPolicy {

    fun validateIfStudentIsAllowedToBorrowBook(readerId: ReaderId): Result<Unit> {
        // for now always allow borrow requests
        return Result.success(Unit)
    }

}