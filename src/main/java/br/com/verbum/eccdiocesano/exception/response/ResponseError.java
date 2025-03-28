package br.com.verbum.eccdiocesano.exception.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseError {

    private int codigoStatus;
    private String status;
    private String message;

    public ResponseError(int codigoStatus, String status, String message){
        this.codigoStatus = codigoStatus;
        this.message = message;
        this.status = status;
    }
}