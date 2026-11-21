package com.universidad.compras.estado;

public class EstadoAprobada implements EstadoSolicitud {
    @Override
    public String aprobar(ContextoSolicitud ctx) {
        return "Error: la solicitud ya se encuentra aprobada";
    }

    @Override
    public String rechazar(ContextoSolicitud ctx) {
        return "Error: la solicitud ya fue aprobada y no puede rechazarse directamente";
    }

    @Override
    public String ejecutar(ContextoSolicitud ctx) {
        ctx.setEstado(new EstadoEjecutada());
        return "Ejecutada";
    }

    @Override
    public String cancelar(ContextoSolicitud ctx) {
        ctx.setEstado(new EstadoCancelada());
        return "Cancelada";
    }

    @Override
    public String getNombre() {
        return "APROBADA";
    }
}
