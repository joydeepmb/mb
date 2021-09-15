package app.bandhan.microbanking.loan.GroupLoan

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import app.bandhan.microbanking.R
import app.bandhan.microbanking.dao.Product_Info
import app.bandhan.microbanking.dashboard.DashBoardActivity
import app.bandhan.microbanking.databinding.CommonButtonLayoutBinding
import app.bandhan.microbanking.databinding.CustomerHeaderDetailsBinding
import app.bandhan.microbanking.databinding.FragmentGroupLoanIntroBinding
import app.bandhan.microbanking.db.MBDB
import app.bandhan.microbanking.model.*
import app.bandhan.microbanking.repository.AppRepository
import app.bandhan.microbanking.util.OnClickVal
import app.bandhan.microbanking.util.Resource
import app.bandhan.microbanking.util.Utils
import app.bandhan.microbanking.viewmodel.*

class GroupLoanIntroFragment: Fragment(R.layout.fragment_group_loan_intro) {

    lateinit var btn_binding:CommonButtonLayoutBinding
    lateinit var binding:FragmentGroupLoanIntroBinding
    lateinit var customer_header_details_binding: CustomerHeaderDetailsBinding
    lateinit var customer_view_model: CustomerViewModel
    lateinit var common_view_model: CommonViewModel
    lateinit var validation_view_model:ValidationViewModel
    var store_customer_info:ArrayList<Customer_Info> =ArrayList()
    var store_product:List<Product_Info> = ArrayList()
    private var mb_DB: MBDB? = null
    var is_valid:Boolean=false
    lateinit var group_loan_save_view_model:GroupLoanSaveViewModel
    lateinit var product_info_response:Product_Info

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        //val someInt = requireArguments().getInt("some_int")
        super.onViewCreated(view, savedInstanceState)
        group_loan_save_view_model = ViewModelProvider(requireActivity()).get(GroupLoanSaveViewModel::class.java)
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        binding = FragmentGroupLoanIntroBinding.inflate(layoutInflater)
        initialization()
        val view = binding.root
        customer_header_details_binding= CustomerHeaderDetailsBinding.bind(view)
        btn_binding=CommonButtonLayoutBinding.bind(view)
        customer_header_details_binding.tvheaderDetails.setText("Group Loan Intro")
        observable_on_ui()
        get_product_data()
        on_click_on_ui()
        set_value_in_ui()
        initObserver()

