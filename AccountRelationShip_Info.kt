package app.bandhan.microbanking.dao

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "accountRelationShip_info")
data class AccountRelationShip_Info(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "account_rel_id")
    var account_rel_id: Int = 0,

    @ColumnInfo(name = "fn")
    var fn: String = "",

    @ColumnInfo(name = "fv")
    var fv: String = "",

    )
