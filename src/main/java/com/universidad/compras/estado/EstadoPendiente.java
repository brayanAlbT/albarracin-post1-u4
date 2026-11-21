package com.universidad.compras.estado;

public class EstadoPendiente implements EstadoSolicitud {
    @Override
    public String aprobar(ContextoSolicitud ctx) {
        ctx.setEstado(new EstadoAprobada());
        return "Aprobada";
    }

    @Override
    public String rechazar(ContextoSolicitud ctx) {
        ctx.setEstado(new EstadoRechazada());
        return "Rechazada";
    }

    @Override
    public String ejecutar(ContextoSolicitud ctx) {
        return "Error: debe estar aprobada antes de ejecutarse";
    }

    @Override
    public String cancelar(ContextoSolicitud ctx) {
        ctx.setEstado(new EstadoCancelada());
        return "Cancelada";
    }

    @Override
    public String getNombre() {
        return "PENDIENTE";
    }
}
