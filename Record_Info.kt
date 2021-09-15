package app.bandhan.microbanking.dao

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "record_info")
data class Record_Info(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "record_id")
    var record_id: Int = 0,

    @ColumnInfo(name = "fn")
    var fn: String = "",

    @ColumnInfo(name = "fv")
    var fv: String = "",

)