package org.medrecord.service;

import org.medrecord.dto.PerfilUsuarioDTO;
import org.medrecord.model.Usuario;
import org.medrecord.repository.UsuarioRepository;

import java.sql.SQLException;
import java.util.Map;
import java.util.HashMap;

public class UsuarioService {
    private final UsuarioRepository usuarioRepo;

    public UsuarioService(UsuarioRepository usuarioRepo) {
        this.usuarioRepo = usuarioRepo;
    }

    /**
     * Crea usuario con verificación y retorna información completa
     */
    public Map<String, Object> createUserWithVerification(Usuario usuario) throws SQLException {
        usuarioRepo.saveUser(usuario);

        // Obtener el token de verificación generado
        String token = usuarioRepo.obtenerTokenVerificacion(usuario.getCorreo());

        Map<String, Object> result = new HashMap<>();
        result.put("mensaje", "Usuario creado exitosamente");
        result.put("tokenVerificacion", token);
        result.put("correo", usuario.getCorreo());

        return result;
    }

    public PerfilUsuarioDTO getByIdUser(int idUsuario) throws SQLException {
        return usuarioRepo.findByIdUser(idUsuario);
    }

    public void createUser(Usuario usuario) throws SQLException {
        usuarioRepo.saveUser(usuario);
    }

    public void updateUser(Usuario usuario) throws SQLException {
        usuarioRepo.updateUser(usuario);
    }

    public void deleteByIdUser(int idUsuario) throws SQLException {
        usuarioRepo.deleteUser(idUsuario);
    }

    // LOGIN
    public Usuario login(String email, String password) throws SQLException {
        return usuarioRepo.login(email, password);
    }

    // VERIFICAR EMAIL
    public boolean verificarEmail(String token) throws SQLException {
        return usuarioRepo.verificarEmail(token);
    }

    // OBTENER TOKEN DE VERIFICACIÓN
    public String obtenerTokenVerificacion(String email) throws SQLException {
        return usuarioRepo.obtenerTokenVerificacion(email);
    }

    // BUSCAR USUARIO POR TOKEN
    public Usuario findByTokenVerificacion(String token) throws SQLException {
        return usuarioRepo.findByTokenVerificacion(token);
    }
}