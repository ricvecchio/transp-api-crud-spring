package com.transportadora.management.service;

import com.transportadora.management.dto.ClienteDTO;
import com.transportadora.management.dto.ClientePaginacaoDTO;
import com.transportadora.management.dto.mapper.ClienteMapper;
import com.transportadora.management.exception.RecordNotFoundException;
import com.transportadora.management.repository.ClienteRepository;
import com.transportadora.management.model.Cliente;
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
public class ClienteService {

    private final ClienteRepository clienteRepository;

    private final ClienteMapper clienteMapper;

    public ClienteService(ClienteRepository clienteRepository, ClienteMapper clienteMapper) {
        this.clienteRepository = clienteRepository;
        this.clienteMapper = clienteMapper;
    }

    public ClientePaginacaoDTO list(@PositiveOrZero int page, @Positive @Max(100) int tamPagina, String filter) {
        Pageable pageable = PageRequest.of(page, tamPagina);
        Page<Cliente> pageCliente;

        if (filter.isBlank()) {
            pageCliente = clienteRepository.findAllByOrderByIdClienteDesc(pageable);
        } else {
            pageCliente = clienteRepository.findAllByFilter(filter, pageable);
        }

        List<ClienteDTO> clientes = pageCliente.get().map(clienteMapper::toDTO).collect(Collectors.toList());

        return new ClientePaginacaoDTO(clientes, pageCliente.getTotalElements(), pageCliente.getTotalPages());
    }

    public ClienteDTO findById(Long idCliente) {
        return clienteRepository.findById(idCliente).map(clienteMapper::toDTO).orElse(null);
    }

    public List<ClienteDTO> buscarTrechoNome(String trechoBusca) {
        List<Cliente> clientesEncontrados = clienteRepository.clientesPorTrecho(trechoBusca);
        System.out.println("Trecho da Busca: " + trechoBusca);
        System.out.println("clientesEncontrados: " + clientesEncontrados);
        if (clientesEncontrados.isEmpty() == true) {
            return null;
        } else {
            return converteDadosCliente(clientesEncontrados);
        }
    }

    public ClienteDTO create(@Valid @NotNull ClienteDTO clienteDTO) {
        return clienteMapper.toDTO(clienteRepository.save(clienteMapper.toEntity(clienteDTO)));
    }

    public ClienteDTO update(@NotNull @Positive Long idCliente, @Valid ClienteDTO clienteDTO) {
        return clienteRepository.findById(idCliente)
                .map(recordFound -> {
                    recordFound.setNome(clienteDTO.nome());
                    recordFound.setCpfCnpj(clienteDTO.cpfCnpj());
                    recordFound.setRazaoSocial(clienteDTO.razaoSocial());
                    recordFound.setTelefone(clienteDTO.telefone());
                    recordFound.setCelular(clienteDTO.celular());
                    recordFound.setEmail(clienteDTO.email());
                    recordFound.setCep(clienteDTO.cep());
                    recordFound.setLogradouro(clienteDTO.logradouro());
                    recordFound.setNumero(clienteDTO.numero());
                    recordFound.setComplemento(clienteDTO.complemento());
                    recordFound.setBairro(clienteDTO.bairro());
                    recordFound.setCidade(clienteDTO.cidade());
                    recordFound.setEstado(clienteDTO.estado());
                    recordFound.setTipoPgto(clienteDTO.tipoPgto());
                    recordFound.setCepEntrega(clienteDTO.cepEntrega());
                    recordFound.setLogradouroEntrega(clienteDTO.logradouroEntrega());
                    recordFound.setNumeroEntrega(clienteDTO.numeroEntrega());
                    recordFound.setComplementoEntrega(clienteDTO.complementoEntrega());
                    recordFound.setBairroEntrega(clienteDTO.bairroEntrega());
                    recordFound.setCidadeEntrega(clienteDTO.cidadeEntrega());
                    recordFound.setEstadoEntrega(clienteDTO.estadoEntrega());
                    recordFound.setSfobras(clienteDTO.sfobras());
                    recordFound.setCno(clienteDTO.cno());
                    recordFound.setIe(clienteDTO.ie());
                    recordFound.setMangueira(clienteDTO.mangueira());
                    recordFound.setPrecoCx5(clienteDTO.precoCx5());
                    recordFound.setPrecoCx10(clienteDTO.precoCx10());
                    recordFound.setPrecoCx15(clienteDTO.precoCx15());
                    recordFound.setPrecoLv5(clienteDTO.precoLv5());
                    recordFound.setPrecoLv10(clienteDTO.precoLv10());
                    recordFound.setPrecoLv15(clienteDTO.precoLv15());
                    recordFound.setObservacao(clienteDTO.observacao());
                    recordFound.setDataAtualizacaoCliente(clienteDTO.dataAtualizacaoCliente());
                    return clienteMapper.toDTO(clienteRepository.save(recordFound));
                }).orElseThrow(() -> new RecordNotFoundException(idCliente));
    }

    public void delete(@NotNull @Positive Long idCliente) {
        clienteRepository.delete(clienteRepository.findById(idCliente)
                .orElseThrow(() -> new RecordNotFoundException(idCliente)));
    }

    private List<ClienteDTO> converteDadosCliente(List<Cliente> cliente) {
        return cliente.stream()
                .map(c -> new ClienteDTO(
                        c.getIdCliente(),
                        c.getNome(),
                        c.getCpfCnpj(),
                        c.getRazaoSocial(),
                        c.getTelefone(),
                        c.getCelular(),
                        c.getEmail(),
                        c.getCep(),
                        c.getLogradouro(),
                        c.getNumero(),
                        c.getComplemento(),
                        c.getBairro(),
                        c.getCidade(),
                        c.getEstado(),
                        c.getTipoPgto(),
                        c.getCepEntrega(),
                        c.getLogradouroEntrega(),
                        c.getNumeroEntrega(),
                        c.getComplementoEntrega(),
                        c.getBairroEntrega(),
                        c.getCidadeEntrega(),
                        c.getEstadoEntrega(),
                        c.getSfobras(),
                        c.getCno(),
                        c.getIe(),
                        c.getMangueira(),
                        c.getPrecoCx5(),
                        c.getPrecoCx10(),
                        c.getPrecoCx15(),
                        c.getPrecoLv5(),
                        c.getPrecoLv10(),
                        c.getPrecoLv15(),
                        c.getObservacao(),
                        c.getDataAtualizacaoCliente()))
                .collect(Collectors.toList());
    }
}

