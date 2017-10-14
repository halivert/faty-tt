CREATE TABLE INDIVIDUO( 
  ID_INDIVIDUO INT AUTO_INCREMENT PRIMARY KEY, 
  NOMBRE VARCHAR(50), 
  APELLIDO_PATERNO VARCHAR(50), 
  APELLIDO_MATERNO VARCHAR(50), 
  EMAIL VARCHAR(50), 
  KEYWORD BLOB, 
  FECHA_NACIMIENTO DATE, 
  ID_SEXO INT, 
  ROL INT
);

CREATE TABLE MEDICO(
  ID_MEDICO INT AUTO_INCREMENT PRIMARY KEY,
  ID_INDIVIDUO INT,
  CEDULA_PROFESIONAL VARCHAR(20)
);

CREATE TABLE PACIENTE(
  ID_PACIENTE INT AUTO_INCREMENT PRIMARY KEY,
  ID_INDIVIDUO INT,
  ID_MEDICO INT,
  PESO DOUBLE(18, 2),
  TALLA DOUBLE(18, 2),
  ESTATURA DOUBLE(18, 2),
  IMC DOUBLE(18, 2),
  LIPIDOS DOUBLE(18, 2),
  CARBOHIDRATOS DOUBLE(18, 2),
  PROTEINAS DOUBLE(18, 2)
);

CREATE TABLE SEXO(
  ID_SEXO INT AUTO_INCREMENT PRIMARY KEY,
  DESCRIPCION VARCHAR(50)
);