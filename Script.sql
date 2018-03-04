CREATE TABLE USUARIO (
  ID_USUARIO INT AUTO_INCREMENT PRIMARY KEY,
  NOMBRE VARCHAR(50) NOT NULL,
  APELLIDO_PATERNO VARCHAR(50) NOT NULL,
  APELLIDO_MATERNO VARCHAR(50) NOT NULL,
  EMAIL VARCHAR(50) NOT NULL UNIQUE,
  KEYWORD VARCHAR(100) NOT NULL,
  FECHA_NACIMIENTO DATE NOT NULL,
  SEXO VARCHAR(10) NOT NULL
);

CREATE TABLE MEDICO (
  ID_USUARIO INT PRIMARY KEY,
  CEDULA_PROFESIONAL VARCHAR(20) NOT NULL UNIQUE,

  CONSTRAINT FK_USUARIO_MEDICO FOREIGN KEY(ID_USUARIO) REFERENCES USUARIO(ID_USUARIO)
);

CREATE TABLE PACIENTE (
  ID_USUARIO INT PRIMARY KEY,
  ID_MEDICO INT NOT NULL,

  CONSTRAINT FK_USUARIO_PACIENTE FOREIGN KEY(ID_USUARIO) REFERENCES USUARIO(ID_USUARIO),
  CONSTRAINT FK_MEDICO_PACIENTE FOREIGN KEY(ID_MEDICO) REFERENCES MEDICO(ID_USUARIO)
);

CREATE TABLE REGISTRO_GLUCOSA (
  ID_REGISTRO_GLUCOSA INT AUTO_INCREMENT PRIMARY KEY,
  ID_PACIENTE INT NOT NULL,
  FECHA_REGISTRO TIMESTAMP NOT NULL,
  FECHA_ACTUALIZACION TIMESTAMP NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  AZUCAR DOUBLE(18, 2) NOT NULL,

  CONSTRAINT FK_PACIENTE_RG FOREIGN KEY(ID_PACIENTE) REFERENCES PACIENTE(ID_USUARIO)
);

CREATE TABLE TOKEN_MEDICO (
  TOKEN VARCHAR(10) PRIMARY KEY,
  ID_MEDICO INT NOT NULL,

  CONSTRAINT FK_MEDICO_TOKEN_MEDICO FOREIGN KEY(ID_MEDICO) REFERENCES MEDICO(ID_USUARIO)
);

CREATE TABLE HISTORIAL_CLINICO (
  ID_HISTORIAL_CLINICO INT AUTO_INCREMENT PRIMARY KEY,
  ID_PACIENTE INT NOT NULL,
  FECHA TIMESTAMP NOT NULL,
  PESO DOUBLE(18, 2) NOT NULL,
  TALLA DOUBLE(18, 2) NOT NULL,
  ESTATURA DOUBLE(18, 2) NOT NULL,
  IMC DOUBLE(18, 2) NOT NULL,
  LIPIDOS DOUBLE(18, 2) NOT NULL,
  CARBOHIDRATOS DOUBLE(18, 2) NOT NULL,
  PROTEINAS DOUBLE(18, 2) NOT NULL,
  AZUCAR DOUBLE(18, 2) NOT NULL,

  CONSTRAINT FK_PACIENTE_HC FOREIGN KEY(ID_PACIENTE) REFERENCES PACIENTE(ID_USUARIO)
);

CREATE TABLE DIETA (
  ID_DIETA INT AUTO_INCREMENT PRIMARY KEY,
  ID_PACIENTE INT NOT NULL,
  ID_MEDICO INT NOT NULL,
  DESCRIPCION VARCHAR(50) NOT NULL,
  ALIMENTOS_DISPONIBLES JSON,

  CONSTRAINT FK_PACIENTE_DIETA FOREIGN KEY(ID_PACIENTE) REFERENCES PACIENTE(ID_USUARIO),
  CONSTRAINT FK_MEDICO_DIETA FOREIGN KEY(ID_MEDICO) REFERENCES MEDICO(ID_USUARIO)
);

