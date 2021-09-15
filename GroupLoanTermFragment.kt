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
import app.bandhan.microbanking.dao.*
import app.bandhan.microbanking.dashboard.DashBoardActivity
import app.bandhan.microbanking.databinding.CommonButtonLayoutBinding
import app.bandhan.microbanking.databinding.CustomerHeaderDetailsBinding
import app.bandhan.microbanking.databinding.FragmentGroupLoanTermBinding
import app.bandhan.microbanking.db.MBDB
import app.bandhan.microbanking.model.ArgumentLite
import app.bandhan.microbanking.repository.AppRepository
import app.bandhan.microbanking.util.Utils
import app.bandhan.microbanking.viewmodel.CommonViewModel
import app.bandhan.microbanking.viewmodel.GroupLoanSaveViewModel
import app.bandhan.microbanking.viewmodel.ViewModelProviderFactory

class GroupLoanTermFragment:Fragment(R.layout.fragment_group_loan_term) {

    lateinit var binding:FragmentGroupLoanTermBinding
    lateinit var customer_header_details_binding: CustomerHeaderDetailsBinding
    lateinit var btn_binding: CommonButtonLayoutBinding
    lateinit var common_view_model:CommonViewModel
    private var mb_DB: MBDB? = null
    var store_occupation:ArrayList<Occupation_Info> = ArrayList()
    var store_sector_code:ArrayList<Sector_Code_Info> = ArrayList()
    var store_sub_sector_code:ArrayList<Sub_Sector_Code_Info> = ArrayList()
    var store_nature_of_borrower_code:ArrayList<Nature_of_Borrower_Code_Info> = ArrayList()
    lateinit var group_loan_save_view_model: GroupLoanSaveViewModel
    var is_valid:Boolean=false
    lateinit var sector_code_info_response:Sector_Code_Info
    lateinit var sub_sector_code_info_response:Sub_Sector_Code_Info
    lateinit var occupation_code_info_response:Occupation_Info
    lateinit var nature_of_Borrower_Code_Info: Nature_of_Borrower_Code_Info

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        //val someInt = requireArguments().getInt("some_int")
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        binding = FragmentGroupLoanTermBinding.inflate(layoutInflater)
        initialization()
        val view = binding.root
        customer_header_details_binding= CustomerHeaderDetailsBinding.bind(view)
        btn_binding=CommonButtonLayoutBinding.bind(view)
        customer_header_details_binding.tvheaderDetails.setText("Term Loan")
        get_data_from_db()
        onclickonUI()
        initobserver()
        return view
    }

    private fun initobserver() {
        group_loan_save_view_model.store_app_request_value.observe(viewLifecycleOwner, Observer { it
            it.sqlScriptList.forEach {
                it.argumentListLite.forEach {
                    println("val response***"+it.fn+it.fv)
                    when(it.fn){
                        "@loanInterestRate"->{
                            //product_info_response=common_view_model.get_product_name(it.fv!!.toInt())
                            binding.etLoanInterestRate.setText(it.fv)
                        }
                        "@sectorCode"->{
                            sector_code_info_response=common_view_model.get_sector_code_name(it.fv!!.toInt())
                            binding.tvSectorCode.setText(sector_code_info_response.fv)
                        }
                        "@subSectorCode"->{
                            sub_sector_code_info_response=common_view_model.get_sub_sector_code_name(it.fv!!.toInt())
                            binding.tvSubSectorCode.setText(sub_sector_code_info_response.fv)
                        }
                        "@occupationCode"->{
                            occupation_code_info_response=common_view_model.get_occupation_code_name(it.fv.toString())
                            binding.tvOccupationCode.setText(occupation_code_info_response.fv)
                            //Log.e("TAG",occupation_code_info_response.fv.toString())
                        }
                        "@natureOfBorrowerAccount"->{
                            val id=it.fv!!.toString().substringBefore(".").toInt()
                            Log.e("TAG%%%%",id.toString())

                            /*nature_of_Borrower_Code_Info=common_view_model.get_nature_of_borrower_code_name(id.toString())
                            binding.tvBorrowerAmount.setText(nature_of_Borrower_Code_Info.fv)*/
                        }
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
        binding.etMaturityDate.setText(Utils.getDatewithMonth().toString())
        group_loan_save_view_model = ViewModelProvider(requireActivity()).get(GroupLoanSaveViewModel::class.java)
    }

    private fun get_data_from_db() {
        if(store_occupation.size>0)
            store_occupation.clear()
        common_view_model.get_data("occupation").observe(viewLifecycleOwner,Observer{
            store_occupation=it as ArrayList<Occupation_Info>
        })

        if(store_sector_code.size>0)
            store_sector_code.clear()
        common_view_model.get_data("sector").observe(viewLifecycleOwner, Observer { it
                store_sector_code=it as ArrayList<Sector_Code_Info>
        })

        if(store_sub_sector_code.size>0)
            store_sub_sector_code.clear()
        common_view_model.get_data("sub_sector").observe(viewLifecycleOwner, Observer { it
            store_sub_sector_code=it as ArrayList<Sub_Sector_Code_Info>
        })

        if(store_nature_of_borrower_code.size>0)
            store_nature_of_borrower_code.clear()
        common_view_model.get_data("nature_of_borrower").observe(viewLifecycleOwner, Observer { it
           store_nature_of_borrower_code=it as ArrayList<Nature_of_Borrower_Code_Info>
        })

        /**
         * Term Tenure View Model Call Here.
         */
        common_view_model.get_data("term").observe(viewLifecycleOwner, Observer { it
            it as List<Term_Tenure>
            it.forEach {
                Log.e("TAG","$it")
                when(it.fn){
                    "productTerm"->{
                        binding.etTerm.setText(it.fv)
                    }"productTenure"->{
                    binding.etTentureType.setText(it.fv)
                    }
                }
            }
        })
    }

    private fun onclickonUI() {
        customer_header_details_binding.ibBack.setOnClickListener {
            findNavController().navigateUp() }
        btn_binding.btnContinue.setOnClickListener {


            is_valid=Utils.checkTermLoanField(binding.etLoanInterestRate ,binding.textInputLoanInterestRate , binding.etTerm , binding.textInputTerm,
                binding.etTentureType , binding.textInputTenureType , binding.etMaturityDate , binding.textInputMaturityDate , binding.etInstallmentAmount,
                binding.textInputInstallmentAmount,binding.tvSectorCode.text.toString(),binding.tvSectorCode,binding.tvSubSectorCode.text.toString(),binding.tvSubSectorCode,
                binding.tvOccupationCode.text.toString(),binding.tvOccupationCode,binding.etLandHolding , binding.textInputLandHolding,binding.tvPlantMachinery.text.toString(),
                binding.tvPlantMachinery,binding.tvBorrowerAmount.text.toString(),binding.tvBorrowerAmount
            )

            when(is_valid){
                true->{
                    val sectore_code_info=common_view_model.get_sector_code_id(binding.tvSectorCode.getText().toString())
                    val sub_sector_code_info=common_view_model.get_sub_sector_code_id(binding.tvSubSectorCode.getText().toString())
                    val occupation_code_info=common_view_model.get_occupation_code_id(binding.tvOccupationCode.getText().toString())
                    val nature_of_borrower_code_info=common_view_model.get_nature_of_borrower_code_id(binding.tvBorrowerAmount.getText().toString())

                    group_loan_save_view_model.store_app_request_value.observe(viewLifecycleOwner, Observer {

                        it.sqlScriptList.forEach {
                            var argumentListLite=it.argumentListLite as ArrayList<ArgumentLite>
                            argumentListLite.add(ArgumentLite("@loanIndex","5"))
                            argumentListLite.add(ArgumentLite("@loanInterestRate",binding.etLoanInterestRate.getText().toString()))
                            argumentListLite.add(ArgumentLite("@term",binding.etTerm.text.toString()))
                            //argumentListLite.add(ArgumentLite("@term","1"))
                            argumentListLite.add(ArgumentLite("@tenureType",binding.etTentureType.text.toString()))
                            //argumentListLite.add(ArgumentLite("@tenureType","12"))
                            argumentListLite.add(ArgumentLite("@maturityDate",binding.etMaturityDate.getText().toString()))
                            argumentListLite.add(ArgumentLite("@installmentAmount","2050"))
                            argumentListLite.add(ArgumentLite("@sectorCode",sectore_code_info.fn))
                            argumentListLite.add(ArgumentLite("@subSectorCode",sub_sector_code_info.fn))
                            argumentListLite.add(ArgumentLite("@occupationCode",occupation_code_info.fn))
                            argumentListLite.add(ArgumentLite("@landHolding","125"))
                            argumentListLite.add(ArgumentLite("@investmentInPlantMachinery","15000"))
                            argumentListLite.add(ArgumentLite("@natureOfBorrowerAccount",nature_of_borrower_code_info.fn))
                            //argumentListLite.add(ArgumentLite("@natureOfBorrowerAccount",""nature_of_borrower_code_info.fn""))
                        }
                    })
                    findNavController().navigate(R.id.action_groupLoanTermFragment_to_groupLoanAccount)
                }false->{
                    Log.e("false","false")
                }
            }
        }

        customer_header_details_binding.ibHome.setOnClickListener {
            Intent(requireActivity(), DashBoardActivity::class.java).also {
                Intent.FLAG_ACTIVITY_CLEAR_TOP
                Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(it)
                //finish()
            }
        }

        binding.fmOccupationCode.setOnClickListener {
            if(store_occupation.isEmpty())
                Toast.makeText(requireContext(),"Occupation Code Empty",Toast.LENGTH_SHORT).show()
            else{
                Utils.common_alert_data(requireContext(),
                    binding.tvOccupationCode,
                    store_occupation.distinctBy { it.occupation_id } as ArrayList<*>,"Select Occupation")
            }

        }
        binding.fmSectorCode.setOnClickListener {
            if(store_sector_code.isEmpty())
                Toast.makeText(requireContext(),"Sector Code Empty",Toast.LENGTH_SHORT).show()
            else{
                Utils.common_alert_data(requireContext(),
                    binding.tvSectorCode,
                    store_sector_code.distinctBy { it.sector_code_id } as ArrayList<*>,"Select Sector Code")
            }

        }
        binding.fmSubSectorCode.setOnClickListener {
            if(store_sub_sector_code.isEmpty())
                Toast.makeText(requireContext(),"Sub Sector Code Empty",Toast.LENGTH_SHORT).show()
            else{
                Utils.common_alert_data(requireContext(),
                    binding.tvSubSectorCode,
                    store_sub_sector_code.toSet().toList() as ArrayList<*>,"Select Sub Sector Code")
            }

        }
        binding.fmNatureOfAmount.setOnClickListener {
            if(store_nature_of_borrower_code.isEmpty())
                Toast.makeText(requireContext(),"Sub Sector Code Empty",Toast.LENGTH_SHORT).show()
            else{
                Utils.common_alert_data(requireContext(),
                    binding.tvBorrowerAmount,
                    store_nature_of_borrower_code.distinctBy { it.nature_of_borrower_code_id } as ArrayList<*>,"Select Nature of Borrower Amount")
            }
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
}