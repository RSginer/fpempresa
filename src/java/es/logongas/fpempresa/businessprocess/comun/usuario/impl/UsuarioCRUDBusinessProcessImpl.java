/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.logongas.fpempresa.businessprocess.comun.usuario.impl;

import es.logongas.fpempresa.businessprocess.comun.usuario.UsuarioCRUDBusinessProcess;
import es.logongas.fpempresa.modelo.comun.usuario.EstadoUsuario;
import es.logongas.fpempresa.modelo.comun.usuario.TipoUsuario;
import es.logongas.fpempresa.modelo.comun.usuario.Usuario;
import es.logongas.fpempresa.service.comun.usuario.UsuarioCRUDService;
import es.logongas.ix3.businessprocess.impl.CRUDBusinessProcessImpl;
import es.logongas.ix3.core.BusinessException;
import es.logongas.ix3.rule.ActionRule;
import es.logongas.ix3.rule.ConstraintRule;
import es.logongas.ix3.rule.RuleContext;
import es.logongas.ix3.rule.RuleGroupPredefined;
import es.logongas.ix3.security.authorization.BusinessSecurityException;

/**
 *
 * @author logongas
 */
public class UsuarioCRUDBusinessProcessImpl extends CRUDBusinessProcessImpl<Usuario, Integer> implements UsuarioCRUDBusinessProcess {

    @Override
    public void updatePassword(UpdatePasswordArguments updatePasswordArguments) throws BusinessException {
        UsuarioCRUDService usuarioCRUDService = (UsuarioCRUDService) serviceFactory.getService(Usuario.class);
        Usuario principal = (Usuario) updatePasswordArguments.principal;

        if (updatePasswordArguments.usuario == null) {
            throw new BusinessException("No hay usuario al que cambiar la contraseña");
        } else if (principal == null) {
            throw new BusinessException("Debes haber entrado para cambiar la contraseña");
        } else if (updatePasswordArguments.usuario.getIdIdentity() == principal.getIdIdentity()) {
            if (usuarioCRUDService.checkPassword(updatePasswordArguments.dataSession, updatePasswordArguments.usuario, updatePasswordArguments.currentPassword)) {
                usuarioCRUDService.updatePassword(updatePasswordArguments.dataSession, updatePasswordArguments.usuario, updatePasswordArguments.newPassword);
            } else {
                throw new BusinessException("La contraseña actual no es válida");
            }
        } else if (principal.getTipoUsuario() == TipoUsuario.ADMINISTRADOR) {
            //Si eres administrador no necesitas la contraseña actual
            usuarioCRUDService.updatePassword(updatePasswordArguments.dataSession, updatePasswordArguments.usuario, updatePasswordArguments.newPassword);
        } else {
            throw new BusinessException("Solo un Administrador o el propio usuario puede cambiar la contraseña");
        }

    }

    @Override
    public Usuario getUsuarioFromTitulado(GetUsuarioFromTituladoArguments getUsuarioFromTituladoArguments) throws BusinessException {
        UsuarioCRUDService usuarioCRUDService = (UsuarioCRUDService) serviceFactory.getService(Usuario.class);

        return usuarioCRUDService.getUsuarioFromTitulado(getUsuarioFromTituladoArguments.dataSession, getUsuarioFromTituladoArguments.titulado.getIdTitulado());
    }

    @Override
    public byte[] getFoto(GetFotoArguments getFotoArguments) throws BusinessException {
        UsuarioCRUDService usuarioCRUDService = (UsuarioCRUDService) serviceFactory.getService(Usuario.class);

        return usuarioCRUDService.read(getFotoArguments.dataSession, getFotoArguments.usuario.getIdIdentity()).getFoto();
    }

    @Override
    public void updateFoto(UpdateFotoArguments updateFotoArguments) throws BusinessException {
        UsuarioCRUDService usuarioCRUDService = (UsuarioCRUDService) serviceFactory.getService(Usuario.class);

        updateFotoArguments.usuario.setFoto(updateFotoArguments.foto);
        usuarioCRUDService.update(updateFotoArguments.dataSession, updateFotoArguments.usuario);
    }

