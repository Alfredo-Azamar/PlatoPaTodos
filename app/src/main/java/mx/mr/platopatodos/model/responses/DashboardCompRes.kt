package mx.mr.platopatodos.model.responses

/**
 * Dashboard Competitive Response Model
 * @author Héctor González Sánchez
 */

data class DashboardCompRes(
    var table: List<TableItemDBComp>
)

data class TableItemDBComp(
    var Nombre: String,
    var R_Pagadas: String,
    var R_Donadas: String,
    var TotalVisitas: String,
    var MontoRecaudado: String
)
