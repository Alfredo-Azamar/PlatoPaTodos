package mx.mr.platopatodos.model.requests

import com.google.gson.annotations.SerializedName

/**
 * Dining Incident Request Model
 *
 * This data class represents the request model for reporting a dining incident. It includes
 * information about the dining location's name, the type of incident, its description, and the date.
 *
 * @property diningName The name of the dining location.
 * @property issue The type of incident (e.g., "Falla eléctrica" or "Accidente").
 * @property description A description of the incident.
 * @property date The date when the incident occurred.
 *
 * @constructor Creates an `IncidentReq` request with the provided details.
 * @param diningName The name of the dining location.
 * @param issue The type of incident.
 * @param description The incident description.
 * @param date The date of the incident.
 *
 * @authors Héctor González Sánchez
 * @authors Alfredo Azamar López
 */

data class IncidentReq(
    @SerializedName("nombreCom") var diningName: String,
    @SerializedName("tipo") var issue: String,
    @SerializedName("desc") var description: String,
    @SerializedName("fecha") var date: String
)