    @Override
    public Usuario updateEstadoUsuario(UpdateEstadoUsuarioArguments updateEstadoUsuarioArguments) throws BusinessException {
        UsuarioCRUDService usuarioCRUDService = (UsuarioCRUDService) serviceFactory.getService(Usuario.class);

        updateEstadoUsuarioArguments.usuario.setEstadoUsuario(updateEstadoUsuarioArguments.estadoUsuario);
        return usuarioCRUDService.update(updateEstadoUsuarioArguments.dataSession, updateEstadoUsuarioArguments.usuario);
    }

    @Override
    public Usuario updateCentro(UpdateCentroArguments updateCentroArguments) throws BusinessException {
        UsuarioCRUDService usuarioCRUDService = (UsuarioCRUDService) serviceFactory.getService(Usuario.class);

        updateCentroArguments.usuario.setCentro(updateCentroArguments.centro);
        updateCentroArguments.usuario.setEstadoUsuario(EstadoUsuario.PENDIENTE_ACEPTACION);

        return usuarioCRUDService.update(updateCentroArguments.dataSession, updateCentroArguments.usuario);
    }

    @ConstraintRule(message = "Error en el sistema de mensajes en 'isCheckInsertCentro'", groups = RuleGroupPredefined.PreInsert.class)
    private boolean isCheckInsertCentro(RuleContext<Usuario> ruleContext) throws BusinessException {
        Usuario principal = (Usuario) ruleContext.getPrincipal();
        Usuario usuario = ruleContext.getEntity();
        if ((usuario.getTipoUsuario() == TipoUsuario.CENTRO)) {

            businessTrue(usuario.getTitulado() == null, "El titulado está prohibido para usuarios de centro");
            businessTrue(usuario.getEmpresa() == null, "La empresa está prohibida para usuarios de centro");

            //Comprobar el principal que lo inserta
            if ((principal != null) && (principal.getTipoUsuario() == TipoUsuario.ADMINISTRADOR)) {
                securityTrue(principal.getEstadoUsuario() == EstadoUsuario.ACEPTADO, "Debes estar aceptado");

                businessTrue(usuario.getEstadoUsuario() == EstadoUsuario.ACEPTADO, "El usuario debe estar aceptado");
                businessTrue(usuario.getCentro() != null, "El centro es requerido para el usuario");
            } else if ((principal != null) && (principal.getTipoUsuario() == TipoUsuario.CENTRO)) {
                securityTrue(principal.getEstadoUsuario() == EstadoUsuario.ACEPTADO, "Debes estar aceptado");
                securityTrue(principal.getCentro() != null, "Ya debes pertenecer a un centro");

                businessTrue(usuario.getEstadoUsuario() == EstadoUsuario.ACEPTADO, "El usuario debe estar aceptado");
                businessTrue(usuario.getCentro() != null, "El centro es requerido para el usuario");
                businessTrue(principal.getCentro().getIdCentro() == usuario.getCentro().getIdCentro(), "El centro debe ser el mismo centro que el tuyo");
            } else if (principal == null) {
                businessTrue(usuario.getEstadoUsuario() == null, "El estado debe ser vacio");
                businessTrue(usuario.getCentro() == null, "El centro debe ser vacio");
            } else {
                throw new BusinessSecurityException("No tienes permiso para añadir un profesor");
            }

        }

        return true;

    }

