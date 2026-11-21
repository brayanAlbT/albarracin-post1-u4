package com.universidad.compras.estado;

/**
 * Patrón State (Interfaz de Estado de la Solicitud)
 */
public interface EstadoSolicitud {
    String aprobar(ContextoSolicitud ctx);
    String rechazar(ContextoSolicitud ctx);
    String ejecutar(ContextoSolicitud ctx);
    String cancelar(ContextoSolicitud ctx);
    String getNombre();
}
