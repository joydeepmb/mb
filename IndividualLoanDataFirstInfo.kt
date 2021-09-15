package app.bandhan.microbanking.loan.IndividualsLoan

import android.content.Intent
import android.os.Bundle
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
import app.bandhan.microbanking.dao.Product_Info
import app.bandhan.microbanking.dao.Sector_Code_Info
import app.bandhan.microbanking.dao.Sub_Sector_Code_Info
import app.bandhan.microbanking.dashboard.DashBoardActivity
import app.bandhan.microbanking.databinding.CommonButtonLayoutBinding
import app.bandhan.microbanking.databinding.CustomerHeaderDetailsBinding
import app.bandhan.microbanking.databinding.FragmentIndividualJointAccountDetailsBinding
import app.bandhan.microbanking.databinding.FragmentIndividualLoanDataFirstInfoBinding
import app.bandhan.microbanking.db.MBDB
import app.bandhan.microbanking.model.Customer_Info
import app.bandhan.microbanking.repository.AppRepository
import app.bandhan.microbanking.util.Utils
import app.bandhan.microbanking.viewmodel.CommonViewModel
import app.bandhan.microbanking.viewmodel.CustomerViewModel
import app.bandhan.microbanking.viewmodel.ViewModelProviderFactory

class IndividualLoanDataFirstInfo:Fragment(R.layout.fragment_individual_loan_data_first_info) {

    lateinit var binding:FragmentIndividualLoanDataFirstInfoBinding
    lateinit var customer_header_details_binding : CustomerHeaderDetailsBinding
    lateinit var btn_binding: CommonButtonLayoutBinding

    lateinit var customer_view_model: CustomerViewModel
    lateinit var common_view_model: CommonViewModel
    private var mb_DB: MBDB? = null
    var store_sector_code:List<Sector_Code_Info> = ArrayList()
    var store_sub_sector_code:List<Sub_Sector_Code_Info> = ArrayList()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        //val someInt = requireArguments().getInt("some_int")
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        binding = FragmentIndividualLoanDataFirstInfoBinding.inflate(layoutInflater)
        initialization()
        val view = binding.root
        customer_header_details_binding= CustomerHeaderDetailsBinding.bind(view)
        btn_binding=CommonButtonLayoutBinding.bind(view)
        customer_header_details_binding.tvheaderDetails.setText("Loan Info")
        onClickUI()
        get_data_from_db()
        return view
    }

    private fun get_data_from_db() {
        common_view_model.get_data("sector").observe(viewLifecycleOwner, Observer { it
            store_sector_code=it as List<Sector_Code_Info>
        })

        common_view_model.get_data("sub_sector").observe(viewLifecycleOwner, Observer { it
            store_sub_sector_code=it as List<Sub_Sector_Code_Info>
        })
    }

    private fun initialization() {
        mb_DB = MBDB.getDataBase(requireContext())
        val repository = AppRepository()
        val factory = ViewModelProviderFactory(requireActivity().application, repository,requireContext())
        customer_view_model= ViewModelProvider(requireActivity(), factory).get(CustomerViewModel::class.java)
        common_view_model= ViewModelProvider(requireActivity(), factory).get(CommonViewModel::class.java)
        customer_view_model.initialize_db(mb_DB!!,requireContext())
    }

    private fun onClickUI() {
        customer_header_details_binding.ibBack.setOnClickListener {
            findNavController().navigateUp() }
        btn_binding.btnContinue.setOnClickListener { findNavController().navigate(R.id.action_individualLoanDataFirstInfo_to_individualLoanDataSecondInfo) }
        binding.fmSectorCode.setOnClickListener {
            if(store_sector_code.isEmpty())
                Toast.makeText(requireContext(),"Sector code empty",Toast.LENGTH_SHORT).show()
            else{
                Utils.common_alert_data(requireContext(),
                    binding.tvSectorCode,
                    store_sector_code as ArrayList<*>,"Select Sector Code")
            }

        }
        binding.fmSubSectorCode.setOnClickListener {
            if(store_sub_sector_code.isEmpty())
                Toast.makeText(requireContext(),"Sub Sector code empty",Toast.LENGTH_SHORT).show()
            else{
                Utils.common_alert_data(requireContext(),
                    binding.tvSubSectorCode,
                    store_sub_sector_code as ArrayList<*>,"Select Sub Sector Code")
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