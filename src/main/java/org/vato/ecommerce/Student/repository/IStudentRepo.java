package org.vato.ecommerce.Student.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.vato.ecommerce.Student.model.Student;

import java.util.Optional;

@Repository
public interface IStudentRepo extends JpaRepository<Student, Integer> {

    @Query("SELECT u FROM Student u WHERE u.email = :email")
    Optional<Student> findByEmail(@Param("email") String email);

    boolean existsByEmail(String email);
}