    @ConstraintRule(message = "Error en el sistema de mensajes en 'isCheckUpdateCentroAndEstado'", groups = RuleGroupPredefined.PreUpdate.class)
    private boolean isCheckUpdateCentroAndEstado(RuleContext<Usuario> ruleContext) throws BusinessException {
        Usuario principal = (Usuario) ruleContext.getPrincipal();
        Usuario usuario = ruleContext.getEntity();
        Usuario usuarioOriginal = ruleContext.getOriginalEntity();

        if ((usuario.getTipoUsuario() == TipoUsuario.CENTRO)) {

            businessTrue(usuario.getTitulado() == null, "El titulado está prohibido para usuarios de centro");
            businessTrue(usuario.getEmpresa() == null, "La empresa está prohibida para usuarios de centro");

            //Comprobar el principal que lo inserta
            if ((principal != null) && (principal.getTipoUsuario() == TipoUsuario.ADMINISTRADOR)) {
                securityTrue(principal.getEstadoUsuario() == EstadoUsuario.ACEPTADO, "Debes estar aceptado");

                businessTrue(usuario.getCentro() != null, "El centro es requerido para el usuario");
            } else if ((principal != null) && (principal.getTipoUsuario() == TipoUsuario.CENTRO)) {
                securityTrue(principal.getEstadoUsuario() == EstadoUsuario.ACEPTADO, "Debes estar aceptado");
                securityTrue(principal.getCentro() != null, "Ya debes pertenecer a un centro");

                if (principal.getIdIdentity() == usuario.getIdIdentity()) {
                    businessTrue(usuario.getCentro() != null, "El centro es requerido");
                    businessTrue(usuarioOriginal.getCentro() != null, "El usuario ya debía de tener un centro");

                    boolean ahoraTieneCentro = (usuario.getCentro() != null);
                    boolean antesTeniaCentro = (usuarioOriginal.getCentro() != null);

                    if (ahoraTieneCentro && antesTeniaCentro) {
                        if (usuario.getCentro().getIdCentro() != usuarioOriginal.getCentro().getIdCentro()) {
                            businessTrue(usuario.getEstadoUsuario() == EstadoUsuario.PENDIENTE_ACEPTACION, "El profesor debe estar pendiente de aceptación al cambiar de centro");
                        }
                    } else if (ahoraTieneCentro && !antesTeniaCentro) {
                        businessTrue(usuario.getEstadoUsuario() == EstadoUsuario.PENDIENTE_ACEPTACION, "El profesor debe estar pendiente de aceptación al cambiar de centro");
                    } else if (!ahoraTieneCentro && antesTeniaCentro) {
                        throw new BusinessException("No puedes estar sin centro");
                    } else if (!ahoraTieneCentro && !antesTeniaCentro) {
                        throw new BusinessException("No puedes estar sin centro");
                    } else {
                        throw new RuntimeException("Error de logica:" + ahoraTieneCentro + "," + antesTeniaCentro);

                    }

                } else {
                    businessTrue(usuario.getCentro() != null, "El centro es requerido para el usuario");
                    businessTrue(principal.getCentro().getIdCentro() == usuario.getCentro().getIdCentro(), "El centro debe ser el mismo centro que el tuyo");
                }
            } else {
                throw new BusinessSecurityException("No tienes permiso para actualizar un profesor");
            }
        }

        return true;

    }

    @ConstraintRule(message = "Error en el sistema de mensajes en 'isCheckInsertEmpresa'", groups = RuleGroupPredefined.PreInsert.class)
    private boolean isCheckInsertEmpresa(RuleContext<Usuario> ruleContext) throws BusinessException {
        Usuario principal = (Usuario) ruleContext.getPrincipal();
        Usuario usuario = ruleContext.getEntity();

        if ((usuario.getTipoUsuario() == TipoUsuario.EMPRESA)) {

            businessTrue(usuario.getTitulado() == null, "El titulado está prohibido para usuarios de empresa");
            businessTrue(usuario.getCentro() == null, "El centro está prohibido para usuarios de empresa");

            //El principal que lo inserta
            if ((principal != null) && (principal.getTipoUsuario() == TipoUsuario.ADMINISTRADOR)) {
                securityTrue(principal.getEstadoUsuario() == EstadoUsuario.ACEPTADO, "Debes estar aceptado");

                businessTrue(usuario.getEmpresa() != null, "La empresa es requerida para usuarios de una empresa");
            } else if ((principal != null) && (principal.getTipoUsuario() == TipoUsuario.EMPRESA)) {
                securityTrue(principal.getEstadoUsuario() == EstadoUsuario.ACEPTADO, "Debes estar aceptado");
                securityTrue(principal.getEmpresa() != null, "Ya debes pertenecer a una empresa");

                businessTrue(usuario.getEmpresa() != null, "La empresa es requerida para usuarios de una empresa");
                businessTrue(usuario.getEmpresa().getIdEmpresa() == principal.getEmpresa().getIdEmpresa(), "La empresa debe ser la misma empresa que la tuya");
            } else if (principal == null) {
                businessTrue(usuario.getEmpresa() == null, "La empresa está prohibida al insertar el usuario");
            } else {
                throw new BusinessSecurityException("No tienes permiso para añadir un usuario de una empresa");
            }
        }

        return true;

    }

