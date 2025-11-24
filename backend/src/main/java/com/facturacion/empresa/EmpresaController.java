package com.facturacion.empresa;

import com.facturacion.common.NotFoundException;
import com.facturacion.user.User;
import jakarta.validation.Valid;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/empresas")
public class EmpresaController {

    private final EmpresaRepository empresaRepository;

    public EmpresaController(EmpresaRepository empresaRepository) {
        this.empresaRepository = empresaRepository;
    }

    @GetMapping("/{ruc}")
    public EmpresaResponse obtenerPorRuc(@PathVariable String ruc) {
        Empresa empresa = empresaRepository.findByRuc(ruc)
                .orElseThrow(() -> new NotFoundException("Empresa no encontrada"));
        return new EmpresaResponse(empresa);
    }

    @PostMapping
    public EmpresaResponse crearOActualizar(@AuthenticationPrincipal User user,
                                            @Valid @RequestBody EmpresaRequest request) {
        Empresa empresa = user.getEmpresa() != null ? user.getEmpresa() : new Empresa();
        empresa.setRuc(request.getRuc());
        empresa.setRazonSocial(request.getRazonSocial());
        empresa.setDireccion(request.getDireccion());
        empresa.setTelefono(request.getTelefono());
        empresa.setOwner(user);
        Empresa guardada = empresaRepository.save(empresa);
        user.setEmpresa(guardada);
        return new EmpresaResponse(guardada);
    }
}
