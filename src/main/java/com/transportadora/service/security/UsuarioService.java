package com.transportadora.service.security;

import com.transportadora.dto.security.UsuarioDTO;
import com.transportadora.model.Usuario;
import com.transportadora.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

//    @Autowired
//    private UsuarioVerificadorRepository usuarioVerificadorRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

//    @Autowired
//    private EmailService emailService;

    public List<UsuarioDTO> listarTodos(){
        List<Usuario> usuarios = usuarioRepository.findAll();
        return usuarios.stream().map(UsuarioDTO::new).toList();
    }

    public void inserir(UsuarioDTO usuario) {
        Usuario usuarioEntity = new Usuario(usuario);
        usuarioEntity.setSenha(passwordEncoder.encode(usuario.getSenha()));
        usuarioRepository.save(usuarioEntity);
    }

    public void inserirNovoUsuario(UsuarioDTO usuario) {
        Usuario usuarioEntity = new Usuario(usuario);
        usuarioEntity.setSenha(passwordEncoder.encode(usuario.getSenha()));
//        usuarioEntity.setSituacao(TipoSituacaoUsuario.PENDENTE);
        usuarioEntity.setId(null);
        usuarioRepository.save(usuarioEntity);

//        UsuarioVerificadorEntity verificador = new UsuarioVerificadorEntity();
//        verificador.setUsuario(usuarioEntity);
//        verificador.setUuid(UUID.randomUUID());
//        verificador.setDataExpiracao(Instant.now().plusMillis(900000));
//        usuarioVerificadorRepository.save(verificador);

        //TODO - Enviar um email para verificar a conta
//        emailService.enviarEmailTexto(usuario.getEmail(),
//                "Novo usuário cadastrado",
//                "Você está recebendo um email de cadastro o número para validação é " + verificador.getUuid());

    }

//    public String verificarCadastro(String uuid) {
//
//        UsuarioVerificadorEntity usuarioVerificacao = usuarioVerificadorRepository.findByUuid(UUID.fromString(uuid)).get();
//
//        if(usuarioVerificacao != null) {
//            if(usuarioVerificacao.getDataExpiracao().compareTo(Instant.now()) >= 0) {
//
//                Usuario u = usuarioVerificacao.getUsuario();
//                u.setSituacao(TipoSituacaoUsuario.ATIVO);
//
//                usuarioRepository.save(u);
//
//                return "Usuário Verificado";
//            }else {
//                usuarioVerificadorRepository.delete(usuarioVerificacao);
//                return "Tempo de verificação expirado";
//            }
//        }else {
//            return "Usuario não verificado";
//        }
//
//    }

    public UsuarioDTO alterar(UsuarioDTO usuario) {
        Usuario usuarioEntity = new Usuario(usuario);
        usuarioEntity.setSenha(passwordEncoder.encode(usuario.getSenha()));
        return new UsuarioDTO(usuarioRepository.save(usuarioEntity));
    }

    public void excluir(Long id) {
        Usuario usuario = usuarioRepository.findById(id).get();
        usuarioRepository.delete(usuario);
    }

    public UsuarioDTO buscarPorId(Long id) {
        return new UsuarioDTO(usuarioRepository.findById(id).get());
    }
}