    @ConstraintRule(message = "Error en el sistema de mensajes en 'isCheckUpdateEmpresa'", groups = RuleGroupPredefined.PreUpdate.class)
    private boolean isCheckUpdateEmpresa(RuleContext<Usuario> ruleContext) throws BusinessException {
        Usuario principal = (Usuario) ruleContext.getPrincipal();
        Usuario usuario = ruleContext.getEntity();
        Usuario usuarioOriginal = ruleContext.getOriginalEntity();

        if ((usuario.getTipoUsuario() == TipoUsuario.EMPRESA)) {

            //Comprobar el principal que lo inserta
            if ((principal != null) && (principal.getTipoUsuario() == TipoUsuario.ADMINISTRADOR)) {
                securityTrue(principal.getEstadoUsuario() == EstadoUsuario.ACEPTADO, "Debes estar aceptado");

                businessTrue(usuario.getEstadoUsuario() == EstadoUsuario.ACEPTADO, "El usuario debe estar aceptado");
                businessTrue(usuario.getEmpresa() != null, "La empresa es requerida para el usuario");
            } else if ((principal != null) && (principal.getTipoUsuario() == TipoUsuario.EMPRESA)) {
                securityTrue(principal.getEstadoUsuario() == EstadoUsuario.ACEPTADO, "Debes estar aceptado");
                securityTrue(principal.getEmpresa() != null, "Ya debes pertenecer a una empresa");

                if (principal.getIdIdentity() == usuario.getIdIdentity()) {
                    businessTrue(usuario.getEmpresa() != null, "La empresa es requerida");
                    businessTrue(usuarioOriginal.getEmpresa() != null, "El usuario ya debía de tener una empresa");

                    boolean ahoraTieneEmpresa = (usuario.getEmpresa() != null);
                    boolean antesTeniaEmpresa = (usuarioOriginal.getEmpresa() != null);

                    if (ahoraTieneEmpresa && antesTeniaEmpresa) {
                        if (usuario.getEmpresa().getIdEmpresa() != usuarioOriginal.getEmpresa().getIdEmpresa()) {
                            businessTrue(usuario.getEstadoUsuario() == EstadoUsuario.PENDIENTE_ACEPTACION, "El profesor debe estar pendiente de aceptación al cambiar de empresa");
                        }
                    } else if (ahoraTieneEmpresa && !antesTeniaEmpresa) {
                        businessTrue(usuario.getEstadoUsuario() == EstadoUsuario.PENDIENTE_ACEPTACION, "El empresario debe estar pendiente de aceptación al cambiar de empresa");
                    } else if (!ahoraTieneEmpresa && antesTeniaEmpresa) {
                        throw new BusinessException("No puedes estar sin empresa");
                    } else if (!ahoraTieneEmpresa && !antesTeniaEmpresa) {
                        throw new BusinessException("No puedes estar sin empresa");
                    } else {
                        throw new RuntimeException("Error de logica:" + ahoraTieneEmpresa + "," + antesTeniaEmpresa);

                    }
                } else {
                    businessTrue(usuario.getEstadoUsuario() == EstadoUsuario.ACEPTADO, "El usuario debe estar aceptado");
                    businessTrue(usuario.getEmpresa() != null, "La empresa es requerida para el usuario");
                    businessTrue(principal.getEmpresa().getIdEmpresa() == usuario.getEmpresa().getIdEmpresa(), "La empresa debe ser de la misma empresa que la tuya");
                }
            } else {
                throw new BusinessSecurityException("No tienes permiso para actualizar un profesor");
            }

        }

        return true;

    }

