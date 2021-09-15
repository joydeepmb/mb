package app.bandhan.microbanking.dao

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface DaoOccupationInfo {

    @Query("select distinct * from occupation_info")
    fun getAllRecord(): LiveData<List<Occupation_Info>>

    @Query("select * from occupation_info where occupation_id in (:occupationId)")
    fun getRecordById(occupationId: Int): Occupation_Info

    @Query("select * from occupation_info where fv in (:fnName)")
    fun getOccupationId(fnName: String): Occupation_Info

    @Query("select * from occupation_info where fn in (:fnId)")
    fun getOccupationName(fnId: String): Occupation_Info

    @Query("delete from occupation_info")
    fun deleteAllRecord()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertRecord(userInfo:Occupation_Info)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateRecord(userInfo:Occupation_Info)

    @Delete
    fun deleteRecord(userInfo:Occupation_Info)

    @Query("SELECT * from occupation_info WHERE occupation_id= :occupation_Id")
    fun getItemById(occupation_Id: Int): List<Occupation_Info?>?

    @Transaction
    fun insertOrUpdate(item:Occupation_Info) {
        if (getItemById(item.occupation_id)!!.isEmpty())
            insertRecord(item)
        else
            updateRecord(item)
    }
}
