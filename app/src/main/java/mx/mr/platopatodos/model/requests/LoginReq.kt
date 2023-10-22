package mx.mr.platopatodos.model.requests

import com.google.gson.annotations.SerializedName

/**
 * Login Request Model
 *
 * This data class represents the request model for user login. It includes the user's username
 * and password for authentication.
 *
 * @property username The username of the user.
 * @property password The user's password.
 *
 * @constructor Creates a `LoginReq` request with the provided username and password.
 * @param username The username of the user.
 * @param password The user's password.
 *
 * @authors Héctor González Sánchez
 * @authors Alfredo Azamar López
 */

data class LoginReq(
    @SerializedName("nombreUsuario") var username: String,
    @SerializedName("contra") var password: String
)