CREATE TABLE ALIMENTOS (
  ID_ALIMENTO INT AUTO_INCREMENT PRIMARY KEY,
  TIPO_ALIMENTO VARCHAR(45) NOT NULL,
  ALIMENTO VARCHAR(45) NOT NULL,
  CANTIDAD VARCHAR(45) NOT NULL,
  UNIDAD VARCHAR(45) NOT NULL,
  PESO_BGR INT NOT NULL,
  PESO_NGR INT NOT NULL,
  ENERGIA DOUBLE(18,2) NOT NULL,
  PROTEINA DOUBLE(18,2) NOT NULL,
  LIPIDOS DOUBLE(18,2) NOT NULL,
  CARBOHIDRATOS DOUBLE(18,2) NOT NULL,
  FIBRA_GR DOUBLE(18,2) NOT NULL,
  VITAMINA_A DOUBLE(18,2) NOT NULL,
  ACIDO_ASCORBICO DOUBLE(18,2) NOT NULL,
  ACIDO_FOLICO DOUBLE(18,2) NOT NULL,
  HIERRO_NO DOUBLE(18,2) NOT NULL,
  POTASIO DOUBLE(18,2) NOT NULL,
  AZUCAR_GR DOUBLE(18,2) NOT NULL
);

DELIMITER //
CREATE PROCEDURE SP_MEDICO_ALTA (
  IN _nombre VARCHAR(50),
  IN _apellidoPaterno VARCHAR(50),
  IN _apellidoMaterno VARCHAR(50),
  IN _email VARCHAR(50),
  IN _keyword VARCHAR(100),
  IN _fechaNacimiento DATE,
  IN _sexo VARCHAR(10),
  IN _cedulaProfesional VARCHAR(20)
)
BEGIN
  DECLARE vSalt VARCHAR(10);
  DECLARE vKeyConv VARCHAR(100);
  DECLARE vDigPass VARCHAR(100);
  DECLARE vKeyTr VARCHAR(100);
  DECLARE vI INT;
  DECLARE nvoId INT;

  SELECT COALESCE(MAX(ID_USUARIO) + 1, 1) INTO nvoId
  FROM USUARIO;

  SELECT SUBSTRING(MD5(RAND()) FROM 1 FOR 8) INTO vSalt;
  SET vKeyConv = CONVERT(_keyword, BINARY);

  SET vKeyTr = CONCAT(vKeyConv, vSalt);

  SET vI = 0;
  REPEAT
    SET vKeyTr = MD5(vKeyTr);
    SET vI = vI + 1;
  UNTIL vI > 2330 END REPEAT;

  SET vDigPass = TO_BASE64(CONCAT(vSalt, vKeyTr));
  
  INSERT INTO USUARIO
  VALUES(
    nvoId,
    _nombre,
    _apellidoPaterno,
    _apellidoMaterno,
    _email,
    vDigPass,
    _fechaNacimiento,
    _sexo
  );

  INSERT INTO MEDICO
  VALUES(
    nvoId,
    _cedulaProfesional
  );

END //

