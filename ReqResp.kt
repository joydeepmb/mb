package app.bandhan.microbanking.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ReqResp(
    var ActionId : Int =0,
    var ActionMethodName : String = "",
    var ActionClassType : String = "",
    var NoOfArguments : Int =0,
    var Base64ObjectString :  String = "",
    var RequestType :  String = "",
    var MethodArg :  List<FieldValue> =ArrayList<FieldValue>(),
    var DbTuple :  List<Tuple> = ArrayList<Tuple>(),
    var SqlScriptList :  List<SqlScript> =ArrayList<SqlScript>()
):Parcelable


