package mx.mr.platopatodos.model.requests

import com.google.gson.annotations.SerializedName

/**
 * Login Request Model
 * @author Héctor González Sánchez
 */

data class LoginReq(
    @SerializedName("nombreUsuario") var username: String,
    @SerializedName("contra") var password: String
)

