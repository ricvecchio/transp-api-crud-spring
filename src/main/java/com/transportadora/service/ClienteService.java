package com.transportadora.service;

import com.transportadora.dto.ClienteDTO;
import com.transportadora.dto.ClientePaginacaoDTO;
import com.transportadora.dto.mapper.ClienteMapper;
import com.transportadora.exception.RecordNotFoundException;
import com.transportadora.model.Cliente;
import com.transportadora.repository.ClienteRepository;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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

    public ClientePaginacaoDTO list(@PositiveOrZero int page, @Positive @Max(100) int tamPagina) {
        Page<Cliente> pageCliente = clienteRepository.findAll(PageRequest.of(page, tamPagina));
        List<ClienteDTO> clientes = pageCliente.get().map(clienteMapper::toDTO).collect(Collectors.toList());
        return new ClientePaginacaoDTO(clientes, pageCliente.getTotalElements(), pageCliente.getTotalPages());
    }

    public ClienteDTO findById(@NotNull @Positive Long id) {
        return clienteRepository.findById(id).map(clienteMapper::toDTO)
                .orElseThrow(() -> new RecordNotFoundException(id));
    }

    public ClienteDTO create(@Valid @NotNull ClienteDTO clienteDTO) {
        return clienteMapper.toDTO(clienteRepository.save(clienteMapper.toEntity(clienteDTO)));
    }

    public ClienteDTO update(@NotNull @Positive Long id, @Valid ClienteDTO clienteDTO) {
        return clienteRepository.findById(id)
                .map(recordFound -> {
                    recordFound.setNome(clienteDTO.nome());
                    recordFound.setCpfcnpj(clienteDTO.cpfcnpj());
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
                    return clienteMapper.toDTO(clienteRepository.save(recordFound));
                }).orElseThrow(() -> new RecordNotFoundException(id));
    }

    public void delete(@NotNull @Positive Long id) {
        clienteRepository.delete(clienteRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException(id)));

    }
}
