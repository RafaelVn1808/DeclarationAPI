package com.RafaelVn1808.DeclarationAPI.service;


import lombok.*;
import java.time.LocalDate;


@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class DeclaracaoEstagioService extends DeclaracaoService {

    private String curso;

    public DeclaracaoEstagioService(String nome, Integer matricula, Integer vinculo, LocalDate datainicio, LocalDate datafim, String curso) {
        super(nome, matricula, vinculo, datainicio, datafim);
        this.curso = curso;
    }

    public long calcularHorasTotais() {
        long diasTotais = calcularDiasTotais();
        return (long) ((diasTotais / 30.0) * 120); // Regra: 120h por mês
    }

    @Override
    public String toString() {
        long horasTotais = calcularHorasTotais();
        return new StringBuilder()
                .append("\n\n\n                                         DECLARAÇÃO DE CARGA HORÁRIA DE ESTÁGIO\n\n\n")
                .append("Declaramos para os devidos fins que o(a) estagiário(a) ").append(getNome())
                .append(" matriculado no curso de ").append(curso).append(", \n")
                .append("cadastrado sob matrícula ").append(getMatricula()).append("/").append(getVinculo())
                .append(", no período de ").append(getDatainicioFormatada()).append(" a ").append(getDatafimFormatada())
                .append(", exerceu suas atividades totalizando ")
                .append(horasTotais).append(" (").append(converterNumeroParaExtenso(horasTotais))
                .append(") horas de estágio nesta Autarquia.\n\n")
                .append("                                                                                                Belém, ")
                .append(getCurrentDate())
                .toString();
    }
}