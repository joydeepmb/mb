package app.bandhan.microbanking.model

import android.annotation.SuppressLint
import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep
import kotlinx.android.parcel.Parcelize
import android.os.Parcelable
import app.bandhan.microbanking.model.SqlScript
import app.bandhan.microbanking.model.SqlScriptX


@Parcelize
data class PreLoginRequest(

    @SerializedName("ApplicationId")
    var applicationId: String?,
    @SerializedName("TriggeredByUserId")
    var triggeredByUserId: String?,
    @SerializedName("ActionId")
    var actionId: Int?,
    @SerializedName("SqlScriptList")
    var sqlScriptList: List<SqlScriptX>?,
    @SerializedName("DeviceId")
    var deviceId: String?,
    @SerializedName("ResponseStatus")
    var responseStatus: String?,
) : Parcelable