package app.bandhan.microbanking.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SqlScript(
    var ScriptId : Int = 0,
    var ActionId: Int = 0,
    var ScriptType : String = "",
    var ScriptBody : String ="",
    var AddedBy : String ="",
    var AddedOn : String ="",
    var IsExecuted : String ="",
    var ExecutionOrder : Int = 0,
    var ArgumentList : List<FieldValue> = ArrayList<FieldValue>(),
    var ReturnFieldList : List<FieldValue> =ArrayList<FieldValue>(),
    var PkeyId : Int = 0
):Parcelable
