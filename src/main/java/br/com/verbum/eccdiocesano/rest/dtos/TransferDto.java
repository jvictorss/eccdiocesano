package br.com.verbum.eccdiocesano.rest.dtos;

import javax.validation.constraints.NotNull;
import java.util.UUID;

public class TransferDto {

    @NotNull private UUID novaParoquia;
}
