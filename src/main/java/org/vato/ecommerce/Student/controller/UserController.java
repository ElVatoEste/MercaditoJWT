package org.vato.ecommerce.Student.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.vato.ecommerce.Student.model.Student;
import org.vato.ecommerce.Student.service.IStudentService;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@CrossOrigin("*")
public class UserController {

    @Autowired
    private IStudentService studentService;

    // Obtener todos los usuarios
    @GetMapping("/all")
    public List<Student> getAllUsers() {
        return studentService.getAllUsers();
    }

    // Obtener un usuario por ID
    @GetMapping("/find/{id}")
    public <T> T getById(@PathVariable("id") int id) {
        var student = studentService.findById(id);
        return (T) student;
    }

    // Eliminar un usuario por ID
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable("id") int id) {
        return studentService.deleteUser(id);
    }
}
