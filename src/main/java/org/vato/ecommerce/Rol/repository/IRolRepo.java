package org.vato.ecommerce.Rol.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.vato.ecommerce.Rol.model.Rol;

import java.util.Optional;

@Repository
public interface IRolRepo extends JpaRepository<Rol, Integer> {
    Optional<Rol> findByRolName(String Rol);

    boolean existsByRolName(String nameRol);
}
