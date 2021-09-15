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
import app.bandhan.microbanking.dao.Family_Member_Info
import app.bandhan.microbanking.dao.SavingAccount
import app.bandhan.microbanking.dashboard.DashBoardActivity
import app.bandhan.microbanking.databinding.CommonButtonLayoutBinding
import app.bandhan.microbanking.databinding.CustomerHeaderDetailsBinding
import app.bandhan.microbanking.databinding.FragmentGroupLoanAccountBinding
import app.bandhan.microbanking.databinding.FragmentGroupLoanIntroBinding
import app.bandhan.microbanking.db.MBDB
import app.bandhan.microbanking.model.ArgumentLite
import app.bandhan.microbanking.repository.AppRepository
import app.bandhan.microbanking.util.Utils
import app.bandhan.microbanking.viewmodel.CommonViewModel
import app.bandhan.microbanking.viewmodel.CustomerViewModel
import app.bandhan.microbanking.viewmodel.GroupLoanSaveViewModel
import app.bandhan.microbanking.viewmodel.ViewModelProviderFactory

class GroupLoanAccount:Fragment(R.layout.fragment_group_loan_account) {

    lateinit var binding:FragmentGroupLoanAccountBinding
    lateinit var customer_header_details_binding: CustomerHeaderDetailsBinding
    lateinit var btn_binding: CommonButtonLayoutBinding
    lateinit var common_view_model:CommonViewModel
    private var mb_DB: MBDB? = null
    var store_family_member_info: ArrayList<Family_Member_Info> = ArrayList()
    lateinit var group_loan_save_view_model: GroupLoanSaveViewModel
    var is_valid:Boolean=false
    var is_disburse_loan_check:Boolean=false
    var is_water_purifier_check:Boolean=false
    var is_disbursement_mode:Boolean=false
    lateinit var family_member_info_response:Family_Member_Info


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        //val someInt = requireArguments().getInt("some_int")
        //Utils.initialization(requireContext(),requireActivity())
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        binding = FragmentGroupLoanAccountBinding.inflate(layoutInflater)

