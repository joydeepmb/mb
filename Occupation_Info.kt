package app.bandhan.microbanking.dao

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "occupation_info")
data class Occupation_Info(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "occupation_id")
    var occupation_id: Int = 0,

    @ColumnInfo(name = "fn")
    var fn: String = "",

    @ColumnInfo(name = "fv")
    var fv: String = "",

)
