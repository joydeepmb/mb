package app.bandhan.microbanking.viewmodel

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import app.bandhan.microbanking.application.MBApplication
import app.bandhan.microbanking.dao.*
import app.bandhan.microbanking.db.MBDB
import app.bandhan.microbanking.repository.AppRepository
import app.bandhan.microbanking.util.Event
import app.bandhan.microbanking.util.Resource
import app.bandhan.microbanking.util.Utils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.supervisorScope
import retrofit2.Response


class CommonViewModel(app: Application,
                      private var appRepository: AppRepository,
                      ctx: Context
): AndroidViewModel(app) {

    /**
     * LiveData And MutableLiveData of Occupation.
     */

    private val _occupationResponse = MutableLiveData<Event<Resource<String>>>()
    val occupationResponse: LiveData<Event<Resource<String>>> = _occupationResponse

    /**
     * LiveData And MutableLiveData of Product.
     */

    private val _productResponse = MutableLiveData<Event<Resource<String>>>()
    val productResponse: LiveData<Event<Resource<String>>> = _productResponse

    /**
     * LiveData And MutableLiveData of Sector Code.
     */

    private val _sectorCodeResponse = MutableLiveData<Event<Resource<String>>>()
    val sectorCodeResponse: LiveData<Event<Resource<String>>> = _sectorCodeResponse

    /**
     * LiveData And MutableLiveData of Sub Sector Code.
     */

    private val _subsectorCodeResponse = MutableLiveData<Event<Resource<String>>>()
    val subsectorCodeResponse: LiveData<Event<Resource<String>>> = _subsectorCodeResponse

    /**
     * LiveData And MutableLiveData of Nature of Borrower.
     */

    private val _natureofBorrowerResponse = MutableLiveData<Event<Resource<String>>>()
    val natureofBorrowerResponse: LiveData<Event<Resource<String>>> = _natureofBorrowerResponse

    /**
     * LiveData And MutableLiveData of Family Member.
     */

    private val _familymemberResponse = MutableLiveData<Event<Resource<String>>>()
    val familymemberResponse: LiveData<Event<Resource<String>>> = _familymemberResponse


    /**
     * LiveData And MutableLiveData of Term Tenure.
     */
    private val _termTenureResponse = MutableLiveData<Event<Resource<String>>>()
    val termTernureResponse : LiveData<Event<Resource<String>>> = _termTenureResponse

    /**
     * LiveData And MutableLiveData Of Saving Account.
     */
    private val _savingAccountResponse = MutableLiveData<Event<Resource<String>>>()
    val savingAccountResponse : LiveData<Event<Resource<String>>> = _savingAccountResponse


    /**
     * LiveData And MutableLiveData Of Individual Loan Account RelationShip
     */
    private val _accountRelationShipResponse = MutableLiveData<Event<Resource<String>>>()
    val accountRelationShipResponse : LiveData<Event<Resource<String>>> = _accountRelationShipResponse

    /**
     * LiveData And MutableLiveData Of Individual Loan Mode Of Operation
     */
    private val _modeOfOperationResponse = MutableLiveData<Event<Resource<String>>>()
    val modeOfOperationResponse : LiveData<Event<Resource<String>>> = _modeOfOperationResponse

    /**
     * LiveData And MutableLiveData Of Individual Loan Customer Info
     */
    private val _customerInfoResponse = MutableLiveData<Event<Resource<String>>>()
    val customerInfoResponse : LiveData<Event<Resource<String>>> = _customerInfoResponse


    var recordResponse: LiveData<List<Record_Info>> = MutableLiveData()
    var product_Response: LiveData<List<Product_Info>> = MutableLiveData()
    lateinit var emppwdResponse: Record_Info
    lateinit var occu_create_request:Any
    lateinit var product_request:Any
    lateinit var sector_code_request:Any
    lateinit var sub_sector_code_request:Any
    lateinit var nature_of_borrower_request:Any
    lateinit var family_member_request:Any
    lateinit var term_Tenure_Request : Any
    lateinit var saving_Account_Request : Any
    lateinit var product_info_response:Product_Info
    lateinit var sector_code_response:Sector_Code_Info
    lateinit var sub_sector_code_response:Sub_Sector_Code_Info
    lateinit var occupation_response:Occupation_Info
    lateinit var nature_of_borrower_code:Nature_of_Borrower_Code_Info
    lateinit var family_member_info:Family_Member_Info
    lateinit var account_RelationShip_Request : Any
    lateinit var mode_of_operation_request : Any
    lateinit var customer_Info_Request : Any


    var sector_Code_Response: LiveData<List<Sector_Code_Info>> = MutableLiveData()
    var sub_sector_Code_Response: LiveData<List<Sub_Sector_Code_Info>> = MutableLiveData()
    var nature_of_borrower_Code_Response: LiveData<List<Nature_of_Borrower_Code_Info>> = MutableLiveData()
    var occupation_Response: LiveData<List<Occupation_Info>> = MutableLiveData()
    var family_member_Response: LiveData<List<Family_Member_Info>> = MutableLiveData()
    var termTenure_Response : LiveData<List<Term_Tenure>> = MutableLiveData()
    var savingAccount_Response : LiveData<List<SavingAccount>> = MutableLiveData()
    var accountRelationShip_Response : LiveData<List<AccountRelationShip_Info>> = MutableLiveData()
    var modeOfOperation_Response : LiveData<List<ModeOfOperation_Info>> = MutableLiveData()
    var customerInfo_Response : LiveData<List<Customer_Info>> = MutableLiveData()

    var common_Response: LiveData<List<Any>> = MutableLiveData()

    lateinit var db: MBDB;

    fun initialize_db(common_db: MBDB, ctx: Context){
        db =common_db
    }

    /**
     * All Request Function.
     */

    fun request(body:Any,show_val:String){
        when(show_val){
            "occupation"->{
                occu_create_request=body
                connection_api()
            }"product"->{
                product_request=body
                connection_api()
            }"sector_code"->{
                sector_code_request=body
                connection_api()
            }"sub_sector_code"->{
                sub_sector_code_request=body
                connection_api()
            }"nature_of_borrower"->{
                nature_of_borrower_request=body
                connection_api()
            }"family_member"->{
                family_member_request=body
                connection_api()
            }"term"->{
                term_Tenure_Request=body
                connection_api()
            }"savingAccount"->{
                saving_Account_Request=body
                connection_api()
            }"account_relation"->{
                account_RelationShip_Request=body
                connection_api()
            }"mode_of_operation"->{
                mode_of_operation_request=body
                connection_api()
            }"customer_info"->{
                customer_Info_Request=body
                connection_api()
            }
        }
    }

    private fun connection_api() {
        viewModelScope.launch {
            supervisorScope {
                try {
                    if (Utils.hasInternetConnection(getApplication<MBApplication>())) {
                        val call1 = async { appRepository.request(occu_create_request) }
                        val call2 = async { appRepository.request(product_request) }
                        val call3 = async { appRepository.request(sector_code_request) }
                        val call4 = async { appRepository.request(sub_sector_code_request) }
                        val call5 = async { appRepository.request(nature_of_borrower_request) }
                        val call6 = async { appRepository.request(family_member_request) }
                        val call7 = async { appRepository.request(term_Tenure_Request) }
                        val call8 = async { appRepository.request(saving_Account_Request) }
                        val call9 = async { appRepository.request(account_RelationShip_Request) }
                        val call10 = async { appRepository.request(mode_of_operation_request) }
                        val call11 = async { appRepository.request(customer_Info_Request) }


                        /**
                         * Occupation Async Call.
                         */
                        _occupationResponse.postValue(handleResponse(
                            try {
                                call1.await()
                            }catch (ex: Exception){
                                null
                            }!!))


                        /**
                         * Product Async Call.
                         */

                        _productResponse.postValue(handleResponse(
                            try {
                                call2.await()
                            }catch (ex: Exception){
                                null
                            }!!))


                        /**
                         * Sector Code Async Call.
                         */

                        _sectorCodeResponse.postValue(handleResponse(
                            try {
                                call3.await()
                            }catch (ex: Exception){
                                null
                            }!!))


                        /**
                         * Sub Sector Async Call.
                         */
                        _subsectorCodeResponse.postValue(handleResponse(
                            try {
                                call4.await()
                            }catch (ex: Exception){
                                null
                            }!!))


                        /**
                         * Nature of Borrower Async Call.
                         */

                        _natureofBorrowerResponse.postValue(handleResponse(
                            try {
                                call5.await()
                            }catch (ex: Exception){
                                null
                            }!!))

                        /**
                         * Family Member Async Call.
                         */

                        _familymemberResponse.postValue(handleResponse(
                            try {
                                call6.await()
                            }catch (ex: Exception){
                                null
                            }!!))

                        /**
                         * Term Tenure Async Call.
                         */
                        _termTenureResponse.postValue(handleResponse(
                            try {
                                call7.await()
                            }catch (ex:Exception){
                                null
                            }!!))
                        /**
                         * Saving Account Async Call.
                         */
                        _savingAccountResponse.postValue(handleResponse(
                            try{
                                call8.await()
                            }catch (ex:Exception){
                                null
                            }!!))

                        /**
                         * Account RelationShip Async Call.
                         */
                        _accountRelationShipResponse.postValue(handleResponse(
                            try {
                                call9.await()
                            }catch (ex:Exception){
                                null
                            }!!))

                        /**
                         * Mode Of Operation Async Call.
                         */
                        _modeOfOperationResponse.postValue(handleResponse(
                            try {
                                call10.await()
                            }catch (ex:Exception){
                                null
                            }!!))

                        /**
                         * Customer Info Async Call.
                         */
                        _customerInfoResponse.postValue(handleResponse(
                            try {
                                call11.await()
                            }catch (ex:Exception){
                                null
                            }!!))
                    }else{

                    }
                }catch (e:Exception){

                }
            }

        }
    }


    /**
     * handle all response
     */
    private fun handleResponse(response: Response<String>):Event<Resource<String>>?{

        if(response.isSuccessful){
            response.body()?.let { resultResponse ->
                return Event(Resource.Success(resultResponse))
            }
        }
        return Event((Resource.Error(response.message())))
    }

    fun insert(insert_list:ArrayList<*>,list_val:String){
        viewModelScope.launch(Dispatchers.IO) {
           when(list_val){
               "occupation_insert"->{
                   var occupation_info_list:ArrayList<Occupation_Info>
                   occupation_info_list=insert_list as ArrayList<Occupation_Info>
                   occupation_info_list.forEach {
                       var show_occupation_info=Occupation_Info(it.occupation_id,it.fn,it.fv)
                       db.daoOccupationInfo().insertOrUpdate(show_occupation_info)
                       //Log.e("occupation info insert","occupation info insert")
                   }
               }"product_insert"->{
                    var product_info_list: ArrayList<Product_Info>
                    product_info_list= insert_list as ArrayList<Product_Info>
                    product_info_list.forEach {
                           var show_product_info= Product_Info(it.product_id,it.fn,it.fv)
                           db.daoProductInfo().insertOrUpdate(show_product_info)
                   }
               }"sector_code_insert"->{
                   var sector_code_list: ArrayList<Sector_Code_Info>
                   sector_code_list= insert_list as ArrayList<Sector_Code_Info>
                   sector_code_list.forEach {
                       var show_sector_code_info= Sector_Code_Info(it.sector_code_id,it.fn,it.fv)
                       db.daoSectorCodeInfo().insertOrUpdate(show_sector_code_info)
                      }
                }"sub_sector_code_info"->{
                    var sub_sector_code_list: ArrayList<Sub_Sector_Code_Info>
                    sub_sector_code_list= insert_list as ArrayList<Sub_Sector_Code_Info>
                    sub_sector_code_list.forEach {
                       var show_sub_sector_code_info= Sub_Sector_Code_Info(it.sub_sector_code_id,it.fn,it.fv)
                       db.daoSubSectorCodeInfo().insertOrUpdate(show_sub_sector_code_info)
                   }
                }"nature_of_borrower_code_insert"->{
                    var nature_of_borrower_code_list: ArrayList<Nature_of_Borrower_Code_Info>
                    nature_of_borrower_code_list= insert_list as ArrayList<Nature_of_Borrower_Code_Info>
                    nature_of_borrower_code_list.forEach {
                    var show_nature_of_code_code_info= Nature_of_Borrower_Code_Info(it.nature_of_borrower_code_id,it.fn,it.fv)
                    db.daoNatureofBorrowerCodeInfo().insertOrUpdate(show_nature_of_code_code_info)
                   //Log.e("natureofborrowerinsert","natureofborrowerinsert")
               }
            }"family_member_insert"->{
               var family_member_code_list: ArrayList<Family_Member_Info>
               family_member_code_list= insert_list as ArrayList<Family_Member_Info>
               family_member_code_list.forEach {
                   var show_family_member_code_info= Family_Member_Info(it.family_member_id,it.fn,it.fv)
                   db.daoFamilyMemberInfo().insertOrUpdate(show_family_member_code_info)
                   Log.e("family member insert","family member value insert")
               }
            }"term"->{
                   var termTenureList: ArrayList<Term_Tenure>
                   termTenureList= insert_list as ArrayList<Term_Tenure>
                   termTenureList.forEach {
                   var termTenureInfoData = Term_Tenure(it.termtenure_id,it.fn,it.fv)
                   db.daoTermTernureInfo().insertOrUpdate(termTenureInfoData)
                   //Log.e("Term Tenure Code Insert","Term Tenure Code Insert")
               }
            }"saving_account"->{
                   var savingAccountList: ArrayList<SavingAccount>
                   savingAccountList= insert_list as ArrayList<SavingAccount>
                   savingAccountList.forEach {
                       var savingAccountinfoData = SavingAccount(it.savingaccount_id,it.fn,it.fv)
                       db.daoSavingAccountInfo().insertOrUpdate(savingAccountinfoData)
                   }
            }"account_relation"->{
                   var accountRelationShipList: ArrayList<AccountRelationShip_Info>
                   accountRelationShipList= insert_list as ArrayList<AccountRelationShip_Info>
                   accountRelationShipList.forEach {
                       var accountRelationShipInfoData = AccountRelationShip_Info(it.account_rel_id,it.fn,it.fv)
                       db.daoAccountRelationInfo().insertOrUpdate(accountRelationShipInfoData)
                   }
            }"mode_of_operation"->{
               var modeOfOperationList: ArrayList<ModeOfOperation_Info>
               modeOfOperationList= insert_list as ArrayList<ModeOfOperation_Info>
               modeOfOperationList.forEach {
                   var modeOfOperationInfoData = ModeOfOperation_Info(it.modeOfOperation_id,it.fn,it.fv)
                   db.daoModeOfOperation().insertOrUpdate(modeOfOperationInfoData)
                   Log.e("mode operation","mode operation data Insert")
               }

            }"customer_info"->{
               var CustomerInfoList: ArrayList<Customer_Info>
               CustomerInfoList= insert_list as ArrayList<Customer_Info>
               CustomerInfoList.forEach {
                   var customerInfoData = Customer_Info(it.customer_id,it.fn,it.fv)
                   db.daoCustomerInfo().insertOrUpdate(customerInfoData)
               }
            }
           }
        }
    }


    //common data
    fun get_data(show_val: String): LiveData<List<Any>> {
        when(show_val){
            "occupation"->{
                occupation_Response=db.daoOccupationInfo().getAllRecord()
                common_Response=occupation_Response as LiveData<List<Any>>
            }"product"->{
                product_Response=db.daoProductInfo().getAllRecord()
                common_Response=product_Response as LiveData<List<Any>>
            }"sector"->{
                sector_Code_Response=db.daoSectorCodeInfo().getAllRecord()
                common_Response=sector_Code_Response as LiveData<List<Any>>
            }"sub_sector"->{
                sub_sector_Code_Response=db.daoSubSectorCodeInfo().getAllRecord()
                common_Response=sub_sector_Code_Response as LiveData<List<Any>>
            }"nature_of_borrower"->{
                nature_of_borrower_Code_Response=db.daoNatureofBorrowerCodeInfo().getAllRecord()
                common_Response=nature_of_borrower_Code_Response as LiveData<List<Any>>
            }"family_member"->{
                family_member_Response=db.daoFamilyMemberInfo().getAllRecord()
                common_Response=family_member_Response as LiveData<List<Any>>
            }"term"->{
                termTenure_Response = db.daoTermTernureInfo().getAllRecord()
                common_Response=termTenure_Response as LiveData<List<Any>>
            }"saving_account"->{
                savingAccount_Response = db.daoSavingAccountInfo().getAllRecord()
                common_Response=savingAccount_Response as LiveData<List<Any>>
            }"account_relation"->{
                accountRelationShip_Response = db.daoAccountRelationInfo().getAllRecord()
                common_Response=accountRelationShip_Response as LiveData<List<Any>>
            }"mode_of_operation"->{
                modeOfOperation_Response = db.daoModeOfOperation().getAllRecord()
                common_Response=modeOfOperation_Response as LiveData<List<Any>>
            }"customer_info"->{
                customerInfo_Response = db.daoCustomerInfo().getAllRecord()
                common_Response=customerInfo_Response as LiveData<List<Any>>
            }
        }

        return common_Response
    }

    // get product id with product name
    fun get_product_id(product_name:String): Product_Info {
        product_info_response=db.daoProductInfo().getProductName(product_name)
        return product_info_response
    }
    //get product name with respect of product id
    fun get_product_name(product_id:Int): Product_Info {
        product_info_response=db.daoProductInfo().getProductId(product_id)
        return product_info_response
    }

    // get sector code id with sector code name
    fun get_sector_code_id(sector_code_name:String): Sector_Code_Info {
        sector_code_response=db.daoSectorCodeInfo().getSectorCodeId(sector_code_name)
        return sector_code_response
    }
    //get sector code name with respect of sector code id
    fun get_sector_code_name(sector_code_id:Int): Sector_Code_Info {
        sector_code_response=db.daoSectorCodeInfo().getSectorCodeName(sector_code_id)
        return sector_code_response
    }
    // get sub sector code id with sub sector code name
    fun get_sub_sector_code_id(sub_sector_code_name:String): Sub_Sector_Code_Info {
        sub_sector_code_response=db.daoSubSectorCodeInfo().getSubSectorCodeId(sub_sector_code_name)
        return sub_sector_code_response
    }
    //get sector code name with respect of sector code id
    fun get_sub_sector_code_name(sub_sector_code_id:Int): Sub_Sector_Code_Info {
        sub_sector_code_response=db.daoSubSectorCodeInfo().getSubSectorCodeName(sub_sector_code_id)
        return sub_sector_code_response
    }

    // get occupation code id with occupation code name
    fun get_occupation_code_id(occupation_code_name:String): Occupation_Info {
        occupation_response=db.daoOccupationInfo().getOccupationId(occupation_code_name)
        return occupation_response
    }
    //get occupation code name with respect of occupation code id
    fun get_occupation_code_name(occupation_id:String): Occupation_Info {
        occupation_response=db.daoOccupationInfo().getOccupationName(occupation_id)
        return occupation_response
    }

    // get nature of borrower id with occupation code name
    fun get_nature_of_borrower_code_id(nature_of_borrower_name:String): Nature_of_Borrower_Code_Info {
        nature_of_borrower_code=db.daoNatureofBorrowerCodeInfo().getNatureofBorrowerName(nature_of_borrower_name)
        return nature_of_borrower_code
    }
    // get nature of borrower name with occupation code name
    fun get_nature_of_borrower_code_name(nature_of_borrower_id:String): Nature_of_Borrower_Code_Info {
        nature_of_borrower_code=db.daoNatureofBorrowerCodeInfo().getNatureofBorrowerId(nature_of_borrower_id)
        return nature_of_borrower_code
    }

    // get family member id with family member name
    fun get_family_member_id(family_member_name:String): Family_Member_Info {
        family_member_info=db.daoFamilyMemberInfo().getFamilyMemberId(family_member_name)
        return family_member_info
    }
    // get nature of borrower name with occupation code name
    fun get_family_member_name(family_member_id:Int): Family_Member_Info {
        family_member_info=db.daoFamilyMemberInfo().getFamilyMemberName(family_member_id)
        return family_member_info
    }

}