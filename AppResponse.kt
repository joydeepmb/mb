package app.bandhan.microbanking.model

import android.os.Parcelable
import app.bandhan.microbanking.util.CONSTANT
import kotlinx.android.parcel.Parcelize

@Parcelize
data class AppResponse(
    var ResponseStatus : String = CONSTANT.FAILURE_RESPONSE,
    var ErrorMessage: String ="",
    var ErrorDetail : String ="",
    var ErrorCode : String ="",
    var ErrorLocation : String =""
):Parcelable
