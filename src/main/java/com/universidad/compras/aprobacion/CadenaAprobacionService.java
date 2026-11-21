package com.universidad.compras.aprobacion;

import com.universidad.compras.modelo.Solicitud;
import org.springframework.stereotype.Service;

/**
 * Patrón Chain of Responsibility (Servicio de Cadena Reconfigurable)
 */
@Service
public class CadenaAprobacionService implements ServicioAprobacion {
    private final AprobadorNivel cadena;

    public CadenaAprobacionService() {
        AprobadorNivel cumplimiento = new RevisorCumplimientoHandler();
        AprobadorNivel supervisor = new SupervisorAreaHandler();
        AprobadorNivel gerente = new GerenteAreaHandler();
        AprobadorNivel director = new DirectorFinancieroHandler();

        cumplimiento.setSiguiente(supervisor)
                    .setSiguiente(gerente)
                    .setSiguiente(director);

        this.cadena = cumplimiento;
    }

    public CadenaAprobacionService(AprobadorNivel cadena) {
        this.cadena = cadena;
    }

    @Override
    public ResultadoAprobacion evaluar(Solicitud solicitud) {
        return cadena.evaluar(solicitud);
    }
}
