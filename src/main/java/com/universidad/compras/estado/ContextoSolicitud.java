package com.universidad.compras.estado;

import com.universidad.compras.modelo.Solicitud;

/**
 * Patrón State (Contexto que mantiene la referencia al estado actual)
 */
public class ContextoSolicitud {
    private final Solicitud solicitud;
    private EstadoSolicitud estadoActual;

    public ContextoSolicitud(Solicitud solicitud) {
        this.solicitud = solicitud;
        this.estadoActual = resolverEstadoInicial(solicitud.getEstado());
    }

    private EstadoSolicitud resolverEstadoInicial(String estadoStr) {
        if ("APROBADA".equalsIgnoreCase(estadoStr)) return new EstadoAprobada();
        if ("EJECUTADA".equalsIgnoreCase(estadoStr)) return new EstadoEjecutada();
        if ("RECHAZADA".equalsIgnoreCase(estadoStr)) return new EstadoRechazada();
        if ("CANCELADA".equalsIgnoreCase(estadoStr)) return new EstadoCancelada();
        return new EstadoPendiente();
    }

    public void setEstado(EstadoSolicitud nuevoEstado) {
        this.estadoActual = nuevoEstado;
        this.solicitud.setEstado(nuevoEstado.getNombre());
    }

    public String aprobar() {
        return estadoActual.aprobar(this);
    }

    public String rechazar() {
        return estadoActual.rechazar(this);
    }

    public String ejecutar() {
        return estadoActual.ejecutar(this);
    }

    public String cancelar() {
        return estadoActual.cancelar(this);
    }

    public Solicitud getSolicitud() {
        return solicitud;
    }

    public EstadoSolicitud getEstadoActual() {
        return estadoActual;
    }
}
