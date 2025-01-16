package com.transportadora.controller.security;

import com.transportadora.dto.security.AuthenticationDTO;
import com.transportadora.dto.security.UsuarioDTO;
import com.transportadora.service.security.AuthService;
import com.transportadora.service.security.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "https://saotomecatime.onrender.com")
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping(value = "/login")
    public ResponseEntity<?> login(@RequestBody AuthenticationDTO authDto){
        return ResponseEntity.ok(authService.login(authDto));
    }

    @PostMapping(value = "/novoUsuario")
    public void inserirNovoUsuario(@RequestBody UsuarioDTO novoUsuario){
        usuarioService.inserirNovoUsuario(novoUsuario);
    }

//    @GetMapping(value = "/verificarCadastro/{uuid}")
//    public String verificarCadastro(@PathVariable("uuid") String uuid) {
//        return usuarioService.verificarCadastro(uuid);
//    }

}
