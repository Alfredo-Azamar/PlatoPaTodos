package mx.mr.platopatodos.model.requests

import com.google.gson.annotations.SerializedName

data class IncidentReq(
    @SerializedName("nombreCom") var diningName: String,
    @SerializedName("tipo") var issue: String,
    @SerializedName("desc") var description: String,
    @SerializedName("fecha") var date: String
)
