package com.cassisi.book

import com.cassisi.reader.ReaderId
import java.time.LocalDate
import java.util.*

sealed interface Loan

object NoLoan : Loan

data class ActiveLoan(
    val loanId: LoanId,
    val readerId: ReaderId,
    val startDate: LocalDate,
    val endDate: LocalDate,
    val extensions: Int
) : Loan

data class LoanId(val uuid: UUID)