/**
 * FPempresa Copyright (C) 2014 Lorenzo González
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
package es.logongas.fpempresa.modelo.estadisticas;

import java.util.List;

/**
 *
 * @author logongas
 */
public class Estadisticas {

    private final List<FamiliaEstadistica> tituladosPorFamilia;
    private final List<FamiliaEstadistica> ofertasPorFamilia;
    private final List<FamiliaEstadistica> candidatosPorFamilia;

    private final int numeroTitulados;
    private final int numeroOfertas;
    private final int numeroCandidatos;

    public Estadisticas(List<FamiliaEstadistica> tituladosPorFamilia, List<FamiliaEstadistica> ofertasPorFamilia, List<FamiliaEstadistica> candidatosPorFamilia) {
        this.tituladosPorFamilia = tituladosPorFamilia;
        this.ofertasPorFamilia = ofertasPorFamilia;
        this.candidatosPorFamilia = candidatosPorFamilia;

        this.numeroTitulados = getSum(this.tituladosPorFamilia);
        this.numeroOfertas = getSum(this.ofertasPorFamilia);
        this.numeroCandidatos = getSum(this.candidatosPorFamilia);
    }

    private int getSum(List<FamiliaEstadistica> familiasEstadistica) {
        int sum = 0;

        if (familiasEstadistica != null) {
            for (FamiliaEstadistica familiaEstadistica : familiasEstadistica) {
                sum = sum + familiaEstadistica.getValor();
            }
        }

        return sum;
    }

    /**
     * @return the tituladosPorFamilia
     */
    public List<FamiliaEstadistica> getTituladosPorFamilia() {
        return tituladosPorFamilia;
    }

    /**
     * @return the ofertasPorFamilia
     */
    public List<FamiliaEstadistica> getOfertasPorFamilia() {
        return ofertasPorFamilia;
    }

    /**
     * @return the candidatosPorFamilia
     */
    public List<FamiliaEstadistica> getCandidatosPorFamilia() {
        return candidatosPorFamilia;
    }

    /**
     * @return the numeroTitulados
     */
    public int getNumeroTitulados() {
        return numeroTitulados;
    }

    /**
     * @return the numeroOfertas
     */
    public int getNumeroOfertas() {
        return numeroOfertas;
    }

    /**
     * @return the numeroCandidatos
     */
    public int getNumeroCandidatos() {
        return numeroCandidatos;
    }

}
