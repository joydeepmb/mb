package app.bandhan.microbanking.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class RecordTable(

    @SerializedName("TableName") val tableName : String ?=null,
    @SerializedName("PrimaryKeyField") val primaryKeyField : String?=null,
    @SerializedName("PrimaryKeyDbField") val primaryKeyDbField : String?=null,
    @SerializedName("RecordList") val recordList : List<String>,
    @SerializedName("DbName") val dbName : String?=null
):Parcelable
