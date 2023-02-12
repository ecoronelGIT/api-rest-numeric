# api-rest-numeric

Esta API está construida con Java 17 y Spring Boot. Utilizamos maven para el manejo de dependencias y el build de la aplicación.

* Para la base de datos se utiliza *postgres*, con flyway para generar las tablas y llevar una versión de los scripts que se ejecuten. También vamos a utilizar *redis* para guardar valores en el cache.

* Para el manejo de la comunicación con clientes vamos a utilizar *Feign Client*, que tiene la posibilidad de configurar reintentos.

* También vamos a utilizar spring actuator para agregar varias funcionalidades de una manera simple y controlada:
  - Un /health para que al momento de deployar podamos hacer una consulta de si el servicio esta corriendo.
  - /info y prometheus para poder obtener métricas de la JVM, asi poder monitorear la aplicación (mediante alguna herramienta como Grafana)

* Para la restricción de llamadas a la API vamos a utilizar resilience4j. Donde podremos especificar con algunas configuraciones cuantos request se pueden realizar por minuto

* El testeo se realiza con spring-boot-test. Vamos a utilizar unos isolation test, la idea es no hacer test de unidad salvo si tenemos casos donde la lógica no puede accederse mediante unn endpoint. Con estos test vamos a probar todo el flujo de la aplicación (pegada al endpoint, lógica de negocio, guardado en base de datos, guardado en cache y el llamado al cliente con los valores controlados)
  - Utilizamos H2 como base embebida.
  - Una librería de it.ozimov para embeber redis.
  - Mock Server para hacer el mockeo del llamado al cliente y par realizar el llamado a los endpoints que generemos.
  

#### Mock de Servicio

Vamos a utilizar un servicio gratuito para hacer un mock de la llamada y generar una respuesta.

Proyecto Generado: https://mockapi.io/projects/63e567af34cbbece435f7f93

URL para obtener la respuesta: https://mocki.io/v1/858d2783-8c82-4c7c-ae21-248cf7dfdd7b

Body de respuesta:
```
{
  "percentage": 10
}
```

### Levantar la aplicación

#### Instalar imagen en docker
Utilizamos el comando para generar la imagen, spring-boot tiene ya esta funcionalidad que nos facilita el proceso.
```
mvn spring-boot:build-image -Dspring-boot.build-image.imageName=numeric-api
```

puede necesitar hacer un *docker login* para poder registrar la imagen

#### Correr la aplicación
Solo necesitamos ejecutar el docker compose donde ya esta configurado todo
```
docker-compose up
```
#### Imagen disponible en hub.docker.com
Con el siguiente comando bajamos la imagen pública disponible
```
docker pull ezequielcoronel/numeric-repo:numeric-api
```

Después en el docker-compose descomentar la línea 

```
#image: ezequielcoronel/numeric-repo:numeric-api
```

borrar la línea duplicada, y ejecutar *docker-compose up*

###Swagger
Una vez iniciada la app, se puede acceder utilizando la siguiente URL

http://localhost:8080/numeric-api/swagger-ui/index.html