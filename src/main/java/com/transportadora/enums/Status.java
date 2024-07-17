package com.transportadora.enums;

public enum Status {
    EMITIDO("Emitido"), CANCELADO("Cancelado"), SALVO("Salvo");

    private String value;

    private Status(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return value;
    }
}
