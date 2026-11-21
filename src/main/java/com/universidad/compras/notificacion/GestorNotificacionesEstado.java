package com.universidad.compras.notificacion;

import com.universidad.compras.modelo.Solicitud;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Patrón Observer (Sujeto / Emisor de Eventos de Estado)
 */
@Service
public class GestorNotificacionesEstado {
    private final List<ObservadorEstadoSolicitud> observadores = new ArrayList<>();

    public GestorNotificacionesEstado() {
        // Registro de los 3 observadores requeridos por el sistema
        suscribir(new NotificadorEmailObserver());
        suscribir(new NotificadorContabilidadObserver());
        suscribir(new NotificadorAuditoriaObserver());
    }

    public void suscribir(ObservadorEstadoSolicitud observador) {
        observadores.add(observador);
    }

    public void desuscribir(ObservadorEstadoSolicitud observador) {
        observadores.remove(observador);
    }

    public void notificarCambioEstado(Solicitud solicitud, String nuevoEstado) {
        String estadoAnterior = solicitud.getEstado();
        solicitud.setEstado(nuevoEstado);
        for (ObservadorEstadoSolicitud obs : observadores) {
            obs.onCambioEstado(solicitud, estadoAnterior, nuevoEstado);
        }
    }
}
