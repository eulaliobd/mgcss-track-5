# Memoria Técnica y Documentación de Arquitectura
**Proyecto:** mgcss-track-5  
**Asignatura:** Mantenimiento y Gestión del cambio en Sistemas Software (MGCSS)  

---

## 1. Decisiones de Arquitectura y Diseño Técnico

En esta sección se detallan y justifican las decisiones tecnológicas y de diseño adoptadas para garantizar la mantenibilidad, escalabilidad y automatización del sistema `mgcss-track-5`.

### 1.1. Estructura de Paquetes (Arquitectura Hexagonal / Clean Architecture)
En lugar de optar por una arquitectura tradicional acoplada, se ha adoptado una estructura inspirada en la **Arquitectura Hexagonal (Puertos y Adaptadores)** y el Diseño Guiado por el Dominio (DDD). Esta decisión aísla las reglas de negocio de los detalles técnicos externos (frameworks, bases de datos o interfaces web).

La estructura bajo `src/main/java/com/mgcss/` se organiza en:

* **`domain` (El Núcleo):** Contiene los modelos puros de negocio (`Cliente`, `Solicitud`, `Tecnico`), enumerados (`Estado`, `TipoCliente`) y los *Puertos* o contratos, es decir, las interfaces de los repositorios (`ClienteRepository`, etc.). Esta capa es agnóstica a Spring o a la base de datos.
* **`api` (Adaptador de Entrada):** Gestiona la comunicación exterior. Se subdivide en:
  * `controller`: Controladores REST que exponen los *endpoints*.
  * `dto`: Objetos de transferencia de datos utilizados para desacoplar las entidades de dominio de la información que se envía/recibe en la web.
* **`infrastructure` (Adaptador de Salida):** Contiene la implementación técnica de los contratos del dominio. Aquí residen los interfaces de Spring Data JPA (`JpaClienteRepository`, etc.) que conectan la aplicación con la base de datos real.
* **`service` (Casos de Uso):** Contiene la lógica de aplicación que orquesta el flujo de información entre la capa API, el dominio y la infraestructura.

Esta misma estructura lógica se ha replicado escrupulosamente en la carpeta `src/test/java` para mantener las pruebas unitarias y de integración perfectamente organizadas.

### 1.2. Decisión de Diseño: Inversión de Dependencias y Spring Data JPA
Una decisión arquitectónica clave ha sido separar los repositorios del dominio de su implementación en infraestructura mediante el **Principio de Inversión de Dependencias (SOLID)** acoplado al uso de **Spring Data JPA**.

**Justificación de la decisión:**
1. **Desacoplamiento Tecnológico:** El paquete `domain` define qué operaciones necesita la base de datos a través de interfaces puras en Java, pero no sabe *cómo* se guardan. La responsabilidad de conectarse a la base de datos se delega al paquete `infrastructure` usando las interfaces de Spring Data.
2. **Eliminación de Código *Boilerplate*:** Al utilizar los repositorios JPA en la capa de infraestructura, Spring genera dinámicamente las consultas SQL en tiempo de ejecución. Evitamos escribir clases DAO manuales con el `EntityManager` o cadenas de texto SQL propensas a errores.
3. **Seguridad Integrada:** Spring Data JPA utiliza consultas parametrizadas de forma nativa, lo que mitiga por completo las vulnerabilidades de Inyección SQL.

### 1.3. Entornos de Base de Datos: H2 Local vs PostgreSQL Contenedorizado
Para balancear la agilidad en el desarrollo con la fiabilidad en producción, se implementó una estrategia multi-entorno:

* **Perfil de Test (`h2`):** Base de datos en memoria utilizada para la suite de pruebas unitarias y de integración (`src/test/resources`). Permite ejecuciones ultrarrápidas y deterministas en el pipeline de Integración Continua (GitHub Actions).
* **Perfil de Producción (`postgres`):** Base de datos relacional robusta desplegada mediante contenedores. Garantiza que el comportamiento del sistema final sea consistente y no dependa del entorno local.

### 1.4. Orquestación y Despliegue Multicontenedor
Se ha diseñado un archivo `docker-compose.yml` que encapsula la aplicación Spring Boot junto a su base de datos PostgreSQL. Esta aproximación garantiza la **isomorfía de entornos**: el proyecto se ejecutará con la misma configuración exacta en la máquina de cualquier desarrollador y en el servidor de despliegue final, eliminando la clásica excusa de *"en mi máquina funciona"*.

### 1.5. Pipeline de CI/CD (Integración y Entrega Continua)
Para garantizar la calidad del código y automatizar los despliegues, se ha implementado un flujo de trabajo basado en **GitHub Actions**. La configuración reside en el directorio `.github/workflows/`:

* **`ci.yml`:** Ejecuta el pipeline de Integración Continua en cada *Pull Request*. Se encarga de compilar el proyecto, ejecutar la suite de pruebas automatizadas y enviar el código a SonarCloud para su análisis estático. Si el *Quality Gate* falla, el código no se puede fusionar.
* **`release.yml`:** Automatiza el proceso de *Release*. Al crear un nuevo *Tag* semántico (ej. `v1.3.1`), este flujo compila el artefacto `.jar`, construye la imagen Docker y la publica automáticamente en **Docker Hub**, generando además las notas de lanzamiento en GitHub.

### 1.6. Trazabilidad y Documentación Estructurada
En cumplimiento con las normativas de calidad del software, el directorio `/docs` centraliza el histórico de decisiones técnicas y auditorías del proyecto:
* `change-analysis.md`: Análisis de impacto y especificación de casos de uso.
* `refactor-notes.md`: Registro de la deuda técnica resuelta, "Code Smells" corregidos y refactorizaciones aplicadas.
* `release-notes.md`: Justificación del versionado semántico (MAJOR.MINOR.PATCH) aplicado en cada iteración del proyecto.

