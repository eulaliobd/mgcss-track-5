# Pruebas manuales de la API en Swagger

## Caso 1: Crear una solicitud
* **Endpoint usado:** POST /api/solicitudes
* **Datos he enviados:**
	{
		"descripcion": "Solicitud de prueba Swagger1"
	}
 * **Código devuelto por el servidor:** 201
 * **Datos devueltos por el servidor:**
 	{
		"id": 1,
		"estado": "EN_PROCESO",
		"descripcion": "Solicitud de prueba Swagger1",
		"cliente": null
	}
	
## Caso 2: Registrar un nuevo técnico
* **Endpoint usado:** POST /api/tecnicos
* **Datos he enviados:** 
	{
  		"nombre": "Carlos Pérez",
  		"especialidad": "Redes y Sistemas"
	}
 * **Código devuelto por el servidor:** 201
 * **Datos devueltos por el servidor:**
	{
		  "id": 1,
		  "nombre": "Carlos Pérez",
		  "especialidad": "Redes y Sistemas",
		  "activo": true,
		  "experiencia": 0
	}
	
## Caso 3: Registrar un nuevo cliente
* **Endpoint usado:** POST /api/clientes
* **Datos he enviados:** 
	{
		  "nombre": "Ana García",
		  "email": "ana@email.com",
		  "tipoCliente": "STANDARD"
	}
 * **Código devuelto por el servidor:** 201
 * **Datos devueltos por el servidor:**
	{
		  "nombre": "Ana García",
		  "email": "ana@email.com",
		  "tipoCliente": "PARTICULAR",
		  "id": 0
	}	
	
## Caso 4: Desactivar un técnico
* **Endpoint usado:** PATCH /api/tecnicos/{id}/desactivar
* **Datos he enviados:** Parámetro en la URL -> id = 2 
 * **Código devuelto por el servidor:** 200
 * **Datos devueltos por el servidor:**
	{
		  "id": 2,
		  "nombre": "Alvaro",
		  "especialidad": "Hardware",
		  "activo": false,
		  "experiencia": 0
	}
	
## Caso 5: Listar técnicos activos
* **Endpoint usado:** GET /api/tecnicos/disponibles
* **Datos he enviados:** Sin parámetros 
 * **Código devuelto por el servidor:** 200
 * **Datos devueltos por el servidor:**
	[
	  {
		    "id": 1,
		    "nombre": "Carlos Pérez",
		    "especialidad": "Redes y Sistemas",
		    "activo": true,
		    "experiencia": 0
	  },
	  {
		    "id": 3,
		    "nombre": "David",
		    "especialidad": "Aprendizage Automático",
		    "activo": true,
		    "experiencia": 0
	  }
	]
	
## Caso 6: Consultar clientes
* **Endpoint usado:** GET /api/clientes/{id}
* **Datos he enviados:** Parámetro en la URL -> id = 1 
 * **Código devuelto por el servidor:** 200
 * **Datos devueltos por el servidor:**
	{
		  "nombre": "Ana García",
		  "email": "ana@email.com",
		  "tipoCliente": "STANDARD",
		  "id": 1
	}	
	
## Caso 7: Modificar datos de un cliente
* **Endpoint usado:** PUT /api/clientes/{id}
* **Datos he enviados:** Parámetro en la URL -> id = 1 
	{
		  "nombre": "Ana García",
		  "email": "anaGarcia@email.com",
		  "tipoCliente": "PREMIUM"
	}
 * **Código devuelto por el servidor:** 200
 * **Datos devueltos por el servidor:**
	{
		  "nombre": "Ana García",
		  "email": "anagarcia@email.com",
		  "tipoCliente": "PREMIUM",
		  "id": 1
	}	
	
## Caso 8: Listar clientes
* **Endpoint usado:** GET /api/clientes
* **Datos he enviados:** Sin parámetros 
 * **Código devuelto por el servidor:** 200
 * **Datos devueltos por el servidor:**
	[
	  {
		    "nombre": "Javier",
		    "email": "javier@email.com",
		    "tipoCliente": "PREMIUM",
		    "id": 2
	  },
	  {
		    "nombre": "Ana García",
		    "email": "anagarcia@email.com",
		    "tipoCliente": "PREMIUM",
		    "id": 1
	  }
	]
	
## Caso 9: Asignar un técnico a una solicitud
* **Endpoint usado:** PUT /api/solicitudes/{solicitudId}/tecnico/{tecnicoId}
* **Datos he enviados:** Parámetros en la URL -> solicitudId = 1, tecnicoId = 1 
 * **Código devuelto por el servidor:** 200
 * **Datos devueltos por el servidor:**
 
## Caso 10: Cerrar una solicitud
* **Endpoint usado:** PUT /api/solicitudes/{id}/cerrar
* **Datos he enviados:** Parámetros en la URL -> id = 1 
 * **Código devuelto por el servidor:** 200
 * **Datos devueltos por el servidor:**
 
## Caso 11: Listar solicitudes
* **Endpoint usado:** GET /api/solicitudes
* **Datos he enviados:** Sin parámetros
 * **Código devuelto por el servidor:** 200
 * **Datos devueltos por el servidor:**
	[
	  {
		    "id": 2,
		    "estado": "EN_PROCESO",
		    "descripcion": "Solicitud de prueba Swagger 2",
		    "cliente": null
	  },
	  {
		    "id": 3,
		    "estado": "EN_PROCESO",
		    "descripcion": "Solicitud de prueba Swagger 3",
		    "cliente": null
	  },
	  {
		    "id": 1,
		    "estado": "CERRADA",
		    "descripcion": "Solicitud de prueba Swagger 1",
		    "cliente": null
	  }
	]

## Caso 12: Reabrir una solicitud
* **Endpoint usado:** PATCH /api/solicitudes/{id}/reabrir
* **Datos he enviados:** Parámetros en la URL -> id = 1 
 * **Código devuelto por el servidor:** 200
 * **Datos devueltos por el servidor:**
 
## Caso 13: Asignar un cliente a una solicitud
* **Endpoint usado:** PATCH /api/solicitudes/{solicitudId}/cliente/{clienteId}
* **Datos he enviados:** Parámetros en la URL -> solicitudId = 1, clienteId = 1 
 * **Código devuelto por el servidor:** 200
 * **Datos devueltos por el servidor:**
	{
		  "id": 1,
		  "estado": "EN_PROCESO",
		  "descripcion": "Solicitud de prueba Swagger 1",
		  "cliente": {
		    "nombre": "Ana García",
		    "email": "anagarcia@email.com",
		    "tipoCliente": "PREMIUM",
		    "id": 1
		  }
	}

## Caso 14: Consultar una solicitud existente
* **Endpoint usado:** GET /api/clientes/{id}
* **Datos he enviados:** Parámetro en la URL -> id = 1 
 * **Código devuelto por el servidor:** 200
 * **Datos devueltos por el servidor:**
	{
		  "id": 1,
		  "estado": "EN_PROCESO",
		  "descripcion": "Solicitud de prueba Swagger 1",
		  "cliente": {
		    "nombre": "Ana García",
		    "email": "anagarcia@email.com",
		    "tipoCliente": "PREMIUM",
		    "id": 1
		  }
	}
	

	
	
	
	
	
	
	
	
	
	
	
	
		
	
	
	
	