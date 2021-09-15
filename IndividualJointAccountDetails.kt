package app.bandhan.microbanking.loan.IndividualsLoan

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import app.bandhan.microbanking.R
import app.bandhan.microbanking.dashboard.DashBoardActivity
import app.bandhan.microbanking.databinding.CommonButtonLayoutBinding
import app.bandhan.microbanking.databinding.CustomerHeaderDetailsBinding
import app.bandhan.microbanking.databinding.FragmentIndividualAccountRelationProductBinding
import app.bandhan.microbanking.databinding.FragmentIndividualJointAccountDetailsBinding
import app.bandhan.microbanking.db.MBDB
import app.bandhan.microbanking.model.Customer_Info
import app.bandhan.microbanking.repository.AppRepository
import app.bandhan.microbanking.util.Utils.common_view_model
import app.bandhan.microbanking.viewmodel.CommonViewModel
import app.bandhan.microbanking.viewmodel.CustomerViewModel
import app.bandhan.microbanking.viewmodel.ViewModelProviderFactory
import java.util.Observer

class IndividualJointAccountDetails:Fragment(R.layout.fragment_individual_joint_account_details) {

    lateinit var binding:FragmentIndividualJointAccountDetailsBinding
    lateinit var customer_header_details_binding: CustomerHeaderDetailsBinding
    lateinit var btn_binding: CommonButtonLayoutBinding
    lateinit var common_view_model: CommonViewModel
    var store_customer_info:ArrayList<Customer_Info> =ArrayList()
    private var mb_DB: MBDB? = null


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        //val someInt = requireArguments().getInt("some_int")
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        binding = FragmentIndividualJointAccountDetailsBinding.inflate(layoutInflater)
        initialization()
        val view = binding.root

        customer_header_details_binding= CustomerHeaderDetailsBinding.bind(view)
        btn_binding=CommonButtonLayoutBinding.bind(view)
        customer_header_details_binding.tvheaderDetails.setText("Account Details")
        onclickonUI()
        get_data_from_db()
        return view
    }

    private fun initialization() {
        mb_DB = MBDB.getDataBase(requireContext())
        val repository = AppRepository()
        val factory = ViewModelProviderFactory(requireActivity().application, repository,requireContext())
        common_view_model= ViewModelProvider(requireActivity(), factory).get(CommonViewModel::class.java)
        common_view_model.initialize_db(mb_DB!!,requireContext())
    }

    private fun onclickonUI() {
        customer_header_details_binding.ibBack.setOnClickListener {
            findNavController().navigateUp() }
        btn_binding.btnContinue.setOnClickListener {
            findNavController().navigate(R.id.action_IndividualJointAccountDetails_to_individualLoanDataFirstInfo)
        }
        customer_header_details_binding.ibHome.setOnClickListener {
            Intent(requireActivity(), DashBoardActivity::class.java).also {
                Intent.FLAG_ACTIVITY_CLEAR_TOP
                Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(it)
                //finish()
            }
        }
    }

    /**\
     * Fetch Customer Information From View Model
     */
    private fun get_data_from_db() {
        common_view_model.get_data("customer_info").observe(viewLifecycleOwner, androidx.lifecycle.Observer {it->
            it as List<app.bandhan.microbanking.dao.Customer_Info>
            it.forEach {
//                Log.e("TAG" , "it### : $it")
                when(it.fn){
                    "cif" ->{
                        binding.etCustomerId1.setText(it.fv)
                    }"fullName" ->{
                        binding.etFirstCustomerName.setText(it.fv)
                    }
                }
            }
        })

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