ALTER TABLE `usuario` ADD COLUMN `claveResetearContrasenya` varchar(255) DEFAULT NULL;
ALTER TABLE `usuario` ADD COLUMN `fechaClaveResetearContrasenya` datetime DEFAULT NULL;
INSERT INTO `sec_ace` (`aceType`, `idPermission`, `ididentity`, `secureResourceRegExp`, `conditionalScript`, `conditionalExpression`, `priority`, `description`) VALUES ('Allow', '22', '1', 'UsuarioCRUDBusinessProcess.Usuario.enviarMailResetearContrasenya', NULL, NULL, '1', 'Permitir enviar mail resetear contrasenya');
INSERT INTO `sec_ace` (`aceType`, `idPermission`, `ididentity`, `secureResourceRegExp`, `conditionalScript`, `conditionalExpression`, `priority`, `description`) VALUES ('Allow', '22', '1', 'UsuarioCRUDBusinessProcess.Usuario.resetearContrasenya', NULL, NULL, '1', 'Permitir resetear password');