package com.transportadora.management.repository;

import com.transportadora.management.model.Pedido;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {

    Page<Pedido> findAllByOrderByIdPedidoDesc(Pageable pageable);

    @Query("SELECT p FROM Pedido p WHERE " +
            "CAST(p.idPedido AS string) LIKE CONCAT('%', :clienteFiltro, '%') OR " +
            "CAST(p.idCliente AS string) LIKE CONCAT('%', :clienteFiltro, '%') OR " +
            "(LOWER(CAST(FUNCTION('unaccent', p.nome) AS string)) LIKE CONCAT('%', LOWER(CAST(FUNCTION('unaccent', :clienteFiltro) AS string)), '%') OR " +
            "LOWER(CAST(FUNCTION('unaccent', p.razaoSocial) AS string)) LIKE CONCAT('%', LOWER(CAST(FUNCTION('unaccent', :clienteFiltro) AS string)), '%') OR " +
            "LOWER(CAST(FUNCTION('unaccent', p.logradouroEntrega) AS string)) LIKE CONCAT('%', LOWER(CAST(FUNCTION('unaccent', :clienteFiltro) AS string)), '%') OR " +
            "REPLACE(REPLACE(REPLACE(p.cpfCnpj, '.', ''), '-', ''), '/', '') LIKE CONCAT('%', REPLACE(REPLACE(REPLACE(:clienteFiltro, '.', ''), '-', ''), '/', ''), '%') OR " +
            "CAST(p.idCliente AS string) LIKE :clienteFiltro) " +
            "AND p.dataAtualizacaoPedido BETWEEN :dataInicial AND :dataFinal " +
            "AND p.status = :statusFiltro " +
            "ORDER BY p.idPedido DESC")
    Page<Pedido> findByClienteFiltroAndDataBetweenAndStatus(
            @Param("clienteFiltro") String clienteFiltro,
            @Param("dataInicial") LocalDateTime dataInicial,
            @Param("dataFinal") LocalDateTime dataFinal,
            @Param("statusFiltro") String statusFiltro,
            Pageable pageable
    );

    @Query("SELECT p FROM Pedido p WHERE " +
            "CAST(p.idPedido AS string) LIKE CONCAT('%', :clienteFiltro, '%') OR " +
            "CAST(p.idCliente AS string) LIKE CONCAT('%', :clienteFiltro, '%') OR " +
            "(LOWER(CAST(FUNCTION('unaccent', p.nome) AS string)) LIKE CONCAT('%', LOWER(CAST(FUNCTION('unaccent', :clienteFiltro) AS string)), '%') OR " +
            "LOWER(CAST(FUNCTION('unaccent', p.razaoSocial) AS string)) LIKE CONCAT('%', LOWER(CAST(FUNCTION('unaccent', :clienteFiltro) AS string)), '%') OR " +
            "LOWER(CAST(FUNCTION('unaccent', p.logradouroEntrega) AS string)) LIKE CONCAT('%', LOWER(CAST(FUNCTION('unaccent', :clienteFiltro) AS string)), '%') OR " +
            "REPLACE(REPLACE(REPLACE(p.cpfCnpj, '.', ''), '-', ''), '/', '') LIKE CONCAT('%', REPLACE(REPLACE(REPLACE(:clienteFiltro, '.', ''), '-', ''), '/', ''), '%') OR " +
            "CAST(p.idCliente AS string) LIKE :clienteFiltro) " +
            "AND p.dataAtualizacaoPedido >= :dataInicial " +
            "AND p.status = :statusFiltro " +
            "ORDER BY p.idPedido DESC")
    Page<Pedido> findByClienteFiltroAndDataAfterAndStatus(
            @Param("clienteFiltro") String clienteFiltro,
            @Param("dataInicial") LocalDateTime dataInicial,
            @Param("statusFiltro") String statusFiltro,
            Pageable pageable
    );

    @Query("SELECT p FROM Pedido p WHERE " +
            "CAST(p.idPedido AS string) LIKE CONCAT('%', :clienteFiltro, '%') OR " +
            "CAST(p.idCliente AS string) LIKE CONCAT('%', :clienteFiltro, '%') OR " +
            "(LOWER(CAST(FUNCTION('unaccent', p.nome) AS string)) LIKE CONCAT('%', LOWER(CAST(FUNCTION('unaccent', :clienteFiltro) AS string)), '%') OR " +
            "LOWER(CAST(FUNCTION('unaccent', p.razaoSocial) AS string)) LIKE CONCAT('%', LOWER(CAST(FUNCTION('unaccent', :clienteFiltro) AS string)), '%') OR " +
            "LOWER(CAST(FUNCTION('unaccent', p.logradouroEntrega) AS string)) LIKE CONCAT('%', LOWER(CAST(FUNCTION('unaccent', :clienteFiltro) AS string)), '%') OR " +
            "REPLACE(REPLACE(REPLACE(p.cpfCnpj, '.', ''), '-', ''), '/', '') LIKE CONCAT('%', REPLACE(REPLACE(REPLACE(:clienteFiltro, '.', ''), '-', ''), '/', ''), '%') OR " +
            "CAST(p.idCliente AS string) LIKE :clienteFiltro) " +
            "AND p.dataAtualizacaoPedido <= :dataFinal " +
            "AND p.status = :statusFiltro " +
            "ORDER BY p.idPedido DESC")
    Page<Pedido> findByClienteFiltroAndDataBeforeAndStatus(
            @Param("clienteFiltro") String clienteFiltro,
            @Param("dataFinal") LocalDateTime dataFinal,
            @Param("statusFiltro") String statusFiltro,
            Pageable pageable
    );

    @Query("SELECT p FROM Pedido p WHERE " +
            "CAST(p.idPedido AS string) LIKE CONCAT('%', :clienteFiltro, '%') OR " +
            "CAST(p.idCliente AS string) LIKE CONCAT('%', :clienteFiltro, '%') OR " +
            "(LOWER(CAST(FUNCTION('unaccent', p.nome) AS string)) LIKE CONCAT('%', LOWER(CAST(FUNCTION('unaccent', :clienteFiltro) AS string)), '%') OR " +
            "LOWER(CAST(FUNCTION('unaccent', p.razaoSocial) AS string)) LIKE CONCAT('%', LOWER(CAST(FUNCTION('unaccent', :clienteFiltro) AS string)), '%') OR " +
            "LOWER(CAST(FUNCTION('unaccent', p.logradouroEntrega) AS string)) LIKE CONCAT('%', LOWER(CAST(FUNCTION('unaccent', :clienteFiltro) AS string)), '%') OR " +
            "REPLACE(REPLACE(REPLACE(p.cpfCnpj, '.', ''), '-', ''), '/', '') LIKE CONCAT('%', REPLACE(REPLACE(REPLACE(:clienteFiltro, '.', ''), '-', ''), '/', ''), '%') OR " +
            "CAST(p.idCliente AS string) LIKE :clienteFiltro) " +
            "AND p.status = :statusFiltro " +
            "ORDER BY p.idPedido DESC")
    Page<Pedido> findByClienteFiltroAndStatus(
            @Param("clienteFiltro") String clienteFiltro,
            @Param("statusFiltro") String statusFiltro,
            Pageable pageable
    );

    @Query("SELECT p FROM Pedido p WHERE " +
            "p.dataAtualizacaoPedido BETWEEN :dataInicial AND :dataFinal " +
            "AND p.status = :statusFiltro " +
            "ORDER BY p.idPedido DESC")
    Page<Pedido> findByDataBetweenAndStatus(
            @Param("dataInicial") LocalDateTime dataInicial,
            @Param("dataFinal") LocalDateTime dataFinal,
            @Param("statusFiltro") String statusFiltro,
            Pageable pageable
    );

    @Query("SELECT p FROM Pedido p WHERE " +
            "p.dataAtualizacaoPedido >= :dataInicial " +
            "AND p.status = :statusFiltro " +
            "ORDER BY p.idPedido DESC")
    Page<Pedido> findByDataAfterAndStatus(
            @Param("dataInicial") LocalDateTime dataInicial,
            @Param("statusFiltro") String statusFiltro,
            Pageable pageable
    );

    @Query("SELECT p FROM Pedido p WHERE " +
            "p.dataAtualizacaoPedido <= :dataFinal " +
            "AND p.status = :statusFiltro " +
            "ORDER BY p.idPedido DESC")
    Page<Pedido> findByDataBeforeAndStatus(
            @Param("dataFinal") LocalDateTime dataFinal,
            @Param("statusFiltro") String statusFiltro,
            Pageable pageable
    );

    @Query(value = "SELECT p FROM Pedido p WHERE p.status = :statusFiltro ORDER BY p.idPedido DESC",
            countQuery = "SELECT COUNT(p) FROM Pedido p WHERE p.status = :statusFiltro")
    Page<Pedido> findByStatus(@Param("statusFiltro") String statusFiltro, Pageable pageable);

    @Query("SELECT p FROM Pedido p WHERE p.idCliente = :idCliente ORDER BY p.dataAtualizacaoPedido DESC")
    List<Pedido> findTopByIdClienteOrderByDataAtualizacaoPedidoDesc(
            @Param("idCliente") Long idCliente,
            Pageable pageable);

//    @Query(value = "SELECT id_cliente AS idCliente, preco_total AS precoTotal, mes_total AS mesTotal, ano_total AS anoTotal " +
//            "FROM ( " +
//            "    SELECT " +
//            "        p.id_cliente, " +
//            "        COALESCE(SUM(CAST((REPLACE(REPLACE(REPLACE(REGEXP_REPLACE(p.preco_final, E'[\\s\\u00A0]', '', 'g'), 'R$', ''), '.', ''), ',', '.') AS DOUBLE PRECISION)), 0) AS preco_total, " +
//            "        EXTRACT(MONTH FROM p.data_atualizacao_pedido) AS mes_total, " +
//            "        EXTRACT(YEAR FROM p.data_atualizacao_pedido) AS ano_total, " +
//            "        ROW_NUMBER() OVER ( " +
//            "            PARTITION BY EXTRACT(MONTH FROM p.data_atualizacao_pedido) " +
//            "            ORDER BY COALESCE(SUM(CAST(REPLACE(REPLACE(REPLACE(REGEXP_REPLACE(p.preco_final, E'[\\s\\u00A0]', '', 'g'), 'R$', ''), '.', ''), ',', '.') AS DOUBLE PRECISION)), 0) DESC " +
//            "        ) AS ranking " +
//            "    FROM pedidos p " +
//            "    WHERE EXTRACT(YEAR FROM p.data_atualizacao_pedido) = 2025 " +
//            "    GROUP BY p.id_cliente, mes_total, ano_total " +
//            ") AS sub " +
//            "WHERE sub.ranking <= 5 " +
//            "ORDER BY sub.ano_total, sub.mes_total, sub.preco_total DESC",
//            nativeQuery = true)
//    List<Object[]> findTop5ClientesPorMesNative();
    @Query(value = "SELECT id_cliente AS idCliente, preco_total AS precoTotal, mes_total AS mesTotal, ano_total AS anoTotal " +
            "FROM ( " +
            "    SELECT " +
            "        p.id_cliente, " +
            "        COALESCE(SUM(CAST((REPLACE(REPLACE(REPLACE(REGEXP_REPLACE(p.preco_final, E'[\\s\\u00A0]', '', 'g'), 'R$', ''), '.', ''), ',', '.') AS DOUBLE PRECISION)), 0) AS preco_total, " +
            "        EXTRACT(MONTH FROM p.data_atualizacao_pedido) AS mes_total, " +
            "        EXTRACT(YEAR FROM p.data_atualizacao_pedido) AS ano_total, " +
            "        ROW_NUMBER() OVER ( " +
            "            PARTITION BY EXTRACT(MONTH FROM p.data_atualizacao_pedido) " +
            "            ORDER BY COALESCE(SUM(CAST(REPLACE(REPLACE(REPLACE(REGEXP_REPLACE(p.preco_final, E'[\\s\\u00A0]', '', 'g'), 'R$', ''), '.', ''), ',', '.') AS DOUBLE PRECISION)), 0) DESC " +
            "        ) AS ranking " +
            "    FROM \"Pedidos\" p " +  // ATENÇÃO: Aspas se a tabela tem P maiúsculo
            "    WHERE EXTRACT(YEAR FROM p.data_atualizacao_pedido) = 2025 " +
            "    GROUP BY p.id_cliente, " +
            "             EXTRACT(MONTH FROM p.data_atualizacao_pedido), " +
            "             EXTRACT(YEAR FROM p.data_atualizacao_pedido) " +
            ") AS sub " +
            "WHERE sub.ranking <= 5 " +
            "ORDER BY sub.ano_total, sub.mes_total, sub.preco_total DESC", nativeQuery = true)
    List<Object[]> findTop5ClientesPorMesNative();

    @Query(value = "SELECT " +
            "EXTRACT(MONTH FROM p.data_atualizacao_pedido) AS mes_total, " +
            "EXTRACT(YEAR FROM p.data_atualizacao_pedido) AS ano_total, " +
            "COALESCE(SUM(CAST(REPLACE(REPLACE(REPLACE(REGEXP_REPLACE(p.preco_final, E'[\\s\\u00A0]', '', 'g'), 'R$', ''), '.', ''), ',', '.') AS DOUBLE PRECISION)), 0) AS valor_total_mes " +
            "FROM pedidos p " +
            "WHERE EXTRACT(YEAR FROM p.data_atualizacao_pedido) = 2025 " +
            "GROUP BY mes_total, ano_total " +
            "ORDER BY ano_total, mes_total",
            nativeQuery = true)
    List<Object[]> findTotaisPorMes();

//    @Query(value = "SELECT p FROM Pedido p WHERE p.status = :statusFiltro ORDER BY p.idPedido DESC",
//            countQuery = "SELECT COUNT(p) FROM Pedido p WHERE p.status = :statusFiltro")
//    Page<Pedido> findTop5ClientesPorMesNative(@Param("statusFiltro") String statusFiltro, Pageable pageable);
//
//
//    @Query(value = "SELECT p FROM Pedido p WHERE p.status = :statusFiltro ORDER BY p.idPedido DESC",
//            countQuery = "SELECT COUNT(p) FROM Pedido p WHERE p.status = :statusFiltro")
//    Page<Pedido> findTotaisPorMes(@Param("statusFiltro") String statusFiltro, Pageable pageable);
}
