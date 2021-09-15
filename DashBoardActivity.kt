package app.bandhan.microbanking.dashboard

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Window
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import app.bandhan.microbanking.databinding.ActivityDashboardBinding
import app.bandhan.microbanking.databinding.CustomerHeaderDetailsBinding
import app.bandhan.microbanking.loan.activity.LoanActivity
import app.bandhan.microbanking.util.Utils


class DashBoardActivity : AppCompatActivity() {
    lateinit var ctx: Context
    lateinit var binding: ActivityDashboardBinding
    private var backPressedTime:Long = 0
    lateinit var backToast: Toast
    lateinit var customer_header_details_binding: CustomerHeaderDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        requestWindowFeature(Window.FEATURE_NO_TITLE) //will hide the title
        supportActionBar!!.hide()
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_login)
        ctx = this
        binding = ActivityDashboardBinding.inflate(layoutInflater)
        customer_header_details_binding = CustomerHeaderDetailsBinding.bind(binding.root)
        customer_header_details_binding.tvheaderDetails.setText("Dashboard")
        customer_header_details_binding.ibBack.setOnClickListener { println("Back") }
        setContentView(binding.root)
        binding.llGroup.setOnClickListener { Utils.showMessage(binding.llDashBoard,"Working Progress") }
        binding.llCasa.setOnClickListener { Utils.showMessage(binding.llDashBoard,"Working Progress") }
        binding.llLoan.setOnClickListener {
            Intent(this@DashBoardActivity, LoanActivity::class.java).also {
                Intent.FLAG_ACTIVITY_CLEAR_TOP
                Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(it)
                //finish()
            }
        }
    }


    override fun onBackPressed() {
        /*super.onBackPressed()
        finish()*/
        backToast = Toast.makeText(this, "Press back again to leave the app.", Toast.LENGTH_LONG)
        if (backPressedTime + 2000 > System.currentTimeMillis()) {
            backToast.cancel()
            //super.onBackPressed()
            finish()
            finishAffinity()
            System.exit(0)
            return
        } else {
            backToast.show()
        }
        backPressedTime = System.currentTimeMillis()
    }
}