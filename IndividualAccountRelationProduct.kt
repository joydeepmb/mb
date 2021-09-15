package app.bandhan.microbanking.loan.IndividualsLoan

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
import app.bandhan.microbanking.dao.AccountRelationShip_Info
import app.bandhan.microbanking.dao.ModeOfOperation_Info
import app.bandhan.microbanking.dao.Product_Info
import app.bandhan.microbanking.dashboard.DashBoardActivity
import app.bandhan.microbanking.databinding.CommonButtonLayoutBinding
import app.bandhan.microbanking.databinding.CustomerHeaderDetailsBinding
import app.bandhan.microbanking.databinding.FragmentIndividualAccountRelationProductBinding
import app.bandhan.microbanking.db.MBDB
import app.bandhan.microbanking.model.*
import app.bandhan.microbanking.repository.AppRepository
import app.bandhan.microbanking.util.Utils
import app.bandhan.microbanking.viewmodel.CommonViewModel
import app.bandhan.microbanking.viewmodel.CustomerViewModel
import app.bandhan.microbanking.viewmodel.ViewModelProviderFactory

class IndividualAccountRelationProduct:Fragment(R.layout.fragment_individual_account_relation_product) {

    lateinit var binding:FragmentIndividualAccountRelationProductBinding
    lateinit var customer_header_details_binding: CustomerHeaderDetailsBinding
    lateinit var btn_binding:CommonButtonLayoutBinding
    lateinit var customer_view_model: CustomerViewModel
    lateinit var common_view_model: CommonViewModel
    var store_customer_info:ArrayList<Customer_Info> =ArrayList()
    var store_product:List<Product_Info> = ArrayList()
    var StoreAccountRelationShipData : List<AccountRelationShip_Info> = ArrayList()
    var storeModeOfOperation : List<ModeOfOperation_Info> = ArrayList()
    private var mb_DB: MBDB? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        //val someInt = requireArguments().getInt("some_int")
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        binding = FragmentIndividualAccountRelationProductBinding.inflate(layoutInflater)
        initialization()
        val view = binding.root
        customer_header_details_binding= CustomerHeaderDetailsBinding.bind(view)
        btn_binding=CommonButtonLayoutBinding.bind(view)
        customer_header_details_binding.tvheaderDetails.setText("Individual Relation")
        get_product_data()
        onclickUI()
        return view
    }

    private fun initialization() {
        mb_DB = MBDB.getDataBase(requireContext())
        val repository = AppRepository()
        val factory = ViewModelProviderFactory(requireActivity().application, repository,requireContext())
        customer_view_model= ViewModelProvider(requireActivity(), factory).get(CustomerViewModel::class.java)
        common_view_model= ViewModelProvider(requireActivity(), factory).get(CommonViewModel::class.java)
        customer_view_model.initialize_db(mb_DB!!,requireContext())
    }

    private fun get_product_data() {
        common_view_model.get_data("product").observe(viewLifecycleOwner, Observer { it
            store_product=it as List<Product_Info>
        })

        //Fetch Account RelationShip Data
        common_view_model.get_data("account_relation").observe(viewLifecycleOwner, Observer { it
            StoreAccountRelationShipData = it as List<AccountRelationShip_Info>

        })
        //Fetch Mode Of Operation Data
        common_view_model.get_data("mode_of_operation").observe(viewLifecycleOwner , Observer { it
            storeModeOfOperation = it as List<ModeOfOperation_Info>

        })

        //Call Customer Info View Model Function.
        //set_value_on_ui()
    }


    private fun onclickUI() {
        customer_header_details_binding.ibBack.setOnClickListener {
            requireActivity().finish() }
        btn_binding.btnContinue.setOnClickListener {
            findNavController().navigate(R.id.action_individualAccountRelationProduct_to_individualJointAccountDetails)
        }
        binding.fmIndividualLoanProductType.setOnClickListener {
            Utils.common_alert_data(requireContext(),
                binding.tvProductType,
                store_product as ArrayList<*>,"Select Product")
        }
        customer_header_details_binding.ibHome.setOnClickListener {
            Intent(requireActivity(), DashBoardActivity::class.java).also {
                Intent.FLAG_ACTIVITY_CLEAR_TOP
                Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(it)
                //finish()
            }
        }

        binding.fmAccountRelationShip.setOnClickListener(){
            if(StoreAccountRelationShipData.isEmpty())
                Toast.makeText(requireContext(),"Account RelationShip Empty", Toast.LENGTH_SHORT).show()
            else{
                Utils.common_alert_data(requireContext(),
                    binding.tvAccountRelationShip,
                    StoreAccountRelationShipData as ArrayList<*> , "Select Account RelationShip")
            }
        }

        binding.fmModeOfOperation.setOnClickListener(){
            if(storeModeOfOperation.isEmpty())
                Toast.makeText(requireContext(),"Mode Of Operation Empty", Toast.LENGTH_SHORT).show()
            else{
                Utils.common_alert_data(requireContext(),
                    binding.tvModeOfOperation,
                    storeModeOfOperation as ArrayList<*> , "Select Mode Of Operation")
            }
        }
        binding.ImgBtnCustomerId.setOnClickListener {
            set_value_on_ui()
        }
    }

    /**
     *  Fetch Customer Information From Common View Model
     */
    private fun set_value_on_ui() {
        common_view_model.get_data("customer_info").observe(viewLifecycleOwner, Observer { it->
            it as List<app.bandhan.microbanking.dao.Customer_Info>
            it.forEach {
//                Log.e("TAG","List Customer List : $it")
                when(it.fn){
                    "dscId"->{
                        binding.editBUId.setText(it.fv.toString())
//                        Log.e("tag","fv : $it")
                    }
                }
            }
        })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val onBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {

            }
        }
        requireActivity().getOnBackPressedDispatcher().addCallback(this, onBackPressedCallback)
    }
}