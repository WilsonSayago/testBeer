# testBeer
Tecnologias utilizadas:

- Angular 8, para el fronted.
- SpringBoot 2.1.8.RELEASE, para el backend.

Descripcion Cliente FrontEnd:
- Al momento de desarrollar se tenian las siguientesversiones:
	1- Node v10.16.3
	2- Npm 6.9.0
	3- Angular CLI 8.3.8
- Para iniciar el cliente ejecutar ng serve -o en la carpeta /test-front
- ruta /Beers (Pagina principal), muestra el listado de todas las cervezas.
- ruta /Beers/{id} (Pagina Mostrar - Crear), muestra la cerveza a consultar ademas de contar con un componente
para calcular el monto total por el tipo de moneda y la cantidad de cervezas.


Descripcion API BackEnd:
- Al momento de desarrollar se tenian las siguientesversiones:
	1- Java 1.8
	2- SpringBoot 2.1.8.RELEASE
- Para iniciar el API ejecutar ./mvnw clean install para generar el JAR en la carpeta /spring-boot-backend-apirest, luego ir a la carpeta /spring-boot-backend-apirest\target y ejecutar java -jar spring-boot-backend-apirest-0.0.1-SNAPSHOT.jar 
- Ruta base /beers
- Puerto: 8080
- GET: 
	1- /; regresa todas las cervezas con su informacion.
	2- /{beerID}; busca una cerveza por su id, retorna un Map<?>.
	3- /{beerID}/boxprice; calcula el precio total a cancelar por la informacion de la cerveza ademas del tipo de moneda y la cantidad enviada, retorna un Map<?>.
- POST:
	1- /; guarda una cerveza en la DB, retorna un Map<?>.
- Se realiaron validacion las cuales son retornadas en el Map<?> de la respuesta de cada endpoint con el tag "errors".
- Se implementaron test para los endpoint GET(1,2) y POST(1), manejan su propia DB(test).
- Se manejo la DB H2, la cual es una base de datos en memoria, la cual se pobla al iniciar la aplicacion con el script en la carpeta resource. DB se llama testdb.
- Se consume del API https://currencylayer.com/, la url y el key estan ubicados en el property.
 

