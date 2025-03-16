package com.transportadora.management.service;

import com.transportadora.management.dto.PedidoDTO;
import com.transportadora.management.dto.PedidoPaginacaoDTO;
import com.transportadora.management.dto.mapper.PedidoMapper;
import com.transportadora.management.exception.RecordNotFoundException;
import com.transportadora.management.model.Pedido;
import com.transportadora.management.repository.PedidoRepository;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Validated
@Service
public class MetricasService {

    @Cacheable(value = "Pedidos")
    public ResponseEntity<?> metricas(int page, int tamPagina, String filtro) {
        return ResponseEntity.ok(Collections.singletonMap("message", "Em desenvolvimento!"));
    }

}
