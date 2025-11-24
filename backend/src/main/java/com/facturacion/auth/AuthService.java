package com.facturacion.auth;

import com.facturacion.auth.dto.AuthRequest;
import com.facturacion.auth.dto.AuthResponse;
import com.facturacion.auth.dto.RegisterRequest;
import com.facturacion.auth.jwt.JwtService;
import com.facturacion.common.BadRequestException;
import com.facturacion.empresa.Empresa;
import com.facturacion.empresa.EmpresaRepository;
import com.facturacion.user.Role;
import com.facturacion.user.User;
import com.facturacion.user.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final EmpresaRepository empresaRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthService(UserRepository userRepository,
                       EmpresaRepository empresaRepository,
                       PasswordEncoder passwordEncoder,
                       JwtService jwtService,
                       AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.empresaRepository = empresaRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    @Transactional
    public AuthResponse register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new BadRequestException("El email ya está registrado");
        }
        User user = new User();
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setNombre(request.getNombre());
        user.setRole(Role.USER);

        if (request.getRuc() != null && !request.getRuc().isBlank()) {
            Empresa empresa = new Empresa();
            empresa.setRuc(request.getRuc());
            empresa.setRazonSocial(request.getRazonSocial() != null ? request.getRazonSocial() : request.getNombre());
            empresa.setDireccion(request.getDireccion());
            empresa.setOwner(user);
            user.setEmpresa(empresa);
        }
        User saved = userRepository.save(user);
        String token = jwtService.generateToken(saved);
        return new AuthResponse(token, saved.getNombre());
    }

    public AuthResponse login(AuthRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new BadRequestException("Credenciales inválidas"));
        String token = jwtService.generateToken(user);
        return new AuthResponse(token, user.getNombre());
    }
}
