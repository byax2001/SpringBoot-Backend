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
- **spring.jpa.database-platform=org.hibernate.dialect.PostgreSQLDialect**: 
Define el dialecto específico de PostgreSQL para que Hibernate traduzca las consultas a SQL compatible con esta base de datos.
- **spring.jpa.show-sql=true**: 
Muestra en la consola las consultas SQL ejecutadas por la aplicación.
- **spring.jpa.properties.hibernate.format_sql=true**: 
Formatea las consultas SQL en la consola para facilitar su lectura.


## Implementación de JWT en Spring Boot

### Archivos clave en la configuración de JWT:
Se crearán los siguientes archivos para implementar la autenticación basada en JWT:

- **ApplicationConfig.java**
- **SecurityConfig.java**
- **JwtAuthenticationFilter.java**
- **JwtService.java**
- **AuthController.java**
- **AuthResponse.java**
- **AuthService.java**
- **Usuario.java**

---

### **1. ApplicationConfig.java**
Este archivo configura el `AuthenticationProvider`, que define cómo se buscará la información completa de un usuario en base a su username o ID. Al extender `UserDetails`, se pueden utilizar métodos como `getPassword()` para comparar la contraseña ingresada con la almacenada en la base de datos.  

- La contraseña ingresada se recibe en texto plano y se codifica usando un codificador hash como `BCryptPasswordEncoder`. Esta codificación se compara con la versión hash almacenada en la base de datos.  
- Si ambas coinciden, se valida correctamente el usuario.  

---

### **2. SecurityConfig.java**
Este archivo define la configuración de seguridad del backend. Entre sus responsabilidades están:
- **Deshabilitar Crsf**: Protecciones contra ataques CSRF se desactivan, ya que se emplea JWT para la autenticación, lo que elimina la necesidad de tokens CSRF.
- **Configurar CORS:** Permite gestionar solicitudes desde orígenes distintos, evitando conflictos al integrar con frameworks frontend como React o Angular. Esto incluye permitir métodos específicos como `OPTIONS` para evitar problemas de preflight requests.
- **Autorización específica:** Define que solo los endpoints relacionados con `/auth` (login y registro) sean accesibles sin autenticación.
- **Desactivar sesiones:** Configura el backend como stateless, asegurando que cada petición sea autenticada por el token JWT.
- **Agregar filtros:** Incluye el `JwtAuthenticationFilter` para manejar la validación de tokens.
- **Construir el `SecurityFilterChain`:** Define el comportamiento de seguridad a nivel global.

---

### **3. JwtAuthenticationFilter.java**
Este filtro maneja la lógica de autenticación de JWT a nivel de peticiones HTTP.

- **doFilterInternal:** 
  - Obtiene el token desde la cabecera `Authorization` de la solicitud.
  - Si el token es nulo o inválido, devuelve un error HTTP 403.
  - Valida el usuario contenido en el token y crea un objeto `UserDetails` si el usuario existe.
  - Verifica si el token es válido antes de autenticar al usuario y proceder con la solicitud.

---

### **4. JwtService.java**
Este servicio contiene la lógica para manejar los tokens JWT.

- **Generación de tokens:**
  - Usa la biblioteca JWT para crear un token basado en los detalles del usuario (`UserDetails`).
  - Utiliza un `HashMap` para añadir claims personalizados al token.
  - Métodos como `setClaims`, `setSubject`, y `setIssuedAt` configuran el contenido del token, incluyendo el nombre del usuario y su tiempo de expiración.
- **Decodificación de tokens:**
  - `getClaims`: Divide las partes del token para obtener y verificar su contenido.
  - `getExpiration`: Extrae la fecha de expiración del token.
  - `isTokenExpired`: Verifica si el token ya expiró.
  - `isTokenValid`: Compara el usuario contenido en el token con los detalles del usuario actual.

---

### **5. AuthController.java**
Define los endpoints para el login y el registro de usuarios:

- **Login:** Valida las credenciales proporcionadas y devuelve un token JWT.
- **Registro:** Crea un nuevo usuario en la base de datos y genera un token JWT para el mismo.

---

### **6. AuthResponse.java**
Este es un modelo simple que encapsula el token JWT generado. Se utiliza como respuesta en los endpoints de autenticación.

```java
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponse {
    private String token;
}
```

---

### **7. AuthService.java**
Este servicio contiene la lógica central para manejar el login y registro de usuarios:

- **Login:** Valida las credenciales ingresadas y genera un token si son correctas.
- **Registro:** Crea un nuevo usuario con la contraseña codificada y lo almacena en la base de datos.

---

### **8. Usuario.java**
Este modelo representa la entidad `Usuario` y extiende la interfaz `UserDetails` de Spring Security.

- **Atributos principales:**
  - `id`, `nombre`, `usuario` y `contrasena`.
- **Métodos adicionales:**
  - Implementa métodos como `getAuthorities`, `getPassword`, y `getUsername` para integrarse con el `AuthenticationProvider`.
- **Roles:** Actualmente asigna de manera predeterminada el rol `ROLE_USER`.

```java
@Override
public Collection<? extends GrantedAuthority> getAuthorities() {
    return List.of(new SimpleGrantedAuthority("ROLE_USER"));
}
```

---