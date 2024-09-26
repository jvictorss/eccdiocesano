package br.com.verbum.eccdiocesano.domain.entities;

import br.com.verbum.eccdiocesano.domain.reuse.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.UUID;

@Entity
@Table(name = "paroquia")
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Paroquia extends BaseEntity {

    private String nome;
    private String cidade;
    private String estado;
    private UUID idSetor;
    private UUID idDiocese;
    private Boolean isActive;
}
