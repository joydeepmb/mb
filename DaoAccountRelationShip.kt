package app.bandhan.microbanking.dao

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface DaoAccountRelationShip {
    @Query("Select * from accountRelationShip_info")
    fun getAllRecord(): LiveData<List<AccountRelationShip_Info>>

    @Query("select * from accountRelationShip_info where account_rel_id in (:accountRelId)")
    fun getRecordById(accountRelId: Int): AccountRelationShip_Info

    @Query("select * from accountRelationShip_info where fn in (:fnName)")
    fun getEmpPwd(fnName: String): AccountRelationShip_Info

    @Query("delete from accountRelationShip_info")
    fun deleteAllRecord()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertRecord(userInfo:AccountRelationShip_Info)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateRecord(userInfo:AccountRelationShip_Info)

    @Delete
    fun deleteRecord(userInfo:AccountRelationShip_Info)

    @Query("SELECT * from accountRelationShip_info WHERE account_rel_id= :accountRelId")
    fun getItemById(accountRelId:Int): List<AccountRelationShip_Info?>?

    @Transaction
    fun insertOrUpdate(item:AccountRelationShip_Info) {
        if (getItemById(item.account_rel_id)!!.isEmpty())
            insertRecord(item)
        else
            updateRecord(item)
    }
}