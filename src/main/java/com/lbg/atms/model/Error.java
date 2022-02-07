package com.lbg.atms.model;

import io.swagger.v3.oas.annotations.Hidden;

@Hidden
public class Error {
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
