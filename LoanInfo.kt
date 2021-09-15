package app.bandhan.microbanking.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class LoanInfo(
    @SerializedName("customer_id")
    var customer_id:String?=null,

    @SerializedName("product_type")
    var product_type:String?=null
):Parcelable
