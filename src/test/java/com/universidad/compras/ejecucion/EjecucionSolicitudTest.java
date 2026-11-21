package com.universidad.compras.ejecucion;

import com.universidad.compras.modelo.Solicitud;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class EjecucionSolicitudTest {

    @Test
    void ejecutarReservaPresupuestoYGeneraOrden() {
        Solicitud s = new Solicitud("S-010", "ana@udes.edu.co", 3000000, "SOFTWARE", "CC-100");
        s.setEstado("APROBADA");
        GestorEjecucionSolicitud ejecutor = new GestorEjecucionSolicitud();

        ejecutor.ejecutarOperacion(s, new ReservarPresupuestoCommand(new PresupuestoService(), s));
        ejecutor.ejecutarOperacion(s, new GenerarOrdenCompraCommand(new OrdenCompraService(), s, "PROV-001"));

        assertEquals("EJECUTADA", s.getEstado());
    }

    @Test
    void deshacerSoloLaUltimaOperacionNoAfectaLaAnterior() {
        Solicitud s = new Solicitud("S-011", "luis@udes.edu.co", 4000000, "MATERIAL_OFICINA", "CC-200");
        GestorEjecucionSolicitud ejecutor = new GestorEjecucionSolicitud();

        assertDoesNotThrow(() -> {
            ejecutor.ejecutarOperacion(s, new ReservarPresupuestoCommand(new PresupuestoService(), s));
            ejecutor.ejecutarOperacion(s, new GenerarOrdenCompraCommand(new OrdenCompraService(), s, "PROV-002"));
            ejecutor.deshacerUltimaOperacion(); // Deshace solo orden de compra, reserva se mantiene
        });
    }

    @Test
    void elHistorialConservaTodasLasOperacionesNoSoloLaUltima() {
        Solicitud s = new Solicitud("S-012", "marta@udes.edu.co", 2000000, "SOFTWARE", "CC-100");
        GestorEjecucionSolicitud ejecutor = new GestorEjecucionSolicitud();

        assertDoesNotThrow(() -> {
            ejecutor.ejecutarOperacion(s, new ReservarPresupuestoCommand(new PresupuestoService(), s));
            ejecutor.ejecutarOperacion(s, new GenerarOrdenCompraCommand(new OrdenCompraService(), s, "PROV-003"));
            assertEquals(2, ejecutor.getHistorial().size());
        });
    }
}
