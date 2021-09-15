package app.bandhan.microbanking.dao

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "ternTenure")
data class Term_Tenure(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "termtenure_id")
    var termtenure_id : Int=0,

    @ColumnInfo(name = "fn")
    var fn: String = "",

    @ColumnInfo(name = "fv")
    var fv: String = "",


)
