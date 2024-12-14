package com.transportadora.repository;

import com.transportadora.model.Pedido;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {

    Page<Pedido> findAllByOrderByIdPedidoDesc(Pageable pageable);

    Page<Pedido> findByDataAtualizacaoPedidoBetweenOrderByIdPedidoDesc(
            LocalDateTime dataInicial,
            LocalDateTime dataFinal,
            Pageable pageable
    );

    Page<Pedido> findByDataAtualizacaoPedidoAfterOrderByIdPedidoDesc(
            LocalDateTime dataInicial,
            Pageable pageable
    );

    Page<Pedido> findByDataAtualizacaoPedidoBeforeOrderByIdPedidoDesc(
            LocalDateTime dataFinal,
            Pageable pageable
    );
}
