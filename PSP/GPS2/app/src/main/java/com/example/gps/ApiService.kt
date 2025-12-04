import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    @POST("api/locations")
    fun sendLocation(@Body locationData: LocationData): Call<ApiResponse>
}

data class ApiResponse(
    val success: Boolean,
    val message: String,
    val timestamp: Long = System.currentTimeMillis()
)