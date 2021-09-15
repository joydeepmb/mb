package app.bandhan.microbanking.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SqlScriptList(

    @SerializedName("ScriptId") val scriptId : Int,
    @SerializedName("ActionId") val actionId : Int,
    @SerializedName("ScriptType") val scriptType : String,
    @SerializedName("ScriptBody") val scriptBody : String,
    @SerializedName("AddedBy") val addedBy : String,
    @SerializedName("AddedOn") val addedOn : String,
    @SerializedName("IsExecuted") val isExecuted : String,
    @SerializedName("ArgumentList") val argumentList : List<String>,
    @SerializedName("ExecutionOrder") val executionOrder : Int,
    @SerializedName("ReturnFieldList") val returnFieldList : List<String>,
    @SerializedName("ScriptName") val scriptName : String,
    @SerializedName("GenPkey") val genPGenPkey : String,
    @SerializedName("PkeyFieldName") val pPkeyFieldNameFieldName : String,
    @SerializedName("PkeyFieldAlias") val pPkeyFieldAliasFieldAlias : String,
    @SerializedName("DbName") val dbName : String,
    @SerializedName("TableName") val tableName : String,
    @SerializedName("PkeyId") val pPkeyIdId : Int,
    @SerializedName("ArgumentListLite") val argumentListLite : List<ArgumentLite>,
    @SerializedName("RecordTable") val recordTable : RecordTable,
    @SerializedName("RecordTableLite") val recordTableLite : RecordTableLite,
    @SerializedName("FkeyFieldName") val fFkeyFieldNameFieldName : String,
    @SerializedName("FkeyFieldAlias") val fFkeyFieldAliasFieldAlias : String,
    @SerializedName("FkeyFieldValue") val fFkeyFieldValueFieldValue : String,
    @SerializedName("TargetDbIds") val targetDbIds : String
):Parcelable
