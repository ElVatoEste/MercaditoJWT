package org.vato.ecommerce.Rol.service;


import org.springframework.http.ResponseEntity;
import org.vato.ecommerce.Rol.model.Rol;

import java.util.List;

public interface IRolService {

    List<Rol> getAllRoles();

    <T> T findById(int id);

    ResponseEntity<String> createRol (String nameRol);

    ResponseEntity<String> deleteRol(int id);
}
