CREATE TABLE `empresa` (
  `idEmpresa` int(11) NOT NULL AUTO_INCREMENT,
  `nombreComercial` varchar(255) DEFAULT NULL,
  `razonSocial` varchar(255) DEFAULT NULL,
  `cif` varchar(9) DEFAULT NULL,
  `tipoVia` varchar(20) DEFAULT NULL,
  `nombreVia` varchar(255) DEFAULT NULL,
  `otrosDireccion` varchar(255) DEFAULT NULL,
  `codigoPostal` varchar(255) DEFAULT NULL,
  `idProvincia` int(11) DEFAULT NULL,
  `idMunicipio` int(11) DEFAULT NULL,
  `idCentro` int(11) DEFAULT NULL,
  PRIMARY KEY (`idEmpresa`),
  KEY `KEY_EMPRESA_PROVINCIA` (`idProvincia`),
  KEY `KEY_EMPRESA_MUNICIPIO` (`idMunicipio`),
  CONSTRAINT `KEY_EMPRESA_MUNICIPIO` FOREIGN KEY (`idProvincia`) REFERENCES `provincia` (`idProvincia`),
  CONSTRAINT `KEY_EMPRESA_PROVINCIA` FOREIGN KEY (`idMunicipio`) REFERENCES `municipio` (`idMunicipio`),
  KEY `KEY_EMPRESA_Centro` (`idCentro`),
  CONSTRAINT `KEY_EMPRESA_Centro` FOREIGN KEY (`idCentro`) REFERENCES `centro` (`idCentro`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;