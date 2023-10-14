package mx.mr.platopatodos.model.responses

data class VulnerableSitRes(
    var table: List<TableItemSP>
)

data class TableItemSP(
    var Nombre: String
)
