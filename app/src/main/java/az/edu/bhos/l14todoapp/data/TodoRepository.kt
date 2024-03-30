package az.edu.bhos.l14todoapp.data

import az.edu.bhos.l14todoapp.entities.TodoEntity
import kotlinx.coroutines.flow.Flow

interface TodoRepository {
    suspend fun syncTodos()
    fun observeTodoEntries(): Flow<List<TodoEntity>>
    suspend fun save(todoEntity: TodoEntity)
}
class TodoRepositoryImpl(
    private val localData: TodoLocalData,
    private val remoteData: TodoRemoteData
) : TodoRepository {

    override suspend fun syncTodos() {
        val todoList = remoteData.getTodos()
         todoList.map { todoDto ->
            localData.save(todoDto.toEntity())
        }

    }


    override fun observeTodoEntries(): Flow<List<TodoEntity>> {
        return localData.observeTodoItems()
    }

    override suspend fun save(todoEntity: TodoEntity) {
        localData.save(todoEntity)
    }
}
