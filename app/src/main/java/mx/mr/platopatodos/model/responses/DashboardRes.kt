package mx.mr.platopatodos.model.responses

data class DashboardRes(
    var table: List<TableItemDB>
)

data class TableItemDB(
    var Nombre: String
)