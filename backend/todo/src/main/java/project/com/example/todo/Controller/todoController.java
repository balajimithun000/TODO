package project.com.example.todo.Controller;


import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.com.example.todo.Service.TodoService;
import project.com.example.todo.models.Todo;

import java.util.List;
@Slf4j
@RestController
@RequestMapping("/api/todo")

public class todoController {

    private static final Logger log = LoggerFactory.getLogger(todoController.class);
    @Autowired
    private TodoService todoService;


    @ApiResponses(value = {
            @ApiResponse( responseCode = "200", description = "todo retrived sucess"),
            @ApiResponse(responseCode = "404" , description = "todo not found")

    })
    @GetMapping("/{id}")
        ResponseEntity<Todo> getTodoById(@PathVariable long id){
        try{
            Todo createdTodo= todoService.getTodoById(id);
            return new ResponseEntity<>(createdTodo ,HttpStatus.OK);
        }catch(RuntimeException expception) {
            log.info("error");
            log.warn("");
            log.error("",expception);
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        }
        @GetMapping
        ResponseEntity<List<Todo>> getTodos(){
        return new ResponseEntity<List<Todo>>(todoService.getTodos(),HttpStatus.OK);
    }

    @GetMapping("/page")
    ResponseEntity<Page<Todo>> getTodosPaged(@RequestParam int page,@RequestParam int size){
        return new ResponseEntity<>(todoService.getAllTodosPages(page,size), HttpStatus.OK);
    }

    @PostMapping("/create")
    ResponseEntity<Todo> createUser(@RequestBody Todo todo) {
        return new ResponseEntity<>(todoService.createTodo(todo), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    ResponseEntity<Todo>updateTodoById( @PathVariable long id ,@RequestBody Todo todo){
        todo.setId(id);
        return new ResponseEntity<>(todoService.updateTodo(todo),HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
     void deleteTodoById(@PathVariable long id){

        todoService.deleteTodoById(id);
    }



}



