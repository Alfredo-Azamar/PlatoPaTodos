package mx.mr.platopatodos.model.responses

import com.google.gson.annotations.SerializedName

/**
 * Customer's vulnerable situation Response Model
 * @author Héctor González Sánchez
 */

data class vulCondRes(
    val table: List<vulCondItem>
)

data class vulCondItem(
    val Nombre: String
)
