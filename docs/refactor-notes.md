# Notas de Refactorización (Sesión 8)

## Refactorización 1: Eliminar código muerto
1. **Problema identificado:** En `Solicitud.java` existe un bloque de código comentado (`//private LocalDateTime fechaCierre;`).
2. **Métrica asociada:** Code Smells (Maintainability Rating).
3. **Riesgo potencial si no se corrige:** Ensucia el código, reduce la legibilidad y causa confusión sobre si el código es necesario o no.

## Refactorización 2: Actualizar API de Streams
1. **Problema identificado:** En `TecnicoService.java` se usa `Stream.collect(Collectors.toList())` en lugar de la versión moderna `Stream.toList()`.
2. **Métrica asociada:** Code Smells (Maintainability Rating).
3. **Riesgo potencial si no se corrige:** Uso de sintaxis más verbosa y creación de listas mutables cuando la intención es devolver listas de solo lectura, lo que podría llevar a errores de modificación accidental.