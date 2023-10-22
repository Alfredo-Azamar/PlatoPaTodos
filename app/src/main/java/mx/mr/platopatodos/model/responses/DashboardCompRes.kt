package mx.mr.platopatodos.model.responses

/**
 * Dashboard Competitive Response Model
 *
 * This data class represents the response model for competitive dashboard information. It includes
 * a list of `TableItemDBComp` objects, each containing details about a specific dining location.
 *
 * @param table The list of competitive dashboard items.
 *
 * @constructor Creates a `DashboardCompRes` with the provided list of competitive dashboard items.
 * @sample table
 *
 * @author Héctor González Sánchez
 * @author Alfredo Azamar López
 */
data class DashboardCompRes(
    var table: List<TableItemDBComp>
)

/**
 * Competitive Dashboard Item Model
 *
 * This data class represents an item within the competitive dashboard response model. It holds
 * information about a specific dining location, including its name, paid reservations, donated
 * reservations, total visits, and total revenue.
 *
 * @param Nombre The name of the dining location.
 * @param R_Pagadas The number of paid reservations.
 * @param R_Donadas The number of donated reservations.
 * @param TotalVisitas The total number of visits.
 * @param MontoRecaudado The total revenue amount.
 *
 * @constructor Creates a `TableItemDBComp` with the provided details of a specific dining location.
 * @sample Nombre
 * @sample R_Pagadas
 * @sample R_Donadas
 * @sample TotalVisitas
 * @sample MontoRecaudado
 *
 * @author Héctor González Sánchez
 * @author Alfredo Azamar López
 */
data class TableItemDBComp(
    var Nombre: String,
    var R_Pagadas: String,
    var R_Donadas: String,
    var TotalVisitas: String,
    var MontoRecaudado: String
)
