[![CI Pipeline](https://github.com/eulaliobd/mgcss-track-5/actions/workflows/ci.yml/badge.svg)](https://github.com/eulaliobd/mgcss-track-5/actions/workflows/ci.yml)
[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=eulaliobd_mgcss-track-5&metric=coverage)](https://sonarcloud.io/summary/new_code?id=eulaliobd_mgcss-track-5)
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=eulaliobd_mgcss-track-5&metric=alert_status)](https://sonarcloud.io/summary/new_code?id=eulaliobd_mgcss-track-5)

# MGCSS Track - Plataforma de Gestión de Solicitudes de Servicio

Bienvenido al repositorio oficial del proyecto **mgcss-track-5**, desarrollado para la asignatura de Mejora y Gestión de la Calidad del Subsistema de Software (MGCSS).

Este proyecto consiste en un sistema backend robusto para la gestión de clientes, técnicos y solicitudes de servicio, diseñado con un fuerte enfoque en la calidad del código, la mantenibilidad y la automatización de procesos (DevOps).

---

## Stack Tecnológico y Arquitectura

El sistema ha evolucionado para utilizar un stack moderno y orientado a la producción:

**Backend & Frameworks:**
* **Lenguaje:** Java 17
* **Framework Core:** Spring Boot 3 / Spring Data JPA
* **Gestor de dependencias:** Maven
* **Documentación API:** OpenAPI / Swagger UI

**Bases de Datos:**
* **Producción:** PostgreSQL (Orquestado en contenedores)
* **Testing:** H2 (Base de datos en memoria para integración continua)

**DevOps & Calidad (CI/CD):**
* **Control de Versiones:** Git & GitHub
* **Integración y Entrega Continua:** GitHub Actions (`ci.yml`, `release.yml`)
* **Análisis Estático de Código:** SonarCloud (Quality Gate integrado)
* **Orquestación y Despliegue:** Docker, Docker Compose y Docker Hub

**Arquitectura:**
El código sigue una estructura de **Arquitectura Hexagonal (Puertos y Adaptadores)**, separando estrictamente la lógica de negocio (`domain`) de los detalles técnicos de la base de datos (`infrastructure`) y la exposición web (`api`).

---

## Despliegue Rápido (Quick Start)

El proyecto está dockerizado y publicado en Docker Hub. Para desplegar el sistema completo (Aplicación + Base de Datos PostgreSQL) no es necesario disponer de Java ni de Maven, **solo necesitas tener instalado Docker Desktop**.

1. Descarga el archivo `docker-compose.yml` de la [última Release](https://github.com/eulaliobd/mgcss-track-5/releases) (extrayéndolo del código fuente comprimido).
2. Coloca el archivo en un directorio vacío.
3. Abre una terminal en ese directorio y ejecuta:
   ```bash
   docker-compose up -d

