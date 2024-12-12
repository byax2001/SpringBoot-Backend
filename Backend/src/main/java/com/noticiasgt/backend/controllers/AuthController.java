package com.noticiasgt.backend.controllers;
import com.noticiasgt.backend.models.AuthResponse;
import com.noticiasgt.backend.models.LoginRequest;
import com.noticiasgt.backend.models.RegisterRequest;
import com.noticiasgt.backend.models.Usuario;
import com.noticiasgt.backend.services.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/login")
    @CrossOrigin(origins = "*")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest usuario) {
        return ResponseEntity.ok(authService.login(usuario));
    }

    @PostMapping(value = "/registro")
    @CrossOrigin(origins = "*")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest request)
    {
        System.out.println(request.getNombre());
        return ResponseEntity.ok(authService.register(request));
    }
}
