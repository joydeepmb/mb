package app.bandhan.microbanking.login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import app.bandhan.microbanking.dashboard.DashBoardActivity
import app.bandhan.microbanking.databinding.ActivityLoginBiometricBinding
import app.bandhan.microbanking.databinding.ActivityLoginPasswordBinding
import app.bandhan.microbanking.util.Utils

class LoginBiomectric : AppCompatActivity() {
    lateinit var ctx: Context
    lateinit var binding: ActivityLoginBiometricBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        requestWindowFeature(Window.FEATURE_NO_TITLE) //will hide the title
        supportActionBar!!.hide()
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_login)
        ctx = this
        binding = ActivityLoginBiometricBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.llBiometric.setOnClickListener {
            Utils.showMessage(binding.llLoginBiometric,"Working Progress")
        }

        binding.llPassword.setOnClickListener {

                Intent(this@LoginBiomectric, LoginPasswordActivity::class.java).also {
                    Intent.FLAG_ACTIVITY_CLEAR_TOP
                    Intent.FLAG_ACTIVITY_NEW_TASK
                    startActivity(it)
                    finish()
                }

            // observeSplashLiveData()
        }
    }
}