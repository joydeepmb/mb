package app.bandhan.microbanking.common

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.Window
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import app.bandhan.microbanking.R
import app.bandhan.microbanking.dao.*
import app.bandhan.microbanking.dao.Customer_Info
import app.bandhan.microbanking.databinding.ActivityCommonBinding
import app.bandhan.microbanking.db.MBDB
import app.bandhan.microbanking.model.*
import app.bandhan.microbanking.repository.AppRepository
import app.bandhan.microbanking.util.Resource
import app.bandhan.microbanking.util.Utils
import app.bandhan.microbanking.viewmodel.CommonViewModel
import app.bandhan.microbanking.viewmodel.GroupLoanSaveViewModel
import app.bandhan.microbanking.viewmodel.LoginViewModel
import app.bandhan.microbanking.viewmodel.ViewModelProviderFactory

class MBCommonActivity : AppCompatActivity() {

    lateinit var binding:ActivityCommonBinding
    var store_val:String?=null
    lateinit var navController: NavController
    lateinit var common_view_model: CommonViewModel
    lateinit var ctx:Context
    private var mb_DB: MBDB? = null
    val group_loan_save_view_model: GroupLoanSaveViewModel by viewModels()

    @SuppressLint("ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        requestWindowFeature(Window.FEATURE_NO_TITLE) //will hide the title
        supportActionBar!!.hide()
        super.onCreate(savedInstanceState)
        ctx=this
        binding = ActivityCommonBinding.inflate(layoutInflater)
        setContentView(binding.root)
        if(intent.getStringExtra("loan_info")!=null)
            store_val=intent.getStringExtra("loan_info")
        //println(store_val)
        initialization()
        observable_in_ui()

    }
    private fun go_to_next() {

        val args = Bundle()
        args.putString("formName", store_val)
        navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        navController.navigate(R.id.MBCommonFragment,args)
    }

