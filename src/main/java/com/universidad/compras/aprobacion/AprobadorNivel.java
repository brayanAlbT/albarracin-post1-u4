package com.universidad.compras.aprobacion;

import com.universidad.compras.modelo.Solicitud;

/**
 * Patrón Chain of Responsibility (Manejador Base)
 */
public abstract class AprobadorNivel {
    protected AprobadorNivel siguiente;

    public AprobadorNivel setSiguiente(AprobadorNivel siguiente) {
        this.siguiente = siguiente;
        return siguiente;
    }

    public abstract ResultadoAprobacion evaluar(Solicitud solicitud);
}
