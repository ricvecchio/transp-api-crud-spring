package com.transportadora.management.service;

import com.transportadora.management.dto.ClienteGastoDTO;
import com.transportadora.management.dto.DashboardDTO;
import com.transportadora.management.dto.GastoMensalDTO;
import com.transportadora.management.repository.PedidoRepository;
import com.transportadora.user.dto.UserDTO;
import com.transportadora.user.dto.UserPaginacaoDTO;
import com.transportadora.user.entities.User;
import com.transportadora.user.repository.UserRepository;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.*;
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

    @Cacheable(value = "Pedidos")
    public DashboardDTO dashboard(int page, int pageSize, String filter) {
        System.out.println("[DashboardService] Entrou aqui /dashboard");

        List<Object[]> topClientesResult = pedidoRepository.findTop5ClientesPorMesNative();
        List<Object[]> totaisPorMesResult = pedidoRepository.findTotaisPorMes();
        Map<String, List<ClienteGastoDTO>> clientesPorMes = new HashMap<>();

        // Processa topClientesResult
        for (Object[] row : topClientesResult) {
            if (row == null || row.length < 4) {
                System.out.println("[DashboardService] Linha inválida em topClientesResult: " + Arrays.toString(row));
                continue;
            }
            try {
                Long idCliente = Long.parseLong(row[0].toString());
                Double precoTotal = Double.parseDouble(row[1].toString());
                Integer mes = Integer.parseInt(row[2].toString());
                Integer ano = Integer.parseInt(row[3].toString());

                String chave = ano + "-" + mes;
                clientesPorMes
                        .computeIfAbsent(chave, k -> new ArrayList<>())
                        .add(new ClienteGastoDTO(idCliente, precoTotal));
            } catch (Exception e) {
                System.out.println("[DashboardService] Erro ao processar linha topClientesResult: " + Arrays.toString(row));
                e.printStackTrace();
            }
        }

        List<GastoMensalDTO> resposta = new ArrayList<>();
        // Processa totaisPorMesResult
        for (Object[] row : totaisPorMesResult) {
            if (row == null || row.length < 3) {
                System.out.println("[DashboardService] Linha inválida em totaisPorMesResult: " + Arrays.toString(row));
                continue;
            }
            try {
                Integer mes = Integer.parseInt(row[0].toString());
                Integer ano = Integer.parseInt(row[1].toString());
                Double totalMes = Double.parseDouble(row[2].toString());

                String chave = ano + "-" + mes;
                List<ClienteGastoDTO> clientes = clientesPorMes.getOrDefault(chave, new ArrayList<>());

                resposta.add(new GastoMensalDTO(ano, mes, totalMes, clientes));
            } catch (Exception e) {
                System.out.println("[DashboardService] Erro ao processar linha totaisPorMesResult: " + Arrays.toString(row));
                e.printStackTrace();
            }
        }

        return new DashboardDTO(resposta);
    }

//    @Cacheable(value = "Pedidos")
//    public DashboardDTO dashboard(int page, int pageSize, String filter) {
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
//        return new DashboardDTO(resposta);
//    }

    //EXCLUIR TUDO ABAIXO
    private final UserRepository userRepository; // EXCLUIR
    @Cacheable(value = "users")
    public UserPaginacaoDTO list(int page, int pageSize, String filter) {
        System.out.println("[DashboardService] Entrou aqui /list"); //EXCLUIR
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