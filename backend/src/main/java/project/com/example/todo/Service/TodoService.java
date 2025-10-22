package project.com.example.todo.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import project.com.example.todo.Repository.TodoRepository;
import project.com.example.todo.models.Todo;

import java.util.List;

@Service
public class TodoService {

    @Autowired
    private TodoRepository todoRepository;

    public Todo createTodo(Todo todo){

         return todoRepository.save(todo);
    }

    public Todo getTodoById(Long id){

        return todoRepository.findById(id).orElseThrow( ()->new RuntimeException("todo not found"));
    }
    public List<Todo> getTodos(){
     return todoRepository.findAll();
    }

    public Todo updateTodo(Todo todo){
        return todoRepository.save(todo);
    }

    public void deleteTodoById(Long id){
        todoRepository.delete(getTodoById(id));
    }

    public Page<Todo> getAllTodosPages(int page , int size){
        Pageable pageable = PageRequest.of(page,size);
        return todoRepository.findAll(pageable);

    }
    public  void deleteTodo(Todo todo){
        todoRepository.delete(todo);
    }
}
