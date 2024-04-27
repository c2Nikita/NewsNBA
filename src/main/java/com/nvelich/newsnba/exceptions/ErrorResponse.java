package com.nvelich.newsnba.exceptions;

import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@XmlRootElement
public class ErrorResponse {
    private int code;
    private String message;

    public ErrorResponse(String message, int code) {
        this.code = code;
        this.message = message;
    }
}
