package mx.mr.platopatodos.model.requests

import com.google.gson.annotations.SerializedName

/**
 * Changing the Dining Status Request Model
 * @author Héctor González Sánchez
 */

data class ChgStatusDining(
    @SerializedName("nombreCom") var diningName: String,
    @SerializedName("estadoCom") var diningStatus: String,
)
