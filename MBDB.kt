package app.bandhan.microbanking.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import app.bandhan.microbanking.dao.*

@Database(entities = [(Record_Info::class),(Occupation_Info::class),(Product_Info::class),
    (Sector_Code_Info::class),(Sub_Sector_Code_Info::class),(Nature_of_Borrower_Code_Info::class),
    (Family_Member_Info::class),
    (SavingAccount::class),
    (Term_Tenure::class),
    (AccountRelationShip_Info::class),
    (ModeOfOperation_Info::class),
    (Customer_Info::class)], version = 1, exportSchema = false)

abstract class MBDB : RoomDatabase() {
    companion object {
        private var INSTANCE: MBDB? = null
        fun getDataBase(context: Context): MBDB {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(context.applicationContext, MBDB::class.java, "mb-db")
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries().build()
            }
            return INSTANCE as MBDB
        }
    }
    abstract fun daoRecordInfo(): DaoRecordInfo
    abstract fun daoOccupationInfo(): DaoOccupationInfo
    abstract fun daoProductInfo(): DaoProductInfo
    abstract fun daoSubSectorCodeInfo(): DaoSubSectorCodeInfo
    abstract fun daoSectorCodeInfo(): DaoSectorCodeInfo
    abstract fun daoNatureofBorrowerCodeInfo(): DaoNatureofBorrowerCodeInfo
    abstract fun daoFamilyMemberInfo(): DaoFamilyMemberInfo
    abstract fun daoSavingAccountInfo(): DaoSavingAccount
    abstract fun daoTermTernureInfo(): DaoTermTenure
    abstract fun daoAccountRelationInfo() : DaoAccountRelationShip
    abstract fun daoModeOfOperation() : DaoModeOfOperation
    abstract fun daoCustomerInfo() : DaoCustomerInfo
}