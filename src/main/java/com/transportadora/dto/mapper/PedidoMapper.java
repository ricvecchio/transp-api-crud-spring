package com.transportadora.dto.mapper;

import com.transportadora.dto.PedidoDTO;
import com.transportadora.enums.Status;
import com.transportadora.model.Pedido;
import org.springframework.stereotype.Component;

@Component
public class PedidoMapper {

    public PedidoDTO toDTO(Pedido pedido) {
        if (pedido == null) {
            return null;
        }
        return new PedidoDTO(pedido.getIdPedido(), pedido.getNomePedido(), pedido.getRazaoSocial()
                , pedido.getCpfcnpjPedido(), pedido.getTipoPgto(), pedido.getCepPedido()
                , pedido.getLogradouroPedido(), pedido.getNumeroPedido(), pedido.getComplementoPedido()
                , pedido.getBairroPedido(), pedido.getCidadePedido(), pedido.getEstadoPedido()
                , pedido.getSfobras(), pedido.getCno(), pedido.getIe(), pedido.getMangueira()
                , pedido.getVolume(), pedido.getPrecoCx5(), pedido.getPrecoCx10(), pedido.getPrecoCx15()
                , pedido.getPrecoLv5(), pedido.getPrecoLv10(), pedido.getPrecoLv15(), pedido.getAjudanteHora()
                , pedido.getObservacao()
                , pedido.getStatus().getValue()
                , pedido.getIdCliente()
                , pedido.getNome()
                , pedido.getCpfcnpj()
                , pedido.getTelefone()
                , pedido.getCelular()
                , pedido.getEmail(), pedido.getCep(), pedido.getLogradouro(), pedido.getNumero(), pedido.getComplemento(),
                pedido.getBairro(), pedido.getCidade(), pedido.getEstado(), pedido.getDataAtualizacaoPedido());
    }

    public Pedido toEntity(PedidoDTO pedidoDTO) {

        if (pedidoDTO == null) {
            return null;
        }

        Pedido pedido = new Pedido();
        if (pedidoDTO.idPedido() != null) {
            pedido.setIdPedido(pedidoDTO.idPedido());
        }
        pedido.setNomePedido(pedidoDTO.nomePedido());
        pedido.setRazaoSocial(pedidoDTO.razaoSocial());
        pedido.setCpfcnpjPedido(pedidoDTO.cpfcnpjPedido());
        pedido.setTipoPgto(pedidoDTO.tipoPgto());
        pedido.setCepPedido(pedidoDTO.cepPedido());
        pedido.setLogradouroPedido(pedidoDTO.logradouroPedido());
        pedido.setNumeroPedido(pedidoDTO.numeroPedido());
        pedido.setComplementoPedido(pedidoDTO.complementoPedido());
        pedido.setBairroPedido(pedidoDTO.bairroPedido());
        pedido.setCidadePedido(pedidoDTO.cidadePedido());
        pedido.setEstadoPedido(pedidoDTO.estadoPedido());
        pedido.setSfobras(pedidoDTO.sfobras());
        pedido.setCno(pedidoDTO.cno());
        pedido.setIe(pedidoDTO.ie());
        pedido.setMangueira(pedidoDTO.mangueira());
        pedido.setVolume(pedidoDTO.volume());
        pedido.setPrecoCx5(pedidoDTO.precoCx5());
        pedido.setPrecoCx10(pedidoDTO.precoCx10());
        pedido.setPrecoCx15(pedidoDTO.precoCx15());
        pedido.setPrecoLv5(pedidoDTO.precoLv5());
        pedido.setPrecoLv10(pedidoDTO.precoLv10());
        pedido.setPrecoLv15(pedidoDTO.precoLv15());
        pedido.setAjudanteHora(pedidoDTO.ajudanteHora());
        pedido.setObservacao(pedidoDTO.observacao());
        pedido.setStatus(convertStatusValue(pedidoDTO.status()));
        pedido.setIdCliente(pedidoDTO.idCliente());
        pedido.setNome(pedidoDTO.nome());
        pedido.setCpfcnpj(pedidoDTO.cpfcnpj());
        pedido.setTelefone(pedidoDTO.telefone());
        pedido.setCelular(pedidoDTO.celular());
        pedido.setEmail(pedidoDTO.email());
        pedido.setCep(pedidoDTO.cep());
        pedido.setLogradouro(pedidoDTO.logradouro());
        pedido.setNumero(pedidoDTO.numero());
        pedido.setComplemento(pedidoDTO.complemento());
        pedido.setBairro(pedidoDTO.bairro());
        pedido.setCidade(pedidoDTO.cidade());
        pedido.setEstado(pedidoDTO.estado());
        pedido.setDataAtualizacaoPedido(pedidoDTO.dataAtualizacaoPedido());
        return pedido;
    }

    public Status convertStatusValue(String value) {
        if (value == null) {
            return null;
        }
        return switch (value) {
            case "Emitido" -> Status.EMITIDO;
            case "Cancelado" -> Status.CANCELADO;
            case "Salvo" -> Status.SALVO;
            default -> throw new IllegalArgumentException("Status inválido: " + value);
        };
    }
};