package app.bandhan.microbanking.splash

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Window
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import app.bandhan.microbanking.R
import app.bandhan.microbanking.dashboard.DashBoardActivity
import app.bandhan.microbanking.db.MBDB
import app.bandhan.microbanking.login.LoginActivity
import app.bandhan.microbanking.login.LoginPasswordActivity
import app.bandhan.microbanking.model.SplashModel
import app.bandhan.microbanking.repository.AppRepository
import app.bandhan.microbanking.viewmodel.LoginViewModel
import app.bandhan.microbanking.viewmodel.SplashViewModel
import app.bandhan.microbanking.viewmodel.ViewModelProviderFactory

class SplashActivity:AppCompatActivity() {
    val splashViewModel: SplashViewModel by viewModels()
    lateinit var ctx: Context
    lateinit var login_view_model: LoginViewModel
    private var mb_DB: MBDB? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        requestWindowFeature(Window.FEATURE_NO_TITLE) //will hide the title
        supportActionBar!!.hide()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        ctx = this
        mb_DB = MBDB.getDataBase(this)
        initialization()
        observeSplashLiveData()
    }

    private fun initialization() {
        val repository = AppRepository()
        val factory = ViewModelProviderFactory(application, repository,ctx)
        login_view_model= ViewModelProvider(this, factory).get(LoginViewModel::class.java)
        login_view_model.initialize_db(mb_DB!!,ctx)
    }

    private fun observeSplashLiveData() {
        splashViewModel.initSplashScreen()
        val observer = Observer<SplashModel> {


            login_view_model.get_record_data().observe(this, Observer { it->
                if(it.isEmpty()){
                    //val intent = Intent(this, LoginActivity::class.java)
                    val intent = Intent(this, DashBoardActivity::class.java)
                    Intent.FLAG_ACTIVITY_NEW_TASK
                    Intent.FLAG_ACTIVITY_CLEAR_TOP
                    startActivity(intent)
                    finish()
                }

                else{
                        Intent(this@SplashActivity, DashBoardActivity::class.java).also {
                        Intent.FLAG_ACTIVITY_CLEAR_TOP
                        Intent.FLAG_ACTIVITY_NEW_TASK
                        startActivity(it)
                        finish()
                    }
                }
            })

        }
        splashViewModel.liveData.observe(this, observer)
    }
}