package app.bandhan.microbanking.dao

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface DaoSubSectorCodeInfo {
    @Query("select * from sub_sector_code_info")
    fun getAllRecord(): LiveData<List<Sub_Sector_Code_Info>>

    @Query("select * from sub_sector_code_info where sub_sector_code_id in (:subsectorCodeId)")
    fun getRecordById(subsectorCodeId: Int): Sub_Sector_Code_Info

    @Query("select * from sub_sector_code_info where fv in (:fnName)")
    fun getSubSectorCodeId(fnName: String): Sub_Sector_Code_Info

    @Query("select * from sub_sector_code_info where fn in (:sub_sector_code_id)")
    fun getSubSectorCodeName(sub_sector_code_id: Int): Sub_Sector_Code_Info

    @Query("delete from sub_sector_code_info")
    fun deleteAllRecord()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertRecord(userInfo:Sub_Sector_Code_Info)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateRecord(userInfo:Sub_Sector_Code_Info)

    @Delete
    fun deleteRecord(userInfo:Sub_Sector_Code_Info)

    @Query("SELECT * from sub_sector_code_info WHERE sub_sector_code_id= :subsectorcodeid")
    fun getItemById(subsectorcodeid: Int): List<Sub_Sector_Code_Info?>?

    @Transaction
    fun insertOrUpdate(item:Sub_Sector_Code_Info) {
        if (getItemById(item.sub_sector_code_id)!!.isEmpty())
            insertRecord(item)
        else
            updateRecord(item)
    }
}