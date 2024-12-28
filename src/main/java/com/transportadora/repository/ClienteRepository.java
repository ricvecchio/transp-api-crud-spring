package com.transportadora.repository;

import com.transportadora.model.Cliente;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    @Query("SELECT v FROM Cliente v WHERE " +
            "TRANSLATE(LOWER(v.nome), 'áéíóúàèìòùãõâêîôûç', 'aeiouaeiouaoaeiouc') LIKE LOWER(CONCAT('%', TRANSLATE(:filter, 'áéíóúàèìòùãõâêîôûç', 'aeiouaeiouaoaeiouc'), '%')) OR " +
            "REPLACE(REPLACE(REPLACE(v.cpfCnpj, '.', ''), '-', ''), '/', '') LIKE CONCAT('%', REPLACE(REPLACE(REPLACE(:filter, '.', ''), '-', ''), '/', ''), '%') OR " +
            "TRANSLATE(LOWER(v.razaoSocial), 'áéíóúàèìòùãõâêîôûç', 'aeiouaeiouaoaeiouc') LIKE LOWER(CONCAT('%', TRANSLATE(:filter, 'áéíóúàèìòùãõâêîôûç', 'aeiouaeiouaoaeiouc'), '%')) OR " +
            "TRANSLATE(LOWER(v.logradouroEntrega), 'áéíóúàèìòùãõâêîôûç', 'aeiouaeiouaoaeiouc') LIKE LOWER(CONCAT('%', TRANSLATE(:filter, 'áéíóúàèìòùãõâêîôûç', 'aeiouaeiouaoaeiouc'), '%'))")
    List<Cliente> clientesPorTrecho(@Param("filter") String filter);

    Page<Cliente> findAllByOrderByIdClienteDesc(Pageable pageable);

    // Manter o uso de 'filter' também na consulta paginada
    @Query("SELECT v FROM Cliente v WHERE " +
            "TRANSLATE(LOWER(v.nome), 'áéíóúàèìòùãõâêîôûç', 'aeiouaeiouaoaeiouc') LIKE LOWER(CONCAT('%', TRANSLATE(:filter, 'áéíóúàèìòùãõâêîôûç', 'aeiouaeiouaoaeiouc'), '%')) OR " +
            "REPLACE(REPLACE(REPLACE(v.cpfCnpj, '.', ''), '-', ''), '/', '') LIKE CONCAT('%', REPLACE(REPLACE(REPLACE(:filter, '.', ''), '-', ''), '/', ''), '%') OR " +
            "TRANSLATE(LOWER(v.razaoSocial), 'áéíóúàèìòùãõâêîôûç', 'aeiouaeiouaoaeiouc') LIKE LOWER(CONCAT('%', TRANSLATE(:filter, 'áéíóúàèìòùãõâêîôûç', 'aeiouaeiouaoaeiouc'), '%')) OR " +
            "TRANSLATE(LOWER(v.logradouroEntrega), 'áéíóúàèìòùãõâêîôûç', 'aeiouaeiouaoaeiouc') LIKE LOWER(CONCAT('%', TRANSLATE(:filter, 'áéíóúàèìòùãõâêîôûç', 'aeiouaeiouaoaeiouc'), '%'))")
    Page<Cliente> findAllByFilter(@Param("filter") String filter, Pageable pageable);
}