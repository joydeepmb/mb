package app.bandhan.microbanking.dao

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "sub_sector_code_info")
data class Sub_Sector_Code_Info(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "sub_sector_code_id")
    var sub_sector_code_id: Int = 0,

    @ColumnInfo(name = "fn")
    var fn: String = "",

    @ColumnInfo(name = "fv")
    var fv: String = "",

    )
