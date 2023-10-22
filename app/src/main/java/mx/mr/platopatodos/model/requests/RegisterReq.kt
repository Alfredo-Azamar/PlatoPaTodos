package mx.mr.platopatodos.model.requests

import com.google.gson.annotations.SerializedName

/**
 * Customer's Registration Request Model
 *
 * This data class represents the request model for customer registration. It includes customer
 * details such as name, last names, CURP, date of birth, gender, and vulnerable situations.
 *
 * @property name The first name of the customer.
 * @property p_lastName The customer's first last name.
 * @property m_lastName The customer's second last name.
 * @property curp The customer's CURP (Clave Única de Registro de Población).
 * @property bDate The customer's date of birth.
 * @property gender The customer's gender (e.g., Male, Female).
 * @property vulSituation An array of vulnerable situations affecting the customer.
 *
 * @constructor Creates a `RegisterReq` request with the provided customer details.
 * @param name The first name of the customer.
 * @param p_lastName The customer's first last name.
 * @param m_lastName The customer's second last name.
 * @param curp The customer's CURP.
 * @param bDate The customer's date of birth.
 * @param gender The customer's gender.
 * @param vulSituation An array of vulnerable situations affecting the customer.
 *
 * @authors Héctor González Sánchez
 * @authors Alfredo Azamar López
 */

data class RegisterReq(
    @SerializedName("nombre") var name: String,
    @SerializedName("apellidoP") var p_lastName: String,
    @SerializedName("apellidoM") var m_lastName: String,
    @SerializedName("curp") var curp: String,
    @SerializedName("fechaNacim") var bDate: Int,
    @SerializedName("sexo") var gender: String,
    @SerializedName("nombreCond") var vulSituation: Array<String>
)
