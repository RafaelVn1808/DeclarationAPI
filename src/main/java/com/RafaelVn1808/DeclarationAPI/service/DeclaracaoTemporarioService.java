package com.RafaelVn1808.DeclarationAPI.service;


import lombok.*;
import java.time.LocalDate;
import java.time.Period;


@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class DeclaracaoTemporarioService extends DeclaracaoService {

    private String cargo;

    public DeclaracaoTemporarioService(String nome, Integer matricula, Integer vinculo, LocalDate dataInicio, LocalDate dataFim, String cargo) {
        super(nome, matricula, vinculo, dataInicio, dataFim);
        this.cargo = cargo;
    }

    @Override
    public String toString() {
        Period periodo = calcularPeriodo();
        long diasTotais = calcularDiasTotais();

        return new StringBuilder()
                .append("\n\n\n                                                        DECLARAÇÃO DE TEMPO DE SERVIÇO \n\n\n")
                .append("Declaramos para os devidos fins que o Sr(a) ").append(getNome()).append(", ")
                .append("cadastrado sob a matrícula n° ").append(getMatricula()).append("/").append(getVinculo())
                .append(" foi servidor no período de ").append(getDatainicioFormatada()).append(" a ").append(getDatafimFormatada())
                .append(",\n como ").append(cargo).append(", totalizando ").append(diasTotais)
                .append(" (").append(converterNumeroParaExtenso(diasTotais)).append(") dias, equivalentes a ")
                .append(periodo.getYears()).append(" (").append(converterNumeroParaExtenso(periodo.getYears())).append(") ano(s), ")
                .append(periodo.getMonths()).append(" (").append(converterNumeroParaExtenso(periodo.getMonths())).append(") mês(es) e ")
                .append(periodo.getDays()).append(" (").append(converterNumeroParaExtenso(periodo.getDays())).append(") dia(s) de tempo de serviço.\n\n")
                .append("                                                                                                           Belém, ")
                .append(getCurrentDate())
                .toString();
    }
}
