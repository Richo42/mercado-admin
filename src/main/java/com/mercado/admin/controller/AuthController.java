package com.mercado.admin.controller;

import com.mercado.admin.entity.Usuario;
import com.mercado.admin.repository.UsuarioRepository;
import com.mercado.admin.security.JWTAuthenticationConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final JWTAuthenticationConfig jwtAuthenticationConfig;
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        Usuario usuario = usuarioRepository.findByUsername(request.username())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        if (!passwordEncoder.matches(request.password(), usuario.getPassword())) {
            throw new RuntimeException("Contraseña incorrecta");
        }

        String token = jwtAuthenticationConfig.getJWTToken(usuario.getUsername(), "ROLE_" + usuario.getRole());
        return ResponseEntity.ok(new LoginResponse(token));
    }

    public record LoginRequest(String username, String password) {}
    public record LoginResponse(String token) {}
}