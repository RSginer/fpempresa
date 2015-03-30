CREATE TABLE `empresa` (
  `idEmpresa` int(11) NOT NULL AUTO_INCREMENT,
  `nombreComercial` varchar(255) DEFAULT NULL,
  `razonSocial` varchar(255) DEFAULT NULL,
  `cif` varchar(9) DEFAULT NULL,
  `tipoVia` varchar(20) DEFAULT NULL,
  `nombreVia` varchar(255) DEFAULT NULL,
  `otrosDireccion` varchar(255) DEFAULT NULL,
  `codigoPostal` varchar(255) DEFAULT NULL,
  `idMunicipio` int(11) DEFAULT NULL,
  `idCentro` int(11) DEFAULT NULL,
  PRIMARY KEY (`idEmpresa`),
  KEY `KEY_EMPRESA_MUNICIPIO` (`idMunicipio`),
  CONSTRAINT `KEY_EMPRESA_MUNICIPIO` FOREIGN KEY (`idMunicipio`) REFERENCES `municipio` (`idMunicipio`),
  KEY `KEY_EMPRESA_Centro` (`idCentro`),
  CONSTRAINT `KEY_EMPRESA_Centro` FOREIGN KEY (`idCentro`) REFERENCES `centro` (`idCentro`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE `oferta` (
    `idOferta` int(11) NOT NULL AUTO_INCREMENT,
    `fecha` date DEFAULT NULL,
    `idEmpresa` integer DEFAULT NULL,
    `puesto` varchar(255) DEFAULT NULL,
    `descripcion` longtext DEFAULT NULL,
    `idMunicipio` integer DEFAULT NULL,
    `idFamilia` integer DEFAULT NULL,
    `tipoOferta` varchar(20) DEFAULT NULL,
    `estadoOferta` varchar(20) DEFAULT NULL,
    PRIMARY KEY  (`idOferta`),
    KEY `KEY_OFERTA_EMPRESA`   (`idEmpresa`)  ,CONSTRAINT `KEY_OFERTA_EMPRESA`   FOREIGN KEY (`idEmpresa`)   REFERENCES `empresa`   (`idEmpresa`),
    KEY `KEY_OFERTA_MUNICIPIO` (`idMunicipio`),CONSTRAINT `KEY_OFERTA_MUNICIPIO` FOREIGN KEY (`idMunicipio`) REFERENCES `municipio` (`idMunicipio`),
    KEY `KEY_OFERTA_FAMILIA`   (`idFamilia`)  ,CONSTRAINT `KEY_OFERTA_FAMILIA`   FOREIGN KEY (`idFamilia`)   REFERENCES `familia`   (`idFamilia`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `ofertaciclo` (
    `idOfertaCiclo` int(11) NOT NULL AUTO_INCREMENT,
    `idOferta` integer not null,
    `idCiclo` integer not null,
    PRIMARY KEY  (`idOfertaCiclo`),
    KEY `KEY_OFERTACICLO_OFERTA`(`idOferta`),CONSTRAINT `KEY_OFERTACICLO_OFERTA` FOREIGN KEY (`idOferta`) REFERENCES `oferta`(`idOferta`),
    KEY `KEY_OFERTACICLO_CICLO` (`idCiclo`) ,CONSTRAINT `KEY_OFERTACICLO_CICLO`  FOREIGN KEY (`idCiclo`)  REFERENCES `ciclo` (`idCiclo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;