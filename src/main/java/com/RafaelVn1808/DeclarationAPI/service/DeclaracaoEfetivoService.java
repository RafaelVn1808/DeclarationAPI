package com.RafaelVn1808.DeclarationAPI.service;

import lombok.*;
import java.time.LocalDate;
import java.time.Period;

@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class DeclaracaoEfetivoService extends DeclaracaoService {

    private Integer numberPort;
    private LocalDate dataPtDt;
    private LocalDate dataDoe;
    private Integer numberDoe;
    private String cargo;
    private LocalDate posse;

    public DeclaracaoEfetivoService(String nome, Integer matricula, Integer vinculo, LocalDate datainicio, LocalDate datafim,
                                    Integer numberPort, LocalDate dataPtDt, LocalDate dataDoe, Integer numberDoe, String cargo,
                                    LocalDate posse) {
        super(nome, matricula, vinculo, datainicio, datafim);
        this.numberPort = numberPort;
        this.dataPtDt = dataPtDt;
        this.dataDoe = dataDoe;
        this.numberDoe = numberDoe;
        this.cargo = cargo;
        this.posse = posse;
    }

    public DeclaracaoEfetivoService(String nome, Integer matricula, Integer vinculo, LocalDate datainicio, LocalDate datafim,
                                    LocalDate dataPtDt, LocalDate dataDoe, Integer numberDoe, String cargo, LocalDate posse) {
        super(nome, matricula, vinculo, datainicio, datafim);
        this.dataPtDt = dataPtDt;
        this.dataDoe = dataDoe;
        this.numberDoe = numberDoe;
        this.cargo = cargo;
        this.posse = posse;
    }

    public String dataPtDtFormatada() {
        return dataPtDt != null ? dataPtDt.format(FORMATTER) : "Data não informada";
    }

    public String dataDoeFormatada() {
        return dataDoe != null ? dataDoe.format(FORMATTER) : "Data não informada";
    }

    public String posseFormatada() {
        return posse != null ? posse.format(FORMATTER) : "Data não informada";
    }

    @Override
    public String toString() {
        StringBuilder declaracao = new StringBuilder();
        declaracao.append("\n\n\n                                                    DECLARAÇÃO DE TEMPO DE SERVIÇO \n\n\n")
                .append("Declaramos para os devidos fins que o Sr. ").append(getNome()).append(", ")
                .append("cadastrado sob a matrícula n° ").append(getMatricula()).append("/").append(getVinculo())
                .append(" é servidor Efetivo desta Policia Científica do Pará. ");

        if (numberPort != null) {
            declaracao.append("\nFoi nomeado pela portaria nº ").append(numberPort).append(" de ")
                    .append(dataPtDtFormatada()).append(" - GAB, publicado no diário oficial DOE ")
                    .append(numberDoe).append(" de ").append(dataDoeFormatada()).append(", ");
        } else {
            declaracao.append("\nFoi nomeado através do Decreto Governamental de ").append(dataPtDtFormatada())
                    .append(" publicado no diário oficial D.O.E ").append(numberDoe).append(" de ")
                    .append(dataDoeFormatada()).append(", ");
        }

        declaracao.append("tomou posse e entrou em exercício do cargo");

        if (getDataInicio() != null && getDataFim() != null) {
            Period periodo = calcularPeriodo();
            long diasTotais = calcularDiasTotais();
            declaracao.append(" no período de ").append(getDatainicioFormatada()).append(" até ").append(getDatafimFormatada())
                    .append(", totalizando ").append(diasTotais).append(" dias (")
                    .append(periodo.getYears()).append(" anos, ")
                    .append(periodo.getMonths()).append(" meses e ")
                    .append(periodo.getDays()).append(" dias).");
        } else {
            declaracao.append(" (Datas de início e/ou fim não informadas, não foi possível calcular o tempo de serviço).");
        }

        return wrapText(declaracao.toString(), 90, 60);
    }

    private String wrapText(String input, int maxLineLength, int maxLinesPerPage) {
        StringBuilder result = new StringBuilder();
        String[] words = input.split(" ");
        int lineLength = 0;
        int lineCount = 0;

        for (String word : words) {
            if (lineLength + word.length() > maxLineLength) {
                result.append("\n");
                lineLength = 0;
                lineCount++;
            } else if (lineLength > 0) {
                result.append(" ");
                lineLength += 1;
            }

            result.append(word);
            lineLength += word.length();

            if (lineCount >= maxLinesPerPage) {
                result.append("\f"); // Quebra de página
                lineCount = 0;
            }
        }

        return result.toString();
    }
}
