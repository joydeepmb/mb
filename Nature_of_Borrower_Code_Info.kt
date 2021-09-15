package app.bandhan.microbanking.dao

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "nature_of_borrower_code_info")
data class Nature_of_Borrower_Code_Info(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "nature_of_borrower_code_id")
    var nature_of_borrower_code_id: Int = 0,

    @ColumnInfo(name = "fn")
    var fn: String = "",

    @ColumnInfo(name = "fv")
    var fv: String = "",

    )
