package com.transportadora.user.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record UserDTO(
        @JsonProperty("idUsuario") java.util.UUID idUsuario,
        String name,
        String email,
        String username,
        String password,
        String permission) {
}
