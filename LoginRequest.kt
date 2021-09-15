package app.bandhan.microbanking.model



import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import android.os.Parcelable

@Parcelize
data class LoginRequest(
    @SerializedName("ApplicationId") val applicationId : String?=null,
    @SerializedName("TriggeredByUserId") val triggeredByUserId : String?=null,
    @SerializedName("ActionId") val actionId : Int=0,
    @SerializedName("SqlScriptList") val sqlScriptList : List<SqlScriptX>,
    @SerializedName("DeviceId") val deviceId : String?=null,
    @SerializedName("ResponseStatus") val responseStatus : String?=null
) : Parcelable