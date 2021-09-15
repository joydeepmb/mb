package app.bandhan.microbanking.viewmodel

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import app.bandhan.microbanking.R
import app.bandhan.microbanking.application.MBApplication
import app.bandhan.microbanking.dao.Record_Info
import app.bandhan.microbanking.db.MBDB
import app.bandhan.microbanking.model.LoginRequest
import app.bandhan.microbanking.model.PreLoginRequest
import app.bandhan.microbanking.repository.AppRepository
import app.bandhan.microbanking.util.Event
import app.bandhan.microbanking.util.Resource
import app.bandhan.microbanking.util.Utils
import kotlinx.coroutines.*
import retrofit2.Response
import java.io.IOException

class LoginViewModel(app: Application,
                     private var appRepository: AppRepository,
                     ctx: Context
):AndroidViewModel(app) {
    private val _loginResponse = MutableLiveData<Event<Resource<String>>>()
    val loginResponse: LiveData<Event<Resource<String>>> = _loginResponse
    var recordResponse: LiveData<List<Record_Info>> =MutableLiveData()
    lateinit var emppwdResponse: Record_Info
    lateinit var create_login_request:Any


    lateinit var db:MBDB ;

    fun initialize_db(common_db:MBDB,ctx: Context){
        db =common_db
    }



    fun loginUser(body: LoginRequest) = viewModelScope.launch {
        loginData(body)
    }

    private suspend fun loginData(body:Any) {
        _loginResponse.postValue(Event(Resource.Loading()))
        try {
            if (Utils.hasInternetConnection(getApplication<MBApplication>())) {
                val response = appRepository.request(body)
                _loginResponse.postValue(handleSplashResponse(response))
            } else {
                _loginResponse.postValue(
                    Event(
                        Resource.Error(getApplication<Application>().getString(
                            R.string.no_internet_connection)))
                )
            }
        } catch (t: Throwable) {
            when (t) {
                is IOException -> {
                    _loginResponse.postValue(
                        Event(
                            Resource.Error(
                                getApplication<MBApplication>().getString(
                                    R.string.network_failure
                                )
                            ))
                    )
                }
                else -> {
                    _loginResponse.postValue(
                        Event(
                            Resource.Error(
                                getApplication<MBApplication>().getString(
                                    R.string.conversion_error
                                )
                            ))
                    )
                }
            }
        }
    }

    private fun handleSplashResponse(response: Response<String>): Event<Resource<String>>? {

        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                println("Response"+resultResponse)
                return Event(Resource.Success(resultResponse))
            }
        }
        return Event(Resource.Error(response.message()))
    }


    // insert data
    fun record_insert(record_info_list: List<Record_Info>){
        viewModelScope.launch(Dispatchers.IO) {
            record_info_list.forEach {
                var show_record_info=Record_Info(it.record_id,it.fn,it.fv)
                db.daoRecordInfo().insertOrUpdate(show_record_info)

            }
        }
    }

    // get record data
    fun get_record_data():LiveData<List<Record_Info>>{
        recordResponse=db.daoRecordInfo().getAllRecord()
        return recordResponse
    }

    // emp pwd
    fun get_emp_pwd():Record_Info{
        emppwdResponse=db.daoRecordInfo().getEmpPwd("empPwd")
        return emppwdResponse
    }
}