        return view
    }

    private fun initObserver() {
        /*lifecycleScope.launch {
            validation_view_model.isGroupLoanIntroSubmitEnabled.collect { value ->
                is_valid = value
            }
        }*/
        group_loan_save_view_model.store_app_request_value.observe(viewLifecycleOwner, Observer { it
           it.sqlScriptList.forEach {
               it.argumentListLite.forEach {
                   //println("val response***"+it.fn+it.fv)
                   when(it.fn){
                       "@productType"->{
                           product_info_response=common_view_model.get_product_name(it.fv!!.toInt())
                           binding.tvProductType.setText(product_info_response.fv)

                       }
                   }
               }
           }
        })
    }

    private fun set_value_in_ui() {
        validation_view_model.setCustomerId(binding.etCustomerId.text.toString())
        validation_view_model.setProductType(binding.tvProductType.getText().toString())
        validation_view_model.setBUID(binding.etBUId.getText().toString())
        validation_view_model.setGroupId(binding.etGroupId.getText().toString())
    }

    private fun on_click_on_ui() {

        btn_binding.btnContinue.setOnClickListener {

            is_valid=Utils.CheckAllFields(binding.etCustomerId,
                    binding.tvProductType.getText().toString(),
                    binding.tvProductType,
                    binding.fmProductType,
                    binding.etBUId,
                    binding.etAmountApplied,
                    binding.etAmountSanctioned,
                    binding.textInputUserId,
                    binding.textInputBUId,
                    binding.textInputAmountApplied,
                    binding.textInputAmountSanctioned)

                when(is_valid){
                    true->{

                        product_info_response=common_view_model.get_product_id(binding.tvProductType.getText().toString())
                        //Log.e("TAG Product Id:",product_info_response.fn.toString())
                        val method_arg_List:List<MethodArg> = ArrayList()
                        val db_tuple_lite:List<DbTupleLite> = ArrayList()
                        val sql_script_list:ArrayList<SqlScriptList> = ArrayList()
                        val argumentList:List<String> = ArrayList()
                        val return_field_list:List<String> = ArrayList()
                        val argumentListLite:ArrayList<ArgumentLite> = ArrayList()
                        val recordList:List<String> = ArrayList()
                        val dbTupleLite:List<String> = ArrayList()
                        val base_object_val : List<String> = ArrayList()

                        var record_table= RecordTable("","","",recordList,"")
                        var record_table_lite= RecordTableLite("","","","",recordList)


                        argumentListLite.add(ArgumentLite("@customerId",binding.etCustomerId.getText().toString()))
                        argumentListLite.add(ArgumentLite("@productType",product_info_response.fn.toString()))
                        argumentListLite.add(ArgumentLite("@buId",binding.etBUId.getText().toString()))
                        argumentListLite.add(ArgumentLite("@groupId",binding.etGroupId.getText().toString()))
                        argumentListLite.add(ArgumentLite("@customerName",binding.etCustomerName.getText().toString()))
                        argumentListLite.add(ArgumentLite("@spouseName",binding.etFatherName.getText().toString()))
                        argumentListLite.add(ArgumentLite("@groupName",binding.etGroupName.getText().toString()))
                        argumentListLite.add(ArgumentLite("@creditOfficeId",binding.etCreditOfficeId.getText().toString()))
                        argumentListLite.add(ArgumentLite("@collectionDay",binding.etCollectionDay.getText().toString()))
                        argumentListLite.add(ArgumentLite("@amountApplied",binding.etAmountApplied.getText().toString()))
                        argumentListLite.add(ArgumentLite("@amountSanctioned",binding.etAmountSanctioned.getText().toString()))

                        sql_script_list.add(
                            SqlScriptList(1093,0,
                                "","","","2021-09-01T00:00:00+05:30",
                                "",argumentList,0,return_field_list,"","",""
                                ,"","","",-999,argumentListLite,
                                record_table,record_table_lite,"","","",""
                            )
                        )

                        group_loan_save_view_model.loadAppRequest(AppRequest(-999,
                            binding.etCustomerId.getText().toString().toInt(),1088,
                            "","","","",0,
                            "",method_arg_List,db_tuple_lite,sql_script_list,"",
                            0,"",dbTupleLite,-999,0,"","",base_object_val,
                            "FAILURE","","","",""))

                        findNavController().navigate(R.id.action_groupLoanIntroFragment_to_groupLoanTermFragment)
                    }false->{
                        Log.e("false","false")
                }
            }

        }
        customer_header_details_binding.ibBack.setOnClickListener {
            requireActivity().finish() }
        customer_header_details_binding.ibHome.setOnClickListener {
            Intent(requireActivity(), DashBoardActivity::class.java).also {
                Intent.FLAG_ACTIVITY_CLEAR_TOP
                Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(it)
                //finish()
            }
        }

        binding.ImgBtnCustomerId.setOnClickListener {
            check_customer_id()
        }
        binding.fmProductType.setOnClickListener {

            Utils.common_alert_data(requireContext(),
                binding.tvProductType,
                store_product as ArrayList<*>,"Select Product")
        }
    }

    private fun get_product_data() {
        common_view_model.get_data("product").observe(viewLifecycleOwner, Observer { it
            store_product=it as List<Product_Info>
        })
    }



    private fun observable_on_ui() {
        //product response
        customer_view_model.customerResponse.observe(viewLifecycleOwner, Observer { event ->
            event.getContentIfNotHandled()?.let { response ->
                when (response) {
                    is Resource.Success -> {
                        Utils.dismissProgressDialog()
                        response.data?.let { loginResponse ->
                            var var_res=Utils.return_data(loginResponse,response_class = MBResponse())
                            if(var_res is MBResponse){

                                val id:Int=0
                                var_res.dbTupleLite!!.forEach {
                                    it.recordList!!.forEach {
                                        if(store_customer_info.size>0)
                                            store_customer_info.clear()
                                        it.record!!.forEach {
                                           var record_x_info= Customer_Info(it.fn.toString(),it.fv.toString())
                                            store_customer_info.add(record_x_info)

                                         }
                                        set_value_on_ui(store_customer_info)
                                    }

                                }
                            }
                        }
                    }

                    is Resource.Error -> {
                        response.message?.let { message ->
                            Utils.dismissProgressDialog()
                            //Utils.showMessage(binding.llCommon,message)
                        }
                    }

                    is Resource.Loading<*> -> {

                    }
                }
            }
        })
    }

    private fun check_customer_id() {
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

        var record_table= RecordTable("","","",recordList,"")
        var record_table_lite= RecordTableLite("","","","",recordList)
        val argument_list_lite=ArgumentLite("@customerId","211002019004")
        argumentListLite.add(argument_list_lite)

        sql_script_list.add(
            SqlScriptList(1083,0,
            "","","","2021-08-12T00:00:00+05:30",
            "",argumentList,0,return_field_list,"","",""
            ,"","","",-999,argumentListLite,
            record_table,record_table_lite,"","","",""
        )
        )

        var occupation_code_request= AppRequest(-999,
            binding.etCustomerId.getText().toString().toInt(),1076,
            "","","","",0,
            "",method_arg_List,db_tuple_lite,sql_script_list,"",
            0,"",dbTupleLite,-999,0,"","",base_object_val,
            "FAILURE","","","","")

        Utils.showProgressDialog(requireContext())
        customer_view_model.CustomerInfo(occupation_code_request)

    }

    private fun initialization() {
        mb_DB = MBDB.getDataBase(requireContext())
        val repository = AppRepository()
        val factory = ViewModelProviderFactory(requireActivity().application, repository,requireContext())
        customer_view_model= ViewModelProvider(requireActivity(), factory).get(CustomerViewModel::class.java)
        common_view_model=ViewModelProvider(requireActivity(), factory).get(CommonViewModel::class.java)
        customer_view_model.initialize_db(mb_DB!!,requireContext())
        //common_view_model.initialize_db(mb_DB!!,requireContext())
        validation_view_model=ViewModelProvider(this).get(ValidationViewModel::class.java)
        group_loan_save_view_model = ViewModelProvider(requireActivity()).get(GroupLoanSaveViewModel::class.java)
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val onBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {}
        }
        requireActivity().getOnBackPressedDispatcher().addCallback(this, onBackPressedCallback)
    }

    private fun set_value_on_ui(customer_info_list:ArrayList<Customer_Info>) {
        customer_info_list.forEach {
            //println("Data***"+it.fn.toString()+"***"+it.fv.toString())
            when(it.fn){
                "dscId"->{
                    binding.etBUId.setText(it.fv.toString())
                }
                "cif"->{

                }
                "fullName"->{
                    binding.etCustomerName.setText(it.fv.toString())
                }
                "spouseOrFatherName"->{
                    binding.etFatherName.setText(it.fv.toString())
                }
                "groupId"->{
                    binding.etGroupId.setText(it.fv.toString())
                }
                "groupName"->{
                    binding.etGroupName.setText(it.fv.toString())
                }
                "creditOfficeId"->{
                    binding.etCreditOfficeId.setText(it.fv.toString())
                }
                "collectionDay"->{
                    binding.etCollectionDay.setText(it.fv.toString())
                }
            }

        }
    }
}