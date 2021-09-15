package app.bandhan.microbanking.dao

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface DaoTermTenure {
    @Query("select * from ternTenure")
    fun getAllRecord(): LiveData<List<Term_Tenure>>

    @Query("select * from ternTenure where termtenure_id in (:termtenure_id)")
    fun getRecordById(termtenure_id: Int): Term_Tenure

    @Query("select * from ternTenure where fn in (:fnName)")
    fun getEmpPwd(fnName: String): Term_Tenure

    @Query("delete from ternTenure")
    fun deleteAllRecord()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertRecord(userInfo:Term_Tenure)

    @Update
    fun updateRecord(userInfo:Term_Tenure)

    @Delete
    fun deleteRecord(userInfo:Term_Tenure)

    @Query("SELECT * from ternTenure WHERE termtenure_id= :termtenure_id")
    fun getItemById(termtenure_id: Int): List<Term_Tenure?>?

    @Transaction
    fun insertOrUpdate(item:Term_Tenure) {
        if (getItemById(item.termtenure_id)!!.isEmpty())
            insertRecord(item)
        else
            updateRecord(item)
    }
}