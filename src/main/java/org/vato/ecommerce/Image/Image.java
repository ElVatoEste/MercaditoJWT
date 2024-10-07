package org.vato.ecommerce.Image;

import jakarta.persistence.*;
import lombok.*;
import org.vato.ecommerce.Publication.Publication;
import org.vato.ecommerce.Student.model.Student;

import java.util.Set;

@Entity
@Table(name = "Tbl_Image")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_Imagen")
    private int id;

    @Column(name = "Nombre_Imagen", columnDefinition = "NVARCHAR(255)")
    private String name;

    @Column(name = "Tipo_Imagen")
    private String type;

    @Lob
    @Column(name = "Imagen", nullable = false, columnDefinition = "VARBINARY(MAX)")
    private byte[] imageData;

    // Relación con Post (Muchas imágenes pertenecen a una publicación)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_Publicacion")
    private Publication publication;
}
