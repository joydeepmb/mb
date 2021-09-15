package app.bandhan.microbanking.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Customer_Info(
    var fn: String ?=null,
    var fv:String?=null
    ):Parcelable
