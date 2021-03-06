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
package es.logongas.fpempresa.modelo.empresa;

import com.aeat.valida.Validador;
import com.fasterxml.jackson.annotation.JsonProperty;
import es.logongas.fpempresa.modelo.centro.Centro;
import es.logongas.fpempresa.modelo.comun.Contacto;
import es.logongas.fpempresa.modelo.comun.geo.Direccion;
import es.logongas.ix3.rule.ConstraintRule;
import es.logongas.ix3.rule.RuleContext;
import es.logongas.ix3.rule.RuleGroupPredefined;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.hibernate.validator.constraints.NotBlank;

/**
 *
 * @author Lorenzo
 */
public class Empresa {

    private int idEmpresa;

    @NotBlank
    private String nombreComercial;

    @NotBlank
    private String razonSocial;

    @NotBlank
    @Size(min = 9, max = 9)
    private String cif;

    @JsonProperty("direccion")
    @NotNull
    @Valid
    private Direccion direccion;

    @JsonProperty("centro")
    private Centro centro;

    @JsonProperty("contacto")
    @NotNull
    @Valid
    private Contacto contacto;

    @ConstraintRule(fieldName = "cif", message = "El número o la letra del CIF '${entity.cif}' no es válida", groups = RuleGroupPredefined.PreInsertOrUpdate.class)
    private boolean validarLetraCif(RuleContext<Empresa> ruleContext) {
        Validador validadorCIF = new Validador();

        if (validadorCIF.checkNif(this.cif) > 0) {
            return true;
        } else {
            return false;
        }
    }

    public Empresa() {
    }

    @Override
    public String toString() {
        return getNombreComercial();
    }

    /**
     * @return the idEmpresa
     */
    public int getIdEmpresa() {
        return idEmpresa;
    }

    /**
     * @param idEmpresa the idEmpresa to set
     */
    public void setIdEmpresa(int idEmpresa) {
        this.idEmpresa = idEmpresa;
    }

    /**
     * @return the nombreComercial
     */
    public String getNombreComercial() {
        return nombreComercial;
    }

    /**
     * @param nombreComercial the nombreComercial to set
     */
    public void setNombreComercial(String nombreComercial) {
        this.nombreComercial = nombreComercial;
    }

    /**
     * @return the razonSocial
     */
    public String getRazonSocial() {
        return razonSocial;
    }

    /**
     * @param razonSocial the razonSocial to set
     */
    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    /**
     * @return the cif
     */
    public String getCif() {
        return cif;
    }

    /**
     * @param cif the cif to set
     */
    public void setCif(String cif) {
        this.cif = cif;
    }

    /**
     * @return the direccion
     */
    public Direccion getDireccion() {
        return direccion;
    }

    /**
     * @param direccion the direccion to set
     */
    public void setDireccion(Direccion direccion) {
        this.direccion = direccion;
    }

    /**
     * @return the centro
     */
    public Centro getCentro() {
        return centro;
    }

    /**
     * @param centro the centro to set
     */
    public void setCentro(Centro centro) {
        this.centro = centro;
    }

    /**
     * @return the contacto
     */
    public Contacto getContacto() {
        return contacto;
    }

    /**
     * @param contacto the contacto to set
     */
    public void setContacto(Contacto contacto) {
        this.contacto = contacto;
    }

}
