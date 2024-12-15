package com.transportadora.repository;

import com.transportadora.model.Pedido;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {

    Page<Pedido> findAllByOrderByIdPedidoDesc(Pageable pageable);

    @Query("SELECT p FROM Pedido p WHERE " +
            "(LOWER(p.nome) LIKE :clienteFiltro OR LOWER(p.razaoSocial) LIKE :clienteFiltro) " +
            "AND p.dataAtualizacaoPedido BETWEEN :dataInicial AND :dataFinal")
    Page<Pedido> findByClienteFiltroAndDataBetween(
            @Param("clienteFiltro") String clienteFiltro,
            @Param("dataInicial") LocalDateTime dataInicial,
            @Param("dataFinal") LocalDateTime dataFinal,
            Pageable pageable
    );

    @Query("SELECT p FROM Pedido p WHERE " +
            "(LOWER(p.nome) LIKE :clienteFiltro OR LOWER(p.razaoSocial) LIKE :clienteFiltro) " +
            "AND p.dataAtualizacaoPedido >= :dataInicial")
    Page<Pedido> findByClienteFiltroAndDataAfter(
            @Param("clienteFiltro") String clienteFiltro,
            @Param("dataInicial") LocalDateTime dataInicial,
            Pageable pageable
    );

    @Query("SELECT p FROM Pedido p WHERE " +
            "(LOWER(p.nome) LIKE :clienteFiltro OR LOWER(p.razaoSocial) LIKE :clienteFiltro) " +
            "AND p.dataAtualizacaoPedido <= :dataFinal")
    Page<Pedido> findByClienteFiltroAndDataBefore(
            @Param("clienteFiltro") String clienteFiltro,
            @Param("dataFinal") LocalDateTime dataFinal,
            Pageable pageable
    );

    @Query("SELECT p FROM Pedido p WHERE " +
            "(LOWER(p.nome) LIKE :clienteFiltro OR LOWER(p.razaoSocial) LIKE :clienteFiltro)")
    Page<Pedido> findByClienteFiltro(
            @Param("clienteFiltro") String clienteFiltro,
            Pageable pageable
    );

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
