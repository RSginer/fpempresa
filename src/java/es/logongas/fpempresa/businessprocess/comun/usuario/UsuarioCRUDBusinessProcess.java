/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.logongas.fpempresa.businessprocess.comun.usuario;

import es.logongas.fpempresa.modelo.centro.Centro;
import es.logongas.fpempresa.modelo.comun.usuario.EstadoUsuario;
import es.logongas.fpempresa.modelo.comun.usuario.Usuario;
import es.logongas.fpempresa.modelo.titulado.Titulado;
import es.logongas.ix3.businessprocess.BusinessProcess;
import es.logongas.ix3.businessprocess.CRUDBusinessProcess;
import es.logongas.ix3.core.BusinessException;
import es.logongas.ix3.core.Principal;
import es.logongas.ix3.dao.DataSession;

/**
 *
 * @author logongas
 */
public interface UsuarioCRUDBusinessProcess extends CRUDBusinessProcess<Usuario, Integer> {

    void updatePassword(UpdatePasswordArguments updatePasswordArguments) throws BusinessException;
    Usuario getUsuarioFromTitulado(GetUsuarioFromTituladoArguments getUsuarioFromTituladoArguments) throws BusinessException;
    byte[] getFoto(GetFotoArguments getFotoArguments) throws BusinessException;
    void updateFoto(UpdateFotoArguments updateFotoArguments) throws BusinessException;
    
    Usuario updateEstadoUsuario(UpdateEstadoUsuarioArguments updateEstadoUsuarioArguments) throws BusinessException;
    Usuario updateCentro(UpdateCentroArguments updateCentroArguments) throws BusinessException;
    
    public class UpdatePasswordArguments extends BusinessProcess.BusinessProcessArguments {

        final public Usuario usuario;
        final public String currentPassword;
        final public String newPassword;

        public UpdatePasswordArguments(Principal principal, DataSession dataSession, Usuario usuario, String currentPassword, String newPassword) {
            super(principal, dataSession);
            this.usuario = usuario;

            this.currentPassword = currentPassword;
            this.newPassword = newPassword;
        }
        
        

    }

    public class GetUsuarioFromTituladoArguments extends CRUDBusinessProcess.ParametrizedSearchArguments {

        public Titulado titulado;

        public GetUsuarioFromTituladoArguments() {
        }

        
        
        public GetUsuarioFromTituladoArguments(Principal principal, DataSession dataSession, Titulado titulado) {
            super(principal, dataSession);
            
            this.titulado = titulado;
        }

    }
    public class GetFotoArguments extends CRUDBusinessProcess.ParametrizedSearchArguments {
        
        public Usuario usuario;

        public GetFotoArguments() {
        }

        
        
        public GetFotoArguments(Principal principal, DataSession dataSession, Usuario usuario) {
            super(principal, dataSession);
            
            this.usuario = usuario;
        }

    }   
    
    public class UpdateFotoArguments extends BusinessProcess.BusinessProcessArguments {
        
        final public Usuario usuario;
        final public byte[] foto;

        public UpdateFotoArguments(Principal principal, DataSession dataSession, Usuario usuario, byte[] foto) {
            super(principal, dataSession);
            
            this.usuario = usuario;
            this.foto = foto;
        }

    }   
    
    public class UpdateEstadoUsuarioArguments extends BusinessProcess.BusinessProcessArguments {       
        final public Usuario usuario;
        final public EstadoUsuario estadoUsuario;

        public UpdateEstadoUsuarioArguments(Principal principal, DataSession dataSession, Usuario usuario, EstadoUsuario estadoUsuario) {
            super(principal, dataSession);
            
            this.usuario = usuario;
            this.estadoUsuario = estadoUsuario;
        }

    }      
 
    
    public class UpdateCentroArguments extends BusinessProcess.BusinessProcessArguments {
        
        final public Usuario usuario;
        final public Centro centro;

        public UpdateCentroArguments(Principal principal, DataSession dataSession, Usuario usuario, Centro centro) {
            super(principal, dataSession);
            
            this.usuario = usuario;
            this.centro = centro;
        }

    }     

}
