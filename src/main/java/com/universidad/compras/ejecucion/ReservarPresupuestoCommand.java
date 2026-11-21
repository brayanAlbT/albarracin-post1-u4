package com.universidad.compras.ejecucion;

import com.universidad.compras.modelo.Solicitud;

public class ReservarPresupuestoCommand implements OperacionSolicitud {
    private final PresupuestoService presupuestoService;
    private final Solicitud solicitud;
    private boolean ejecutada = false;

    public ReservarPresupuestoCommand(PresupuestoService presupuestoService, Solicitud solicitud) {
        this.presupuestoService = presupuestoService;
        this.solicitud = solicitud;
    }

    @Override
    public void ejecutar() {
        presupuestoService.reservar(solicitud.getCentroCosto(), solicitud.getMonto());
        ejecutada = true;
    }

    @Override
    public void deshacer() {
        if (ejecutada) {
            presupuestoService.liberar(solicitud.getCentroCosto(), solicitud.getMonto());
            ejecutada = false;
        }
    }

    @Override
    public String getDescripcion() {
        return "Reserva presupuestal en " + solicitud.getCentroCosto() + " por $" + solicitud.getMonto();
    }
}
