package br.com.verbum.eccdiocesano.domain.entities;

import br.com.verbum.eccdiocesano.domain.reuse.BaseEntity;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Entity
@Table(name = "diocese")
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Diocese extends BaseEntity {

    private String cidadeDiocese;
    private String estadoDiocese;
    private String nomeDiocese;
    private Boolean isActive;
    @OneToMany(mappedBy = "idDiocese", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Setor> setores;
}
