package com.transportadora.controller;

import com.transportadora.dto.PedidoDTO;
import com.transportadora.dto.PedidoPaginacaoDTO;
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
@RequestMapping("/api/pedidos")
public class PedidoController {

    private final PedidoService pedidoService;

    public PedidoController(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    @GetMapping
    public PedidoPaginacaoDTO list(@RequestParam(defaultValue = "0") @PositiveOrZero int page,
                                   @RequestParam(defaultValue = "10") @Positive @Max(100) int pageSize) {
        return pedidoService.list(page, pageSize);
    }

    @GetMapping("/{id}")
    public PedidoDTO findById(@PathVariable @NotNull @Positive Long id) {
        return pedidoService.findById(id);
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public PedidoDTO save(@RequestBody @Valid @NotNull PedidoDTO pedido) {
        return pedidoService.create(pedido);
    }

    @PutMapping("/{id}")
    public PedidoDTO update(@PathVariable @NotNull @Positive Long id, @RequestBody @Valid PedidoDTO pedido) {
        return pedidoService.update(id, pedido);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable @NotNull @Positive Long id) {
        pedidoService.delete(id);
    }

}
