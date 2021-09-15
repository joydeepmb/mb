package app.bandhan.microbanking.dao

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface DaoRecordInfo  {

    @Query("select * from record_info")
    fun getAllRecord(): LiveData<List<Record_Info>>

    @Query("select * from record_info where record_id in (:recordId)")
    fun getRecordById(recordId: Int): Record_Info

    @Query("select * from record_info where fn in (:fnName)")
    fun getEmpPwd(fnName: String): Record_Info

    @Query("delete from record_info")
    fun deleteAllRecord()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertRecord(userInfo:Record_Info)

    @Update
    fun updateRecord(userInfo:Record_Info)

    @Delete
    fun deleteRecord(userInfo:Record_Info)

    @Query("SELECT * from record_info WHERE record_id= :record_Id")
    fun getItemById(record_Id: Int): List<Record_Info?>?

    @Transaction
    fun insertOrUpdate(item:Record_Info) {
        if (getItemById(item.record_id)!!.isEmpty())
            insertRecord(item)
        else
            updateRecord(item)
    }
}