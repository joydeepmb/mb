package app.bandhan.microbanking.dao

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface DaoCustomerInfo {
    @Query("Select * from customer_details_info")
    fun getAllRecord(): LiveData<List<Customer_Info>>

    @Query("select * from customer_details_info where customer_id in (:customerId)")
    fun getRecordById(customerId: Int): Customer_Info

    @Query("select * from customer_details_info where fn in (:fnName)")
    fun getEmpPwd(fnName: String): Customer_Info

    @Query("delete from customer_details_info")
    fun deleteAllRecord()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertRecord(userInfo:Customer_Info)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateRecord(userInfo:Customer_Info)

    @Delete
    fun deleteRecord(userInfo:Customer_Info)

    @Query("SELECT * from customer_details_info WHERE customer_id= :customerId")
    fun getItemById(customerId:Int): List<Customer_Info?>?

    @Transaction
    fun insertOrUpdate(item:Customer_Info) {
        if (getItemById(item.customer_id)!!.isEmpty())
            insertRecord(item)
        else
            updateRecord(item)
    }
}