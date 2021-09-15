package app.bandhan.microbanking.model


import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import android.os.Parcelable

@Parcelize
data class ArgumentLite(
    @SerializedName("Fn")
    var fn: String?=null,
    @SerializedName("Fv")
    var fv: String?=null
):Parcelable