    @ActionRule(groups = RuleGroupPredefined.PreInsertOrUpdate.class)
    private void establecerEstadoEmpresa(RuleContext<Usuario> ruleContext) throws BusinessException {
        Usuario usuario = ruleContext.getEntity();

        if ((usuario.getTipoUsuario() == TipoUsuario.EMPRESA)) {
            usuario.setEstadoUsuario(EstadoUsuario.ACEPTADO);
        }
    }

    @ConstraintRule(message = "Error en el sistema de mensajes en 'isCheckInsertTitulado'", groups = RuleGroupPredefined.PreInsert.class)
    private boolean isCheckInsertTitulado(RuleContext<Usuario> ruleContext) throws BusinessException {
        Usuario principal = (Usuario) ruleContext.getPrincipal();
        Usuario usuario = ruleContext.getEntity();

        if ((usuario.getTipoUsuario() == TipoUsuario.TITULADO)) {

            businessTrue(usuario.getEmpresa() == null, "La empresa está prohibida para titulados");
            businessTrue(usuario.getCentro() == null, "El centro está prohibido para titulados");
            businessTrue(usuario.getTitulado() == null, "El titulado está prohibido al insertar el usuario");

            if ((principal != null) && (principal.getTipoUsuario() == TipoUsuario.ADMINISTRADOR)) {
                securityTrue(principal.getEstadoUsuario() == EstadoUsuario.ACEPTADO, "Debes estar aceptado");
            } else if (principal == null) {
                //No hace falta comprobar nada ya que no hay usuario pero se permite insertat sin principal
            } else {
                throw new BusinessSecurityException("No tienes permiso para añadir un titulado");
            }

        }

        return true;

    }

    @ConstraintRule(message = "Error en el sistema de mensajes en 'isCheckUpdateTituladoAndEstado'", groups = RuleGroupPredefined.PreUpdate.class)
    private boolean isCheckUpdateTituladoAndEstado(RuleContext<Usuario> ruleContext) throws BusinessException {
        Usuario principal = (Usuario) ruleContext.getPrincipal();
        Usuario usuario = ruleContext.getEntity();
        Usuario usuarioOriginal = ruleContext.getOriginalEntity();

        if ((usuario.getTipoUsuario() == TipoUsuario.TITULADO)) {

            businessTrue(usuario.getEmpresa() == null, "La empresa está prohibida para titulados");
            businessTrue(usuario.getCentro() == null, "El centro está prohibido para titulados");

            //Comprobar la seguridad del principal que lo actualiza
            if ((principal != null) && (principal.getTipoUsuario() == TipoUsuario.ADMINISTRADOR)) {
                securityTrue(principal.getEstadoUsuario() == EstadoUsuario.ACEPTADO, "Debes estar aceptado");
            } else if ((principal != null) && (principal.getTipoUsuario() == TipoUsuario.TITULADO)) {
                securityTrue(principal.getIdIdentity() == usuario.getIdIdentity(), "No puedes actualizar a un titulado que no sea tu mismo");
            } else {
                throw new BusinessSecurityException("Solo el titulado o los administradores pueden actualizar al titulado");
            }

            if ((usuario.getTitulado() != null) && (usuarioOriginal.getTitulado() != null)) {
                businessTrue(usuario.getTitulado().getIdTitulado() == usuarioOriginal.getTitulado().getIdTitulado(), "No es posible cambiar de titulado");
            } else if ((usuario.getTitulado() == null) && (usuarioOriginal.getTitulado() != null)) {
                throw new BusinessException("No es posible quitar el titulado");
            } else if ((usuario.getTitulado() != null) && (usuarioOriginal.getTitulado() == null)) {
                //No pasa nada se le ha puesto el titulado
            } else if ((usuario.getTitulado() == null) && (usuarioOriginal.getTitulado() == null)) {
                //No pasa nada sigue sin titulado
            } else {
                throw new BusinessException("Error de lógica:" + (usuario.getTitulado()) + "," + (usuarioOriginal.getTitulado()));
            }

        }

        return true;

    }

    @ActionRule(groups = RuleGroupPredefined.PreInsertOrUpdate.class)
    private void establecerEstadoTitulado(RuleContext<Usuario> ruleContext) throws BusinessException {
        Usuario usuario = ruleContext.getEntity();

        if ((usuario.getTipoUsuario() == TipoUsuario.TITULADO)) {
            usuario.setEstadoUsuario(EstadoUsuario.ACEPTADO);
        }
    }

