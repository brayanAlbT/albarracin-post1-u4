package com.universidad.compras.ejecucion;

/**
 * Patrón Command (Interfaz de Operación Reversible)
 */
public interface OperacionSolicitud {
    void ejecutar();
    void deshacer();
    String getDescripcion();
}
