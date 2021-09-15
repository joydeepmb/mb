package app.bandhan.microbanking.dao

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface DaoModeOfOperation {
    @Query("Select * from modeOfOperation")
    fun getAllRecord(): LiveData<List<ModeOfOperation_Info>>

    @Query("select * from modeOfOperation where modeOfOperation_id in (:modeOfOperationId)")
    fun getRecordById(modeOfOperationId: Int): ModeOfOperation_Info

    @Query("select * from modeOfOperation where fn in (:fnName)")
    fun getEmpPwd(fnName: String): ModeOfOperation_Info

    @Query("delete from modeOfOperation")
    fun deleteAllRecord()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertRecord(userInfo:ModeOfOperation_Info)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateRecord(userInfo:ModeOfOperation_Info)

    @Delete
    fun deleteRecord(userInfo:ModeOfOperation_Info)

    @Query("SELECT * from modeOfOperation WHERE modeOfOperation_id= :modeOfOperationId")
    fun getItemById(modeOfOperationId:Int): List<ModeOfOperation_Info?>?

    @Transaction
    fun insertOrUpdate(item:ModeOfOperation_Info) {
        if (getItemById(item.modeOfOperation_id)!!.isEmpty())
            insertRecord(item)
        else
            updateRecord(item)
    }
}