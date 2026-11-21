package com.universidad.compras.notificacion;

import com.universidad.compras.modelo.Solicitud;

/**
 * Patrón Observer (Interfaz de Observador / Suscriptor)
 */
public interface ObservadorEstadoSolicitud {
    void onCambioEstado(Solicitud solicitud, String estadoAnterior, String estadoNuevo);
}
