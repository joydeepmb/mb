package app.bandhan.microbanking.dao

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface DaoProductInfo {
    @Query("select * from product_info")
    fun getAllRecord(): LiveData<List<Product_Info>>

    @Query("select * from product_info where product_id in (:productId)")
    fun getRecordById(productId: Int): Product_Info

    @Query("select * from product_info where fv in (:fnName)")
    fun getProductName(fnName: String): Product_Info

    @Query("select * from product_info where fn in (:fnId)")
    fun getProductId(fnId: Int): Product_Info

    @Query("delete from product_info")
    fun deleteAllRecord()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertRecord(userInfo:Product_Info)

    @Update
    fun updateRecord(userInfo:Product_Info)

    @Delete
    fun deleteRecord(userInfo:Product_Info)

    @Query("SELECT * from product_info WHERE product_id= :product_id")
    fun getItemById(product_id: Int): List<Product_Info?>?

    @Transaction
    fun insertOrUpdate(item:Product_Info) {
        if (getItemById(item.product_id)!!.isEmpty())
            insertRecord(item)
        else
            updateRecord(item)
    }
}