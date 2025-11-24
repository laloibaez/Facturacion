package com.facturacion.orden;

import com.facturacion.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface OrdenRepository extends JpaRepository<Orden, UUID> {
    List<Orden> findByUser(User user);
}
