package org.vato.ecommerce.Rol.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.vato.ecommerce.Student.model.Student;

import java.util.Set;

@Entity
@Table(name = "Tbl_Rol")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Rol {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_Rol")
    private Integer rolID;

    @Column(name = "NameRol", columnDefinition = "NVARCHAR(255)")
    private String rolName;

}
