

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
package es.logongas.fpempresa.service.mail.impl;

import es.logongas.fpempresa.service.mail.MailService;
import java.util.List;
import javax.mail.internet.InternetAddress;

/**
 * Servicio de envio de EMails.
 * @author logongas
 */
public class MailServiceImpl implements MailService {
    
    @Override
    public void sendPlainMail(List<InternetAddress> to,InternetAddress from,String subject,String plainBody) {
        
    }

    @Override
    public void sendHTMLMail(List<InternetAddress> to,InternetAddress from,String subject,String htmlBody) {
        
    }

    @Override
    public void setEntityType(Class t) {
        throw new UnsupportedOperationException("No se permite establece el tipo de la entidad");
    }

    @Override
    public Class getEntityType() {
        return null;
    }
}
