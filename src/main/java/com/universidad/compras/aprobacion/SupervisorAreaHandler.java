package com.universidad.compras.aprobacion;

import com.universidad.compras.modelo.Solicitud;

public class SupervisorAreaHandler extends AprobadorNivel {
    @Override
    public ResultadoAprobacion evaluar(Solicitud solicitud) {
        if (solicitud.getMonto() <= 2000000) {
            solicitud.setEstado("APROBADA");
            solicitud.setNivelResolutor("Supervisor de Área");
            return new ResultadoAprobacion(true, "Supervisor de Área", "Aprobado por monto hasta $2.000.000");
        }
        if (siguiente != null) {
            return siguiente.evaluar(solicitud);
        }
        return new ResultadoAprobacion(false, "Ninguno", "Monto supera los límites permitidos sin decisor asignado");
    }
}
