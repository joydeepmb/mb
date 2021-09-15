package app.bandhan.microbanking.dao

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "sector_code_info")
data class Sector_Code_Info(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "sector_code_id")
    var sector_code_id: Int = 0,

    @ColumnInfo(name = "fn")
    var fn: String = "",

    @ColumnInfo(name = "fv")
    var fv: String = "",

    )

