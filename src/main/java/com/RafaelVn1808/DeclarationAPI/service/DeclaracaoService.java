package com.RafaelVn1808.DeclarationAPI.service;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;



import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;


@NoArgsConstructor
public class DeclaracaoService {


    public static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");


    private String nome;
    private Integer matricula;
    private Integer vinculo;


    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private LocalDate dataInicio;


    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private LocalDate dataFim;

    public DeclaracaoService(String nome, Integer matricula, Integer vinculo, LocalDate datainicio, LocalDate datafim) {
        this.nome = nome;
        this.matricula = matricula;
        this.vinculo = vinculo;
        this.dataInicio = datainicio;
        this.dataFim = datafim;
    }

    public String getDatainicioFormatada() {
        return dataInicio != null ? dataInicio.format(FORMATTER) : "Data não informada";
    }

    public String getDatafimFormatada() {
        return dataFim != null ? dataFim.format(FORMATTER) : "Data não informada";
    }

    public long calcularDiasTotais() {
        if (dataInicio == null || dataFim == null) {
            throw new IllegalArgumentException("Data de início e fim não podem ser nulas");
        }
        return ChronoUnit.DAYS.between(dataInicio, dataFim);
    }

    public Period calcularPeriodo() {
        if (dataInicio == null || dataFim == null) {
            throw new IllegalArgumentException("Datas de início e fim não podem ser nulas para o cálculo do período.");
        }
        return Period.between(dataInicio, dataFim);
    }


    public String getCurrentDate() {
        return LocalDate.now().format(FORMATTER);
    }

    public String getNome() {
        return nome;
    }

    public Integer getMatricula() {
        return matricula;
    }

    public Integer getVinculo() {
        return vinculo;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setMatricula(Integer matricula) {
        this.matricula = matricula;
    }

    public void setVinculo(Integer vinculo) {
        this.vinculo = vinculo;
    }

    public LocalDate getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(LocalDate dataInicio) {
        this.dataInicio = dataInicio;
    }

    public LocalDate getDataFim() {
        return dataFim;
    }

    public void setDataFim(LocalDate dataFim) {
        this.dataFim = dataFim;
    }

    public static String converterNumeroParaExtenso(long numero) {
        if (numero == 0) return "zero";

        String[] unidades = {"", "um", "dois", "três", "quatro", "cinco",
                "seis", "sete", "oito", "nove"};
        String[] dezenas = {"", "", "vinte", "trinta", "quarenta", "cinquenta",
                "sessenta", "setenta", "oitenta", "noventa"};
        String[] especiais = {"dez", "onze", "doze", "treze", "quatorze", "quinze",
                "dezesseis", "dezessete", "dezoito", "dezenove"};
        String[] centenas = {"", "cem", "duzentos", "trezentos", "quatrocentos", "quinhentos",
                "seiscentos", "setecentos", "oitocentos", "novecentos"};

        StringBuilder resultado = new StringBuilder();

        if (numero >= 1000) {
            long milhar = numero / 1000;
            resultado.append(converterNumeroParaExtenso(milhar)).append(" mil");
            numero %= 1000;
            if (numero > 0) resultado.append(" e ");
        }

        if (numero >= 100) {
            long centena = numero / 100;
            if (centena == 1 && numero % 100 == 0) {
                resultado.append("cem");
            } else {
                resultado.append(centenas[(int) centena]);
            }
            numero %= 100;
            if (numero > 0) resultado.append(" e ");
        }

        if (numero >= 20) {
            long dezena = numero / 10;
            resultado.append(dezenas[(int) dezena]);
            numero %= 10;
            if (numero > 0) resultado.append(" e ");
        } else if (numero >= 10) {
            resultado.append(especiais[(int) (numero - 10)]);
            numero = 0;
        }

        if (numero > 0) {
            resultado.append(unidades[(int) numero]);
        }

        return resultado.toString().trim();
    }

}