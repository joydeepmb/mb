package app.bandhan.microbanking.loan.GroupLoan

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import app.bandhan.microbanking.R
import app.bandhan.microbanking.dao.Term_Tenure
import app.bandhan.microbanking.dashboard.DashBoardActivity
import app.bandhan.microbanking.databinding.CustomerHeaderDetailsBinding
import app.bandhan.microbanking.databinding.FragmentGroupScoreCalculationBinding
import app.bandhan.microbanking.db.MBDB
import app.bandhan.microbanking.model.*
import app.bandhan.microbanking.repository.AppRepository
import app.bandhan.microbanking.util.OnClickVal
import app.bandhan.microbanking.util.Resource
import app.bandhan.microbanking.util.Utils
import app.bandhan.microbanking.viewmodel.CommonViewModel
import app.bandhan.microbanking.viewmodel.GroupLoanSaveViewModel
import app.bandhan.microbanking.viewmodel.LoanSaveViewModel
import app.bandhan.microbanking.viewmodel.ViewModelProviderFactory

class GroupLoanScoreCalculation: Fragment(R.layout.fragment_group_score_calculation),OnClickVal {

    lateinit var binding:FragmentGroupScoreCalculationBinding
    lateinit var customer_header_details_binding: CustomerHeaderDetailsBinding
    lateinit var common_view_model: CommonViewModel
    private var mb_DB: MBDB? = null
    lateinit var loan_save_view_model:LoanSaveViewModel
    lateinit var group_loan_save_view_model: GroupLoanSaveViewModel


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        //val someInt = requireArguments().getInt("some_int")
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        binding = FragmentGroupScoreCalculationBinding.inflate(layoutInflater)
        val view = binding.root
        customer_header_details_binding= CustomerHeaderDetailsBinding.bind(view)
        customer_header_details_binding.tvheaderDetails.setText("Score Calculation")
        initialization()
        get_data_from_db()
        onclickui()
        observable_on_ui()
        return view
    }

    private fun observable_on_ui() {
        loan_save_view_model.loansaveResponse.observe(viewLifecycleOwner, Observer { event ->
            event.getContentIfNotHandled()?.let { response ->
                when (response) {
                    is Resource.Success -> {
                        Utils.dismissProgressDialog()
                        response.data?.let { loginResponse ->
                            var var_res=Utils.return_data(loginResponse,response_class = MBResponse())
                            if(var_res is MBResponse){

                                val id:Int=0
                                when(var_res.responseStatus){
                                    "SUCCESS"->{
                                        Intent(requireActivity(), DashBoardActivity::class.java).also {
                                            Intent.FLAG_ACTIVITY_CLEAR_TOP
                                            Intent.FLAG_ACTIVITY_NEW_TASK
                                            startActivity(it)
                                            requireActivity().finish()
                                        }
                                    }"FAILURE"->{
                                        Toast.makeText(requireContext(),var_res.errorMessage.toString(),Toast.LENGTH_LONG).show()
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

    private fun get_data_from_db() {
        /**
         * Term Tenure View Model Call Here.
         */
        common_view_model.get_data("term").observe(viewLifecycleOwner, Observer { it

            it as List<Term_Tenure>
            it.forEach {
                Log.e("TAG","$it")
                when(it.fn){
                    "productProcessingFee" ->{
                        binding.tvFeesProcessing.setText(it.fv)
                    }
                }

            }

        })

    }

    private fun initialization() {
        mb_DB = MBDB.getDataBase(requireContext())
        val repository = AppRepository()
        val factory = ViewModelProviderFactory(requireActivity().application, repository,requireContext())
        common_view_model= ViewModelProvider(this, factory).get(CommonViewModel::class.java)
        common_view_model.initialize_db(mb_DB!!,requireContext())

        loan_save_view_model= ViewModelProvider(this, factory).get(LoanSaveViewModel::class.java)
        common_view_model.initialize_db(mb_DB!!,requireContext())
        group_loan_save_view_model = ViewModelProvider(requireActivity()).get(GroupLoanSaveViewModel::class.java)
    }

    private fun onclickui() {

        customer_header_details_binding.ibBack.setOnClickListener {
            findNavController().navigateUp()
        }
        customer_header_details_binding.ibHome.setOnClickListener {
            Intent(requireActivity(), DashBoardActivity::class.java).also {
                Intent.FLAG_ACTIVITY_CLEAR_TOP
                Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(it)
                //finish()
            }
        }

        binding.btnContinue.setOnClickListener {

            val method_arg_List:List<MethodArg> = ArrayList()
            val db_tuple_lite:List<DbTupleLite> = ArrayList()
            val sql_script_list:ArrayList<SqlScriptList> = ArrayList()
            var argumentList:List<String> = ArrayList()
            val return_field_list:List<String> = ArrayList()
            var argumentListLite:ArrayList<ArgumentLite> = ArrayList()
            val recordList:List<String> = ArrayList()
            val dbTupleLite:List<String> = ArrayList()
            val base_object_val : List<String> = ArrayList()

            var record_table= RecordTable("","","",recordList,"")
            var record_table_lite= RecordTableLite("","","","",recordList)

            group_loan_save_view_model.store_app_request_value.observe(viewLifecycleOwner, Observer {

                it.sqlScriptList.forEach {
                    argumentListLite=it.argumentListLite as ArrayList<ArgumentLite>
                    argumentListLite.add(ArgumentLite("@creditScore","800"))
                    argumentListLite.add(ArgumentLite("@offeredLoan","74300"))
                    argumentListLite.add(ArgumentLite("@processingFees","700"))
                    argumentListLite.add(ArgumentLite("@documentationCharges","80"))
                    argumentListLite.add(ArgumentLite("@serviceCharge","90"))
                    argumentListLite.add(ArgumentLite("@calculateFees","5"))
                }

            })

            sql_script_list.add(
                SqlScriptList(1092,0,
                    "","","","2021-09-01T00:00:00+05:30",
                    "",argumentList,0,return_field_list,"","",""
                    ,"","","",-999,argumentListLite,
                    record_table,record_table_lite,"","","",""
                )
            )

            val save_body= AppRequest(-999,
                101026,1088,
                "","","","",0,
                "",method_arg_List,db_tuple_lite,sql_script_list,"",
                0,"",dbTupleLite,-999,0,"","",base_object_val,
                "FAILURE","","","","")


            Utils.alert_for_prefield_view_save_data(requireContext(),argumentListLite,object :
                OnClickVal{
                override fun onClick(item: Any) {
                    if(item is String)
                        when(item){
                            "continue"->{
                                 Utils.showProgressDialog(requireContext())
                                  loan_save_view_model.loanSave(save_body)
                            }
                        }
                }

            })


        }

    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val onBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                //requireActivity().finish()
                //  Utils.showErrorMessage(binding.llCustomerPayment,"Use Back Arrow")
            }
        }
        requireActivity().getOnBackPressedDispatcher().addCallback(this, onBackPressedCallback)
    }

    override fun onClick(item: Any) {
        if(item is String)
        Log.e("TAG Data",item)
    }
}