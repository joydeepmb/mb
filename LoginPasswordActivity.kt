package app.bandhan.microbanking.login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import app.bandhan.microbanking.dao.Record_Info
import app.bandhan.microbanking.dashboard.DashBoardActivity
import app.bandhan.microbanking.databinding.ActivityLoginBinding
import app.bandhan.microbanking.databinding.ActivityLoginPasswordBinding
import app.bandhan.microbanking.db.MBDB
import app.bandhan.microbanking.repository.AppRepository
import app.bandhan.microbanking.util.Utils
import app.bandhan.microbanking.util.Utils.sha256
import app.bandhan.microbanking.viewmodel.LoginViewModel
import app.bandhan.microbanking.viewmodel.ViewModelProviderFactory

class LoginPasswordActivity : AppCompatActivity() {

    lateinit var ctx: Context
    lateinit var binding: ActivityLoginPasswordBinding
    var password:String?=null
    lateinit var login_view_model: LoginViewModel
    private var mb_DB: MBDB? = null
    lateinit var store_record_info:Record_Info
    var emp_pwd:String?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        requestWindowFeature(Window.FEATURE_NO_TITLE) //will hide the title
        supportActionBar!!.hide()
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_login)
        ctx = this
        mb_DB = MBDB.getDataBase(this)
        binding = ActivityLoginPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initialization()
        get_password()


        binding.btnUserPassword.setOnClickListener {
            if(binding.etPassword.getText().toString().isNullOrEmpty())
                Utils.showMessage(binding.llLoginPassword,"Type password")
            else{
                password = binding.etPassword.getText().toString().trim().sha256()
                //Log.e("Password",password.toString())
                if(emp_pwd.equals(password,true)){
                    Intent(this@LoginPasswordActivity, DashBoardActivity::class.java).also {
                        Intent.FLAG_ACTIVITY_CLEAR_TOP
                        Intent.FLAG_ACTIVITY_NEW_TASK
                        startActivity(it)
                        finish()
                    }
                }else{
                    Utils.showMessage(binding.llLoginPassword,"Password mismathced")
                }

            }
            // observeSplashLiveData()
        }
    }

    private fun get_password() {
        store_record_info=login_view_model.get_emp_pwd()
        Log.e("fn",store_record_info.fn)
        Log.e("fv",store_record_info.fv)
        emp_pwd=store_record_info.fv

    }

    private fun initialization() {
        val repository = AppRepository()
        val factory = ViewModelProviderFactory(application, repository,ctx)
        login_view_model= ViewModelProvider(this, factory).get(LoginViewModel::class.java)
        login_view_model.initialize_db(mb_DB!!,ctx)
    }
}