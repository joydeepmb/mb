package app.bandhan.microbanking.dao

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface DaoSectorCodeInfo {
    @Query("select * from sector_code_info")
    fun getAllRecord(): LiveData<List<Sector_Code_Info>>

    @Query("select * from sector_code_info where sector_code_id in (:sectorCodeId)")
    fun getRecordById(sectorCodeId: Int): Sector_Code_Info

    @Query("select * from sector_code_info where fv in (:fnName)")
    fun getSectorCodeId(fnName: String): Sector_Code_Info

    @Query("select * from sector_code_info where fn in (:fnId)")
    fun getSectorCodeName(fnId: Int): Sector_Code_Info

    @Query("delete from sector_code_info")
    fun deleteAllRecord()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertRecord(userInfo:Sector_Code_Info)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateRecord(userInfo:Sector_Code_Info)

    @Delete
    fun deleteRecord(userInfo:Sector_Code_Info)

    @Query("SELECT * from sector_code_info WHERE sector_code_id= :sectorcodeid")
    fun getItemById(sectorcodeid: Int): List<Sector_Code_Info?>?

    @Transaction
    fun insertOrUpdate(item:Sector_Code_Info) {
        if (getItemById(item.sector_code_id)!!.isEmpty())
            insertRecord(item)
        else
            updateRecord(item)
    }
}