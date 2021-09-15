package app.bandhan.microbanking.repository

import app.bandhan.microbanking.model.LoginRequest
import app.bandhan.microbanking.network.RetrofitInstance

class AppRepository {

    suspend fun request(body:Any) = RetrofitInstance.loginApi.Call(body)

}