CREATE PROCEDURE SP_PACIENTE_ALTA (
  IN _nombre VARCHAR(50),
  IN _apellidoPaterno VARCHAR(50),
  IN _apellidoMaterno VARCHAR(50),
  IN _email VARCHAR(50),
  IN _keyword VARCHAR(100),
  IN _fechaNacimiento DATE,
  IN _sexo VARCHAR(10),
  IN _codigoMedico VARCHAR(10)
)
BEGIN
  DECLARE vSalt VARCHAR(10);
  DECLARE vKeyConv VARCHAR(100);
  DECLARE vDigPass VARCHAR(100);
  DECLARE vKeyTr VARCHAR(100);
  DECLARE vI INT;
  DECLARE nvoId INT;
  DECLARE _idMedico INT DEFAULT -1;

  SELECT COALESCE(MAX(ID_USUARIO) + 1, 1) INTO nvoId
  FROM USUARIO;

  SELECT SUBSTRING(MD5(RAND()) FROM 1 FOR 8) INTO vSalt;
  SET vKeyConv = CONVERT(_keyword, BINARY);

  SET vKeyTr = CONCAT(vKeyConv, vSalt);

  SET vI = 0;
  REPEAT
    SET vKeyTr = MD5(vKeyTr);
    SET vI = vI + 1;
  UNTIL vI > 2330 END REPEAT;

  SET vDigPass = TO_BASE64(CONCAT(vSalt, vKeyTr));
  
  SELECT COALESCE(ID_MEDICO, -1) INTO _idMedico
  FROM TOKEN_MEDICO WHERE TOKEN = _codigoMedico;
  
  INSERT INTO USUARIO
  VALUES(
    nvoId,
    _nombre,
    _apellidoPaterno,
    _apellidoMaterno,
    _email,
    vDigPass,
    _fechaNacimiento,
    _sexo
  );

  INSERT INTO PACIENTE
  VALUES(
    nvoId,
    _idMedico
  );

  DELETE FROM TOKEN_MEDICO 
  WHERE TOKEN = _codigoMedico;

END //

CREATE PROCEDURE SP_USUARIO_LOGIN (
  IN _email VARCHAR(100),
  IN _keyword VARCHAR(100),
  OUT _correcto BOOLEAN
)
BEGIN
  DECLARE vSalt VARCHAR(10);
  DECLARE vKeyConv VARCHAR(100);
  DECLARE vDigPass VARCHAR(100);
  DECLARE vKeyTr VARCHAR(100);
  DECLARE vStKey VARCHAR(100);
  DECLARE vI INT;

  SELECT SUBSTRING(FROM_BASE64(KEYWORD) FROM 1 FOR 8) INTO vSalt
  FROM USUARIO
  WHERE EMAIL = _email;

  SET vKeyConv = CONVERT(_keyword, BINARY);
  SET vKeyTr = CONCAT(vKeyConv, vSalt);

  SET vI = 0;
  REPEAT
    SET vKeyTr = MD5(vKeyTr);
    SET vI = vI + 1;
  UNTIL vI > 2330 END REPEAT;

  SET vDigPass = TO_BASE64(CONCAT(vSalt, vKeyTr));

  SELECT KEYWORD INTO vStKey
  FROM USUARIO
  WHERE EMAIL = _email;

  IF STRCMP(vStKey, vDigPass) = 0 THEN
    SELECT ID_USUARIO INTO _correcto
    FROM USUARIO
    WHERE EMAIL = _email;
  ELSE
    SET _correcto = -1;
  END IF;
  
END //
DELIMITER ;



/*
********CAMBIOS
*/

ALTER TABLE ALIMENTOS ADD COLUMN (
	CARGA_GL VARCHAR(5),
	AZUCAR_PE VARCHAR(5),	
	COLESTEROL VARCHAR(5),
	AG_SATURADOS VARCHAR(5),
	AG_MSATURADOS VARCHAR(5),
	AG_PSATURADOS VARCHAR(5),
	CALCIO VARCHAR(5),	
	SELENIO VARCHAR(5),
	FOSFORO VARCHAR(5),
	POTASIO_P VARCHAR(5),
	HIERRO VARCHAR(5),	
	SODIO VARCHAR(5)
);

LOAD DATA LOCAL INFILE "C:\\Users\\Edgar\\Desktop\\Comidas-copia.csv" INTO TABLE CERESDV.ALIMENTOS CHARACTER SET 'utf8' FIELDS TERMINATED BY ',' ENCLOSED BY '\"' LINES TERMINATED BY '\n'