    private fun call_api_for_get_occupation_product() {

        //for occupation
        val method_arg_List:List<MethodArg> = ArrayList()
        val db_tuple_lite:List<DbTupleLite> = ArrayList()
        val sql_script_list:ArrayList<SqlScriptList> = ArrayList()
        val argumentList:List<String> = ArrayList()
        val return_field_list:List<String> = ArrayList()
        val argumentListLite:ArrayList<ArgumentLite> = ArrayList()
        val recordList:List<String> = ArrayList()
        val dbTupleLite:List<String> = ArrayList()
        val base_object_val : List<String> = ArrayList()


        var record_table=RecordTable("","","",recordList,"")
        var record_table_lite=RecordTableLite("","","","",recordList)
        //val argument_list_lite=ArgumentLite()
        //argumentListLite.add(argument_list_lite)

        sql_script_list.add(SqlScriptList(1084,0,
            "","","","2021-08-12T00:00:00+05:30",
            "",argumentList,0,return_field_list,"","",""
            ,"","","",-999,argumentListLite,
            record_table,record_table_lite,"","","",""
        ))

        var occupation_code_request=AppRequest(-999,
            101026,1077,
            "","","","",0,
            "",method_arg_List,db_tuple_lite,sql_script_list,"",
            0,"",dbTupleLite,-999,0,"","",base_object_val,
            "FAILURE","","","","")

        Utils.showProgressDialog(ctx)
        common_view_model.request(occupation_code_request,"occupation")

        //product list
        val pro_method_arg_List:List<MethodArg> = ArrayList()
        val pro_db_tuple_lite:List<DbTupleLite> = ArrayList()
        val pro_sql_script_list:ArrayList<SqlScriptList> = ArrayList()
        val pro_argumentList:List<String> = ArrayList()
        val pro_return_field_list:List<String> = ArrayList()
        val pro_argumentListLite:ArrayList<ArgumentLite> = ArrayList()
        val pro_recordList:List<String> = ArrayList()
        val pro_dbTupleLite:List<String> = ArrayList()
        val pro_base_object_val : List<String> = ArrayList()

        var pro_record_table=RecordTable("","","",pro_recordList,"")
        var pro_record_table_lite=RecordTableLite("","","","",pro_recordList)
       // var pro_argument_lite=ArgumentLite()
        //pro_argumentListLite.add(pro_argument_lite)

        pro_sql_script_list.add(SqlScriptList(1085,0,
            "","","","2021-08-12T00:00:00+05:30",
            "",pro_argumentList,0,pro_return_field_list,"","",""
            ,"","","",-999,pro_argumentListLite,
            pro_record_table,pro_record_table_lite,"","","",""
        ))

        var product_code_request=AppRequest(-999,
            101026,1078,
            "","","","",0,
            "",pro_method_arg_List,pro_db_tuple_lite,pro_sql_script_list,"",
            0,"",pro_dbTupleLite,-999,0,"","",pro_base_object_val,
            "FAILURE","","","","")
        common_view_model.request(product_code_request,"product")


        //Select Sector Code
        val sector_code_method_arg_List:List<MethodArg> = ArrayList()
        val sector_code_db_tuple_lite:List<DbTupleLite> = ArrayList()
        val sector_code_sql_script_list:ArrayList<SqlScriptList> = ArrayList()
        val sector_code_argumentList:List<String> = ArrayList()
        val sector_code_return_field_list:List<String> = ArrayList()
        val sector_code_argumentListLite:ArrayList<ArgumentLite> = ArrayList()
        val sector_code_recordList:List<String> = ArrayList()
        val sector_code_dbTupleLite:List<String> = ArrayList()
        val sector_code_base_object_val : List<String> = ArrayList()

        var sector_code_record_table=RecordTable("","","",sector_code_recordList,"")
        var sector_code_record_table_lite=RecordTableLite("","","","",sector_code_recordList)
        //var sector_code_argument_lite=ArgumentLite()
        //pro_argumentListLite.add(pro_argument_lite)

        sector_code_sql_script_list.add(SqlScriptList(1079,0,
            "","","","2021-08-11T00:00:00+05:30",
            "",sector_code_argumentList,0,sector_code_return_field_list,"","",""
            ,"","","",-999,sector_code_argumentListLite,
            sector_code_record_table,sector_code_record_table_lite,"","","",""
        ))

        var sector_code_code_request=AppRequest(-999,
            101026,1070,
            "","","","",0,
            "",sector_code_method_arg_List,sector_code_db_tuple_lite,sector_code_sql_script_list,"",
            0,"",sector_code_dbTupleLite,-999,0,"","",sector_code_base_object_val,
            "FAILURE","","","","")
        common_view_model.request(sector_code_code_request,"sector_code")


        // Select Sub Sector Code
        val sub_sector_code_method_arg_List:List<MethodArg> = ArrayList()
        val sub_sector_code_db_tuple_lite:List<DbTupleLite> = ArrayList()
        val sub_sector_code_sql_script_list:ArrayList<SqlScriptList> = ArrayList()
        val sub_sector_code_argumentList:List<String> = ArrayList()
        val sub_sector_code_return_field_list:List<String> = ArrayList()
        val sub_sector_code_argumentListLite:ArrayList<ArgumentLite> = ArrayList()
        val sub_sector_code_recordList:List<String> = ArrayList()
        val sub_sector_code_dbTupleLite:List<String> = ArrayList()
        val sub_sector_code_base_object_val : List<String> = ArrayList()

        var sub_sector_code_record_table=RecordTable("","","",sub_sector_code_recordList,"")
        var sub_sector_code_record_table_lite=RecordTableLite("","","","",sub_sector_code_recordList)
        sub_sector_code_argumentListLite.add(ArgumentLite("@sectorId","110"))
        //var sector_code_argument_lite=ArgumentLite()
        //pro_argumentListLite.add(pro_argument_lite)

        sub_sector_code_sql_script_list.add(SqlScriptList(1080,0,
            "","","","2021-08-11T00:00:00+05:30",
            "",sub_sector_code_argumentList,0,sub_sector_code_return_field_list,"","",""
            ,"","","",-999,sub_sector_code_argumentListLite,
            sub_sector_code_record_table,sub_sector_code_record_table_lite,"","","",""
        ))

        var sub_sector_code_code_request=AppRequest(-999,
            101026,1071,
            "","","","",0,
            "",sub_sector_code_method_arg_List,sub_sector_code_db_tuple_lite,sub_sector_code_sql_script_list,"",
            0,"",sub_sector_code_dbTupleLite,-999,0,"","",sub_sector_code_base_object_val,
            "FAILURE","","","","")
        common_view_model.request(sub_sector_code_code_request,"sub_sector_code")


        //Nature of Borrower code api integrate
        val nature_of_borrower_code_method_arg_List:List<MethodArg> = ArrayList()
        val nature_of_borrower_code_db_tuple_lite:List<DbTupleLite> = ArrayList()
        val nature_of_borrower_code_sql_script_list:ArrayList<SqlScriptList> = ArrayList()
        val nature_of_borrower_code_argumentList:List<String> = ArrayList()
        val nature_of_borrower_code_return_field_list:List<String> = ArrayList()
        val nature_of_borrower_code_argumentListLite:ArrayList<ArgumentLite> = ArrayList()
        val nature_of_borrower_code_recordList:List<String> = ArrayList()
        val nature_of_borrower_code_dbTupleLite:List<String> = ArrayList()
        val nature_of_borrower_code_base_object_val : List<String> = ArrayList()

        var nature_of_borrower_code_record_table=RecordTable("","","",nature_of_borrower_code_recordList,"")
        var nature_of_borrower_record_table_lite=RecordTableLite("","","","",nature_of_borrower_code_recordList)
        //nature_of_borrower_code_argumentListLite.add(ArgumentLite("@sectorId","110"))
        //var sector_code_argument_lite=ArgumentLite()
        //pro_argumentListLite.add(pro_argument_lite)

        nature_of_borrower_code_sql_script_list.add(SqlScriptList(1094,0,
            "","","","2021-09-03T00:00:00+05:30",
            "",nature_of_borrower_code_argumentList,0,nature_of_borrower_code_return_field_list,"","",""
            ,"","","",-999,nature_of_borrower_code_argumentListLite,
            nature_of_borrower_code_record_table,nature_of_borrower_record_table_lite,"","","",""
        ))

        var nature_of_borrower_code_code_request=AppRequest(-999,
            101026,1092,
            "","","","",0,
            "",nature_of_borrower_code_method_arg_List,nature_of_borrower_code_db_tuple_lite,nature_of_borrower_code_sql_script_list,"",
            0,"",nature_of_borrower_code_dbTupleLite,-999,0,"","",nature_of_borrower_code_base_object_val,
            "FAILURE","","","","")
        common_view_model.request(nature_of_borrower_code_code_request,"nature_of_borrower")


        //family member request and response

        val family_member_code_method_arg_List:List<MethodArg> = ArrayList()
        val family_member_code_db_tuple_lite:List<DbTupleLite> = ArrayList()
        val family_member_code_sql_script_list:ArrayList<SqlScriptList> = ArrayList()
        val family_member_code_argumentList:List<String> = ArrayList()
        val family_member_code_return_field_list:List<String> = ArrayList()
        val family_member_code_argumentListLite:ArrayList<ArgumentLite> = ArrayList()
        val family_member_code_recordList:List<String> = ArrayList()
        val family_member_code_dbTupleLite:List<String> = ArrayList()
        val family_member_code_base_object_val : List<String> = ArrayList()

        var family_member_code_record_table=RecordTable("","","",family_member_code_recordList,"")
        var family_member_record_table_lite=RecordTableLite("","","","",family_member_code_recordList)
        //nature_of_borrower_code_argumentListLite.add(ArgumentLite("@sectorId","110"))
        //var sector_code_argument_lite=ArgumentLite()
        //pro_argumentListLite.add(pro_argument_lite)

        family_member_code_sql_script_list.add(SqlScriptList(1095,0,
            "","","","2021-09-03T00:00:00+05:30",
            "",family_member_code_argumentList,0,family_member_code_return_field_list,"","",""
            ,"","","",-999,family_member_code_argumentListLite,
            family_member_code_record_table,family_member_record_table_lite,"","","",""
        ))

        var family_member_code_request=AppRequest(-999,
            101026,1093,
            "","","","",0,
            "",family_member_code_method_arg_List,family_member_code_db_tuple_lite,family_member_code_sql_script_list,"",
            0,"",family_member_code_dbTupleLite,-999,0,"","",family_member_code_base_object_val,
            "FAILURE","","","","")
        common_view_model.request(family_member_code_request,"family_member")


        //Term Tenure List
        val term_tenure_method_arg_List:List<MethodArg> = ArrayList()
        val term_tenure_db_tuple_lite:List<DbTupleLite> = ArrayList()
        val term_tenure_sql_script_list:ArrayList<SqlScriptList> = ArrayList()
        val term_tenure_argumentList:List<String> = ArrayList()
        val term_tenure_return_field_list:List<String> = ArrayList()
        val term_tenure_argumentListLite:ArrayList<ArgumentLite> = ArrayList()
        val term_tenure_recordList:List<String> = ArrayList()
        val term_tenure_dbTupleLite:List<String> = ArrayList()
        val term_tenure_base_object_val : List<String> = ArrayList()

        var term_tenure_record_table=RecordTable("","","",term_tenure_recordList,"")
        var term_tenure_record_table_lite=RecordTableLite("","","","",term_tenure_recordList)
        term_tenure_argumentListLite.add(ArgumentLite("@productId","6601"))

        term_tenure_sql_script_list.add(
            SqlScriptList(1102,0,"","",
                "","2021-09-06T00:00:00+05:30","",term_tenure_argumentList,0,
                term_tenure_return_field_list,"","","","",
                "","",-999,term_tenure_argumentListLite,term_tenure_record_table,term_tenure_record_table_lite,
                "","","","")
        )

        var termTenureRequest = AppRequest(-999,101026,1101,"",
            "","","",0,"",term_tenure_method_arg_List,
            term_tenure_db_tuple_lite,term_tenure_sql_script_list,"",0,"",term_tenure_dbTupleLite,
            -999,0,"","",term_tenure_base_object_val,"FAILURE","","","","")

        common_view_model.request(termTenureRequest,"term")

        //Saving Account Number
        val saving_Account_method_arg_List:List<MethodArg> = ArrayList()
        val  saving_Account_db_tuple_lite:List<DbTupleLite> = ArrayList()
        val  saving_Account_sql_script_list:ArrayList<SqlScriptList> = ArrayList()
        val  saving_Account_argumentList:List<String> = ArrayList()
        val  saving_Account_return_field_list:List<String> = ArrayList()
        val  saving_Account_argumentListLite:ArrayList<ArgumentLite> = ArrayList()
        val  saving_Account_recordList:List<String> = ArrayList()
        val  saving_Account_dbTupleLite:List<String> = ArrayList()
        val  saving_Account_base_object_val : List<String> = ArrayList()

        var saving_Account_record_table=RecordTable("","","",saving_Account_recordList,"")
        var  saving_Account_record_table_lite=RecordTableLite("","","","",saving_Account_recordList)
        saving_Account_argumentListLite.add(ArgumentLite("@customerNumber","150000000102"))

        saving_Account_sql_script_list.add(
            SqlScriptList(1101,0,"","",
                "","2021-09-06T00:00:00+05:30","",saving_Account_argumentList,0,
                saving_Account_return_field_list,"","","","",
                "","",-999,saving_Account_argumentListLite,saving_Account_record_table,saving_Account_record_table_lite,
                "","","","")
        )

        var savingAccountRequest = AppRequest(-999,101026,1100,"",
            "","","",0,"",saving_Account_method_arg_List,
            saving_Account_db_tuple_lite,saving_Account_sql_script_list,"",0,"",saving_Account_dbTupleLite,
            -999,0,"","",saving_Account_base_object_val,"FAILURE","","","","")

        common_view_model.request(savingAccountRequest,"savingAccount")

        //Account RelationShip Data
        val  account_rel_method_arg_List:List<MethodArg> = ArrayList()
        val  account_rel_db_tuple_lite:List<DbTupleLite> = ArrayList()
        val  account_rel_sql_script_list:ArrayList<SqlScriptList> = ArrayList()
        val  account_rel_argumentList:List<String> = ArrayList()
        val  account_rel_return_field_list:List<String> = ArrayList()
        val  account_rel_argumentListLite:ArrayList<ArgumentLite> = ArrayList()
        val  account_rel_recordList:List<String> = ArrayList()
        val  account_rel_dbTupleLite:List<String> = ArrayList()
        val  account_rel_base_object_val : List<String> = ArrayList()

        var  account_rel_record_table=RecordTable("","","",account_rel_recordList,"")
        var  account_rel_record_table_lite=RecordTableLite("","","","",account_rel_recordList)

        account_rel_sql_script_list.add(
            SqlScriptList(1107,0,"","",
                "","2021-09-09T00:00:00+05:30","",account_rel_argumentList,0,
                account_rel_return_field_list,"","","","",
                "","",-999,account_rel_argumentListLite,account_rel_record_table,account_rel_record_table_lite,
                "","","",""))

        var accountRelationShipRequest = AppRequest(-999,101026,1106,"",
            "","","",0,"",account_rel_method_arg_List,
            account_rel_db_tuple_lite,account_rel_sql_script_list,"",0,"",account_rel_dbTupleLite,
            -999,0,"","",account_rel_base_object_val,"FAILURE","","","","")

        common_view_model.request(accountRelationShipRequest,"account_relation")

        //Mode Of Operation Data
        val  mode_of_operation_method_arg_List:List<MethodArg> = ArrayList()
        val  mode_of_operation_db_tuple_lite:List<DbTupleLite> = ArrayList()
        val  mode_of_operation_sql_script_list:ArrayList<SqlScriptList> = ArrayList()
        val  mode_of_operation_argumentList:List<String> = ArrayList()
        val  mode_of_operation_return_field_list:List<String> = ArrayList()
        val  mode_of_operation_argumentListLite:ArrayList<ArgumentLite> = ArrayList()
        val  mode_of_operation_recordList:List<String> = ArrayList()
        val  mode_of_operation_dbTupleLite:List<String> = ArrayList()
        val  mode_of_operation_base_object_val : List<String> = ArrayList()

        var  mode_of_operation_record_table=RecordTable("","","",mode_of_operation_recordList,"")
        var  mode_of_operation_record_table_lite=RecordTableLite("","","","",mode_of_operation_recordList)

        mode_of_operation_sql_script_list.add(
            SqlScriptList(1106,0,"","",
                "","2021-09-09T00:00:00+05:30","", mode_of_operation_argumentList,0,
                mode_of_operation_return_field_list,"","","","",
                "","",-999, mode_of_operation_argumentListLite, mode_of_operation_record_table, mode_of_operation_record_table_lite,
                "","","",""))

        var  modeOfOperationRequest = AppRequest(-999,101026,1105,"",
            "","","",0,"", mode_of_operation_method_arg_List,
            mode_of_operation_db_tuple_lite, mode_of_operation_sql_script_list,"",0,"", mode_of_operation_dbTupleLite,
            -999,0,"","", mode_of_operation_base_object_val,"FAILURE","","","","")

        common_view_model.request(modeOfOperationRequest,"mode_of_operation")

        val customer_Info_method_arg_List: List<MethodArg> = ArrayList()
        val customer_Info_db_tuple_lite: List<DbTupleLite> = ArrayList()
        val customer_Info_sql_script_list: ArrayList<SqlScriptList> = ArrayList()
        val customer_Info_argumentList: List<String> = ArrayList()
        val customer_Info_return_field_list: List<String> = ArrayList()
        val customer_Info_argumentListLite: ArrayList<ArgumentLite> = ArrayList()
        val customer_Info_recordList: List<String> = ArrayList()
        val customer_Info_dbTupleLite: List<String> = ArrayList()
        val customer_Info_base_object_val: List<String> = ArrayList()

        var customer_Info_record_table = RecordTable("", "", "", customer_Info_recordList, "")
        var customer_Info_record_table_lite = RecordTableLite("", "", "", "", customer_Info_recordList)
        val customer_Info_argument_list_lite = ArgumentLite("@customerId", "150000000101")
        customer_Info_argumentListLite.add(customer_Info_argument_list_lite)

        customer_Info_sql_script_list.add(
            SqlScriptList(
                1083, 0, "", "", "", "2021-08-12T00:00:00+05:30", "",
                customer_Info_argumentList, 0, customer_Info_return_field_list, "", "", "",
                "", "", "", -999, customer_Info_argumentListLite,
                customer_Info_record_table, customer_Info_record_table_lite, "", "", "", ""
            )
        )

        var customer_Info_occupation_code_request = AppRequest(
            -999, 101026, 1076, "", "", "",
            "", 0, "", customer_Info_method_arg_List, customer_Info_db_tuple_lite,
            customer_Info_sql_script_list, "", 0, "", customer_Info_dbTupleLite, -999,
            0, "", "", customer_Info_base_object_val, "FAILURE", "",
            "", "", ""
        )
        common_view_model.request(customer_Info_occupation_code_request,"customer_info")

    }

