package mx.mr.platopatodos.model.requests

import com.google.gson.annotations.SerializedName

/**
 * Attendance Request Model
 * @author Héctor González Sánchez
 */

data class AssistReq(
    @SerializedName("nombreCom") var location: String,
    @SerializedName("tipoRacion") var type: String,
    @SerializedName("raciones") var servings: Int,
    @SerializedName("fecha") var date: String,
    @SerializedName("tipoAcceso") var accessType: String
)
