package com.transportadora.repository;

import com.transportadora.model.Cliente;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    @Query("SELECT v FROM Cliente v WHERE v.nome ILIKE %:trechoBusca% OR v.cpfCnpj ILIKE %:trechoBusca% OR v.razaoSocial ILIKE %:trechoBusca% OR v.logradouroEntrega ILIKE %:trechoBusca%")
    List<Cliente> clientesPorTrecho(String trechoBusca);

    Page<Cliente> findAllByOrderByIdClienteDesc(Pageable pageable);
}
