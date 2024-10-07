package org.vato.ecommerce.Student.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.vato.ecommerce.Image.Image;
import org.vato.ecommerce.Publication.Publication;
import org.vato.ecommerce.Rol.model.Rol;

import java.util.Collection;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "Tbl_Student")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Student implements UserDetails {

    @Id
    @Column(name = "Cif")
    private int cif;

    @Column(name = "Username", columnDefinition = "NVARCHAR(255)")
    private String username;

    @Column(name = "Email", columnDefinition = "NVARCHAR(255)")
    private String email;

    @Column(name = "Contraseña", columnDefinition = "NVARCHAR(255)")
    @JsonIgnore
    private String password;

    @OneToOne(fetch = FetchType.LAZY)  //
    @JoinColumn(name = "ID_Rol", nullable = false)
    private Rol rol;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_Image", unique = true)
    private Image image;

    // Relación uno a muchos: un estudiante puede tener muchas publicaciones
    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private Set<Publication> publications;

    @Column(name = "Status")
    private Boolean active;

    @Column(name = "Telefono", columnDefinition = "NVARCHAR(255)")
    private String phoneNumber;

    @Column(name = "Descripcion_Personal", columnDefinition = "NVARCHAR(255)")
    private String personalDescription;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(rol.getRolName()));
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return active != null ? active : false;
    }
}
