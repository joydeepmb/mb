package app.bandhan.microbanking.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class LoginResponse(
    @SerializedName("ApplicationId") val applicationId : Int=0,
    @SerializedName("TriggeredByUserId") val triggeredByUserId : String?=null,
    @SerializedName("ActionId") val actionId : Int=0,
    @SerializedName("ActionMethodName") val actionMethodName : String?=null,
    @SerializedName("ActionNameSpace") val actionNameSpace : String?=null,
    @SerializedName("GroupMethodName") val groupMethodName : String?=null,
    @SerializedName("GroupNameSpace") val groupNameSpace : String?=null,
    @SerializedName("NoOfArguments") val noOfArguments : Int=0,
    @SerializedName("RequestType") val requestType : String?=null,
    @SerializedName("MethodArg") val methodArg : List<MethodArg>?=null,
    @SerializedName("DbTuple") val dbTuple : List<DbTupleLite>?=null,
    @SerializedName("SqlScriptList") val sqlScriptList : List<SqlScriptX>?=null,
    @SerializedName("Base64ObjectString") val base64ObjectString : String?=null,
    @SerializedName("DeviceTypeId") val deviceTypeId : Int=0,
    @SerializedName("DeviceId") val deviceId : String?=null,
    @SerializedName("DbTupleLite") val dbTupleLite : List<DbTupleLite>?=null,
    @SerializedName("ApiTrailId") val apiTrailId : Int=0,
    @SerializedName("TransactionId") val transactionId : Int=0,
    @SerializedName("Rrn") val rrn : String?=null,
    @SerializedName("ExtRefNo") val extRefNo : String?=null,
    @SerializedName("Base64Objects") val base64Objects : List<String>?=null,
    @SerializedName("ResponseStatus") val responseStatus : String?=null,
    @SerializedName("ErrorMessage") val errorMessage : String?=null,
    @SerializedName("ErrorDetail") val errorDetail : String?=null,
    @SerializedName("ErrorCode") val errorCode : String?=null,
    @SerializedName("ErrorLocation") val errorLocation : String?=null
):Parcelable
