package com.universidad.compras.estado;

public class EstadoEjecutada implements EstadoSolicitud {
    @Override
    public String aprobar(ContextoSolicitud ctx) {
        return "Error: la solicitud ya fue ejecutada";
    }

    @Override
    public String rechazar(ContextoSolicitud ctx) {
        return "Error: la solicitud ya fue ejecutada";
    }

    @Override
    public String ejecutar(ContextoSolicitud ctx) {
        return "Error: ya fue ejecutada";
    }

    @Override
    public String cancelar(ContextoSolicitud ctx) {
        return "Error: no se puede cancelar una solicitud ya ejecutada";
    }

    @Override
    public String getNombre() {
        return "EJECUTADA";
    }
}
