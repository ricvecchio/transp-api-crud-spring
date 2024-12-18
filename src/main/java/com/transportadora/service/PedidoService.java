package com.transportadora.service;

import com.transportadora.dto.PedidoDTO;
import com.transportadora.dto.PedidoPaginacaoDTO;
import com.transportadora.dto.mapper.PedidoMapper;
import com.transportadora.exception.RecordNotFoundException;
import com.transportadora.model.Pedido;
import com.transportadora.repository.PedidoRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.time.*;
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

    public PedidoPaginacaoDTO list(int page, int tamPagina, String clienteFiltro, LocalDate dataInicial, LocalDate dataFinal, String statusFiltro) {
        Pageable pageable = PageRequest.of(page, tamPagina);
        Page<Pedido> pagePedido;

        String clienteFiltroLike = (clienteFiltro != null && !clienteFiltro.isEmpty())
                ? "%" + clienteFiltro.toLowerCase() + "%"
                : null;

        if (clienteFiltroLike != null && dataInicial != null && dataFinal != null && statusFiltro != null) {
            pagePedido = pedidoRepository.findByClienteFiltroAndDataBetweenAndStatus(
                    clienteFiltroLike, dataInicial.atStartOfDay(), dataFinal.atTime(LocalTime.MAX), statusFiltro, pageable);

        } else if (clienteFiltroLike != null && dataInicial != null && statusFiltro != null) {
            pagePedido = pedidoRepository.findByClienteFiltroAndDataAfterAndStatus(
                    clienteFiltroLike, dataInicial.atStartOfDay(), statusFiltro, pageable);

        } else if (clienteFiltroLike != null && dataFinal != null && statusFiltro != null) {
            pagePedido = pedidoRepository.findByClienteFiltroAndDataBeforeAndStatus(
                    clienteFiltroLike, dataFinal.atTime(LocalTime.MAX), statusFiltro, pageable);

        } else if (clienteFiltroLike != null && statusFiltro != null) {
            pagePedido = pedidoRepository.findByClienteFiltroAndStatus(clienteFiltroLike, statusFiltro, pageable);

        } else if (dataInicial != null && dataFinal != null && statusFiltro != null) {
            pagePedido = pedidoRepository.findByDataBetweenAndStatus(
                    dataInicial.atStartOfDay(), dataFinal.atTime(LocalTime.MAX), statusFiltro, pageable);

        } else if (dataInicial != null && statusFiltro != null) {
            pagePedido = pedidoRepository.findByDataAfterAndStatus(dataInicial.atStartOfDay(), statusFiltro, pageable);

        } else if (dataFinal != null && statusFiltro != null) {
            pagePedido = pedidoRepository.findByDataBeforeAndStatus(dataFinal.atTime(LocalTime.MAX), statusFiltro, pageable);

        } else if (statusFiltro != null) {
            pagePedido = pedidoRepository.findByStatus(statusFiltro, pageable);

        } else {
            return list(page, tamPagina, clienteFiltro, dataInicial, dataFinal, null);
        }

        List<PedidoDTO> pedidos = pagePedido.getContent().stream()
                .map(pedidoMapper::toDTO)
                .collect(Collectors.toList());

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
                    recordFound.setNome(pedidoDTO.nome());
                    recordFound.setCpfCnpj(pedidoDTO.cpfCnpj());
                    recordFound.setRazaoSocial(pedidoDTO.razaoSocial());
                    recordFound.setIdCliente(pedidoDTO.idCliente());
                    recordFound.setTelefone(pedidoDTO.telefone());
                    recordFound.setCelular(pedidoDTO.celular());
                    recordFound.setEmail(pedidoDTO.email());
                    recordFound.setCep(pedidoDTO.cep());
                    recordFound.setLogradouro(pedidoDTO.logradouro());
                    recordFound.setNumero(pedidoDTO.numero());
                    recordFound.setComplemento(pedidoDTO.complemento());
                    recordFound.setBairro(pedidoDTO.bairro());
                    recordFound.setCidade(pedidoDTO.cidade());
                    recordFound.setEstado(pedidoDTO.estado());
                    recordFound.setTipoPgto(pedidoDTO.tipoPgto());
                    recordFound.setCepEntrega(pedidoDTO.cepEntrega());
                    recordFound.setLogradouroEntrega(pedidoDTO.logradouroEntrega());
                    recordFound.setNumeroEntrega(pedidoDTO.numeroEntrega());
                    recordFound.setComplementoEntrega(pedidoDTO.complementoEntrega());
                    recordFound.setBairroEntrega(pedidoDTO.bairroEntrega());
                    recordFound.setCidadeEntrega(pedidoDTO.cidadeEntrega());
                    recordFound.setEstadoEntrega(pedidoDTO.estadoEntrega());
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
                    recordFound.setAjudante(pedidoDTO.ajudante());
                    recordFound.setObservacao(pedidoDTO.observacao());
                    recordFound.setStatus(pedidoDTO.status());
                    recordFound.setDataAtualizacaoPedido(pedidoDTO.dataAtualizacaoPedido());
                    return pedidoMapper.toDTO(pedidoRepository.save(recordFound));
                }).orElseThrow(() -> new RecordNotFoundException(idPedido));
    }

    public void cancel(@NotNull @Positive Long idPedido) {
        Pedido pedido = pedidoRepository.findById(idPedido)
                .orElseThrow(() -> new RecordNotFoundException(idPedido));

        pedido.setStatus("Cancelado");
        LocalDateTime localDateTime = LocalDateTime.now();
        pedido.setDataAtualizacaoPedido(localDateTime);
        pedidoRepository.save(pedido);
    }
}
