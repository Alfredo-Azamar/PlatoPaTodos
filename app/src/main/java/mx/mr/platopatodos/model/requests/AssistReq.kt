package mx.mr.platopatodos.model.requests

import com.google.gson.annotations.SerializedName

/**
 * Attendance Request Model
 *
 * This data class represents the request model for recording attendance. It contains details about
 * the dining location, the type of meal, the number of servings, the date, and the type of access.
 *
 * @property location The name of the dining location.
 * @property type The type of meal (e.g., breakfast, lunch, dinner).
 * @property servings The number of servings.
 * @property date The date of attendance.
 * @property accessType The type of access (e.g., regular, special).
 *
 * @constructor Creates an `AssistReq` with the provided details.
 * @param location The name of the dining location.
 * @param type The type of meal.
 * @param servings The number of servings.
 * @param date The date of attendance.
 * @param accessType The type of access.
 *
 * @authors Héctor González Sánchez
 * @authors Alfredo Azamar López
 */

data class AssistReq(
    @SerializedName("nombreCom") var location: String,
    @SerializedName("tipoRacion") var type: String,
    @SerializedName("raciones") var servings: Int,
    @SerializedName("fecha") var date: String,
    @SerializedName("tipoAcceso") var accessType: String
)
