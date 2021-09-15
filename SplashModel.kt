package app.bandhan.microbanking.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SplashModel(
    var splash_data:String?=null
):Parcelable
