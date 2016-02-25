CREATE TABLE `usuario` (
  `idIdentity` int(11) NOT NULL,
  `email` varchar(255) DEFAULT NULL,
  `nombre` varchar(255) DEFAULT NULL,
  `apellidos` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `tipoUsuario` varchar(20) DEFAULT NULL,
  `estadoUsuario` varchar(25) DEFAULT NULL,
  `idCentro` int(11) DEFAULT NULL,
  `idTitulado` int(11) DEFAULT NULL,
  `idEmpresa` int(11) DEFAULT NULL,
  `validadoEmail` int(11) DEFAULT NULL,
  `claveValidacionEmail` varchar(255) DEFAULT NULL,
  `foto` MEDIUMBLOB NULL,
  `fecha` datetime DEFAULT NULL,
  PRIMARY KEY (`idIdentity`),
  UNIQUE KEY `eMail` (`eMail`),
  KEY `KEY_USUARIO_Centro` (`idCentro`),
  KEY `KEY_USUARIO_Titulado` (`idTitulado`),
  KEY `KEY_USUARIO_Empresa` (`idEmpresa`),
  CONSTRAINT `KEY_USUARIO_Centro` FOREIGN KEY (`idCentro`) REFERENCES `centro` (`idCentro`),
  CONSTRAINT `KEY_USUARIO_Titulado` FOREIGN KEY (`idTitulado`) REFERENCES `titulado` (`idTitulado`),
  CONSTRAINT `KEY_USUARIO_Empresa` FOREIGN KEY (`idEmpresa`) REFERENCES `empresa` (`idEmpresa`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
