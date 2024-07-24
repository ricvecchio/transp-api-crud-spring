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
@RequestMapping("/api/pedidos")
public class PedidoController {

    private final PedidoService pedidoService;

    public PedidoController(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    @GetMapping("/api/pedidos")
    public PedidoPaginacaoDTO list(@RequestParam(defaultValue = "0") @PositiveOrZero int page,
                                   @RequestParam(defaultValue = "10") @Positive @Max(100) int pageSize) {
        return pedidoService.list(page, pageSize);
    }

}
