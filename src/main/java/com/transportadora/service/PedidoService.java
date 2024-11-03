package com.transportadora.service;

import com.transportadora.dto.PedidoDTO;
import com.transportadora.dto.PedidoPaginacaoDTO;
import com.transportadora.dto.mapper.PedidoMapper;
import com.transportadora.exception.RecordNotFoundException;
import com.transportadora.model.Pedido;
import com.transportadora.repository.PedidoRepository;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
        Pageable pageable = PageRequest.of(page, tamPagina);
        Page<Pedido> pagePedido = pedidoRepository.findAllByOrderByIdPedidoDesc(pageable);
        List<PedidoDTO> pedidos = pagePedido.get().map(pedidoMapper::toDTO).collect(Collectors.toList());
        return new PedidoPaginacaoDTO(pedidos, pagePedido.getTotalElements(), pagePedido.getTotalPages());
    }

    public PedidoDTO findById(@NotNull @Positive Long idPedido) {
        return pedidoRepository.findById(idPedido).map(pedidoMapper::toDTO)
                .orElseThrow(() -> new RecordNotFoundException(idPedido));
    }

    public PedidoDTO create(@Valid @NotNull PedidoDTO pedidoDTO) {
        return pedidoMapper.toDTO(pedidoRepository.save(pedidoMapper.toEntity(pedidoDTO)));
    }

    public PedidoDTO update(@NotNull @Positive Long idPedido, @Valid PedidoDTO pedidoDTO) {
        return pedidoRepository.findById(idPedido)
                .map(recordFound -> {
                    recordFound.setNomePedido(pedidoDTO.nomePedido());
                    recordFound.setRazaoSocial(pedidoDTO.razaoSocial());
                    recordFound.setCpfcnpjPedido(pedidoDTO.cpfcnpjPedido());
                    recordFound.setTipoPgto(pedidoDTO.tipoPgto());
                    recordFound.setCepPedido(pedidoDTO.cepPedido());
                    recordFound.setLogradouroPedido(pedidoDTO.logradouroPedido());
                    recordFound.setNumeroPedido(pedidoDTO.numeroPedido());
                    recordFound.setComplementoPedido(pedidoDTO.complementoPedido());
                    recordFound.setBairroPedido(pedidoDTO.bairroPedido());
                    recordFound.setCidadePedido(pedidoDTO.cidadePedido());
                    recordFound.setEstadoPedido(pedidoDTO.estadoPedido());
                    recordFound.setSfobras(pedidoDTO.sfobras());
                    recordFound.setCno(pedidoDTO.cno());
                    recordFound.setIe(pedidoDTO.ie());
                    recordFound.setMangueira(pedidoDTO.mangueira());
                    recordFound.setVolume(pedidoDTO.volume());
                    recordFound.setPrecoCx5(pedidoDTO.precoCx5());
                    recordFound.setPrecoCx10(pedidoDTO.precoCx10());
                    recordFound.setPrecoCx15(pedidoDTO.precoCx15());
                    recordFound.setPrecoLv5(pedidoDTO.precoLv5());
                    recordFound.setPrecoLv10(pedidoDTO.precoLv10());
                    recordFound.setPrecoLv15(pedidoDTO.precoLv15());
                    recordFound.setAjudanteHora(pedidoDTO.ajudanteHora());
                    recordFound.setObservacao(pedidoDTO.observacao());
                    recordFound.setStatus((pedidoMapper.convertStatusValue(pedidoDTO.status())));
                    recordFound.setIdCliente(pedidoDTO.idCliente());
                    return pedidoMapper.toDTO(pedidoRepository.save(recordFound));
                }).orElseThrow(() -> new RecordNotFoundException(idPedido));
    }

    public void delete(@NotNull @Positive Long idPedido) {
        pedidoRepository.delete(pedidoRepository.findById(idPedido)
                .orElseThrow(() -> new RecordNotFoundException(idPedido)));

    }
}
