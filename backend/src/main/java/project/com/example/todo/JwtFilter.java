package project.com.example.todo;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import project.com.example.todo.Utils.JwtUtil;

import java.io.IOException;
import java.util.List;

@Component

public class JwtFilter extends OncePerRequestFilter {
    @Autowired
    private  JwtUtil jwtutil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String authheader = request.getHeader("Authorization");

        if (authheader != null && authheader.startsWith("Bearer ")) {

            String token = authheader.substring(7);

            if (jwtutil.validateJwtToken(token)) {

                String email = jwtutil.extractEmail(token);
                var auth = new UsernamePasswordAuthenticationToken(email, null, List.of());
                SecurityContextHolder.getContext().setAuthentication(auth);
            }

        }filterChain.doFilter(request,response);
    }
}
