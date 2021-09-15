package app.bandhan.microbanking.login


import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Window
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope

import app.bandhan.microbanking.application.MBApplication
import app.bandhan.microbanking.dao.Record_Info
import app.bandhan.microbanking.databinding.ActivityLoginBinding
import app.bandhan.microbanking.db.MBDB
import app.bandhan.microbanking.model.*
import app.bandhan.microbanking.repository.AppRepository
import app.bandhan.microbanking.util.Resource
import app.bandhan.microbanking.util.Utils
import app.bandhan.microbanking.viewmodel.LoginViewModel
import app.bandhan.microbanking.viewmodel.PreLoginViewModel
import app.bandhan.microbanking.viewmodel.ValidationViewModel
import app.bandhan.microbanking.viewmodel.ViewModelProviderFactory
import com.google.gson.Gson
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.collect

class LoginActivity:AppCompatActivity() {

    lateinit var ctx: Context
    lateinit var binding: ActivityLoginBinding
    lateinit var pre_login_view_model:PreLoginViewModel
    lateinit var login_view_model:LoginViewModel
    private var mb_DB: MBDB? = null
    lateinit var validation_view_model:ValidationViewModel
    var is_valid:Boolean=false

    override fun onCreate(savedInstanceState: Bundle?) {
        requestWindowFeature(Window.FEATURE_NO_TITLE) //will hide the title
        supportActionBar!!.hide()
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_login)
        ctx = this
        mb_DB = MBDB.getDataBase(this)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initialization()
        set_on_click_on_ui()
        observableinUI()
        initObserver()
    }

    private fun initObserver() {
        lifecycleScope.launch {
            validation_view_model.isSubmitEnabled.collect { value ->
                is_valid = value
            }
        }
    }

    private fun observableinUI() {
        login_view_model.loginResponse.observe(this, Observer { event ->
            event.getContentIfNotHandled()?.let { response ->
                when (response) {
                    is Resource.Success -> {
                        Utils.dismissProgressDialog()
                        response.data?.let { loginResponse ->
                            var var_res=Utils.return_data(loginResponse,response_class = LoginResponse())
                            if(var_res is LoginResponse){
                                val store_record_info:ArrayList<Record_Info> =ArrayList()
                                val id:Int=0
                                var_res.dbTupleLite!!.forEach {
                                    it.recordList!!.forEach {
                                        it.record!!.forEach {
                                            var record_x_info=Record_Info(id,it.fn.toString(),it.fv.toString())
                                            store_record_info.add(record_x_info)
                                        }
                                        login_view_model.record_insert(store_record_info)
                                    }
                                }
                            }
                        }
                    }

                    is Resource.Error -> {

                        response.message?.let { message ->
                            println("message ###"+message)
                            Utils.dismissProgressDialog()
                            Utils.showMessage(binding.llLogin,message)
                        }
                    }

                    is Resource.Loading<*> -> {

                    }
                }
            }
        })

        login_view_model.get_record_data().observe(this, Observer { it
        if(it.size>0)
            send_data_to_password_screen()
        else
            //Utils.showMessage(binding.llLogin,"Login Failed")
            Log.e("Login failed","Login failed")
        })
    }

    private fun set_on_click_on_ui() {
        binding.btnUserLogin.setOnClickListener {
            /*when(is_valid){
                true->{
                    println("%%%"+validation_view_model.errorMessage)
                }false->{
                    println("$$$"+validation_view_model.errorMessage)
                }
            }*/
            if(binding.etUser.getText().toString().isNullOrEmpty())
                Utils.showMessage(binding.llLogin,"Type User Id")
            else{
                val sql_script_List:ArrayList<SqlScriptX> = ArrayList()

                var argumentListLite:ArrayList<ArgumentLite> = ArrayList()
                argumentListLite.add(ArgumentLite("@deviceId","87343bf10ad535d1be721104884519ac"))
                argumentListLite.add(ArgumentLite("@officeId",""))
                argumentListLite.add(ArgumentLite("@employeeCode","103583"))
                argumentListLite.add(ArgumentLite("@empFp","Rk1SACAyMAAAAAD8AAABAAGQAMUAxQEAAABOJUBkACRgQ0ApADBsUEAlAF9sUEDQAGpcQ4ClAHZk\nSkApAH30UEAgAIt4UEBRAJBoUECTAJBoSkCFAJvoUEDeALFoUIBKAMOAUEBiAMNoXUDiAMPoSoA6\nAM0AUEAuANGAV4DXANTgQ4AZAPmYUEB0APlwXUBIAQqAV0DSAQ5cUIBtARPsXUCFARNsXUAiAR+k\nXUBwASF4XYCOASFsV0DnASHkQ0DCAThkXUCQATtsXUAwAUKwXUBaAU64UIC2AVBoV0AlAWPEXYBK\nAWPEXYB5AWPkQ4AuAWU8XUDQAWx8PAAA\n"))
                argumentListLite.add(ArgumentLite("@requestType","EAD"))
                argumentListLite.add(ArgumentLite("@appId","1.3.2"))

                sql_script_List.add(SqlScriptX(0,"2021-08-02T13:41:02.413+05:30","null",argumentListLite))

                val body= LoginRequest("8","103583",769,sql_script_List,"87343bf10ad535d1be721104884519ac","FAILURE")
                Utils.showProgressDialog(ctx)
                login_view_model.loginUser(body)
            }
        }
    }

    private fun send_data_to_password_screen() {
        Intent(this@LoginActivity, LoginPasswordActivity::class.java).also {
            Intent.FLAG_ACTIVITY_CLEAR_TOP
            Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(it)
            finish()
        }
    }

    private fun initialization() {

        val repository = AppRepository()
        val factory = ViewModelProviderFactory(application, repository,ctx)
        login_view_model=ViewModelProvider(this, factory).get(LoginViewModel::class.java)
        login_view_model.initialize_db(mb_DB!!,ctx)
        validation_view_model=ViewModelProvider(this).get(ValidationViewModel::class.java)
    }


    override fun onBackPressed() {
       // super.onBackPressed()
        finish()
        finishAffinity()
        System.exit(0)
    }
}