package app.bandhan.microbanking.dao

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface DaoFamilyMemberInfo {

    @Query("select distinct * from family_member_info")
    fun getAllRecord(): LiveData<List<Family_Member_Info>>

    @Query("select * from family_member_info where family_member_id in (:familymemberid)")
    fun getRecordById(familymemberid: Int): Family_Member_Info

    @Query("select * from family_member_info where fv in (:fnName)")
    fun getFamilyMemberId(fnName: String): Family_Member_Info

    @Query("select * from family_member_info where fn in (:fnId)")
    fun getFamilyMemberName(fnId: Int): Family_Member_Info

    @Query("delete from family_member_info")
    fun deleteAllRecord()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertRecord(userInfo:Family_Member_Info)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateRecord(userInfo:Family_Member_Info)

    @Delete
    fun deleteRecord(userInfo:Family_Member_Info)

    @Query("SELECT * from family_member_info WHERE family_member_id= :familymemberid")
    fun getItemById(familymemberid:Int): List<Family_Member_Info?>?

    @Transaction
    fun insertOrUpdate(item:Family_Member_Info) {
        if (getItemById(item.family_member_id)!!.isEmpty())
            insertRecord(item)
        else
            updateRecord(item)
    }
}
