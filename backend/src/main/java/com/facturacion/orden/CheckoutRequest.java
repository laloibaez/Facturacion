package com.facturacion.orden;

import com.facturacion.pago.MetodoPago;
import jakarta.validation.constraints.NotNull;

public class CheckoutRequest {
    @NotNull
    private TipoComprobante tipoComprobante;

    @NotNull
    private MetodoPago metodoPago;

    public TipoComprobante getTipoComprobante() {
        return tipoComprobante;
    }

    public void setTipoComprobante(TipoComprobante tipoComprobante) {
        this.tipoComprobante = tipoComprobante;
    }

    public MetodoPago getMetodoPago() {
        return metodoPago;
    }

    public void setMetodoPago(MetodoPago metodoPago) {
        this.metodoPago = metodoPago;
    }
}
