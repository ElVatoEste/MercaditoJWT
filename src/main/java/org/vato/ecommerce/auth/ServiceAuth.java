package org.vato.ecommerce.auth;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.vato.ecommerce.Rol.model.Rol;
import org.vato.ecommerce.Rol.repository.IRolRepo;
import org.vato.ecommerce.Student.dto.StudentDTO;
import org.vato.ecommerce.Student.model.Student;
import org.vato.ecommerce.Student.repository.IStudentRepo;
import org.vato.ecommerce.auth.jwt.JwtService;

import java.time.Duration;

@Service
public class ServiceAuth {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private IStudentRepo studentRepository;

    @Autowired
    private IRolRepo rolRepository;

    @Autowired
    private PasswordEncoder passwordEncoder; // Usar para encriptar la contraseña

    @Autowired
    private AuthenticationManager authenticationManager;

    // Método para login, que genera el token JWT y lo devuelve en una cookie
    public ResponseEntity<String> login(String email, String password, HttpServletResponse response) {

        try {
            // La autenticación la hará directamente Spring Security con el userDetailsService.
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(email, password)
            );

            // Guardar el contexto de seguridad
            SecurityContextHolder.getContext().setAuthentication(authentication);

            // Obtener el principal del contexto de seguridad (que es de tipo UserDetails)
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();

            // Generar el token JWT usando el servicio JWT
            String token = jwtService.getToken(userDetails);

            // Crear la cookie con el token JWT
            Cookie jwtCookie = new Cookie("jwt_token", token);
            jwtCookie.setHttpOnly(true); // Asegura que solo esté accesible por HTTP
            jwtCookie.setSecure(true); // Solo se envía a través de HTTPS
            jwtCookie.setPath("/"); // Disponible en todo el dominio
            jwtCookie.setMaxAge(24 * 60 * 60); // Expira en 1 día


            // Añadir la cookie a la respuesta
            response.addCookie(jwtCookie);

            return ResponseEntity.ok("Login exitoso");

        } catch (Exception e) {
            return ResponseEntity.status(401).body("Credenciales Invalidas");
        }
    }

    // Método para registrar un nuevo estudiante y generar un JWT token
    public ResponseEntity<String> register(StudentDTO studentDTO, HttpServletResponse response) {
        // Validar si el correo ya existe
        if (studentRepository.existsByEmail(studentDTO.getEmail())) {
            return ResponseEntity.status(400).body("El email ya está registrado");
        }

        // Encriptar la contraseña
        String encodedPassword = passwordEncoder.encode(studentDTO.getPassword());

        // Buscar el rol "User" y si no existe se crea
        Rol rolUsuario = rolRepository.findByRolName("User").orElseGet(() -> {
            Rol nuevoRol = new Rol();
            nuevoRol.setRolName("User");
            return rolRepository.save(nuevoRol);
        });

        // Crear el nuevo estudiante
        Student student = new Student();
        student.setCif(studentDTO.getCif());
        student.setUsername(studentDTO.getUsername());
        student.setEmail(studentDTO.getEmail());
        student.setPassword(encodedPassword);  // Guardamos la contraseña encriptada
        student.setPhoneNumber(studentDTO.getPhone());
        student.setPersonalDescription(studentDTO.getDescription());
        student.setRol(rolUsuario);
        student.setActive(true);  // Activar el usuario por defecto

        // Guardar el estudiante en la base de datos
        studentRepository.save(student);

        // Autenticar automáticamente al nuevo usuario
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(studentDTO.getEmail(), studentDTO.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        // Generar el token JWT usando el servicio JWT
        String token = jwtService.getToken(userDetails);

        // Crear la cookie con el token JWT
        Cookie jwtCookie = new Cookie("jwt_token", token);
        jwtCookie.setHttpOnly(true); // Asegura que solo esté accesible por HTTP
        jwtCookie.setSecure(true); // Solo se envía a través de HTTPS
        jwtCookie.setPath("/"); // Disponible en todo el dominio
        jwtCookie.setMaxAge(24 * 60 * 60); // Expira en 1 día

        response.addCookie(jwtCookie);

        return ResponseEntity.ok("Usuario registrado con éxito");
    }

    public ResponseEntity<String> logout(HttpServletResponse response) {

        Cookie jwtCookie = new Cookie("jwt_token", null);
        jwtCookie.setHttpOnly(true);
        jwtCookie.setSecure(true);
        jwtCookie.setPath("/");
        jwtCookie.setMaxAge(0); // Expira inmediatamente

        response.addCookie(jwtCookie);

        return ResponseEntity.ok("Logout exitoso");
    }
}
