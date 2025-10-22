package project.com.example.todo.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.com.example.todo.Repository.UserRepository;

import project.com.example.todo.models.User;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public User createUser( User user){

        return userRepository.save(user);
    }

    public User getUserById(Long id){

        return userRepository.findById(id).orElseThrow( ()->new RuntimeException("todo not found"));
    }
}
