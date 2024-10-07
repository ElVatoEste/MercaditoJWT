package org.vato.ecommerce.Student.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.vato.ecommerce.Student.repository.IStudentRepo;
import org.vato.ecommerce.Student.model.Student;
import org.vato.ecommerce.Rol.model.Rol;
import org.vato.ecommerce.Rol.repository.IRolRepo;

import java.util.List;
import java.util.Random;

@Service
public class StudentService implements IStudentService {

    @Autowired
    private IStudentRepo studentRepo;

    @Autowired
    private IRolRepo repoRol;

    @Override
    public List<Student> getAllUsers() {
        return studentRepo.findAll();
    }

    @Override
    @Transactional
    public <T> T findById(int id) {
        Student user = studentRepo.findById(id).orElse(null);
        if (user == null) {
            return (T) ResponseEntity.badRequest().body("The user does not exist.");
        }
        return (T) user;
    }

    public ResponseEntity<String> createUser(String username, String email, String password) {

        if (studentRepo.findByEmail(email).isPresent()) {
            return ResponseEntity.badRequest().body("The user already exists.");
        }

        // Crear un nuevo usuario
        Student student = new Student();
        student.setUsername(username);  // Asignar nombre del DTO
        student.setEmail(email);  // Asignar correo del DTO
        student.setPassword(password);  // La contraseña queda nula

        // Asignar Rol por defecto (por ejemplo, rol de "USER" o cualquier otro rol predeterminado)
        Rol rol = repoRol.findByRolName("User").orElse(null);
        // Aquí puedes definir un rol predeterminado, o puedes cambiar la lógica
        if (rol == null) {
            return ResponseEntity.badRequest().body("The role does not exist.");
        }
        student.setRol(rol);

        // Guardar en la base de datos
        studentRepo.save(student);

        return ResponseEntity.ok("The user was created successfully.");
    }

    private String generateRandomCode() {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random = new Random();
        StringBuilder sb = new StringBuilder(8);
        for (int i = 0; i < 8; i++) {
            sb.append(characters.charAt(random.nextInt(characters.length())));
        }
        return sb.toString();
    }

    @Override
    public ResponseEntity<String> deleteUser(int id) {
        Student user = studentRepo.findById(id).orElse(null);
        if (user == null) {
            return ResponseEntity.badRequest().body("The user does not exist.");
        }
        studentRepo.deleteById(id);
        return ResponseEntity.ok("The user was deleted successfully.");
    }


}