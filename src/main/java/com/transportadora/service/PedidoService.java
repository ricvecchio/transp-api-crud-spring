package com.transportadora.service;

import com.transportadora.dto.PedidoDTO;
import com.transportadora.dto.PedidoPaginacaoDTO;
import com.transportadora.dto.mapper.PedidoMapper;
import com.transportadora.model.auth.Pedido;
import com.transportadora.repository.auth.PedidoRepository;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.stream.Collectors;

@Validated
@Service
public class PedidoService {

    private final PedidoRepository pedidoRepository;

    private final PedidoMapper pedidoMapper;

    public PedidoService(PedidoRepository pedidoRepository, PedidoMapper pedidoMapper) {
        this.pedidoRepository = pedidoRepository;
        this.pedidoMapper = pedidoMapper;
    }

    public PedidoPaginacaoDTO list(@PositiveOrZero int page, @Positive @Max(100) int tamPagina) {
        Page<Pedido> pagePedido = pedidoRepository.findAll(PageRequest.of(page, tamPagina));
        List<PedidoDTO> pedidos = pagePedido.get().map(pedidoMapper::toDTO).collect(Collectors.toList());
        return new PedidoPaginacaoDTO(pedidos, pagePedido.getTotalElements(), pagePedido.getTotalPages());
    }

//    public ClienteDTO findById(@NotNull @Positive Long id) {
//        return clienteRepository.findById(id).map(clienteMapper::toDTO)
//                .orElseThrow(() -> new RecordNotFoundException(id));
//    }

    public PedidoDTO create(@Valid @NotNull PedidoDTO pedidoDTO) {
        return pedidoMapper.toDTO(pedidoRepository.save(pedidoMapper.toEntity(pedidoDTO)));
    }

//    public ClienteDTO update(@NotNull @Positive Long id, @Valid ClienteDTO clienteDTO) {
//        return clienteRepository.findById(id)
//                .map(recordFound -> {
//                    recordFound.setNome(clienteDTO.nome());
//                    recordFound.setCpfcnpj(clienteDTO.cpfcnpj());
//                    recordFound.setTelefone(clienteDTO.telefone());
//                    recordFound.setCelular(clienteDTO.celular());
//                    recordFound.setEmail(clienteDTO.email());
//                    recordFound.setCep(clienteDTO.cep());
//                    recordFound.setLogradouro(clienteDTO.logradouro());
//                    recordFound.setNumero(clienteDTO.numero());
//                    recordFound.setComplemento(clienteDTO.complemento());
//                    recordFound.setBairro(clienteDTO.bairro());
//                    recordFound.setCidade(clienteDTO.cidade());
//                    recordFound.setEstado(clienteDTO.estado());
//                    return clienteMapper.toDTO(clienteRepository.save(recordFound));
//                }).orElseThrow(() -> new RecordNotFoundException(id));
//    }

//    public void delete(@NotNull @Positive Long id) {
//        clienteRepository.delete(clienteRepository.findById(id)
//                .orElseThrow(() -> new RecordNotFoundException(id)));
//
//    }
}
