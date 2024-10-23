package com.transportadora.dto.mapper;

import com.transportadora.dto.ClienteDTO;
import com.transportadora.model.Cliente;
import org.springframework.stereotype.Component;

@Component
public class ClienteMapper {

    public ClienteDTO toDTO(Cliente cliente) {
        if (cliente == null) {
            return null;
        }

        return new ClienteDTO(cliente.getId(), cliente.getNome(), cliente.getCpfcnpj(), cliente.getTelefone(), cliente.getCelular(),
                cliente.getEmail(), cliente.getCep(), cliente.getLogradouro(), cliente.getNumero(), cliente.getComplemento(),
                cliente.getBairro(), cliente.getCidade(), cliente.getEstado());
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

        return cliente;
    }

}
