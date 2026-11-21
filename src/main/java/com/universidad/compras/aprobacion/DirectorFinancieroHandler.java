package com.universidad.compras.aprobacion;

import com.universidad.compras.modelo.Solicitud;

public class DirectorFinancieroHandler extends AprobadorNivel {
    @Override
    public ResultadoAprobacion evaluar(Solicitud solicitud) {
        solicitud.setEstado("APROBADA");
        solicitud.setNivelResolutor("Director Financiero");
        return new ResultadoAprobacion(true, "Director Financiero", "Aprobado por Director Financiero sin límite superior");
    }
}
