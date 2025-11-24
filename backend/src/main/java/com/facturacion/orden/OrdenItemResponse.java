package com.facturacion.orden;

import com.facturacion.producto.ProductResponse;

import java.math.BigDecimal;
import java.util.UUID;

public class OrdenItemResponse {
    private UUID id;
    private ProductResponse producto;
    private Integer cantidad;
    private BigDecimal precioUnitario;
    private BigDecimal subtotal;

    public OrdenItemResponse(OrdenItem item) {
        this.id = item.getId();
        this.producto = new ProductResponse(item.getProducto());
        this.cantidad = item.getCantidad();
        this.precioUnitario = item.getPrecioUnitario();
        this.subtotal = item.getSubtotal();
    }

    public UUID getId() {
        return id;
    }

    public ProductResponse getProducto() {
        return producto;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public BigDecimal getPrecioUnitario() {
        return precioUnitario;
    }

    public BigDecimal getSubtotal() {
        return subtotal;
    }
}
