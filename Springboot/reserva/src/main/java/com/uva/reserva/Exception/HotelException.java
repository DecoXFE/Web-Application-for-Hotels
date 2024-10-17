package com.uva.reserva.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.UNPROCESSABLE_ENTITY)
public class HotelException extends RuntimeException {
    public HotelException(String mensaje) {
        super(mensaje);
    }
}