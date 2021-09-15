package app.bandhan.microbanking.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MethodArg(
    @SerializedName("DbFieldName") val dbFieldName : String?=null,
    @SerializedName("FieldCaption") val fieldCaption : String?=null,
    @SerializedName("FieldDataType") val fieldDataType : String?=null,
    @SerializedName("FieldDataTypeId") val fieldDataTypeId : Int=0,
    @SerializedName("FieldName") val fieldName : String?=null,
    @SerializedName("Value") val value : Int=0
):Parcelable
