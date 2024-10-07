package org.vato.ecommerce.Rol.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.vato.ecommerce.Rol.model.Rol;
import org.vato.ecommerce.Rol.service.IRolService;

import java.util.List;

@RestController
@RequestMapping("/api/roles")
@CrossOrigin("*")
public class RolController {

    @Autowired
    private IRolService serviceRol;

    // Obtener todos los roles
    @GetMapping("/all")
    public List<Rol> getAllRoles() {
        return serviceRol.getAllRoles();
    }

    @GetMapping("/find/{id}")
    public <T> T getById(@PathVariable("id") int id) {
        var rol = serviceRol.findById(id);
        return (T) rol;
    }

    // Crear un nuevo rol
    @PostMapping("/create")
    public ResponseEntity<String> createRol(@RequestParam String nameRol) {
        return serviceRol.createRol(nameRol);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") int id) {
        return serviceRol.deleteRol(id);
    }

}
