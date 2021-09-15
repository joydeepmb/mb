package app.bandhan.microbanking.dao

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "family_member_info")
data class Family_Member_Info(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "family_member_id")
    var family_member_id: Int = 0,

    @ColumnInfo(name = "fn")
    var fn: String = "",

    @ColumnInfo(name = "fv")
    var fv: String = "",

    )
