# Pruebas manuales de la API en Swagger

## Caso 1: Crear una solicitud correctamente
* **Endpoint usado:** POST /api/solicitudes
* **Datos he enviados:**
	JSON
	{
		"descripcion": "Solicitud de prueba Swagger"
	}
 * **Código devuelto por el servidor:** 201
 * **Datos devueltos por el servidor:**
 	JSON
 	{
		"id": 1,
		"estado": "EN_PROCESO",
		"descripcion": "Solicitud de prueba Swagger"
	}

## Caso 2: Error al crear una solicitud vacía
* **Endpoint usado:** POST /api/solicitudes
* **Datos he enviados:**
	{
		"descripcion": ""
	}
 * **Código devuelto por el servidor:** 400
 * **Datos devueltos por el servidor:**
 	{
  		"timestamp": "2026-05-13T10:20:11.470+00:00",
  		"status": 400,
  		"error": "Bad Request",
  		"trace": "org.springframework.web.bind.MethodArgumentNotValidException: Validation failed for argument [0] in public 		org.springframework.http.ResponseEntity<com.mgcss.api.dto.SolicitudResponseDTO> 		com.mgcss.api.controller.SolicitudController.crearSolicitud(com.mgcss.api.dto.SolicitudRequestDTO): [Field error in object 		'solicitudRequestDTO' on field 'descripcion': rejected value []; codes 		[NotBlank.solicitudRequestDTO.descripcion,NotBlank.descripcion,NotBlank.java.lang.String,NotBlank]; arguments 		[org.springframework.context.support.DefaultMessageSourceResolvable: codes [solicitudRequestDTO.descripcion,descripcion]; arguments []; 		default message [descripcion]]; default message [La descripción no puede estar vacía]],
  		"message": "Validation failed for object='solicitudRequestDTO'. Error count: 1",
  		"errors": [
	    {
	      "codes": [
	        "NotBlank.solicitudRequestDTO.descripcion",
	        "NotBlank.descripcion",
	        "NotBlank.java.lang.String",
	        "NotBlank"
	      ],
	      "arguments": [
	        {
	          "codes": [
	            "solicitudRequestDTO.descripcion",
	            "descripcion"
	          ],
	          "arguments": null,
	          "defaultMessage": "descripcion",
	          "code": "descripcion"
	        }
	      ],
	      "defaultMessage": "La descripción no puede estar vacía",
	      "objectName": "solicitudRequestDTO",
	      "field": "descripcion",
	      "rejectedValue": "",
	      "bindingFailure": false,
	      "code": "NotBlank"
	    }
	  ],
	  "path": "/api/solicitudes"
	}
	
## Caso 3: Consultar una solicitud existente
* **Endpoint usado:** GET /api/solicitudes/{id}
* **Datos he enviados:** Parámetro en la URL -> id = 1 -> http://localhost:8080/api/solicitudes/1
 * **Código devuelto por el servidor:** 200
 * **Datos devueltos por el servidor:**
	{
	  "id": 1,
	  "estado": "EN_PROCESO",
	  "descripcion": "Solicitud de prueba Swagger"
	}
	
