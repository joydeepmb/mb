package app.bandhan.microbanking.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class RecordTableLite(
    @SerializedName("TableName") val tableName : String,
    @SerializedName("PrimaryKeyField") val primaryKeyField : String,
    @SerializedName("PrimaryKeyDbField") val primaryKeyDbField : String,
    @SerializedName("DbName") val dbName : String,
    @SerializedName("RecordList") val recordList : List<String>
):Parcelable
