package app.bandhan.microbanking.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class FieldValue(

    var FieldName : String ="",
    var FieldCaption: String ="",
    var FieldDataTypeId : Int =-999,
    var FieldDataType : String ="",
    var DbFieldName : String ="",
    var Value : String ="",
    var ComplexObjectInBase64 : String ?=null

):Parcelable
