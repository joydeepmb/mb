package app.bandhan.microbanking.model


import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import android.os.Parcelable

@Parcelize
data class RecordX(
    @SerializedName("Fn")
    var fn: String?,
    @SerializedName("Fv")
    var fv: String?
) : Parcelable