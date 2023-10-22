package mx.mr.platopatodos.model.responses

/**
 * Login Response Model
 *
 * This data class represents the response model for a login operation. It contains a list of `TableItem`
 * objects, each providing user-related information.
 *
 * @param table The list of user-related items.
 *
 * @constructor Creates a `LoginRes` with the provided list of user-related items.
 * @sample table
 *
 * @author Héctor González Sánchez
 * @author Alfredo Azamar López
 */
data class LoginRes(
    var table: List<TableItem>
)


/**
 * User Information Model
 *
 * This data class represents user information typically retrieved after a successful login. It includes
 * user-specific data, such as the user's name.
 *
 * @param Nombre The name of the user.
 *
 * @constructor Creates a `TableItem` with the provided user name.
 * @sample Nombre
 *
 * @author Héctor González Sánchez
 * @author Alfredo Azamar López
 */
data class TableItem(
    var Nombre: String
)
