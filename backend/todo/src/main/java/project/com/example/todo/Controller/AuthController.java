package project.com.example.todo.Controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import project.com.example.todo.Repository.UserRepository;
import project.com.example.todo.Service.UserService;
import project.com.example.todo.Utils.JwtUtil;
import project.com.example.todo.models.User;


import java.util.Map;
@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthController {


   private final UserService userService;

    private  final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final  JwtUtil jwtUtil;

    @PostMapping("/register")
    public ResponseEntity<String>registerUser(@RequestBody Map<String,String> body){

        String email =body.get("email");
        String rawPassword = body.get("password");

        if(userRepository.findByEmail(email).isPresent()){
            return new ResponseEntity<>("email already extits",HttpStatus.CONFLICT );
        }
        String encodedPassword =passwordEncoder.encode(rawPassword);

        userService.createUser(User.builder()
                .email(email)
                .password(encodedPassword)
                .build());
        return new ResponseEntity<>("successfully register",HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody Map<String, String> body) {

        String email =body.get("email");
        String password = body.get("password");

        var userOptional= userRepository.findByEmail(email);
        if(userOptional.isEmpty()){
            return new ResponseEntity<>("user not register",HttpStatus.UNAUTHORIZED);

        }
        User user =userOptional.get();
        if(!passwordEncoder.matches(password, user.getPassword())){

            return new ResponseEntity<>("invalid user",HttpStatus.UNAUTHORIZED);
        }
        String token = jwtUtil.generateToken(email);

        return ResponseEntity.ok(Map.of("token",token));

    }

}
