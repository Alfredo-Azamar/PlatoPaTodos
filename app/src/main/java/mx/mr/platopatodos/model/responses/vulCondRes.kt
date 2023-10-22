package mx.mr.platopatodos.model.responses

import com.google.gson.annotations.SerializedName

/**
 * Customer's Vulnerable Situation Response Model
 *
 * This data class represents the response model for customer's vulnerable situations. It typically contains a list
 * of vulnerable conditions as [vulCondItem] objects.
 *
 * @property table The list of vulnerable conditions.
 *
 * @constructor Creates a `vulCondRes` with the provided list of vulnerable conditions.
 * @param table The list of vulnerable conditions.
 *
 * @author Héctor González Sánchez
 * @author Alfredo Azamar López
 */
data class vulCondRes(
    val table: List<vulCondItem>
)

/**
 * Vulnerable Condition Item
 *
 * This data class represents an individual vulnerable condition. It typically contains the name of the condition.
 *
 * @property Nombre The name of the vulnerable condition.
 *
 * @constructor Creates a `vulCondItem` with the provided name.
 * @param Nombre The name of the vulnerable condition.
 *
 * @author Héctor González Sánchez
 * @author Alfredo Azamar López
 */
data class vulCondItem(
    val Nombre: String
)
