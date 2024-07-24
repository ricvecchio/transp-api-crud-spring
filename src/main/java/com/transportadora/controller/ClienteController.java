package com.transportadora.controller;

import com.transportadora.dto.ClienteDTO;
import com.transportadora.dto.ClientePaginacaoDTO;
import com.transportadora.dto.PedidoPaginacaoDTO;
import com.transportadora.service.ClienteService;
import com.transportadora.service.PedidoService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Validated
@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

    private final ClienteService clienteService;

    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }


    @GetMapping
    public ClientePaginacaoDTO list(@RequestParam(defaultValue = "0") @PositiveOrZero int page,
                                    @RequestParam(defaultValue = "10") @Positive @Max(100) int pageSize) {
        return clienteService.list(page, pageSize);
    }

    @GetMapping("/{id}")
    public ClienteDTO findById(@PathVariable @NotNull @Positive Long id) {
        return clienteService.findById(id);
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public ClienteDTO save(@RequestBody @Valid @NotNull ClienteDTO cliente) {
        return clienteService.create(cliente);
    }

    @PutMapping("/{id}")
    public ClienteDTO update(@PathVariable @NotNull @Positive Long id, @RequestBody @Valid ClienteDTO cliente) {
        return clienteService.update(id, cliente);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable @NotNull @Positive Long id) {
        clienteService.delete(id);
    }
}
