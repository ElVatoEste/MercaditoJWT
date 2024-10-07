package org.vato.ecommerce.Publication;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.vato.ecommerce.Image.Image;
import org.vato.ecommerce.Student.model.Student;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "Tbl_Publication")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Publication {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_Publicacion")
    private int IdPublication;

    @Column(name = "Titulo", columnDefinition = "NVARCHAR(255)", nullable = false)
    private String title;

    @Column(name = "Descripcion", columnDefinition = "NVARCHAR(255)", nullable = false)
    private String description;

    // Cambiar el tipo de datos de precio a DOUBLE
    @Column(name = "Precio", nullable = false)
    private double price;

    // Cambiar el tipo de datos de stock a INT
    @Column(name = "Stock", nullable = false)
    private int stock;

    @Column(name = "Esta_Destacada")
    private boolean isFeatured;

    @Column(name = "Esta_Visible")
    private boolean isVisible;

    @Column(name = "Fecha_Creacion", nullable = false)
    private LocalDateTime creationDate;

    // Relación con Student (Muchos a uno)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Cif_Student", nullable = false)
    private Student student;

    // Relación con Image (Uno a muchos, una publicación puede tener muchas imágenes)
    @OneToMany(mappedBy = "publication", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private Set<Image> images;
}
