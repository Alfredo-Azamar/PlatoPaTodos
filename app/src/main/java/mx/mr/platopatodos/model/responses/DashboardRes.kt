package mx.mr.platopatodos.model.responses

/**
 * Dashboard Response Model
 * @author Héctor González Sánchez
 */


data class DashboardRes(
    var table: List<TableItemDB>
)

data class TableItemDB(
    var Valor: String
)