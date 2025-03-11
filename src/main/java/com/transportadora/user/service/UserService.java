package com.transportadora.user.service;

import com.transportadora.user.dto.UserDTO;
import com.transportadora.user.dto.UserPaginacaoDTO;
import com.transportadora.user.entities.User;
import com.transportadora.user.repository.UserRepository;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
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

    @Cacheable("users")
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

    @CacheEvict("users")
    public UserDTO update(@NotNull String idUser, @Valid UserDTO userDTO) {
        return userRepository.findById(UUID.fromString(idUser))
                .map(recordFound -> {
                    recordFound.setPermission(userDTO.permission());
                    User updatedUser = userRepository.save(recordFound);
                    return convertToDTO(updatedUser);
                }).orElseThrow(() -> new RuntimeException("Registro não encontrado com o idUser: " + idUser));
    }

    private UserDTO convertToDTO(User user) {
        return new UserDTO(user.getIdUser(), user.getUsername(), user.getEmail(), user.getUsername(), user.getPassword(), user.getPermission());
    }

    @CacheEvict("users")
    public void delete(@NotNull String idUser) {
        userRepository.delete(userRepository.findById(UUID.fromString(idUser))
                .orElseThrow(() -> new RuntimeException("Registro não encontrado com o idUser: " + idUser)));
    }

}

