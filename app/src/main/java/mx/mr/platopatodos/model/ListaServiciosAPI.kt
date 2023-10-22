package mx.mr.platopatodos.model

import mx.mr.platopatodos.model.requests.AssistReq
import mx.mr.platopatodos.model.requests.ChgStatusDining
import mx.mr.platopatodos.model.requests.IncidentReq
import mx.mr.platopatodos.model.requests.LoginReq
import mx.mr.platopatodos.model.requests.MenuReq
import mx.mr.platopatodos.model.requests.RegisterReq
import mx.mr.platopatodos.model.responses.DashboardCompRes
import mx.mr.platopatodos.model.responses.DashboardRes
import mx.mr.platopatodos.model.responses.LoginRes
import mx.mr.platopatodos.model.responses.RegisterRes
import mx.mr.platopatodos.model.responses.StringResponse
import mx.mr.platopatodos.model.responses.vulCondRes
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

/**
 * Services (APIs) Pool
 *
 * This interface defines a collection of service endpoints for making API requests to interact
 * with various features of the application.
 *
 * @see LoginReq Request data model for user login.
 * @see MenuReq Request data model for uploading menus.
 * @see IncidentReq Request data model for uploading incidences.
 * @see RegisterReq Request data model for registering customers.
 * @see AssistReq Request data model for uploading attendance records.
 * @see vulCondRes Response data model for vulnerable condition information.
 * @see DashboardRes Response data model for dashboard information.
 * @see DashboardCompRes Response data model for detailed dashboard information.
 * @see ChgStatusDining Request data model for updating dining status.
 * @see StringResponse Response data model containing a string message.
 *
 * @author Héctor González Sánchez
 * @author Alfredo Azamar López
 */

interface ListaServiciosAPI {

    @Headers("Content-Type: application/json")
    @POST("loginEncargado")
    fun userLogin(@Body info: LoginReq): Call<LoginRes>

    @Headers("Content-Type: application/json")
    @PUT("insertaMenu")
    fun uploadMenu(@Body menu: MenuReq): Call<StringResponse>

    @Headers("Content-Type: application/json")
    @POST("insertaIncidencia")
    fun uploadIncidence(@Body incident: IncidentReq): Call<StringResponse>

    @Headers("Content-Type: application/json")
    @POST("insertaComensalCond")
    fun uploadCustomer(@Body costumer: RegisterReq): Call<RegisterRes>

    @Headers("Content-Type: application/json")
    @POST("insertaAsistencia")
    fun uploadAttendance(@Body attendance: AssistReq): Call<StringResponse>

    @GET("condicionComensal")
    fun getVulSituations(): Call<vulCondRes>

    @GET("dashBoard/{nombreCom}/{fecha}")
    fun getDashboardInfo(@Path("nombreCom") diningName: String, @Path("fecha") date: String): Call<DashboardRes>

    @GET("dashBoardComp/{nombreCom}/{fecha}")
    fun getDashboardCompInfo(@Path("nombreCom") diningName: String, @Path("fecha") date: String): Call<DashboardCompRes>

    @Headers("Content-Type: application/json")
    @PUT("actualizaEstadoCom")
    fun updateDinStatus(@Body description: ChgStatusDining): Call<StringResponse>
}