package com.universidad.compras.estado;

public class EstadoRechazada implements EstadoSolicitud {
    @Override
    public String aprobar(ContextoSolicitud ctx) {
        return "Error: no se puede aprobar una solicitud rechazada";
    }

    @Override
    public String rechazar(ContextoSolicitud ctx) {
        return "Error: ya se encuentra rechazada";
    }

    @Override
    public String ejecutar(ContextoSolicitud ctx) {
        return "Error: no se puede ejecutar una solicitud RECHAZADA";
    }

    @Override
    public String cancelar(ContextoSolicitud ctx) {
        return "Error: no se puede cancelar una solicitud rechazada";
    }

    @Override
    public String getNombre() {
        return "RECHAZADA";
    }
}
