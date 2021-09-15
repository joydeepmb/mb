package app.bandhan.microbanking.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Tuple(
    var TableName : String ="",
    var PrimaryKeyField: String ="",
    var PrimaryKeyDbField : String ="",
    var DbName : String ="",
    var RecordList : List<DbRecord> =ArrayList<DbRecord>()
):Parcelable
