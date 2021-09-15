package app.bandhan.microbanking.model


import android.annotation.SuppressLint
import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep
import kotlinx.android.parcel.Parcelize
import android.os.Parcelable

@Parcelize
data class DbTupleLite(
    @SerializedName("DbName")
    var dbName: String?,
    @SerializedName("PrimaryKeyDbField")
    var primaryKeyDbField: String?,
    @SerializedName("PrimaryKeyField")
    var primaryKeyField: String?,
    @SerializedName("RecordList")
    var recordList: List<Record>?,
    @SerializedName("TableName")
    var tableName: String?
) : Parcelable