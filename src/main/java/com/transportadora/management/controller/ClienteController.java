package com.transportadora.management.controller;

import com.transportadora.management.dto.ClienteDTO;
import com.transportadora.management.dto.ClientePaginacaoDTO;
import com.transportadora.management.service.ClienteService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
@RestController
@CrossOrigin(origins = "https://saotomecatime.onrender.com")
@RequestMapping("/api/clientes")
public class ClienteController {

    private final ClienteService clienteService;

    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @GetMapping
    public ClientePaginacaoDTO list(
            @RequestParam(defaultValue = "0") @PositiveOrZero int page,
            @RequestParam(defaultValue = "10") @Positive @Max(100) int pageSize,
            @RequestParam(defaultValue = "") String filter) {
        return clienteService.list(page, pageSize, filter);
    }

    @GetMapping("/{idCliente}")
    public ClienteDTO findById(@PathVariable @NotNull @Positive Long idCliente) {
        return clienteService.findById(idCliente);
    }

    @GetMapping("/trecho/{trechoBusca}")
    public List<ClienteDTO> buscarTrechoNome(@PathVariable String trechoBusca) {
        System.out.println("Trecho da Busca: " + trechoBusca);
        return clienteService.buscarTrechoNome(trechoBusca);
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public ClienteDTO save(@RequestBody @Valid @NotNull ClienteDTO cliente) {
        return clienteService.create(cliente);
    }

    @PutMapping("/{idCliente}")
    public ClienteDTO update(@PathVariable @NotNull @Positive Long idCliente, @RequestBody @Valid ClienteDTO cliente) {
        return clienteService.update(idCliente, cliente);
    }

    @DeleteMapping("/{idCliente}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable @NotNull @Positive Long idCliente) {
        clienteService.delete(idCliente);
    }
}
