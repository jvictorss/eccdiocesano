package br.com.verbum.eccdiocesano.domain.entities;

import br.com.verbum.eccdiocesano.domain.reuse.BaseEntity;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "casal")
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Casal extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "ele_id")
    private Conjuge ele;

    @ManyToOne
    @JoinColumn(name = "ela_id")
    private Conjuge ela;

    private String dataCasamentoReligioso;
    private String dataCasamentoCivil;
    private String endereco;
    private String bairro;
    private String cidade;
    private String estado;

    @ManyToOne
    @JoinColumn(name = "paroquia_ecc_id")
    private Paroquia paroquiaEcc;

    @ManyToOne
    @JoinColumn(name = "paroquia_atual_id")
    private Paroquia paroquiaAtual;

    @ManyToOne
    @JoinColumn(name = "setor_id")
    private Setor setorial;

    @ManyToOne
    @JoinColumn(name = "diocese_id")
    private Diocese diocese;

    private Long tenantId;
    private String dataPrimeiraEtapa;
    private String dataSegundaEtapa;
    private String dataTerceiraEtapa;
    private Boolean isActive;
}