        val view = binding.root
        customer_header_details_binding= CustomerHeaderDetailsBinding.bind(view)
        btn_binding=CommonButtonLayoutBinding.bind(view)
        customer_header_details_binding.tvheaderDetails.setText("Loan Account")
        initialization()
        get_data_from_db()
        on_click_on_ui()
        checkedCheckBox()
        initObserver()
        return view
    }

    private fun initObserver() {
        group_loan_save_view_model.store_app_request_value.observe(viewLifecycleOwner, Observer { it
            it.sqlScriptList.forEach {
                it.argumentListLite.forEach {
                    println("val response***"+it.fn+it.fv)
                    when(it.fn){
                        "@openingDate"->{
                            //product_info_response=common_view_model.get_product_name(it.fv!!.toInt())
                            binding.etOpeningDate.setText(it.fv)
                        }
                        "@SavingAccount"->{
                            //product_info_response=common_view_model.get_product_name(it.fv!!.toInt())
                            binding.etsavingAccount.setText(it.fv)
                        }
                        "@noOfFamilyMember"->{

                            /*family_member_info_response=common_view_model.get_family_member_name(it.fv!!.toInt())
                            binding.tvNoOfFamilyMember.setText(family_member_info_response.fv)*/
                        }
                    }

                }
            }
        })
    }

    private fun checkedCheckBox() {

            binding.checkDisburseLoanToSaving.setOnClickListener(){
                Utils.onCheckBoxClicked(binding.checkDisburseLoanToSaving,binding.checkedTextInput)
            }
            binding.waterCheckedBox.setOnClickListener(){
                Utils.onCheckBoxClicked(binding.waterCheckedBox,binding.waterCheckedTextInput)
            }
            binding.disburseModeCheckedBox.setOnClickListener(){
                Utils.onCheckBoxClicked(binding.disburseModeCheckedBox,binding.DisburseModeRtgsCheckedTextInput)
            }
        }

    private fun initialization() {
        mb_DB = MBDB.getDataBase(requireContext())
        val repository = AppRepository()
        val factory = ViewModelProviderFactory(requireActivity().application, repository,requireContext())
        common_view_model= ViewModelProvider(this, factory).get(CommonViewModel::class.java)
        common_view_model.initialize_db(mb_DB!!,requireContext())
        group_loan_save_view_model = ViewModelProvider(requireActivity()).get(GroupLoanSaveViewModel::class.java)
        binding.etOpeningDate.setText(Utils.getDatewithMonth().toString())
    }


    private fun get_data_from_db() {
        common_view_model.get_data("family_member").observe(viewLifecycleOwner, Observer { it
            store_family_member_info=it as ArrayList<Family_Member_Info>
        })

        /**
         * Saving Account View Model Call Here.
         */
        common_view_model.get_data("saving_account").observe(viewLifecycleOwner, Observer { it
            it as List<SavingAccount>
            it.forEach {
                Log.e("TAG", "$it")
                binding.etsavingAccount.setText(it.fv)
            }
        })
    }

    private fun on_click_on_ui() {
        customer_header_details_binding.ibBack.setOnClickListener {
            findNavController().navigateUp() }

        btn_binding.btnContinue.setOnClickListener {
            //
            //Log.e("TAG",family_member_info.fn)
            is_disburse_loan_check=Utils.onCheckBoxClicked(binding.checkDisburseLoanToSaving,binding.checkedTextInput)
            is_water_purifier_check=Utils.onCheckBoxClicked(binding.waterCheckedBox,binding.waterCheckedTextInput)
            is_disbursement_mode=Utils.onCheckBoxClicked(binding.disburseModeCheckedBox,binding.DisburseModeRtgsCheckedTextInput)

            is_valid=Utils.checkLoanAccountList(binding.etOpeningDate,
                binding.textOpeningDate,is_disburse_loan_check,
                binding.etsavingAccount,
                binding.textInputSavingAccount,
                binding.tvNoOfFamilyMember.text.toString(),
                binding.tvNoOfFamilyMember,
                binding.etcreditBureauReferenceNumber,
                binding.textCreditBureauReferenceNumber,is_water_purifier_check,is_disbursement_mode
            )

            when(is_valid){
                true->{
                    val family_member_info=common_view_model.get_family_member_id(binding.tvNoOfFamilyMember.getText().toString())
                    group_loan_save_view_model.store_app_request_value.observe(viewLifecycleOwner, Observer {

                        it.sqlScriptList.forEach {
                            var argumentListLite=it.argumentListLite as ArrayList<ArgumentLite>
                            argumentListLite.add(ArgumentLite("@openingDate",binding.etOpeningDate.getText().toString()))
                            argumentListLite.add(ArgumentLite("@disburseLoanToSavingAccount","74500"))
                            argumentListLite.add(ArgumentLite("@SavingAccount",binding.etsavingAccount.getText().toString()))
                            //argumentListLite.add(ArgumentLite("@SavingAccount","74500"))
                            argumentListLite.add(ArgumentLite("@noOfFamilyMember",family_member_info.fn))
                            //argumentListLite.add(ArgumentLite("@noOfFamilyMember","9"))
                            argumentListLite.add(ArgumentLite("@creditBureauReferenceNo","780"))
                            argumentListLite.add(ArgumentLite("@waterPurifier","623"))
                            argumentListLite.add(ArgumentLite("@disbursementModeNeftRtgs","1530"))
                        }
                    })
                    findNavController().navigate(R.id.action_groupLoanAccount_to_groupLoanScoreCalculation)
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
        binding.fmNoOfFamilyMember.setOnClickListener {
            if(store_family_member_info.isEmpty())
                Toast.makeText(requireContext(),"Family Member Empty", Toast.LENGTH_SHORT).show()
            else{
                Utils.common_alert_data(requireContext(),
                    binding.tvNoOfFamilyMember,
                    store_family_member_info.distinctBy { it.family_member_id } as ArrayList<*>,"Select Family Member")
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