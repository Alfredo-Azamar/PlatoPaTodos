package mx.mr.platopatodos.model.responses

/**
 * Login Response Model
 * @author Héctor González Sánchez
 */

data class LoginRes(
    var table: List<TableItem>
)

data class TableItem(
    var Nombre: String
)
