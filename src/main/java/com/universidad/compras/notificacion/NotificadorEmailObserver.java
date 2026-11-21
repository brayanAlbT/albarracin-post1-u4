package com.universidad.compras.notificacion;

import com.universidad.compras.modelo.Solicitud;

public class NotificadorEmailObserver implements ObservadorEstadoSolicitud {
    @Override
    public void onCambioEstado(Solicitud solicitud, String estadoAnterior, String estadoNuevo) {
        ClientesNotificacion.enviarCorreo(
            solicitud.getSolicitanteEmail(),
            "Actualización de Solicitud " + solicitud.getId(),
            "Su solicitud cambió de " + estadoAnterior + " a " + estadoNuevo
        );
    }
}
