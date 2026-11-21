package com.universidad.compras.estado;

public class EstadoCancelada implements EstadoSolicitud {
    @Override
    public String aprobar(ContextoSolicitud ctx) {
        return "Error: no se puede aprobar una solicitud cancelada";
    }

    @Override
    public String rechazar(ContextoSolicitud ctx) {
        return "Error: no se puede rechazar una solicitud cancelada";
    }

    @Override
    public String ejecutar(ContextoSolicitud ctx) {
        return "Error: no se puede ejecutar una solicitud CANCELADA";
    }

    @Override
    public String cancelar(ContextoSolicitud ctx) {
        return "Error: ya se encuentra cancelada";
    }

    @Override
    public String getNombre() {
        return "CANCELADA";
    }
}
