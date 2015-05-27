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
package es.logongas.fpempresa.presentacion.controller;

import es.logongas.fpempresa.modelo.centro.CertificadoTitulo;
import es.logongas.fpempresa.modelo.comun.usuario.EstadoUsuario;
import es.logongas.fpempresa.modelo.comun.usuario.Usuario;
import es.logongas.fpempresa.service.comun.usuario.UsuarioCRUDService;
import es.logongas.ix3.core.BusinessException;
import es.logongas.ix3.core.conversion.Conversion;
import es.logongas.ix3.dao.metadata.MetaData;
import es.logongas.ix3.dao.metadata.MetaDataFactory;
import es.logongas.ix3.service.CRUDService;
import es.logongas.ix3.service.CRUDServiceFactory;
import es.logongas.ix3.web.controllers.AbstractRESTController;
import es.logongas.ix3.web.controllers.Command;
import es.logongas.ix3.web.controllers.CommandResult;
import es.logongas.ix3.web.json.JsonReader;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 * @author logongas
 */
@Controller
public class CertificadoTituloController extends AbstractRESTController {

    @Autowired
    private MetaDataFactory metaDataFactory;

    @Autowired
    private Conversion conversion;

    @Autowired
    private CRUDServiceFactory crudServiceFactory;

    @RequestMapping(value = {"/CertificadoTitulo"}, method = RequestMethod.POST, produces = "application/json")
    public void insert(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, final @RequestBody String jsonIn) {

        restMethod(httpServletRequest, httpServletResponse, null, new Command() {

            @Override
            public CommandResult run(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Map<String, Object> arguments) throws Exception, BusinessException {
                CRUDService<CertificadoTitulo, Integer> certificadoTituloCrudService = crudServiceFactory.getService(CertificadoTitulo.class);

                JsonReader jsonReader = jsonFactory.getJsonReader(CertificadoTitulo.class);
                CertificadoTitulo certificadoTitulo = (CertificadoTitulo) jsonReader.fromJson(jsonIn);

                String[] nifnies = certificadoTitulo.getNifnie().split("[,\\s]");

                for (String nifnie : nifnies) {
                    String realNifnie = nifnie.trim();

                    if (realNifnie.length() > 0) {
                        certificadoTitulo = (CertificadoTitulo) jsonReader.fromJson(jsonIn);
                        certificadoTitulo.setNifnie(realNifnie);
                        certificadoTituloCrudService.insert(certificadoTitulo);
                    }
                }

                return new CommandResult(CertificadoTitulo.class, certificadoTitulo, HttpServletResponse.SC_CREATED);

            }
        });

    }
}
