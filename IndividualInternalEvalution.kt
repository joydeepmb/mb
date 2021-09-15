package app.bandhan.microbanking.loan.IndividualsLoan

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import app.bandhan.microbanking.R
import app.bandhan.microbanking.dashboard.DashBoardActivity
import app.bandhan.microbanking.databinding.CommonButtonLayoutBinding
import app.bandhan.microbanking.databinding.CustomerHeaderDetailsBinding
import app.bandhan.microbanking.databinding.FragmentIndividualInternalEvalutionBinding
import app.bandhan.microbanking.databinding.FragmentInsuranceCoverageBinding

class IndividualInternalEvalution : Fragment(R.layout.fragment_individual_internal_evalution) {

    lateinit var binding:FragmentIndividualInternalEvalutionBinding
    lateinit var customer_header_details_binding: CustomerHeaderDetailsBinding
    lateinit var btn_binding: CommonButtonLayoutBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        //val someInt = requireArguments().getInt("some_int")
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentIndividualInternalEvalutionBinding.inflate(layoutInflater)
        //initialization()
        val view = binding.root
        customer_header_details_binding= CustomerHeaderDetailsBinding.bind(view)
        btn_binding=CommonButtonLayoutBinding.bind(view)
        customer_header_details_binding.tvheaderDetails.setText("Internal Evolution")
        on_click_on_UI()
        return view
    }

    private fun on_click_on_UI() {
        customer_header_details_binding.ibBack.setOnClickListener {
            findNavController().navigateUp() }

        btn_binding.btnContinue.setOnClickListener {
            findNavController().navigate(R.id.action_individualInternalEvalution_to_individualLoanEligibilityCalculator)
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