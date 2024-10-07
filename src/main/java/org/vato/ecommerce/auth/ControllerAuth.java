package org.vato.ecommerce.auth;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.vato.ecommerce.Student.dto.StudentDTO;


@RestController
@RequestMapping("/auth")
@CrossOrigin("*")
public class ControllerAuth {

    @Autowired
    private ServiceAuth serviceAuth;

    @PostMapping("/login")
    public  ResponseEntity<String> login(@RequestParam String email, @RequestParam String password, HttpServletResponse response) {
        return serviceAuth.login(email, password, response);
    }

    @PostMapping(value = "register")
    public ResponseEntity<String> register(@RequestBody StudentDTO studentDTO,  HttpServletResponse response) {
        return serviceAuth.register(studentDTO, response);
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletResponse response) {
        return serviceAuth.logout(response);
    }
}
