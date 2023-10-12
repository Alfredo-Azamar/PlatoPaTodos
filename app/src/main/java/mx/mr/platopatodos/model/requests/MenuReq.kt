package mx.mr.platopatodos.model.requests

import com.google.gson.annotations.SerializedName

/**
 * Menu Request Model
 * @author Héctor González Sánchez
 */

data class MenuReq(
    @SerializedName("nombreCom") var diningName: String,
    @SerializedName("sopaArroz") var soup: String,
    @SerializedName("platoFuerte") var mainCourse: String,
    @SerializedName("panToritilla") var carbs: String,
    @SerializedName("aguaDelDia") var water: String,
    @SerializedName("frijolesSalsa") var beansSauce: String,
    @SerializedName("fecha") var date: String
)
