# Post-contenido — Unidad 4: Patrones de Comportamiento en ComprasUDES

## Descripción
Repositorio del post-contenido de la Unidad 4 del curso **Patrones de Diseño de Software** — Sexto Semestre de Ingeniería de Sistemas en la Universidad Francisco de Paula Santander (UFPS).

Este proyecto comprende una aplicación Spring Boot estándar (`compras-comportamiento`) que modela y resuelve cuatro necesidades de comportamiento complejas del backend de **ComprasUDES** (sistema interno de gestión de solicitudes de compras corporativas universitarias), organizadas en dos partes:
1. **Parte 1 — Aprobación por Niveles y Ejecución Reversible:** Encaminamiento dinámico de solicitudes a través de una jerarquía de decisores mediante el patrón **Chain of Responsibility**, y encapsulamiento de operaciones reversibles con historial mediante el patrón **Command**.
2. **Parte 2 — Notificaciones ante Cambios de Estado y Reglas de Transición:** Desacoplamiento de reacciones del sistema (correo, dashboard, auditoría) mediante el patrón **Observer**, y eliminación de condicionales dispersos mediante el patrón **State**.

---

## Cómo ejecutar

Desde la raíz del repositorio:
```bash
# Compilar y empaquetar el proyecto
mvn clean package

# Ejecutar las pruebas unitarias y de integración de ambas partes
mvn test

# Iniciar la aplicación Spring Boot
mvn spring-boot:run
```

---

## Decisiones de diseño

### Necesidad 1 — Aprobación por niveles jerárquicos
* **Patrón aplicado:** **Chain of Responsibility (Cadena de Responsabilidad)** (`AprobadorNivel`, `SupervisorAreaHandler`, `GerenteAreaHandler`, `DirectorFinancieroHandler`, `RevisorCumplimientoHandler`, `CadenaAprobacionService`).
* **Problema específico:**
  El área de Compras define umbrales de aprobación por monto (Supervisor hasta $2.000.000, Gerente hasta $10.000.000, Director Financiero sin límite) y requiere anteponer un filtro normativo para compras de categoría `"INTERNACIONAL"`. El cliente web (`ControladorSolicitudes`) no debe conocer cuántos niveles existen ni el orden en que se evalúan.
* **Por qué Chain of Responsibility y no Command (Alternativa descartada):**
  Se descartó el patrón **Command** porque su objetivo es encapsular una acción u operación como un objeto autónomo para permitir parametrización, encolamiento o reversibilidad (undo/redo). En esta necesidad no hay una acción que deba almacenarse o deshacerse; hay una **petición que debe fluir secuencialmente a través de una cadena de manejadores independientes**, donde cada eslabón decide de forma autónoma si tiene autoridad para resolverla o si la delega al siguiente eslabón. Chain of Responsibility desacopla al emisor de los receptores y permite reconfigurar la cadena (ej. insertar `RevisorCumplimientoHandler` al inicio) sin alterar ni una sola línea del controlador ni de los demás manejadores.

---

### Necesidad 2 — Ejecución reversible de solicitudes
* **Patrón aplicado:** **Command** (`OperacionSolicitud`, `ReservarPresupuestoCommand`, `GenerarOrdenCompraCommand`, `GestorEjecucionSolicitud`).
* **Problema específico:**
  Al aprobarse una solicitud, se deben ejecutar dos operaciones discretas (`PresupuestoService.reservar` y `OrdenCompraService.generar`) que deben poder deshacerse (*undo*) de manera independiente (ej. revertir la orden sin liberar el presupuesto), manteniendo un historial ordenado y consultable de todas las operaciones realizadas sobre la solicitud.
* **Por qué Command y no Chain of Responsibility (Alternativa descartada):**
  Se descartó **Chain of Responsibility** porque aquí no existe una secuencia de decisores evaluando si procesan o delegan una petición entrante. El actor es uno solo (el equipo de compras) ejecutando deliberadamente operaciones discretas con estado. **Command** encapsula cada operación junto con sus parámetros y su lógica inversa (`deshacer()`), permitiendo al invocador (`GestorEjecucionSolicitud`) apilar las operaciones en un historial (`Deque`/`List`), inspeccionar la bitácora completa y revertir operaciones de manera quirúrgica y atómica sin acoplarse a los servicios financieros de bajo nivel.

---

### Necesidad 3 — Notificaciones ante cambio de estado
* **Patrón aplicado:** **Observer (Observador / Publicador-Suscriptor)** (`ObservadorEstadoSolicitud`, `GestorNotificacionesEstado`, `NotificadorEmailObserver`, `NotificadorContabilidadObserver`, `NotificadorAuditoriaObserver`).
* **Problema específico:**
  Cada transición de estado de una solicitud debe disparar tres reacciones automáticas e independientes (envío de email al solicitante, actualización del dashboard contable y log de auditoría en `ClientesNotificacion`), permitiendo registrar nuevos suscriptores (como canales de mensajería o colectores de prueba) sin modificar el código que ejecuta el cambio de estado.
