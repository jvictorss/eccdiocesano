package br.com.verbum.eccdiocesano.domain.entities;

import br.com.verbum.eccdiocesano.domain.reuse.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
    private String dataNascimento;
    private String dataFalecimento;
    private Boolean isActive;

    @ManyToOne
    @JoinColumn(name = "casal_id")
    private Casal casal;
}
