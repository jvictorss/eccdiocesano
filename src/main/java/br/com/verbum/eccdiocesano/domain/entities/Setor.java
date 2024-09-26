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
@Table(name = "setor")
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Setor extends BaseEntity {

    private String nomeSetor;
    private Boolean isActive;
    private UUID idDiocese;
//    @OneToMany(mappedBy = "setor", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<Paroquia> paroquias;
}
