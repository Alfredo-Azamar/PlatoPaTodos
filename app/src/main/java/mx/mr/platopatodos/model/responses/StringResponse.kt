package mx.mr.platopatodos.model.responses

/**
 * Generic String Response Model
 *
 * This data class represents a generic string response model. It typically contains a message or string
 * response from an API or service.
 *
 * @param message The string message or response.
 *
 * @constructor Creates a `StringResponse` with the provided message.
 * @sample message
 *
 * @author Héctor González Sánchez
 * @author Alfredo Azamar López
 */
data class StringResponse (
    var message: String
)