/**
 * FPempresa Copyright (C) 2015 Lorenzo González
 *
 * This program is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Affero General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package es.logongas.fpempresa.service.empresa.impl;

import es.logongas.fpempresa.dao.empresa.OfertaDAO;
import es.logongas.fpempresa.modelo.centro.Centro;
import es.logongas.fpempresa.modelo.comun.usuario.Usuario;
import es.logongas.fpempresa.modelo.empresa.Empresa;
import es.logongas.fpempresa.modelo.empresa.Oferta;
import es.logongas.ix3.core.BusinessException;
import es.logongas.ix3.service.CRUDService;
import es.logongas.ix3.service.NamedSearch;
import es.logongas.ix3.service.impl.CRUDServiceImpl;
import java.util.List;

/**
 *
 * @author logongas
 */
public class OfertaCRUDServiceImpl extends CRUDServiceImpl<Oferta, Integer> implements CRUDService<Oferta, Integer> {

    private Usuario getPrincipal() {
        return (Usuario) principalLocator.getPrincipal();
    }

    
    private OfertaDAO getOfertaDAO() {
        return (OfertaDAO)getDAO();
    }

    
    @NamedSearch(parameterNames = "usuario")
    public List<Oferta> getOfertasUsuarioTitulado(Usuario usuario) throws BusinessException {       
        
        return getOfertaDAO().getOfertasUsuarioTitulado(usuario);
    };
    
    @NamedSearch(parameterNames = "usuario")
    public List<Oferta> getOfertasInscritoUsuarioTitulado(Usuario usuario) throws BusinessException {
        
        return getOfertaDAO().getOfertasInscritoUsuarioTitulado(usuario);
    };

    @NamedSearch(parameterNames = "centro")
    public List<Oferta> getOfertasEmpresasCentro(Centro centro) throws BusinessException {
        
        return getOfertaDAO().getOfertasEmpresasCentro(centro);
    };

    @NamedSearch(parameterNames = "empresa")
    public List<Oferta> getOfertasEmpresa(Empresa empresa) throws BusinessException {
        
        return getOfertaDAO().getOfertasEmpresa(empresa);
    };  
    
    
}
