package com.universidad.compras.notificacion;

import com.universidad.compras.modelo.Solicitud;
import org.junit.jupiter.api.Test;
import java.util.concurrent.atomic.AtomicBoolean;
import static org.junit.jupiter.api.Assertions.*;

class NotificacionEstadoTest {

    @Test
    void cambiarEstadoDisparaLasTresReaccionesSinLanzarExcepcion() {
        Solicitud s = new Solicitud("S-020", "ana@udes.edu.co", 2500000, "SOFTWARE", "CC-100");
        GestorNotificacionesEstado mecanismo = new GestorNotificacionesEstado();

        assertDoesNotThrow(() -> {
            mecanismo.notificarCambioEstado(s, "APROBADA");
            assertEquals("APROBADA", s.getEstado());
        });
    }

    @Test
    void agregarUnCuartoSuscriptorDePruebaNoRequiereModificarElMecanismo() {
        Solicitud s = new Solicitud("S-021", "carlos@udes.edu.co", 1800000, "MATERIAL_OFICINA", "CC-101");
        GestorNotificacionesEstado mecanismo = new GestorNotificacionesEstado();
        AtomicBoolean cuartoSuscriptorNotificado = new AtomicBoolean(false);

        assertDoesNotThrow(() -> {
            // Cuarto suscriptor dinámico (ej. canal interno de Slack/Teams o colector de prueba)
            mecanismo.suscribir((solicitud, anterior, nuevo) -> {
                cuartoSuscriptorNotificado.set(true);
            });

            mecanismo.notificarCambioEstado(s, "RECHAZADA");
            assertTrue(cuartoSuscriptorNotificado.get(), "El cuarto suscriptor debió recibir la notificación");
        });
    }
}
