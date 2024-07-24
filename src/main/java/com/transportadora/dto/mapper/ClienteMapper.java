package com.transportadora.dto.mapper;

import com.transportadora.dto.ClienteDTO;
import com.transportadora.enums.Status;
import com.transportadora.model.app.Cliente;
import org.springframework.stereotype.Component;

@Component
public class ClienteMapper {

    public ClienteDTO toDTO(Cliente cliente) {
        if (cliente == null) {
            return null;
        }
//        List<PedidoDTO> pedidos = cliente.getPedidos()
//                .stream()
//                .map(pedido -> new PedidoDTO(pedido.getId(), pedido.getNomePedido(), pedido.getRazaoSocial()
//                        , pedido.getCpfcnpjPedido(), pedido.getTipoPgto(), pedido.getCepPedido()
//                        , pedido.getLogradouroPedido(), pedido.getNumeroPedido(), pedido.getComplementoPedido()
//                        , pedido.getBairroPedido(), pedido.getCidadePedido(), pedido.getEstadoPedido()
//                        , pedido.getSfobras(), pedido.getCno(), pedido.getIe(), pedido.getMangueira()
//                        , pedido.getVolume(), pedido.getPrecoCx5(), pedido.getPrecoCx10(), pedido.getPrecoCx15()
//                        , pedido.getPrecoLv5(), pedido.getPrecoLv10(), pedido.getPrecoLv15(), pedido.getAjudanteHora()
//                        , pedido.getObservacao()
//                        , pedido.getStatus().getValue()))
//                .collect(Collectors.toList());
        return new ClienteDTO(cliente.getId(), cliente.getNome(), cliente.getCpfcnpj(), cliente.getTelefone(), cliente.getCelular(),
                cliente.getEmail(), cliente.getCep(), cliente.getLogradouro(), cliente.getNumero(), cliente.getComplemento(),
                cliente.getBairro(), cliente.getCidade(), cliente.getEstado());
//                pedidos);
    }

    public Cliente toEntity(ClienteDTO clienteDTO) {

        if (clienteDTO == null) {
            return null;
        }

        Cliente cliente = new Cliente();
        if (clienteDTO.id() != null) {
            cliente.setId(clienteDTO.id());
        }
        cliente.setNome(clienteDTO.nome());
        cliente.setCpfcnpj(clienteDTO.cpfcnpj());
        cliente.setTelefone(clienteDTO.telefone());
        cliente.setCelular(clienteDTO.celular());
        cliente.setEmail(clienteDTO.email());
        cliente.setCep(clienteDTO.cep());
        cliente.setLogradouro(clienteDTO.logradouro());
        cliente.setNumero(clienteDTO.numero());
        cliente.setComplemento(clienteDTO.complemento());
        cliente.setBairro(clienteDTO.bairro());
        cliente.setCidade(clienteDTO.cidade());
        cliente.setEstado(clienteDTO.estado());

//        List<Pedido> pedidos = clienteDTO.pedidos().stream().map(pedidoDTO -> {
//            var pedido = new Pedido();
//            pedido.setId(pedidoDTO.id());
//            pedido.setNomePedido(pedidoDTO.nomePedido());
//            pedido.setRazaoSocial(pedidoDTO.razaoSocial());
//            pedido.setCpfcnpjPedido(pedidoDTO.cpfcnpjPedido());
//            pedido.setTipoPgto(pedidoDTO.tipoPgto());
//            pedido.setCepPedido(pedidoDTO.cepPedido());
//            pedido.setLogradouroPedido(pedidoDTO.logradouroPedido());
//            pedido.setNumeroPedido(pedidoDTO.numeroPedido());
//            pedido.setComplementoPedido(pedidoDTO.complementoPedido());
//            pedido.setBairroPedido(pedidoDTO.bairroPedido());
//            pedido.setCidadePedido(pedidoDTO.cidadePedido());
//            pedido.setEstadoPedido(pedidoDTO.estadoPedido());
//            pedido.setSfobras(pedidoDTO.sfobras());
//            pedido.setCno(pedidoDTO.cno());
//            pedido.setIe(pedidoDTO.ie());
//            pedido.setMangueira(pedidoDTO.mangueira());
//            pedido.setVolume(pedidoDTO.volume());
//            pedido.setPrecoCx5(pedidoDTO.precoCx5());
//            pedido.setPrecoCx10(pedidoDTO.precoCx10());
//            pedido.setPrecoCx15(pedidoDTO.precoCx15());
//            pedido.setPrecoLv5(pedidoDTO.precoLv5());
//            pedido.setPrecoLv10(pedidoDTO.precoLv10());
//            pedido.setPrecoLv15(pedidoDTO.precoLv15());
//            pedido.setAjudanteHora(pedidoDTO.ajudanteHora());
//            pedido.setObservacao(pedidoDTO.observacao());
//            pedido.setStatus(convertStatusValue(pedidoDTO.status()));
//            pedido.setCliente(cliente);
//            return pedido;
//        }).collect(Collectors.toList());
//        cliente.setPedidos(pedidos);

        return cliente;
    }

//    public Status convertStatusValue(String value) {
//        if (value == null) {
//            return null;
//        }
//        return switch (value) {
//            case "Emitido" -> Status.EMITIDO;
//            case "Cancelado" -> Status.CANCELADO;
//            case "Salvo" -> Status.SALVO;
//            default -> throw new IllegalArgumentException("Status inválido: " + value);
//        };
//    }
}
