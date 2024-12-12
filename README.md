# SpringBoot-Backend 

Este proyecto tiene como finalidad explicar los apartados esenciales para operar en Spring Boot.

## Instalación de Maven

Para instalar Maven, sigue estos pasos:

1. Ve a su [Página Oficial](https://maven.apache.org/download.cgi) y descarga la versión `bin.zip` para Windows.

2. Descomprime el archivo descargado y coloca la carpeta en algún lugar del disco duro, preferiblemente en la raíz (por ejemplo, `C:\` o `D:\`).

3. Accede a la carpeta descomprimida. Asegúrate de que contiene subcarpetas como `bin`, `boot`, `conf`, `lib` y otros archivos.

4. Copia la dirección completa de esta carpeta. Luego, agrégala a las **variables de entorno** de tu sistema:
   - Abre el menú de **Variables de Entorno**.
   - Selecciona la variable `Path` y haz clic en **Editar**.
   - Agrega la dirección de la carpeta (por ejemplo: `C:\Maven\apache-maven-3.9.9`).

5. Guarda los cambios, acepta todo y verifica que Maven está correctamente instalado ejecutando el siguiente comando en el terminal (CMD):

   ```powershell
   mvn -v
   ```
Si todo está configurado correctamente, deberías ver información sobre la versión de Maven instalada.

---

## Crear un Proyecto en Maven

Se puede utilizar herramientas como IntelliJ IDEA o visitar la página oficial [Spring Initializr](https://start.spring.io/) para crear el proyecto base.

### Parámetros clave al crear el proyecto:
- **Group**: Define el nombre de la agrupación a la que pertenece el proyecto. Usualmente sigue el formato de un dominio invertido, por ejemplo, `com.nombre`.
- **Artifact**: Es el nombre del proyecto o módulo que estás desarrollando, utilizado para identificarlo dentro de la estructura de tu aplicación.
- **Name**: Define el nombre de la carpeta raíz donde se generarán todos los archivos relacionados con el proyecto.
- **Package Name**: Es el nombre completo del paquete principal donde se alojarán las clases base del proyecto, generado combinando `Group` y `Artifact`.

## Instalar librerías presentes en el `pom.xml`

Para instalar librerias presentes en pom.xml, utilizar el siguiente comando en la raíz del proyecto:

```powershell
mvn clean install
```

## Instalar librerías en Spring Boot

1. Visitar [Maven Repository](https://mvnrepository.com/) y busca la librería deseada en el campo de búsqueda.
2. Una vez encontrada, haz clic en el resultado correspondiente.
3. Aparecerán diferentes versiones; seleccionar la que se necesite.
4. Copiar el código de dependencia y luego pegarlo en el archivo `pom.xml` dentro del área de dependencias.
![LibreriaMaven](https://github.com/user-attachments/assets/26cd6537-f007-4c87-bcd4-af77746b9a53)
5. Posteriormente se procede a realizar los siguientes pasos:
![DescargarLibreriasIntelliDJ](https://github.com/user-attachments/assets/ecffbdf4-26ce-461f-a887-f77ba1bd0c0f)
6. O bien utilizar el comando anteriormente mencionado:
```powershell
mvn clean install
```
## Conectar Spring Boot con una Base de Datos

Esta guía describe los pasos para configurar Spring Boot con PostgreSQL, incluyendo las dependencias necesarias y la explicación detallada del archivo `application.properties`.

---

### Instalación de Librerías

Asegúrate de incluir las siguientes dependencias en tu archivo `pom.xml` para habilitar la conexión con PostgreSQL:

#### Dependencias clave en el `pom.xml

```xml
<dependencies>
    <!-- Spring Data JPA para interacción con bases de datos -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-data-jpa</artifactId>
    </dependency>
    
    <!-- Driver PostgreSQL -->
    <dependency>
        <groupId>org.postgresql</groupId>
        <artifactId>postgresql</artifactId>
        <scope>runtime</scope>
    </dependency>
</dependencies>
```
- `spring-boot-starter-data-jpa`: Incluye herramientas esenciales para trabajar con JPA (Java Persistence API), que es una especificación para gestionar bases de datos relacionales.
- `postgresql`: Es el driver que permite la conexión entre Spring Boot y la base de datos PostgreSQL.

### Configuración del archivo `application.properties`
Este archivo se encuentra en la carpeta src/main/resources del proyecto y es fundamental para definir las propiedades de la aplicación, incluyendo la conexión con la base de datos.
```yml
# Nombre de la aplicación
spring.application.name=Backend

# Configuración de la base de datos PostgreSQL
spring.datasource.url=jdbc:postgresql://localhost:5432/dbnoticias
spring.datasource.username=admin
spring.datasource.password=not1c1asGT!
spring.datasource.driver-class-name=org.postgresql.Driver

# Configuración de Hibernate (opcional)
spring.jpa.hibernate.ddl-auto=update
spring.jpa.database-platform=org.hibernate.dialect.PostgreSQLDialect

# Habilitar el log de SQL (opcional)
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
```
- **spring.application.name=Backend**:
Define el nombre de la aplicación. Es útil para identificar la instancia si se está ejecutando en un entorno distribuido o con múltiples aplicaciones.

- **spring.datasource.url=jdbc:postgresql://localhost:5432/dbnoticias**:
Especifica la URL de conexión a la base de datos.
    - **jdbc:postgresql**: Protocolo JDBC para bases de datos PostgreSQL.
    - **localhost**: Dirección del servidor donde se aloja la base de datos (en este caso, el equipo local).
    - **5432**: Puerto por defecto de PostgreSQL.
    - **dbnoticias**: Nombre de la base de datos a la que nos conectaremos.
- **spring.datasource.username=admin**:
Nombre de usuario autorizado para acceder a la base de datos.

- **spring.datasource.password=not1c1asGT!**:
Contraseña asociada al usuario anterior.

- **spring.datasource.driver-class-name=org.postgresql.Driver**: 
Especifica el driver necesario para conectar con PostgreSQL.

- **spring.jpa.hibernate.ddl-auto=update**:
Configura el comportamiento de Hibernate para el manejo del esquema de la base de datos:
    - **update**: Actualiza automáticamente las tablas según las entidades del modelo.
    Otros valores posibles: create, create-drop, validate.

