package app.bandhan.microbanking.viewmodel

import android.app.Application
import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import app.bandhan.microbanking.application.MBApplication
import app.bandhan.microbanking.repository.AppRepository



class ViewModelProviderFactory(
    val app: Application,
    val appRepository: AppRepository,
    val ctx:Context
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {

        if (modelClass.isAssignableFrom(PreLoginViewModel::class.java))
            return PreLoginViewModel(app, appRepository,ctx) as T

        if (modelClass.isAssignableFrom(LoginViewModel::class.java))
            return LoginViewModel(app, appRepository,ctx) as T

        if (modelClass.isAssignableFrom(CommonViewModel::class.java))
            return CommonViewModel(app, appRepository,ctx) as T

        if (modelClass.isAssignableFrom(CustomerViewModel::class.java))
            return CustomerViewModel(app, appRepository,ctx) as T

        if (modelClass.isAssignableFrom(LoanSaveViewModel::class.java))
            return LoanSaveViewModel(app, appRepository,ctx) as T


        throw IllegalArgumentException("Unknown class name")
    }

}