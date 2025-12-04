import com.google.gson.annotations.SerializedName

data class LocationData(
    @SerializedName("userId") val userId: String,
    @SerializedName("userName") val userName: String,
    @SerializedName("latitude") val latitude: Double,
    @SerializedName("longitude") val longitude: Double,
    @SerializedName("altitude") val altitude: Double? = null,
    @SerializedName("timestamp") val timestamp: Long = System.currentTimeMillis(),
    @SerializedName("accuracy") val accuracy: Float? = null,
    @SerializedName("speed") val speed: Float? = null
)