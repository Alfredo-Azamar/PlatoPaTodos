package mx.mr.platopatodos.model.responses

/**
 * Dashboard Response Model
 *
 * This data class represents the response model for dashboard information. It includes a list of `TableItemDB`
 * objects, each containing specific values related to the dashboard.
 *
 * @param table The list of dashboard items.
 *
 * @constructor Creates a `DashboardRes` with the provided list of dashboard items.
 * @sample table
 *
 * @author Héctor González Sánchez
 * @author Alfredo Azamar López
 */
data class DashboardRes(
    var table: List<TableItemDB>
)

/**
 * Dashboard Item Model
 *
 * This data class represents an item within the dashboard response model. It holds a single value,
 * typically related to the dashboard.
 *
 * @param Valor The specific value related to the dashboard.
 *
 * @constructor Creates a `TableItemDB` with the provided value.
 * @sample Valor
 *
 * @author Héctor González Sánchez
 * @author Alfredo Azamar López
 */
data class TableItemDB(
    var Valor: String
)