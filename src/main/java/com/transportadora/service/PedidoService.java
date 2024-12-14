package com.transportadora.service;

import com.transportadora.dto.PedidoDTO;
import com.transportadora.dto.PedidoPaginacaoDTO;
import com.transportadora.dto.mapper.PedidoMapper;
import com.transportadora.exception.RecordNotFoundException;
import com.transportadora.model.Pedido;
import com.transportadora.repository.PedidoRepository;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDate;
import java.time.LocalTime;
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

    public PedidoPaginacaoDTO list(int page, int tamPagina, LocalDate dataInicial, LocalDate dataFinal) {
        Pageable pageable = PageRequest.of(page, tamPagina);

        Page<Pedido> pagePedido;
        if (dataInicial != null && dataFinal != null) {
            pagePedido = pedidoRepository.findByDataAtualizacaoPedidoBetweenOrderByIdPedidoDesc(
                    dataInicial.atStartOfDay(),
                    dataFinal.atTime(LocalTime.MAX),
                    pageable
            );
        } else if (dataInicial != null) {
            pagePedido = pedidoRepository.findByDataAtualizacaoPedidoAfterOrderByIdPedidoDesc(
                    dataInicial.atStartOfDay(),
                    pageable
            );
        } else if (dataFinal != null) {
            pagePedido = pedidoRepository.findByDataAtualizacaoPedidoBeforeOrderByIdPedidoDesc(
                    dataFinal.atTime(LocalTime.MAX),
                    pageable
            );
        } else {
            pagePedido = pedidoRepository.findAllByOrderByIdPedidoDesc(pageable);
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
                    recordFound.setStatus((pedidoMapper.convertStatusValue(pedidoDTO.status())));
                    recordFound.setDataAtualizacaoPedido(pedidoDTO.dataAtualizacaoPedido());
                    return pedidoMapper.toDTO(pedidoRepository.save(recordFound));
                }).orElseThrow(() -> new RecordNotFoundException(idPedido));
    }

    public void delete(@NotNull @Positive Long idPedido) {
        pedidoRepository.delete(pedidoRepository.findById(idPedido)
                .orElseThrow(() -> new RecordNotFoundException(idPedido)));
    }
}
