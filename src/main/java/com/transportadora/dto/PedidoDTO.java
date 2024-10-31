package com.transportadora.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.transportadora.enums.Status;
import com.transportadora.enums.validation.ValueOfEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

public record PedidoDTO(
        @JsonProperty("idPedido") Long idPedido,
        @NotBlank @NotNull @Length(min = 5, max = 100) String nomePedido,
        String razaoSocial,
        String cpfcnpjPedido,
        String tipoPgto,
        String cepPedido,
        String logradouroPedido,
        String numeroPedido,
        String complementoPedido,
        String bairroPedido,
        String cidadePedido,
        String estadoPedido,
        String sfobras,
        String cno,
        String ie,
        String mangueira,
        String volume,
        String precoCx5,
        String precoCx10,
        String precoCx15,
        String precoLv5,
        String precoLv10,
        String precoLv15,
        String ajudanteHora,
        String observacao,
        @NotNull @Length(max = 10) @ValueOfEnum(enumClass = Status.class) String status,
        Long idCliente) {
}
