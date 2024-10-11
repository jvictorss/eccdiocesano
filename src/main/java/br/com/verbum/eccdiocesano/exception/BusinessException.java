package br.com.verbum.eccdiocesano.exception;

import lombok.Builder;
import lombok.Getter;

@Getter
public class BusinessException extends Exception {

    @Builder
    public BusinessException(String message) {
        super(message);
    }

    public String getErrorMessage() {
        return super.getMessage();
    }
}
