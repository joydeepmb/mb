package app.bandhan.microbanking.network




import app.bandhan.microbanking.model.LoginRequest
import retrofit2.Response
import retrofit2.http.*

interface API {
    @POST("Bone/MobileDevice/1.0/1234/")
    suspend fun Call(@Body body: Any): Response<String>

}