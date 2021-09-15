package app.bandhan.microbanking.common

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.findNavController
import app.bandhan.microbanking.R
import app.bandhan.microbanking.databinding.FragmentCommonInfoBinding
import app.bandhan.microbanking.databinding.FragmentGroupScoreCalculationBinding

class MBCommonFragment:Fragment(R.layout.fragment_common_info) {

    var formName:String?=null
    lateinit var binding:FragmentCommonInfoBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        //val someInt = requireArguments().getInt("some_int")
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentCommonInfoBinding.inflate(layoutInflater)
        val view = binding.root

        when(formName){
            "individual"->{
                val args = Bundle()
                args.putString("formName", formName)
                findNavController().navigate(R.id.action_MBCommonFragment_to_individualAccountRelationProduct,args)
            }
            "group"->{
                val args = Bundle()
                args.putString("formName", formName)
                findNavController().navigate(R.id.action_MBCommonFragment_to_groupLoanIntroFragment,args)
            }
        }
        return view
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        val args = arguments
        if (args != null)
            formName=args.getString("formName", "")
        //Log.e("TAG",formName.toString())
    }
}