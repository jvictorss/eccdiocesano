package br.com.verbum.eccdiocesano.domain.entities;

import br.com.verbum.eccdiocesano.domain.reuse.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "conjuge")
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Conjuge extends BaseEntity {
    private String nome;
    private String apelido;
    private String telefone;
    private String email;
    private String cpf;
    private String dataNascimento;
    private Boolean isActive;
}
