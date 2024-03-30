package az.edu.bhos.l14todoapp.data.dto

import az.edu.bhos.l14todoapp.entities.TodoEntity
import kotlinx.serialization.Serializable

// TODO complete the dto class
@Serializable
data class TodoRemoteDto(
    val title: String,
    val id:String,
    val completed:Boolean,
    val weekday:String
) {
    fun toEntity() = TodoEntity(
        id = id,
        title = title,
        completed = completed,
        weekday =weekday
    )
}
