package project.com.example.todo.Repository;

import project.com.example.todo.models.Todo;
import org.springframework.data.jpa.repository.JpaRepository;
import project.com.example.todo.models.User;

import java.util.Optional;

public interface TodoRepository  extends JpaRepository<Todo,Long> {
}
