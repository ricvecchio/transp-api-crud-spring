package com.transportadora.user.service;

import com.transportadora.user.dto.UserDTO;
import com.transportadora.user.dto.UserPaginacaoDTO;
import com.transportadora.user.entities.Role;
import com.transportadora.user.entities.User;
import com.transportadora.user.repository.UserRepository;
import jakarta.validation.constraints.Max;
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
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserPaginacaoDTO list(@PositiveOrZero int page, @Positive @Max(100) int pageSize, String filter) {
        Pageable pageable = PageRequest.of(page, pageSize);
        Page<User> pageUser = userRepository.findAllByFilter(filter, pageable);

        List<UserDTO> users = pageUser.getContent().stream().map(user ->
                new UserDTO(
                        user.getId(),
                        user.getName(),
                        user.getEmail(),
                        user.getUsername(),
                        user.getPassword(),
                        user.getRoles().stream().map(Role::getName).collect(Collectors.joining(", "))
                )
        ).collect(Collectors.toList());
        return new UserPaginacaoDTO(users, pageUser.getTotalElements(), pageUser.getTotalPages());
    }

//    public List<ClienteDTO> buscarTrechoNome(String trechoBusca) {
//        List<Cliente> clientesEncontrados = clienteRepository.clientesPorTrecho(trechoBusca);
//
//        if (clientesEncontrados.isEmpty() == true) {
//            return null;
//        } else {
//            return converteDadosCliente(clientesEncontrados);
//        }
//    }

//    public ClienteDTO update(@NotNull @Positive Long idCliente, @Valid ClienteDTO clienteDTO) {
//        return clienteRepository.findById(idCliente)
//                .map(recordFound -> {
//                    recordFound.setNome(clienteDTO.nome());
//                    recordFound.setCpfCnpj(clienteDTO.cpfCnpj());
//                    recordFound.setPrecoLv10(clienteDTO.precoLv10());
//                    return clienteMapper.toDTO(clienteRepository.save(recordFound));
//                }).orElseThrow(() -> new RecordNotFoundException(idCliente));
//    }

//    public void delete(@NotNull @Positive Long idCliente) {
//        clienteRepository.delete(clienteRepository.findById(idCliente)
//                .orElseThrow(() -> new RecordNotFoundException(idCliente)));
//    }

}

