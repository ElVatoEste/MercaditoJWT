package org.vato.ecommerce.Student.service;


import org.springframework.http.ResponseEntity;
import org.vato.ecommerce.Student.model.Student;

import java.util.List;

public interface IStudentService {
    List<Student> getAllUsers();

    <T> T findById(int id);

    ResponseEntity<String> deleteUser(int id);

}
