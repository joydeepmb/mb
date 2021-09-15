package app.bandhan.microbanking.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DbRecord(
    var Record : List<FieldValue> =ArrayList<FieldValue>(),
    var DbTupleLite: List<Tuple> =ArrayList<Tuple>()
):Parcelable
