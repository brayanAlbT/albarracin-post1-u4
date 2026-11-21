package com.universidad.compras.notificacion;

import com.universidad.compras.modelo.Solicitud;

public class NotificadorAuditoriaObserver implements ObservadorEstadoSolicitud {
    @Override
    public void onCambioEstado(Solicitud solicitud, String estadoAnterior, String estadoNuevo) {
        ClientesNotificacion.registrarAuditoria(
            solicitud.getId(),
            estadoNuevo,
            "Transición registrada de " + estadoAnterior + " a " + estadoNuevo
        );
    }
}