Además, en el directorio raíz se encuentran los archivos de Infraestructura como Código (`Dockerfile`, `docker-compose.yml`) y la gestión de dependencias de Maven (`pom.xml`), manteniendo la configuración completamente versionada.

## 2. Instrucciones de Instalación y Despliegue (Release v1.3.1)

Gracias a la estrategia de orquestación mediante contenedores y a la automatización del *pipeline* de GitHub Actions, el despliegue del sistema es completamente agnóstico al sistema operativo del host. 

A continuación, se detallan los pasos para desplegar la última versión estable (**v1.3.1**) en cualquier máquina local o servidor.

### 2.1. Prerrequisitos del Sistema
Para poder ejecutar el entorno, el entorno destino debe contar exclusivamente con:
* **Docker** instalado y el demonio (daemon) ejecutándose.
* **Docker Compose** instalado (incluido por defecto en Docker Desktop).
* Conexión a Internet (para descargar la imagen desde Docker Hub).
* *Nota: NO es necesario instalar Java (JDK), Maven, ni PostgreSQL en la máquina host.*

### 2.2. Guía de Despliegue Paso a Paso

**Paso 1: Obtener el archivo de orquestación**
No es necesario clonar todo el repositorio de código. Basta con extraer el archivo `docker-compose.yml` de la última *Release* oficial.
1. Navegar a la página de **Releases** del repositorio en GitHub (versión `v1.3.1`).
2. En la sección *Assets*, descargar el archivo **`Source code (zip)`**.
3. Descomprimir el archivo y extraer **únicamente** el archivo `docker-compose.yml` (el resto del código fuente no es necesario para el despliegue). Colocar este archivo en una carpeta vacía.

**Paso 2: Arrancar el entorno multicontenedor**
Abrir un terminal en el mismo directorio donde se encuentra el archivo `docker-compose.yml` y ejecutar el siguiente comando:
```bash
docker-compose up -d

### 2.3. Resolución de Problemas Comunes (Troubleshooting)
Dado que el despliegue depende del entorno local de la máquina host, a continuación se documentan los dos errores más comunes y su solución rápida:

* **Error:** `error during connect... the system cannot find the file specified` o menciones a que el *daemon* no se está ejecutando.
  * *Causa:* El motor de Docker (Docker Desktop) no está encendido en el sistema.
  * *Solución:* Abrir la aplicación Docker Desktop, esperar a que el estado indique "Engine running" y volver a ejecutar el comando.

* **Error:** `Conflict. The container name "/mgcss-postgres" is already in use...`
  * *Causa:* Ya existe un contenedor residual de una ejecución previa ocupando los nombres definidos en el archivo compose.
  * *Solución:* Eliminar los contenedores conflictivos ejecutando `docker rm -f mgcss-postgres mgcss-track` en la terminal, o borrarlos visualmente desde la pestaña *Containers* de Docker Desktop.

## 3. Comparativa de Métricas y Calidad del Código

Durante el ciclo de vida del proyecto, se ha utilizado **SonarCloud** integrado en el pipeline de Integración Continua (GitHub Actions) para monitorizar constantemente la calidad del software. 

A continuación, se presentan las métricas finales alcanzadas en la versión de entrega (v1.3.1):

* **Cobertura de Pruebas (Coverage):** `95.1%`. Se ha implementado una sólida base de pruebas unitarias y de integración enfocadas en la capa de dominio y servicios, garantizando que las reglas de negocio (como la imposibilidad de asignar técnicos inactivos) no sufran regresiones.
* **Complejidad Ciclomática / Cognitiva:** El proyecto mantiene una complejidad baja gracias a la división de responsabilidades (Arquitectura Hexagonal). Se han refactorizado los métodos más largos, delegando lógica compleja en servicios específicos y evitando estructuras de control anidadas muy profundas.
* **Duplicación de Código:** `0.0%`. Se ha mantenido muy por debajo del umbral estándar de SonarQube (normalmente < 3%), logrando esto gracias a la reutilización de código mediante la herencia en repositorios JPA y el uso de componentes comunes.
* **Vulnerabilidades (Security):** `0` vulnerabilidades reportadas, gracias en parte a las consultas parametrizadas automáticas de Spring Data JPA que evitan la inyección SQL.

## 4. Análisis de la Deuda Técnica

La deuda técnica representa el coste implícito de un retrabajo adicional causado por elegir una solución fácil o rápida en lugar de la mejor solución a nivel arquitectónico.

* **Technical Debt Ratio (Ratio de Deuda Técnica):** `0.0%` (Calificación: `A`). Este excelente ratio indica que el esfuerzo necesario para arreglar todos los problemas de mantenibilidad del código es residual comparado con el esfuerzo total de desarrollo.
* **Code Smells Identificados:** Actualmente, el sistema reporta `0` Code Smells menores. 

**Justificación de Code Smells restantes (Aceptación de Deuda):**
La mayor parte de los avisos menores que se han decidido mantener sin alterar corresponden a:
1. *Avisos en clases DTO / Entidades:* SonarQube a veces advierte sobre clases con demasiados campos o falta de constructores específicos. Dado que estas clases deben mapear directamente tablas de base de datos o *payloads* de API, es una complejidad accidental necesaria e inherente al framework (Spring/Hibernate).
2. *Excepciones genéricas en pruebas:* Algunos métodos de la suite de testing lanzan `Exception` de forma genérica para facilitar la lectura del test, una práctica considerada aceptable en entornos aislados de pruebas.

En conclusión, el estado actual de la base de código es altamente mantenible, y la deuda técnica actual no representa un riesgo para la evolución funcional descrita en las especificaciones del sistema.