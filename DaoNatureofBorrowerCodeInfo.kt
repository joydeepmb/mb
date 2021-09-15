package app.bandhan.microbanking.dao

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface DaoNatureofBorrowerCodeInfo {
    @Query("select * from nature_of_borrower_code_info")
    fun getAllRecord(): LiveData<List<Nature_of_Borrower_Code_Info>>

    @Query("select * from nature_of_borrower_code_info where nature_of_borrower_code_id in (:natureofborrowercodeid)")
    fun getRecordById(natureofborrowercodeid: Int): Nature_of_Borrower_Code_Info

    @Query("select * from nature_of_borrower_code_info where fv in (:fnName)")
    fun getNatureofBorrowerName(fnName: String): Nature_of_Borrower_Code_Info

    @Query("select * from nature_of_borrower_code_info where fv in (:fnId)")
    fun getNatureofBorrowerId(fnId: String): Nature_of_Borrower_Code_Info

    @Query("delete from nature_of_borrower_code_info")
    fun deleteAllRecord()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertRecord(userInfo:Nature_of_Borrower_Code_Info)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateRecord(userInfo:Nature_of_Borrower_Code_Info)

    @Delete
    fun deleteRecord(userInfo:Nature_of_Borrower_Code_Info)
    @Query("SELECT * from nature_of_borrower_code_info WHERE nature_of_borrower_code_id= :natureofborrowercodeid")
    fun getItemById(natureofborrowercodeid: Int): List<Nature_of_Borrower_Code_Info?>?

    @Transaction
    fun insertOrUpdate(item:Nature_of_Borrower_Code_Info) {
        if (getItemById(item.nature_of_borrower_code_id)!!.isEmpty())
            insertRecord(item)
        else
            updateRecord(item)
    }
}