* **Por qué Observer y no State (Alternativa descartada):**
  Se descartó el patrón **State** porque en esta necesidad el foco no está en cambiar el comportamiento interno de la propia `Solicitud` ante futuros métodos, sino en **notificar a módulos externos y completamente ajenos a ella** (correo, tablero contable, auditoría) que ya ocurrió un evento. En State, los estados encapsulan transiciones internas del ciclo de vida del objeto; en **Observer**, los suscriptores reaccionan pasiva y desacopladamente ante los cambios de estado emitidos por el sujeto observable, garantizando un acoplamiento cero entre el dominio y los canales de notificación.

---

### Necesidad 4 — Reglas de transición según el estado actual
* **Patrón aplicado:** **State (Estado)** (`EstadoSolicitud`, `ContextoSolicitud`, `EstadoPendiente`, `EstadoAprobada`, `EstadoEjecutada`, `EstadoRechazada`, `EstadoCancelada`).
* **Problema específico:**
  Las reglas de negocio sobre qué operaciones (`aprobar`, `rechazar`, `ejecutar`, `cancelar`) son válidas en cada instante estaban dispersas como bloques condicionales `if/else` repetitivos inspeccionando `getEstado()`. Agregar nuevos estados (como `EN_ESPERA_PROVEEDOR`) amenazaba con volver inmanejable el mantenimiento.
* **Por qué State y no Strategy (Alternativa descartada):**
  Aunque **State** y **Strategy** comparten una estructura UML prácticamente idéntica basada en composición e interfaces polimórficas, su intención y dinámica son conceptualmente opuestas:
  * En **Strategy**, el cliente externo elige, configura e inyecta explícitamente el algoritmo deseado desde afuera en cada invocación (por ejemplo, elegir un tipo de descuento o método de ordenamiento).
  * En **State**, el cliente no selecciona el comportamiento: es el **propio objeto quien transmuta su comportamiento automáticamente** como consecuencia de sus transiciones internas a lo largo del tiempo. Cada estado concreto (`EstadoPendiente`, `EstadoAprobada`, etc.) sabe a qué nuevo estado transicionar ante una operación válida y cómo rechazar operaciones inválidas sin alterar el estado actual, erradicando los condicionales anidados dispersos.

---

### Reflexión — Otros tres patrones de comportamiento
1. **Iterator:** Encajaría de forma óptima para el requerimiento de recorrer secuencialmente las solicitudes asignadas a un centro de costo sin exponer si la estructura subyacente de almacenamiento es una lista enlazada, un arreglo, un conjunto o un mapa hash, proveyendo una interfaz uniforme de recorrido (`hasNext()`, `next()`).
2. **Template Method:** Resolvería naturalmente la generación de los tres tipos de comprobantes (orden de compra, reserva presupuestal, acta de rechazo) que comparten un mismo esqueleto procedural invariable (encabezado institucional, renderizado del cuerpo de datos y pie legal con firmas), definiendo el algoritmo en una clase base abstracta y permitiendo a las subclases redefinir únicamente el método primitivo del cuerpo.
3. **Memento:** Si se requiriera guardar y restaurar instantáneas completas del estado interno de una `Solicitud` en cualquier punto de su ciclo de vida sin violar su encapsulamiento, Memento sería la elección indicada. A diferencia de **Command** (que guarda la acción inversa lógica para deshacer paso a paso), Memento captura una fotografía de estado pura e inmutable que puede restaurarse de un solo golpe.

---

## Herramientas Utilizadas
* **Lenguaje:** Java 17 / OpenJDK 21
* **Framework:** Spring Boot 3.2.0 (Spring Web, Spring Test)
* **Gestor de Dependencias y Construcción:** Apache Maven 3.8+
* **Framework de Pruebas:** JUnit 5 (Jupiter API)
* **Control de Versiones:** Git 2.x y GitHub

---

## Conclusiones
El desarrollo del backend de ComprasUDES consolidó la habilidad crítica para diferenciar patrones de comportamiento que comparten características estructurales pero atienden intenciones de diseño divergentes. La Parte 1 demostró que mientras **Chain of Responsibility** organiza una cadena de decisores donde no se sabe a priori quién atenderá la solicitud, **Command** convierte cada operación en un objeto con estado y capacidad reversible de auditoría. En la Parte 2, la distinción entre **Observer** y **State** evidenció la diferencia entre notificar reactivamente a entidades externas desacopladas versus modularizar el comportamiento intrínseco y las transiciones válidas de una entidad de negocio. Finalmente, el contraste entre **State** y **Strategy** reafirmó que el criterio decisivo no es la forma del código, sino quién gobierna la transición: un cliente externo (Strategy) o el ciclo de vida autónomo del objeto (State).
