package br.com.verbum.eccdiocesano.domain.entities;

import br.com.verbum.eccdiocesano.domain.reuse.BaseEntity;
import br.com.verbum.eccdiocesano.rest.enums.Roles;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "usuario")
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Usuario extends BaseEntity implements UserDetails {

    private String nome;
    private String email;
    private String senha;
    private String telefone;
    private String papel;
    private Boolean isActive;
    @ManyToOne
    @JoinColumn(name = "diocese_id")
    private Diocese diocese;
    @ManyToOne
    @JoinColumn(name = "setor_id")
    private Setor setor;
    @ManyToOne
    @JoinColumn(name = "paroquia_id")
    private Paroquia paroquia;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public String getPassword() {
        return this.senha;
    }

    @Override
    public String getUsername() {
        return this.email;
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
        return true;
    }
}
