-- Tabla para usuarios
CREATE TABLE usuario (
    id SERIAL PRIMARY KEY,
    nombre VARCHAR(255) NOT NULL,
    usuario VARCHAR(255) UNIQUE NOT NULL,
    contrasena VARCHAR(255) NOT NULL
);

-- Tabla para categorías
CREATE TABLE categoria (
    id SERIAL PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL
);


-- Tabla para noticias
CREATE TABLE noticia (
    id SERIAL PRIMARY KEY,
    titulo VARCHAR(255) NOT NULL,
    imagen_url VARCHAR(255),
    descripcion TEXT,
    cuerpo TEXT,
    fecha_publicacion TIMESTAMP NOT NULL,
    fuente VARCHAR(255),
    categoria_id INT REFERENCES categoria(id)
);

-- Tabla para noticias recomendadas (relación muchos a muchos)
CREATE TABLE noticia_recomendada (
    id SERIAL PRIMARY KEY,
    noticia_id INT REFERENCES noticia(id)
);
