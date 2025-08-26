package com.example.HPPO_Backend.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "El rol que se intenta agregar esta duplicado")
public class RoleDuplicateException extends Exception {
}
