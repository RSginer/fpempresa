/*
 * Copyright 2013 Lorenzo González.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package es.logongas.fpempresa.security;

import es.logongas.fpempresa.modelo.comun.usuario.Usuario;
import es.logongas.fpempresa.service.comun.usuario.UsuarioService;
import es.logongas.ix3.security.model.Identity;
import es.logongas.ix3.core.BusinessException;
import es.logongas.ix3.security.authentication.impl.CredentialImplLoginPassword;
import es.logongas.ix3.security.authentication.AuthenticationProvider;
import es.logongas.ix3.security.authentication.Credential;
import es.logongas.ix3.security.authentication.Principal;
import es.logongas.ix3.service.ServiceFactory;
import java.io.Serializable;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Autenticar a un usuario mediante el usuario y contraseña de moodle
 *
 * @author Lorenzo González
 */
public class AuthenticationProviderImplUsuario implements AuthenticationProvider {

    @Autowired
    ServiceFactory serviceFactory;

    protected final Log log = LogFactory.getLog(getClass());
    
    
    @Override
    public Principal authenticate(Credential credential) {
        
        try {
            if ((credential instanceof CredentialImplLoginPassword) == false) {
                return null;
            }
            CredentialImplLoginPassword credentialImplLoginPassword = (CredentialImplLoginPassword) credential;
            
            if ((credentialImplLoginPassword.getLogin()==null) ||(credentialImplLoginPassword.getLogin().trim().isEmpty())) {
                return null;
            }
            
            UsuarioService usuarioService = (UsuarioService)serviceFactory.getService(Usuario.class);
            Usuario usuario = usuarioService.readByNaturalKey(credentialImplLoginPassword.getLogin());            
            
            if (usuario!=null) {
                String plainPassword=credentialImplLoginPassword.getPassword();
                
                if (usuarioService.checkPassword(usuario,plainPassword)) {
                    Principal principal=usuario;
                    return principal;
                } else {
                    return null;
                }
            } else {
                return null;
            }
        } catch (BusinessException ex) {
            log.info(ex.getBusinessMessages().toString());
            return null;
        }
    }

    @Override
    public Principal getPrincipalBySID(Serializable sid) throws BusinessException {
        Integer idIdentity = (Integer) sid;
        UsuarioService usuarioService = (UsuarioService)serviceFactory.getService(Usuario.class);

        return usuarioService.read(idIdentity);
    }

    protected Principal getPrincipalByLogin(String login) throws BusinessException {
        UsuarioService usuarioService = (UsuarioService)serviceFactory.getService(Usuario.class);
        Identity identity = usuarioService.readByNaturalKey(login);

        return identity;
    }





}