    @ConstraintRule(message = "Error en el sistema de mensajes en 'isCheckInsertAdministradorAndEstado'", groups = RuleGroupPredefined.PreInsert.class)
    private boolean isCheckInsertAdministradorAndEstado(RuleContext<Usuario> ruleContext) throws BusinessException {
        Usuario principal = (Usuario) ruleContext.getPrincipal();
        Usuario usuario = ruleContext.getEntity();

        if ((usuario.getTipoUsuario() == TipoUsuario.ADMINISTRADOR)) {

            //Comprobar la seguridad del principal que lo actualiza
            if ((principal != null) && (principal.getTipoUsuario() == TipoUsuario.ADMINISTRADOR)) {
                securityTrue(principal.getEstadoUsuario() == EstadoUsuario.ACEPTADO, "Debes estar aceptado");
            } else {
                throw new BusinessSecurityException("Solo los administradores pueden insertar otro administrador");
            }

            businessTrue(usuario.getEmpresa() == null, "La empresa está prohibida para los administradores");
            businessTrue(usuario.getCentro() == null, "El centro está prohibido para los administradores");
            businessTrue(usuario.getTitulado() == null, "El titulado está prohibido los administradores");
            businessTrue(usuario.getEstadoUsuario() == EstadoUsuario.ACEPTADO, "Los administradores siempre deben estar aceptados");

        }

        return true;

    }

    @ConstraintRule(message = "Error en el sistema de mensajes en 'isCheckUpdateAdministradorAndEstado'", groups = RuleGroupPredefined.PreUpdate.class)
    private boolean isCheckUpdateAdministradorAndEstado(RuleContext<Usuario> ruleContext) throws BusinessException {
        Usuario principal = (Usuario) ruleContext.getPrincipal();
        Usuario usuario = ruleContext.getEntity();

        if ((usuario.getTipoUsuario() == TipoUsuario.ADMINISTRADOR)) {

            //Comprobar la seguridad del principal que lo actualiza
            if ((principal != null) && (principal.getTipoUsuario() == TipoUsuario.ADMINISTRADOR)) {
                securityTrue(principal.getEstadoUsuario() == EstadoUsuario.ACEPTADO, "Debes estar aceptado");
            } else {
                throw new BusinessSecurityException("Solo los administradores pueden actualizar otro administrador");
            }

            businessTrue(usuario.getEmpresa() == null, "La empresa está prohibida para los administradores");
            businessTrue(usuario.getCentro() == null, "El centro está prohibido para los administradores");
            businessTrue(usuario.getTitulado() == null, "El titulado está prohibido los administradores");
            businessTrue(usuario.getEstadoUsuario() == EstadoUsuario.ACEPTADO, "Los administradores siempre deben estar aceptados");

        }

        return true;

    }

    private void businessTrue(boolean valid, String message) throws BusinessException {
        if (valid == false) {
            throw new BusinessException(message);
        }
    }

    private void securityTrue(boolean valid, String message) throws BusinessException {
        if (valid == false) {
            throw new BusinessSecurityException(message);
        }
    }

    @Override
    public void enviarMailResetearContrasenya(EnviarMailResetearContrasenyaArguments enviarMailResetearContrasenyaArguments) throws BusinessException {
        UsuarioCRUDService usuarioCRUDService = (UsuarioCRUDService) serviceFactory.getService(Usuario.class);
        usuarioCRUDService.enviarMailResetearPassword(enviarMailResetearContrasenyaArguments.dataSession, enviarMailResetearContrasenyaArguments.email);
    }

    @Override
    public void resetearContrasenya(ResetearContrasenyaArguments resetearContrasenyaArguments) throws BusinessException {
        UsuarioCRUDService usuarioCRUDService = (UsuarioCRUDService) serviceFactory.getService(Usuario.class);
        usuarioCRUDService.resetearContrasenya(resetearContrasenyaArguments.dataSession, resetearContrasenyaArguments.claveResetearPassword, resetearContrasenyaArguments.nuevaContrasenya);
    }

}