## Caso 4: Asignar un técnico a una solicitud
* **Endpoint usado:** PUT /api/solicitudes/{solicitudId}/tecnico/{tecnicoId}
* **Datos he enviados:** Parámetro en la URL -> solicitudId = 1, tecnicoId = 1 -> http://localhost:8080/api/solicitudes/1/tecnico/1
 * **Código devuelto por el servidor:** 500
 * **Datos devueltos por el servidor:**
		{
		  "timestamp": "2026-05-13T10:37:09.791+00:00",
		  "status": 500,
		  "error": "Internal Server Error",
		  "trace": "java.lang.IllegalArgumentException: El técnico especificado no existe\r\n\tat 	com.mgcss.service.SolicitudService.lambda$0(SolicitudService.java:29)\r\n\tat java.base/java.util.Optional.orElseThrow(Optional.java:403)\r\n\tat com.mgcss.service.SolicitudService.asignarTecnico(SolicitudService.java:29)\r\n\tat com.mgcss.api.controller.SolicitudController.asignarTecnico(SolicitudController.java:68)\r\n\tat java.base/jdk.internal.reflect.DirectMethodHandleAccessor.invoke(DirectMethodHandleAccessor.java:103)\r\n\tat java.base/java.lang.reflect.Method.invoke(Method.java:580)\r\n\tat org.springframework.web.method.support.InvocableHandlerMethod.doInvoke(InvocableHandlerMethod.java:255)\r\n\tat org.springframework.web.method.support.InvocableHandlerMethod.invokeForRequest(InvocableHandlerMethod.java:188)\r\n\tat org.springframework.web.servlet.mvc.method.annotation.ServletInvocableHandlerMethod.invokeAndHandle(ServletInvocableHandlerMethod.java:118)\r\n\tat org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter.invokeHandlerMethod(RequestMappingHandlerAdapter.java:926)\r\n\tat org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter.handleInternal(RequestMappingHandlerAdapter.java:831)\r\n\tat org.springframework.web.servlet.mvc.method.AbstractHandlerMethodAdapter.handle(AbstractHandlerMethodAdapter.java:87)\r\n\tat org.springframework.web.servlet.DispatcherServlet.doDispatch(DispatcherServlet.java:1089)\r\n\tat org.springframework.web.servlet.DispatcherServlet.doService(DispatcherServlet.java:979)\r\n\tat org.springframework.web.servlet.FrameworkServlet.processRequest(FrameworkServlet.java:1014)\r\n\tat org.springframework.web.servlet.FrameworkServlet.doPut(FrameworkServlet.java:925)\r\n\tat jakarta.servlet.http.HttpServlet.service(HttpServlet.java:593)\r\n\tat org.springframework.web.servlet.FrameworkServlet.service(FrameworkServlet.java:885)\r\n\tat jakarta.servlet.http.HttpServlet.service(HttpServlet.java:658)\r\n\tat org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:206)\r\n\tat org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:150)\r\n\tat org.apache.tomcat.websocket.server.WsFilter.doFilter(WsFilter.java:51)\r\n\tat org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:175)\r\n\tat org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:150)\r\n\tat org.springframework.web.filter.RequestContextFilter.doFilterInternal(RequestContextFilter.java:100)\r\n\tat org.springframework.web.filter.OncePerRequestFilter.doFilter(OncePerRequestFilter.java:116)\r\n\tat org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:175)\r\n\tat org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:150)\r\n\tat org.springframework.web.filter.FormContentFilter.doFilterInternal(FormContentFilter.java:93)\r\n\tat org.springframework.web.filter.OncePerRequestFilter.doFilter(OncePerRequestFilter.java:116)\r\n\tat org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:175)\r\n\tat org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:150)\r\n\tat org.springframework.web.filter.CharacterEncodingFilter.doFilterInternal(CharacterEncodingFilter.java:201)\r\n\tat org.springframework.web.filter.OncePerRequestFilter.doFilter(OncePerRequestFilter.java:116)\r\n\tat org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:175)\r\n\tat org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:150)\r\n\tat org.apache.catalina.core.StandardWrapperValve.invoke(StandardWrapperValve.java:167)\r\n\tat org.apache.catalina.core.StandardContextValve.invoke(StandardContextValve.java:90)\r\n\tat org.apache.catalina.authenticator.AuthenticatorBase.invoke(AuthenticatorBase.java:482)\r\n\tat org.apache.catalina.core.StandardHostValve.invoke(StandardHostValve.java:115)\r\n\tat org.apache.catalina.valves.ErrorReportValve.invoke(ErrorReportValve.java:93)\r\n\tat org.apache.catalina.core.StandardEngineValve.invoke(StandardEngineValve.java:74)\r\n\tat org.apache.catalina.connector.CoyoteAdapter.service(CoyoteAdapter.java:344)\r\n\tat org.apache.coyote.http11.Http11Processor.service(Http11Processor.java:391)\r\n\tat org.apache.coyote.AbstractProcessorLight.process(AbstractProcessorLight.java:63)\r\n\tat org.apache.coyote.AbstractProtocol$ConnectionHandler.process(AbstractProtocol.java:896)\r\n\tat org.apache.tomcat.util.net.NioEndpoint$SocketProcessor.doRun(NioEndpoint.java:1736)\r\n\tat org.apache.tomcat.util.net.SocketProcessorBase.run(SocketProcessorBase.java:52)\r\n\tat org.apache.tomcat.util.threads.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1191)\r\n\tat org.apache.tomcat.util.threads.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:659)\r\n\tat org.apache.tomcat.util.threads.TaskThread$WrappingRunnable.run(TaskThread.java:63)\r\n\tat java.base/java.lang.Thread.run(Thread.java:1583)\r\n",
		  "message": "El técnico especificado no existe",
		  "path": "/api/solicitudes/1/tecnico/1"
		}
		
## Caso 5: Cerrar la solicitud
* **Endpoint usado:** PUT /api/solicitudes/{id}/cerrar
* **Datos he enviados:** Parámetro en la URL -> id = 1 -> http://localhost:8080/api/solicitudes/1/cerrar
 * **Código devuelto por el servidor:** 200
 * **Datos devueltos por el servidor:**
	{
	  "id": 1,
	  "estado": "EN_PROCESO",
	  "descripcion": "Solicitud de prueba Swagger"
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
		
	
	
	
	