    private fun observable_in_ui() {

        // occupation response
        common_view_model.occupationResponse.observe(this, Observer { event ->
            event.getContentIfNotHandled()?.let { response ->
                when (response) {
                    is Resource.Success -> {
                        Utils.dismissProgressDialog()
                        response.data?.let { loginResponse ->
                            var var_res=Utils.return_data(loginResponse,response_class = MBResponse())
                            if(var_res is MBResponse){
                                val store_occupation_info:ArrayList<Occupation_Info> =ArrayList()
                                val id:Int=0
                                var_res.dbTupleLite!!.forEach {
                                    it.recordList!!.forEach {
                                        val occupation_id=it.record!!.get(0).fv
                                        val occpation_val=it.record!!.get(1).fv
                                        var record_x_info= Occupation_Info(id,occupation_id.toString(),occpation_val.toString())
                                        store_occupation_info.add(record_x_info)
                                    }
                                    common_view_model.insert(store_occupation_info,"occupation_insert")
                                }
                            }
                        }
                    }

                    is Resource.Error -> {
                        response.message?.let { message ->
                            Utils.dismissProgressDialog()
                            Utils.showMessage(binding.llCommon,message)
                        }
                    }

                    is Resource.Loading<*> -> {

                    }
                }
            }
        })

        //product response
        common_view_model.productResponse.observe(this, Observer { event ->
            event.getContentIfNotHandled()?.let { response ->
                when (response) {
                    is Resource.Success -> {
                        Utils.dismissProgressDialog()
                        response.data?.let { loginResponse ->
                            var var_res=Utils.return_data(loginResponse,response_class = MBResponse())
                            if(var_res is MBResponse){
                                val store_product_info:ArrayList<Product_Info> =ArrayList()
                                val id:Int=0
                                var_res.dbTupleLite!!.forEach {
                                    it.recordList!!.forEach {
                                        var product_id=it.record!!.get(0).fv
                                        var product_val=it.record!!.get(1).fv
                                        var record_x_info= Product_Info(id,product_id.toString(),product_val.toString())
                                        store_product_info.add(record_x_info)
                                    }
                                    common_view_model.insert(store_product_info,"product_insert")
                                }
                            }
                        }
                    }

                    is Resource.Error -> {
                        response.message?.let { message ->
                            Utils.dismissProgressDialog()
                            Utils.showMessage(binding.llCommon,message)
                        }
                    }

                    is Resource.Loading<*> -> {

                    }
                }
            }
        })
        // Sector Code db insert
        common_view_model.sectorCodeResponse.observe(this, Observer { event ->
            event.getContentIfNotHandled()?.let { response ->
                when (response) {
                    is Resource.Success -> {
                        Utils.dismissProgressDialog()
                        response.data?.let { loginResponse ->
                            var var_res=Utils.return_data(loginResponse,response_class = MBResponse())
                            if(var_res is MBResponse){
                                val store_sector_code_info:ArrayList<Sector_Code_Info> =ArrayList()
                                val id:Int=0
                                var_res.dbTupleLite!!.forEach {
                                    it.recordList!!.forEach {
                                        var sector_code_id=it.record!!.get(0).fv
                                        var sector_code_val=it.record!!.get(1).fv
                                        var record_x_info= Sector_Code_Info(id,sector_code_id.toString(),sector_code_val.toString())
                                        store_sector_code_info.add(record_x_info)
                                    }
                                    common_view_model.insert(store_sector_code_info,"sector_code_insert")
                                }
                            }
                        }
                    }
                    is Resource.Error -> {
                        response.message?.let { message ->
                            Utils.dismissProgressDialog()
                            Utils.showMessage(binding.llCommon,message)
                        }
                    }

                    is Resource.Loading<*> -> {

                    }
                }
            }
        })

        // Sub Sector Code db insert
        common_view_model.subsectorCodeResponse.observe(this, Observer { event ->
            event.getContentIfNotHandled()?.let { response ->
                when (response) {
                    is Resource.Success -> {
                        Utils.dismissProgressDialog()
                        response.data?.let { loginResponse ->
                            var var_res=Utils.return_data(loginResponse,response_class = MBResponse())
                            if(var_res is MBResponse){
                                val store_sub_sector_code_info:ArrayList<Sub_Sector_Code_Info> =ArrayList()
                                val id:Int=0
                                var_res.dbTupleLite!!.forEach {
                                    it.recordList!!.forEach {
                                        var sub_sector_code_id=it.record!!.get(0).fv
                                        var sub_sector_code_val=it.record!!.get(1).fv
                                        var record_x_info= Sub_Sector_Code_Info(id,sub_sector_code_id.toString(),sub_sector_code_val.toString())
                                        store_sub_sector_code_info.add(record_x_info)
                                    }
                                    common_view_model.insert(store_sub_sector_code_info,"sub_sector_code_info")
                                }
                            }
                        }
                    }

                    is Resource.Error -> {
                        response.message?.let { message ->
                            Utils.dismissProgressDialog()
                            Utils.showMessage(binding.llCommon,message)
                        }
                    }

                    is Resource.Loading<*> -> {}
                }
            }
        })

        //nature of borrower code db insert
        common_view_model.natureofBorrowerResponse.observe(this, Observer { event ->
            event.getContentIfNotHandled()?.let { response ->
                when (response) {
                    is Resource.Success -> {
                        Utils.dismissProgressDialog()
                        response.data?.let { loginResponse ->
                            var var_res=Utils.return_data(loginResponse,response_class = MBResponse())
                            if(var_res is MBResponse){
                                val store_nature_of_borrower_code_info:ArrayList<Nature_of_Borrower_Code_Info> =ArrayList()
                                val id:Int=0
                                var_res.dbTupleLite!!.forEach {
                                    it.recordList!!.forEach {
                                        var nature_of_borrower_code_id=it.record!!.get(0).fv
                                        var nature_of_borrower_code_val=it.record!!.get(1).fv
                                        var record_x_info= Nature_of_Borrower_Code_Info(id,nature_of_borrower_code_id.toString(),nature_of_borrower_code_val.toString())
                                        store_nature_of_borrower_code_info.add(record_x_info)
                                    }
                                    common_view_model.insert(store_nature_of_borrower_code_info,"nature_of_borrower_code_insert")
                                }
                            }
                        }
                    }

                    is Resource.Error -> {
                        response.message?.let { message ->
                            Utils.dismissProgressDialog()
                            Utils.showMessage(binding.llCommon,message)
                        }
                    }

                    is Resource.Loading<*> -> {}
                }
            }
        })

        //family member code db insert
        common_view_model.familymemberResponse.observe(this, Observer { event ->
            event.getContentIfNotHandled()?.let { response ->
                when (response) {
                    is Resource.Success -> {
                        Utils.dismissProgressDialog()
                        response.data?.let { loginResponse ->
                            var var_res=Utils.return_data(loginResponse,response_class = MBResponse())
                            if(var_res is MBResponse){
                                val store_family_member_info:ArrayList<Family_Member_Info> =ArrayList()
                                val id:Int=0
                                var_res.dbTupleLite!!.forEach {
                                    if(store_family_member_info.size>0)
                                        store_family_member_info.clear()
                                    it.recordList!!.forEach {
                                        var family_member_id=it.record!!.get(0).fv
                                        var family_member_val=it.record!!.get(1).fv
                                        var record_x_info= Family_Member_Info(id,family_member_id.toString(),family_member_val.toString())
                                        store_family_member_info.add(record_x_info)
                                    }
                                }
                                Log.e("***",store_family_member_info.size.toString())
                                common_view_model.insert(store_family_member_info,"family_member_insert")
                            }
                        }
                    }

                    is Resource.Error -> {
                        response.message?.let { message ->
                            Utils.dismissProgressDialog()
                            Utils.showMessage(binding.llCommon,message)
                        }
                    }

                    is Resource.Loading<*> -> {}
                }
            }
        })

        //Term Tenure db Insert
        common_view_model.termTernureResponse.observe(this, Observer { event ->
            event.getContentIfNotHandled()?.let { response ->
                when (response) {
                    is Resource.Success -> {
                        Utils.dismissProgressDialog()
                        response.data?.let { loginResponse ->
                            var var_res=Utils.return_data(loginResponse,response_class = MBResponse())
                            if(var_res is MBResponse){
                                val termTenureInfo:ArrayList<Term_Tenure> =ArrayList()
                                val id:Int=0
                                var_res.dbTupleLite!!.forEach {
                                    it.recordList!!.forEach {
                                        it.record!!.forEach {

                                            var record_x_info= Term_Tenure(id,it.fn.toString(),it.fv.toString())
                                            termTenureInfo.add(record_x_info)
                                        }

                                    }
                                    common_view_model.insert(termTenureInfo,"term")
                                }
                            }
                        }
                    }

                    is Resource.Error -> {
                        response.message?.let { message ->
                            Utils.dismissProgressDialog()
                            Utils.showMessage(binding.llCommon,message)
                        }
                    }

                    is Resource.Loading<*> -> {}
                }
            }

        })

        //Saving Account Number Insert
        common_view_model.savingAccountResponse.observe(this, Observer { event ->
            event.getContentIfNotHandled()?.let { response ->
                when (response) {
                    is Resource.Success -> {
                        Utils.dismissProgressDialog()
                        response.data?.let { loginResponse ->
                            var var_res=Utils.return_data(loginResponse,response_class = MBResponse())
                            if(var_res is MBResponse){
                                val savingAccountInfo:ArrayList<SavingAccount> =ArrayList()
                                val id:Int=0
                                var_res.dbTupleLite!!.forEach {
                                    it.recordList!!.forEach {
                                        it.record!!.forEach {
                                            var record_x_info= SavingAccount(id,it.fn.toString(),it.fv.toString())
                                            savingAccountInfo.add(record_x_info)
                                        }

                                    }
                                    common_view_model.insert(savingAccountInfo,"saving_account")
                                }
                            }
                        }
                    }

                    is Resource.Error -> {
                        response.message?.let { message ->
                            Utils.dismissProgressDialog()
                            Utils.showMessage(binding.llCommon,message)
                        }
                    }

                    is Resource.Loading<*> -> {}
                }
            }

        })
        //Account RelationShip DB Insert
        common_view_model.accountRelationShipResponse.observe(this, Observer { event ->
            event.getContentIfNotHandled()?.let { response ->
                when (response) {
                    is Resource.Success -> {
                        Utils.dismissProgressDialog()
                        response.data?.let { loginResponse ->
                            var var_res=Utils.return_data(loginResponse,response_class = MBResponse())
                            if(var_res is MBResponse){
                                val accountRelationShipInfo:ArrayList<AccountRelationShip_Info> =ArrayList()
                                val id:Int=0
                                var_res.dbTupleLite!!.forEach {
                                    it.recordList!!.forEach {
                                        var account_rel_id=it.record!!.get(0).fv
                                        var account_rel_val=it.record!!.get(1).fv

                                        Log.e("TAG",account_rel_id.toString())
                                        Log.e("TAG",account_rel_val.toString())


                                        var record_x_info= AccountRelationShip_Info(id,account_rel_id.toString(),account_rel_val.toString())
                                        accountRelationShipInfo.add(record_x_info)
                                    }
                                    common_view_model.insert(accountRelationShipInfo,"account_relation")
                                }
                            }
                        }
                    }

                    is Resource.Error -> {
                        response.message?.let { message ->
                            Utils.dismissProgressDialog()
                            Utils.showMessage(binding.llCommon,message)
                        }
                    }

                    is Resource.Loading<*> -> {}
                }
            }

        })

        //Mode Of Operation DB Insert
        common_view_model.modeOfOperationResponse.observe(this, Observer { event ->
            event.getContentIfNotHandled()?.let { response ->
                when (response) {
                    is Resource.Success -> {
                        Utils.dismissProgressDialog()
                        response.data?.let { loginResponse ->
                            var var_res=Utils.return_data(loginResponse,response_class = MBResponse())
                            if(var_res is MBResponse){
                                val modeOfOperationInfo:ArrayList<ModeOfOperation_Info> =ArrayList()
                                val id:Int=0
                                var_res.dbTupleLite!!.forEach {
                                    it.recordList!!.forEach {
                                        var mode_of_operation_id=it.record!!.get(0).fv
                                        var mode_of_operation_val=it.record!!.get(1).fv

                                        var record_x_info= ModeOfOperation_Info(id,mode_of_operation_id.toString(),mode_of_operation_val.toString())
                                        modeOfOperationInfo.add(record_x_info)
                                    }
                                    common_view_model.insert(modeOfOperationInfo,"mode_of_operation")
                                }
                            }
                        }
                    }

                    is Resource.Error -> {
                        response.message?.let { message ->
                            Utils.dismissProgressDialog()
                            Utils.showMessage(binding.llCommon,message)
                        }
                    }

                    is Resource.Loading<*> -> {}
                }
            }

        })

        //Customer Info DB Insert
        common_view_model.customerInfoResponse.observe(this, Observer { event ->
            event.getContentIfNotHandled()?.let { response ->
                when (response) {
                    is Resource.Success -> {
                        Utils.dismissProgressDialog()
                        response.data?.let { loginResponse ->
                            var var_res=Utils.return_data(loginResponse,response_class = MBResponse())
                            if(var_res is MBResponse){
                                val customerInfo:ArrayList<Customer_Info> =ArrayList()
                                val id:Int=0
                                var_res.dbTupleLite!!.forEach {
                                    it.recordList!!.forEach {
                                        it.record!!.forEach{
                                            Log.e("Tag","record :"+it.fn +"----"+ it.fv)
                                            var record_x_info= Customer_Info(id,it.fn.toString(),it.fv.toString())
                                            customerInfo.add(record_x_info)
                                        }
                                    }
                                    common_view_model.insert(customerInfo,"customer_info")
                                }
                            }
                        }
                    }

                    is Resource.Error -> {
                        response.message?.let { message ->
                            Utils.dismissProgressDialog()
                            Utils.showMessage(binding.llCommon,message)
                        }
                    }

                    is Resource.Loading<*> -> {}
                }
            }

        })
        common_view_model.get_data("product").observe(this, Observer { it
            if (it.isEmpty())
                call_api_for_get_occupation_product()
            else{
                go_to_next()
            }

        })
    }

    private fun initialization() {
        mb_DB = MBDB.getDataBase(this)
        val repository = AppRepository()
        val factory = ViewModelProviderFactory(application, repository,ctx)
        common_view_model= ViewModelProvider(this, factory).get(CommonViewModel::class.java)
        common_view_model.initialize_db(mb_DB!!,ctx)


    }
}