package com.transportadora.management.service;

import com.transportadora.management.dto.ClienteGastoDTO;
import com.transportadora.management.dto.DashboardDTO;
import com.transportadora.management.dto.GastoMensalDTO;
import com.transportadora.management.repository.PedidoRepository;
import com.transportadora.user.dto.UserDTO;
import com.transportadora.user.dto.UserPaginacaoDTO;
import com.transportadora.user.entities.User;
import com.transportadora.user.security.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.transportadora.user.repository.UserRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Validated
@Service
public class DashboardService {

    private final PedidoRepository pedidoRepository;

    public DashboardService(PedidoRepository pedidoRepository, UserRepository userRepository) {
        this.pedidoRepository = pedidoRepository;
        this.userRepository = userRepository;
    }
//    public DashboardService(PedidoRepository pedidoRepository) {
//        this.pedidoRepository = pedidoRepository;
//    }

//    public DashboardService(PedidoRepository pedidoRepository, PedidoRepository pedidoRepository1, UserRepository userRepository) {
//        this.pedidoRepository = pedidoRepository1;
//        this.userRepository = userRepository;
//    }

    @Cacheable(value = "Pedidos")
    public DashboardDTO dashboard(int page, int pageSize, String filter) {
        System.out.println("[DashboardService] Entrou aqui"); //EXCLUIR

//        List<Object[]> topClientesResult = pedidoRepository.findTop5ClientesPorMesNative();
//        List<Object[]> totaisPorMesResult = pedidoRepository.findTotaisPorMes();
        Map<String, List<ClienteGastoDTO>> clientesPorMes = new HashMap<>();

        List<Object[]> topClientesResult = List.of(); //EXLUIR
        List<Object[]> totaisPorMesResult = List.of(); //EXLUIR

//
        for (Object[] row : topClientesResult) {
            Long idCliente = ((Number) row[0]).longValue();
            Double precoTotal = ((Number) row[1]).doubleValue();
            Integer mes = ((Number) row[2]).intValue();
            Integer ano = ((Number) row[3]).intValue();

            String chave = ano + "-" + mes;
//            clientesPorMes
//                    .computeIfAbsent(chave, k -> new ArrayList<>())
//                    .add(new ClienteGastoDTO(idCliente, precoTotal));
        }

        List<GastoMensalDTO> resposta = new ArrayList<>();

        for (Object[] row : totaisPorMesResult) {
            Integer mes = ((Number) row[0]).intValue();
            Integer ano = ((Number) row[1]).intValue();
            Double totalMes = ((Number) row[2]).doubleValue();
            String chave = ano + "-" + mes;

//            List<ClienteGastoDTO> clientes = clientesPorMes.getOrDefault(chave, new ArrayList<>());
            List<ClienteGastoDTO> clientes = List.of(); //EXCLUIR

            resposta.add(new GastoMensalDTO(ano, mes, totalMes, clientes));
        }

        return new DashboardDTO(resposta);
    }

    //EXCLUIR TUDO ABAIXO
    private final UserRepository userRepository; // EXCLUIR
    @Cacheable(value = "users")
    public UserPaginacaoDTO list(int page, int pageSize, String filter) {
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

}

//@Validated
//@Service
//public class DashboardService {
//
//    private final PedidoRepository pedidoRepository;
//
//    public DashboardService(PedidoRepository pedidoRepository) {
//        this.pedidoRepository = pedidoRepository;
//    }
//
//    @Cacheable(value = "Pedidos")
//    public ResponseEntity<?> dashboard(int page, int pageSize, String filtro) {
//
//        System.out.println("[DashboardService] Entrou aqui");  // EXCLUIR
//
//
//        List<Object[]> topClientesResult = pedidoRepository.findTop5ClientesPorMesNative();
//        List<Object[]> totaisPorMesResult = pedidoRepository.findTotaisPorMes();
//        Map<String, List<ClienteGastoDTO>> clientesPorMes = new HashMap<>();
//
//        for (Object[] row : topClientesResult) {
//            Long idCliente = ((Number) row[0]).longValue();
//            Double precoTotal = ((Number) row[1]).doubleValue();
//            Integer mes = ((Number) row[2]).intValue();
//            Integer ano = ((Number) row[3]).intValue();
//
//            String chave = ano + "-" + mes;
//            clientesPorMes
//                    .computeIfAbsent(chave, k -> new ArrayList<>())
//                    .add(new ClienteGastoDTO(idCliente, precoTotal));
//        }
//
//        List<GastoMensalDTO> resposta = new ArrayList<>();
//
//        for (Object[] row : totaisPorMesResult) {
//            Integer mes = ((Number) row[0]).intValue();
//            Integer ano = ((Number) row[1]).intValue();
//            Double totalMes = ((Number) row[2]).doubleValue();
//            String chave = ano + "-" + mes;
//
//            List<ClienteGastoDTO> clientes = clientesPorMes.getOrDefault(chave, new ArrayList<>());
//
//            resposta.add(new GastoMensalDTO(ano, mes, totalMes, clientes));
//        }
//        return ResponseEntity.ok(resposta);
//    }
//
//}