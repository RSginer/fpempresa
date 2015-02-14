CREATE TABLE `titulado` (
  `idTitulado` int(11) NOT NULL AUTO_INCREMENT,
  `fechaNacimiento` datetime DEFAULT NULL,
  `tipoVia` int(11) DEFAULT NULL,
  `nombreVia` varchar(255) DEFAULT NULL,
  `otrosDireccion` varchar(255) DEFAULT NULL,
  `codigoPostal` varchar(255) DEFAULT NULL,
  `idProvincia` int(11) DEFAULT NULL,
  `idMunicipio` int(11) DEFAULT NULL,
  `telefono` varchar(255) DEFAULT NULL,
  `telefonoAlternativo` varchar(255) DEFAULT NULL,
  `tipoDocumento` int(11) DEFAULT NULL,
  `numeroDocumento` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`idTitulado`),
  KEY `KEY_TITULADO_PROVINCIA` (`idProvincia`),
  KEY `KEY_TITULADO_MUNICIPIO` (`idMunicipio`),
  CONSTRAINT `KEY_TITULADO_PROVINCIA` FOREIGN KEY (`idProvincia`) REFERENCES `provincia` (`idProvincia`),
  CONSTRAINT `KEY_TITULADO_MUNICIPIO` FOREIGN KEY (`idMunicipio`) REFERENCES `municipio` (`idMunicipio`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `experiencialaboral` (
  `idExperienciaLaboral` int(11) NOT NULL AUTO_INCREMENT,
  `nombreEmpresa` varchar(255) DEFAULT NULL,
  `fechaInicio` datetime DEFAULT NULL,
  `fechaFin` datetime DEFAULT NULL,
  `puestoTrabajo` varchar(255) DEFAULT NULL,
  `descripcion` varchar(255) DEFAULT NULL,
  `idTitulado` int(11) DEFAULT NULL,
  PRIMARY KEY (`idExperienciaLaboral`),
  KEY `KEY_EXPERIENCIALABORAL_TITULADO` (`idTitulado`),
  CONSTRAINT `KEY_EXPERIENCIALABORAL_TITULADO` FOREIGN KEY (`idTitulado`) REFERENCES `titulado` (`idTitulado`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



CREATE TABLE `formacionacademica` (
  `idFormacionAcademica` int(11) NOT NULL AUTO_INCREMENT,
  `tipoFormacionAcademica` int(11) DEFAULT NULL,
  `otroCentro` varchar(255) DEFAULT NULL,
  `otroTitulo` varchar(255) DEFAULT NULL,
  `idCentro` int(11) DEFAULT NULL,
  `idCiclo` int(11) DEFAULT NULL,
  `idTitulado` int(11) DEFAULT NULL,
  PRIMARY KEY (`idFormacionAcademica`),
  KEY `FK7C4E0018EE23894D` (`idCentro`),
  KEY `FK7C4E0018E6B99AB0` (`idCiclo`),
  KEY `FK7C4E00184C28477E` (`idTitulado`),
  CONSTRAINT `FK7C4E00184C28477E` FOREIGN KEY (`idTitulado`) REFERENCES `titulado` (`idTitulado`),
  CONSTRAINT `FK7C4E0018E6B99AB0` FOREIGN KEY (`idCiclo`) REFERENCES `ciclo` (`idCiclo`),
  CONSTRAINT `FK7C4E0018EE23894D` FOREIGN KEY (`idCentro`) REFERENCES `centro` (`idCentro`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `tituloidioma` (
  `idTituloIdioma` int(11) NOT NULL AUTO_INCREMENT,
  `fecha` datetime DEFAULT NULL,
  `idioma` int(11) DEFAULT NULL,
  `otroIdioma` varchar(255) DEFAULT NULL,
  `nivelIdioma` int(11) DEFAULT NULL,
  `idTitulado` int(11) DEFAULT NULL,
  PRIMARY KEY (`idTituloIdioma`),
  KEY `FK51BC8E0E4C28477E` (`idTitulado`),
  CONSTRAINT `FK51BC8E0E4C28477E` FOREIGN KEY (`idTitulado`) REFERENCES `titulado` (`idTitulado`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;