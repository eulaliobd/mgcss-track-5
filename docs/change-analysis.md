# Análisis de Impacto (Sesión 9)

1. **¿Qué métodos del dominio se ven afectados?**
   Se debe crear un nuevo método `reabrir()` en la entidad `Solicitud`. Además, los métodos que cambian el estado (`enProceso()`, `cerrar()`) deben modificarse para que registren el evento en el nuevo histórico.

2. **¿Qué reglas actuales cambian?**
   La regla de negocio que dictaba que el estado `CERRADA` era terminal desaparece. Ahora, una solicitud `CERRADA` puede transicionar a `EN_PROCESO`.

3. **¿Qué tests deberían romperse?**
   Cualquier test en `SolicitudTest` que verifique que se lanza una excepción al intentar cambiar el estado de una solicitud previamente cerrada. 

4. **¿Qué parte del modelo debe extenderse?**
   La entidad `Solicitud` debe incluir una estructura interna para el histórico. Optaremos por una lista de objetos de valor (Value Object) `CambioEstado` que guarde el estado y la fecha, ya que es una solución limpia y recomendada.

5. **¿Qué impacto tiene en persistencia?**
   El histórico de estados requerirá modificar el mapeo JPA de `Solicitud`. Usaremos `@ElementCollection` para persistir la lista de objetos de valor sin necesidad de crear un repositorio extra, manteniendo el diseño de Aggregate Root.