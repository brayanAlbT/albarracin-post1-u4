package com.universidad.compras.ejecucion;

import com.universidad.compras.modelo.Solicitud;
import org.springframework.stereotype.Service;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

/**
 * Patrón Command (Invoker con Historial de Operaciones Reversibles)
 */
@Service
public class GestorEjecucionSolicitud {
    private final Deque<OperacionSolicitud> historialPila = new ArrayDeque<>();
    private final List<OperacionSolicitud> historialCompleto = new ArrayList<>();

    public void ejecutarOperacion(Solicitud solicitud, OperacionSolicitud operacion) {
        operacion.ejecutar();
        historialPila.push(operacion);
        historialCompleto.add(operacion);
        solicitud.setEstado("EJECUTADA");
    }

    public void deshacerUltimaOperacion() {
        if (!historialPila.isEmpty()) {
            OperacionSolicitud op = historialPila.pop();
            op.deshacer();
        }
    }

    public List<OperacionSolicitud> getHistorial() {
        return List.copyOf(historialCompleto);
    }
}
