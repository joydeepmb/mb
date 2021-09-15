package app.bandhan.microbanking.dao

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface DaoSavingAccount {
    @Query("Select * from savingaccount_info")
    fun getAllRecord() : LiveData<List<SavingAccount>>

    @Query("Select * from savingaccount_info where savingaccount_id in (:savingAccount_id)")
    fun getRecordById(savingAccount_id : Int) : SavingAccount

    @Query("select * from savingaccount_info where fn in (:fnName)")
    fun getEmpPwd(fnName: String): SavingAccount

    @Query("delete from savingaccount_info")
    fun deleteAllRecord()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertRecord(userInfo:SavingAccount)

    @Update
    fun updateRecord(userInfo:SavingAccount)

    @Delete
    fun deleteRecord(userInfo:SavingAccount)

    @Query("SELECT * from savingaccount_info WHERE savingaccount_id= :savingAccount_id")
    fun getItemById(savingAccount_id: Int): List<SavingAccount?>?

    @Transaction
    fun insertOrUpdate(item:SavingAccount) {
        if (getItemById(item.savingaccount_id)!!.isEmpty())
            insertRecord(item)
        else
            updateRecord(item)
    }
}