package project.com.example.todo.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.com.example.todo.models.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}
