package mx.mr.platopatodos.model.requests

import com.google.gson.annotations.SerializedName

/**
 * Menu Request Model
 *
 * This data class represents the request model for submitting a menu. It includes the name of
 * the dining location, the components of the menu (such as soup, main course, etc.), and the date.
 *
 * @property diningName The name of the dining location.
 * @property soup The soup or rice item in the menu.
 * @property mainCourse The main course item in the menu.
 * @property carbs The bread or tortilla item in the menu.
 * @property water The daily water item in the menu.
 * @property beansSauce The beans or sauce item in the menu.
 * @property date The date for the menu submission.
 *
 * @constructor Creates a `MenuReq` request with the provided details.
 * @param diningName The name of the dining location.
 * @param soup The soup or rice item in the menu.
 * @param mainCourse The main course item in the menu.
 * @param carbs The bread or tortilla item in the menu.
 * @param water The daily water item in the menu.
 * @param beansSauce The beans or sauce item in the menu.
 * @param date The date for the menu submission.
 *
 * @authors Héctor González Sánchez
 * @authors Alfredo Azamar López
 */

data class MenuReq(
    @SerializedName("nombreCom") var diningName: String,
    @SerializedName("sopaArroz") var soup: String,
    @SerializedName("platoFuerte") var mainCourse: String,
    @SerializedName("panTortilla") var carbs: String,
    @SerializedName("aguaDelDia") var water: String,
    @SerializedName("frijolesSalsa") var beansSauce: String,
    @SerializedName("fecha") var date: String
)
