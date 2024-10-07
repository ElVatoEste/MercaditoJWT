package org.vato.ecommerce.Rol.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.vato.ecommerce.Rol.model.Rol;
import org.vato.ecommerce.Rol.repository.IRolRepo;

import java.util.List;
@Service
public class RolService implements IRolService {

    @Autowired
    private IRolRepo repoRol;

    @Override
    public List<Rol> getAllRoles() {
        return repoRol.findAll();
    }

    @Transactional
    public <T> T findById(int id) {
        Rol rol = repoRol.findById(id).orElse(null);
        if(rol == null) {
            return (T) ResponseEntity.badRequest().body("The rol does not exist.");
        }
        return (T) rol;
    }

    @Override
    public ResponseEntity<String> createRol(String nameRol) {

        // Validar que el nombre del rol no sea nulo o vacío
        if (nameRol == null || nameRol.trim().isEmpty()) {
            return ResponseEntity.badRequest().body("The role name must not be empty or null.");
        }

        // Normalizar el nombre del rol (opcional): convertirlo a mayúsculas para evitar duplicados con diferencias en el formato
        nameRol = nameRol.trim().toUpperCase();  // Puedes ajustar a minúsculas si lo prefieres (toLowerCase())

        // Buscar si ya existe un rol con el nombre proporcionado
        if (repoRol.existsByRolName(nameRol)) {
            return ResponseEntity.badRequest().body("The role '" + nameRol + "' already exists.");
        }

        // Crear un nuevo rol ya que no existe
        Rol newRol = new Rol();
        newRol.setRolName(nameRol);

        // Guardar el rol en la base de datos, el ID será asignado automáticamente
        repoRol.save(newRol);

        // Devolver mensaje de éxito
        return ResponseEntity.ok("The role '" + nameRol + "' was created successfully.");
    }



    @Override
    public ResponseEntity<String> deleteRol(int id) {
        Rol rol = repoRol.findById(id).orElse(null);
        if(rol == null) {
            return (ResponseEntity.badRequest().body("The rol does not exist."));
        }
        repoRol.deleteById(id);
        return (ResponseEntity.ok("The rol was deleted successfully."));
    }

}
