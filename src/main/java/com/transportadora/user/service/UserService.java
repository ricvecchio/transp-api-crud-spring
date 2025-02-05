package com.transportadora.user.service;

import com.transportadora.management.dto.ClienteDTO;
import com.transportadora.management.exception.RecordNotFoundException;
import com.transportadora.management.model.Cliente;
import com.transportadora.user.dto.UserDTO;
import com.transportadora.user.dto.UserPaginacaoDTO;
import com.transportadora.user.entities.User;
import com.transportadora.user.repository.UserRepository;
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
import java.util.UUID;
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
                        user.getIdUser(),
                        user.getName(),
                        user.getEmail(),
                        user.getUsername(),
                        user.getPassword(),
                        user.getPermission()
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

    public UserDTO update(@NotNull String idUser, @Valid UserDTO userDTO) {
        System.out.println("AQUI - update Service idUser: " + idUser);
        return userRepository.findById(UUID.fromString(idUser))
                .map(recordFound -> {
                    recordFound.setPermission(userDTO.permission());
                    User updatedUser = userRepository.save(recordFound);
                    return convertToDTO(updatedUser);
                }).orElseThrow(() -> new RuntimeException("Registro não encontrado com o id: " + idUser));
    }

    private UserDTO convertToDTO(User user) {
        return new UserDTO(user.getIdUser(), user.getUsername(), user.getEmail(), user.getUsername(), user.getPassword(), user.getPermission());
    }

//    public void delete(@NotNull @Positive Long idCliente) {
//        clienteRepository.delete(clienteRepository.findById(idCliente)
//                .orElseThrow(() -> new RecordNotFoundException(idCliente)));
//    }

}

