package app.bandhan.microbanking.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import app.bandhan.microbanking.model.AppRequest
import app.bandhan.microbanking.model.LoanInfo

class GroupLoanSaveViewModel:ViewModel() {

    val store_app_request_value = MutableLiveData<AppRequest>()

    fun getAppRequest(): LiveData<AppRequest> {
        return store_app_request_value
    }
    fun loadAppRequest(store_val:AppRequest) {
       // blogs.value = //our list of blogs
        store_app_request_value.value=store_val
    }
}