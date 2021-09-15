package app.bandhan.microbanking.model


import com.google.gson.annotations.SerializedName

import androidx.annotation.Keep
import kotlinx.android.parcel.Parcelize
import android.os.Parcelable


@Parcelize
data class Record(
    @SerializedName("DbTupleLite")
    var dbTupleLite: DbTupleLite?,
    @SerializedName("Record")
    var record: List<RecordX>?
) : Parcelable