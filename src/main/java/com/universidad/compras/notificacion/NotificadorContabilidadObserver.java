package com.universidad.compras.notificacion;

import com.universidad.compras.modelo.Solicitud;

public class NotificadorContabilidadObserver implements ObservadorEstadoSolicitud {
    @Override
    public void onCambioEstado(Solicitud solicitud, String estadoAnterior, String estadoNuevo) {
        ClientesNotificacion.actualizarDashboardContabilidad(
            solicitud.getId(),
            estadoNuevo,
            solicitud.getMonto()
        );
    }
}
