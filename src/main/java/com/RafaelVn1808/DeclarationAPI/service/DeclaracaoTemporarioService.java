package com.RafaelVn1808.DeclarationAPI.service;

import java.time.LocalDate;
import java.time.Period;


public class DeclaracaoTemporarioService extends DeclaracaoService {

    private final String cargo;

    public DeclaracaoTemporarioService(String nome, Integer matricula, Integer vinculo,
                                       LocalDate dataInicio, LocalDate dataFim, String cargo) {
        super(nome, matricula, vinculo, dataInicio, dataFim);
        this.cargo = cargo;
    }

    @Override
    public String toString() {
        Period periodo = calcularPeriodo();
        long diasTotais = calcularDiasTotais();

        StringBuilder declaracao = new StringBuilder()
                .append(centralizarTexto("Governo do Pará"))
                .append(centralizarTexto("Secretaria de Segurança Pública e Defesa Social"))
                .append(centralizarTexto("Polícia Científica do Pará"))
                .append(centralizarTexto("COORDENADORIA DE ADMINISTRAÇÃO/GERÊNCIA DE PESSOAL"))
                .append("\n".repeat(4))
                .append(centralizarTexto("DECLARAÇÃO DE TEMPO DE SERVIÇO"))
                .append("\n".repeat(4))
                .append("Declaramos para os devidos fins que o Sr(a) ").append(getNome())
                .append(" cadastrado sob a matrícula n° ").append(getMatricula()).append("/").append(getVinculo())
                .append(", foi servidor temporário no período de ").append(getDatainicioFormatada())
                .append(" a ").append(getDatafimFormatada()).append(" como ").append(cargo)
                .append(", totalizando ").append(diasTotais).append(" (").append(converterNumeroParaExtenso(diasTotais))
                .append(") ").append(plural(diasTotais, "dia", "dias"))
                .append(", equivalentes a ").append(periodo.getYears()).append(" (").append(converterNumeroParaExtenso(periodo.getYears()))
                .append(") ").append(plural(periodo.getYears(), "ano", "anos")).append(", ")
                .append(periodo.getMonths()).append(" (").append(converterNumeroParaExtenso(periodo.getMonths()))
                .append(") ").append(plural(periodo.getMonths(), "mês", "meses")).append(" e ")
                .append(periodo.getDays()).append(" (").append(converterNumeroParaExtenso(periodo.getDays()))
                .append(") ").append(plural(periodo.getDays(), "dia", "dias")).append(" de tempo de serviço.\n\n")
                .append(alinharDireita("Belém, " + getCurrentDate()))
                .append("\n\n\n")
                .append(centralizarTexto("(Nome do chefe imediato)"))
                .append(centralizarTexto("(Cargo do chefe imediato)"));

        return declaracao.toString();
    }
}
