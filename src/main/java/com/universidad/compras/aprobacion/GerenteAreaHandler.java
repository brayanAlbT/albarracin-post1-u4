package com.universidad.compras.aprobacion;

import com.universidad.compras.modelo.Solicitud;

public class GerenteAreaHandler extends AprobadorNivel {
    @Override
    public ResultadoAprobacion evaluar(Solicitud solicitud) {
        if (solicitud.getMonto() <= 10000000) {
            solicitud.setEstado("APROBADA");
            solicitud.setNivelResolutor("Gerente de Área");
            return new ResultadoAprobacion(true, "Gerente de Área", "Aprobado por monto hasta $10.000.000");
        }
        if (siguiente != null) {
            return siguiente.evaluar(solicitud);
        }
        return new ResultadoAprobacion(false, "Ninguno", "Monto supera los límites permitidos");
    }
}
