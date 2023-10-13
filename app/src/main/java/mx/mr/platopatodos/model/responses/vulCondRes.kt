package mx.mr.platopatodos.model.responses

import com.google.gson.annotations.SerializedName

data class vulCondRes(
    val table: List<vulCondItem>
)

data class vulCondItem(
    val Nombre: String
)
