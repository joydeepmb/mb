package app.bandhan.microbanking.dao

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "modeOfOperation")
data class ModeOfOperation_Info(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "modeOfOperation_id")
    var modeOfOperation_id : Int = 0,

    @ColumnInfo(name = "fn")
    var fn: String = "",

    @ColumnInfo(name = "fv")
    var fv: String = "",

    )
