package com.noticiasgt.backend.models;

public class RespuestaCorta {

    private boolean status;  // Atributo para el estado
    private String mensaje;  // Atributo para el mensaje

    // Constructor
    public RespuestaCorta(boolean status, String mensaje) {
        this.status = status;
        this.mensaje = mensaje;
    }

    // Getter para status
    public boolean isStatus() {
        return status;
    }

    // Setter para status
    public void setStatus(boolean status) {
        this.status = status;
    }

    // Getter para mensaje
    public String getMensaje() {
        return mensaje;
    }

    // Setter para mensaje
    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
}
