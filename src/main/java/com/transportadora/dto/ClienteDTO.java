package com.transportadora.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

public record ClienteDTO(
        @JsonProperty("id") Long id,
        @NotBlank @NotNull @Length(min = 1, max = 100) String nome,
        String cpfcnpj,
        String telefone,
        String celular,
        String email,
        String cep,
        String logradouro,
        String numero,
        String complemento,
        String bairro,
        String cidade,
        String estado) {

}
