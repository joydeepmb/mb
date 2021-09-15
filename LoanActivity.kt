package app.bandhan.microbanking.loan.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import app.bandhan.microbanking.common.MBCommonActivity
import app.bandhan.microbanking.databinding.ActivityDashboardBinding
import app.bandhan.microbanking.databinding.ActivityLoanBinding
import app.bandhan.microbanking.databinding.CustomerHeaderDetailsBinding
import app.bandhan.microbanking.util.Utils

class LoanActivity : AppCompatActivity() {

    lateinit var binding:ActivityLoanBinding
    lateinit var customer_header_details_binding:CustomerHeaderDetailsBinding
    lateinit var ctx:Context

    override fun onCreate(savedInstanceState: Bundle?) {
        requestWindowFeature(Window.FEATURE_NO_TITLE) //will hide the title
        supportActionBar!!.hide()
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_login)
        ctx = this
        binding = ActivityLoanBinding.inflate(layoutInflater)
        customer_header_details_binding = CustomerHeaderDetailsBinding.bind(binding.root)
        customer_header_details_binding.tvheaderDetails.setText("Loan")
        customer_header_details_binding.ibBack.visibility=View.GONE
        customer_header_details_binding.ibHome.visibility=View.GONE
        setContentView(binding.root)


        binding.llIndividual.setOnClickListener {
            Intent(this, MBCommonActivity::class.java).also {
                it.putExtra("loan_info","individual")
                Intent.FLAG_ACTIVITY_CLEAR_TOP
                Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(it)
                //finish()
            }
        }
        binding.llGroupLoan.setOnClickListener {
            Intent(this, MBCommonActivity::class.java).also {
                it.putExtra("loan_info","group")
                Intent.FLAG_ACTIVITY_CLEAR_TOP
                Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(it)
                //finish()
            }
        }
    }

}