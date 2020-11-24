# Before Execute
En primer lugar debemos compilar la aplicación para ello una vez descargado el codigo con el siguiente comando
	
	git clone -----

Nos colocamos en el directorio del proyecto y ejecutamos la instrucción

	mvn clean install
	
Finalizada la ejecución del comando ejecutamos la siguiente instrucción y esperamos hasta ver en pantalla el siguiente texto:

	mvn spring-boot:run
	
	Started TestProjectApplication in
	
Un vez se muestra este texto en la pantalla ya se encuentra desplegada la aplicación. Podemos probar la aplicación mediante el uso de postman o se ha incluido swagger para poder probarlo directamente sobre dicho archivo, para acceder al swagger lo haremos a traves de:

[Swagger](http://localhost:8080/swagger-ui.html)
	
Se ha inicializado con la aplicación una base de datos h2 donde almacenar la información. Por defecto se crea un registro de prueba que podremos ver en dicha base de datos. Para acceder a la misma lo haremos a traves de la siguiente url:

[BBDD h2](http://localhost:8080/h2)
	
Los datos para acceder son:
	Driver Class: org.h2.Driver
	JDBC URL: jdbc:h2:mem:testdb
	User Name: sa
	Password:
	
Un vez hemos introducido dicho datos pulsamos en Connect y ya tenemos acceso a la base de datos.

Adjunto url de API por si no se quiere utililzar el swagger para probar los diferentes endpoints:

* curl -X GET "http://localhost:8080/api/mimacom/task" -H "accept: application/json"
* curl -X POST "http://localhost:8080/api/mimacom/task" -H "accept: */*" -H "Content-Type: application/json" -d "{\"description\": \"string\",\"title\": \"string\"}"
* curl -X GET "http://localhost:8080/api/mimacom/task/1" -H "accept: */*"
* curl -X PUT "http://localhost:8080/api/mimacom/task" -H "accept: */*" -H "Content-Type: application/json" -d "{ \"description\": \"string\", \"id\": 1, \"title\": \"string\"}"
* curl -X PUT "http://localhost:8080/api/mimacom/task/1" -H "accept: */*"
* curl -X DELETE "http://localhost:8080/api/mimacom/task/1" -H "accept: */*"
	