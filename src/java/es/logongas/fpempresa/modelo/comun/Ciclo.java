/**
 *   FPempresa
 *   Copyright (C) 2014  Lorenzo González
 *
 *   This program is free software: you can redistribute it and/or modify
 *   it under the terms of the GNU Affero General Public License as
 *   published by the Free Software Foundation, either version 3 of the
 *   License, or (at your option) any later version.
 *
 *   This program is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU Affero General Public License for more details.
 *
 *   You should have received a copy of the GNU Affero General Public License
 *   along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */


package es.logongas.fpempresa.modelo.comun;

import es.logongas.ix3.persistence.services.annotations.ValuesList;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotBlank;

/**
 * Ciclo de FP
 * @author Lorenzo
 */
public class Ciclo {
    private int idCiclo;
    
    @ValuesList(shortLength=true)
    @NotNull
    private Familia familia;
    
    @NotBlank
    private String descripcion;   
    
    public Ciclo() {
    }

    @Override
    public String toString() {
        return descripcion;
    }
    
    /**
     * @return the idCiclo
     */
    public int getIdCiclo() {
        return idCiclo;
    }

    /**
     * @param idCiclo the idCiclo to set
     */
    public void setIdCiclo(int idCiclo) {
        this.idCiclo = idCiclo;
    }

    /**
     * @return the familiaProfesional
     */
    public Familia getFamilia() {
        return familia;
    }

    /**
     * @param familia the familiaProfesional to set
     */
    public void setFamilia(Familia familia) {
        this.familia = familia;
    }

    /**
     * @return the descripcion
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * @param descripcion the descripcion to set
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
