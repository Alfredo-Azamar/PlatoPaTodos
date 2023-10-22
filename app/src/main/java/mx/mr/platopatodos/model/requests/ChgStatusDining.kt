package mx.mr.platopatodos.model.requests

import com.google.gson.annotations.SerializedName

/**
 * Changing Dining Status Request Model
 *
 * This data class represents the request model for changing the status of a dining location.
 * It contains information about the dining location's name and the desired status.
 *
 * @property diningName The name of the dining location.
 * @property diningStatus The status to be set (e.g., "Abierto" or "Cerrado").
 *
 * @constructor Creates a `ChgStatusDining` request with the provided details.
 * @param diningName The name of the dining location.
 * @param diningStatus The desired status.
 *
 * @authors Héctor González Sánchez
 * @authors Alfredo Azamar López
 */
data class ChgStatusDining(
    @SerializedName("nombreCom") var diningName: String,
    @SerializedName("estadoCom") var diningStatus: String,
)
