package app.bandhan.microbanking.loan.IndividualsLoan

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import app.bandhan.microbanking.R
import app.bandhan.microbanking.dao.Occupation_Info
import app.bandhan.microbanking.dao.Sub_Sector_Code_Info
import app.bandhan.microbanking.dashboard.DashBoardActivity
import app.bandhan.microbanking.databinding.CommonButtonLayoutBinding
import app.bandhan.microbanking.databinding.CustomerHeaderDetailsBinding
import app.bandhan.microbanking.databinding.FragmentIndividualLoanDataFirstInfoBinding
import app.bandhan.microbanking.databinding.FragmentIndividualLoanDataSecondInfoBinding
import app.bandhan.microbanking.db.MBDB
import app.bandhan.microbanking.repository.AppRepository
import app.bandhan.microbanking.util.Utils
import app.bandhan.microbanking.viewmodel.CommonViewModel
import app.bandhan.microbanking.viewmodel.CustomerViewModel
import app.bandhan.microbanking.viewmodel.ViewModelProviderFactory

class IndividualLoanDataSecondInfo : Fragment(R.layout.fragment_individual_loan_data_second_info) {

    lateinit var binding:FragmentIndividualLoanDataSecondInfoBinding
    lateinit var customer_header_details_binding: CustomerHeaderDetailsBinding
    lateinit var btn_binding: CommonButtonLayoutBinding

    lateinit var customer_view_model: CustomerViewModel
    lateinit var common_view_model: CommonViewModel
    private var mb_DB: MBDB? = null
    var store_occupation_data:List<Occupation_Info> = ArrayList()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        //val someInt = requireArguments().getInt("some_int")
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        binding = FragmentIndividualLoanDataSecondInfoBinding.inflate(layoutInflater)
        initialization()
        val view = binding.root
        customer_header_details_binding= CustomerHeaderDetailsBinding.bind(view)
        btn_binding=CommonButtonLayoutBinding.bind(view)
        customer_header_details_binding.tvheaderDetails.setText("Loan Info")
        get_data_from_db()
        onClickUI()
        return view
    }

    private fun get_data_from_db() {
        common_view_model.get_data("occupation").observe(viewLifecycleOwner, Observer { it

            store_occupation_data=it as List<Occupation_Info>
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

        btn_binding.btnContinue.setOnClickListener {
            findNavController().navigate(R.id.action_individualLoanDataSecondInfo_to_individualInsuranceCoverage)
        }
        binding.fmOccupationCode.setOnClickListener {
            Utils.common_alert_data(requireContext(),
                binding.tvOccupationCode,
                store_occupation_data as ArrayList<*>,"Select Occupation")
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