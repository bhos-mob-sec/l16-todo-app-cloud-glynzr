package az.edu.bhos.l14todoapp.flows

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import az.edu.bhos.l14todoapp.data.TodoRepository
import az.edu.bhos.l14todoapp.entities.TodoBundle
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class MainViewModel(
    todoRepo: TodoRepository
) : ViewModel() {

    private val _todoBundles: MutableLiveData<List<TodoBundle>> = MutableLiveData()

    val todoBundles: LiveData<List<TodoBundle>>
        get() = _todoBundles

    init {
        viewModelScope.launch {
            todoRepo.syncTodos()
        }

//        todoRepo.observeTodoEntries()
//            .onEach { todos ->
//                val submit=todos.groupBy { it.weekday }
//                    .map { entry->TodoBundle(entry.key,entry.value) }
//
//                _todoBundles.postValue(submit)
//            }.launchIn(viewModelScope)

        todoRepo.observeTodoEntries()
            .onEach { todos ->
                val submit = todos.groupBy { it.weekday }
                    .map { entry -> TodoBundle(entry.key, entry.value) }
                    .sortedBy { todoBundle ->
                        // Convert weekday strings to corresponding indices
                        when (todoBundle.weekday.toLowerCase()) {
                            "sunday" -> 0
                            "monday" -> 1
                            "tuesday" -> 2
                            "wednesday" -> 3
                            "thursday" -> 4
                            "friday" -> 5
                            "saturday" -> 6
                            else -> 7 // Any other weekday comes last
                        }
                    }

                _todoBundles.postValue(submit)
            }.launchIn(viewModelScope)


    }
}
