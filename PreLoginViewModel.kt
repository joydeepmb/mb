package app.bandhan.microbanking.viewmodel


import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.*
import app.bandhan.microbanking.R
import app.bandhan.microbanking.application.MBApplication
import app.bandhan.microbanking.model.PreLoginRequest
import app.bandhan.microbanking.model.PreLoginResponse
import app.bandhan.microbanking.repository.AppRepository
import app.bandhan.microbanking.util.Event
import app.bandhan.microbanking.util.Resource
import app.bandhan.microbanking.util.Utils
import kotlinx.coroutines.launch
import retrofit2.Response
import java.io.IOException

class PreLoginViewModel(
    app: Application,
    private var appRepository: AppRepository,
    ctx: Context
): AndroidViewModel(app) {

    private val _preLoginResponse = MutableLiveData<Event<Resource<String>>>()
    val preLoginResponse: LiveData<Event<Resource<String>>> = _preLoginResponse
    fun preLoginUser(body: PreLoginRequest) = viewModelScope.launch {
       // Log.e("Call Network","Call Network")
        preLogin(body)
    }

    private suspend fun preLogin(body: PreLoginRequest) {
        _preLoginResponse.postValue(Event(Resource.Loading()))
        try {
            if (Utils.hasInternetConnection(getApplication<MBApplication>())) {
                val response = appRepository.request(body)
                _preLoginResponse.postValue(handleSplashResponse(response))
            } else {
                _preLoginResponse.postValue(
                    Event(
                        Resource.Error(getApplication<Application>().getString(
                            R.string.no_internet_connection)))
                )
            }
        } catch (t: Throwable) {
            when (t) {
                is IOException -> {
                    _preLoginResponse.postValue(
                        Event(
                            Resource.Error(
                                getApplication<MBApplication>().getString(
                                    R.string.network_failure
                                )
                            ))
                    )
                }
                else -> {
                    _preLoginResponse.postValue(
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

                return Event(Resource.Success(resultResponse))
            }
        }
        return Event(Resource.Error(response.message()))
    }

}