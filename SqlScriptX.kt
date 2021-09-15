package app.bandhan.microbanking.model




import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import android.os.Parcelable


@Parcelize
data class SqlScriptX(
    @SerializedName("ScriptId")
    var scriptId: Int=0,
    @SerializedName("AddedOn")
    var addedOn: String?=null,
    @SerializedName("PkeyId")
    var pkeyId: String?=null,
    @SerializedName("ArgumentListLite")
    var argumentListLite: List<ArgumentLite>?,
) : Parcelable