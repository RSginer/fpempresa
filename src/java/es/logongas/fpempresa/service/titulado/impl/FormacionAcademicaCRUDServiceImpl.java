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
package es.logongas.fpempresa.service.titulado.impl;

import es.logongas.fpempresa.dao.centro.CertificadoTituloDAO;
import es.logongas.fpempresa.modelo.centro.Centro;
import es.logongas.fpempresa.modelo.centro.CertificadoTitulo;
import es.logongas.fpempresa.modelo.educacion.Ciclo;
import es.logongas.fpempresa.modelo.titulado.FormacionAcademica;
import es.logongas.fpempresa.modelo.titulado.TipoDocumento;
import es.logongas.ix3.core.BusinessException;
import es.logongas.ix3.dao.DataSession;
import es.logongas.ix3.service.CRUDService;
import es.logongas.ix3.service.impl.CRUDServiceImpl;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 *
 * @author logongas
 */
public class FormacionAcademicaCRUDServiceImpl extends CRUDServiceImpl<FormacionAcademica, Integer> implements CRUDService<FormacionAcademica, Integer> {

    @Override
    public FormacionAcademica insert(DataSession dataSession, FormacionAcademica formacionAcademica) throws BusinessException {

        formacionAcademica.setCertificadoTitulo(getCertificadoTitulo(dataSession, formacionAcademica));
        return super.insert(dataSession, formacionAcademica);
    }

    @Override
    public FormacionAcademica update(DataSession dataSession, FormacionAcademica formacionAcademica) throws BusinessException {

        formacionAcademica.setCertificadoTitulo(getCertificadoTitulo(dataSession, formacionAcademica));
        return super.update(dataSession, formacionAcademica);
    }

    private boolean getCertificadoTitulo(DataSession dataSession, FormacionAcademica formacionAcademica) {
        boolean isCertificadoTitulo;

        if (formacionAcademica.getTitulado().getTipoDocumento() == TipoDocumento.NIF_NIE) {
            Calendar calendar = new GregorianCalendar();
            calendar.setTime(formacionAcademica.getFecha());

            Centro centro = formacionAcademica.getCentro();
            Ciclo ciclo = formacionAcademica.getCiclo();
            int anyo = calendar.get(Calendar.YEAR);
            String nifnie = formacionAcademica.getTitulado().getNumeroDocumento();

            CertificadoTitulo certificadoTitulo = ((CertificadoTituloDAO) daoFactory.getDAO(CertificadoTitulo.class)).getCertificadoTituloByCentroCicloAnyo(dataSession, centro, ciclo, anyo);

            if (certificadoTitulo != null) {
                isCertificadoTitulo = certificadoTitulo.isCertificadoNifNie(nifnie);
            } else {
                isCertificadoTitulo = false;
            }
        } else {
            isCertificadoTitulo = false;
        }

        return isCertificadoTitulo;
    }

}
