package org.vato.ecommerce.Student.dto;

import lombok.Data;

@Data
public class StudentDTO {
    private Integer cif;
    private String username;
    private String password;
    private String email;
    private String phone;
    private String description;
}
