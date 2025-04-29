package com.RafaelVn1808.DeclarationAPI.service;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class DeclaracaoService {
    protected static final int DEFAULT_CHARS_PER_LINE = 70;
    protected static final int DEFAULT_LINES_PER_PAGE = 50;
    public static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    protected static final int LARGURA_PAGINA = 80;
    protected static final Logger logger = LoggerFactory.getLogger(DeclaracaoService.class);

    private String nome;
    private Integer matricula;
    private Integer vinculo;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private LocalDate dataInicio;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private LocalDate dataFim;

    public DeclaracaoService(String nome, Integer matricula, Integer vinculo,
                             LocalDate datainicio, LocalDate datafim) {
        this.nome = nome;
        this.matricula = matricula;
        this.vinculo = vinculo;
        this.dataInicio = datainicio;
        this.dataFim = datafim;
    }

    // Getters e Setters
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getMatricula() {
        return matricula;
    }

    public void setMatricula(Integer matricula) {
        this.matricula = matricula;
    }

    public Integer getVinculo() {
        return vinculo;
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

    public String getDatainicioFormatada() {
        return dataInicio != null ? dataInicio.format(FORMATTER) : "Data não informada";
    }

    public String getDatafimFormatada() {
        return dataFim != null ? dataFim.format(FORMATTER) : "Data não informada";
    }

    public String getCurrentDate() {
        return LocalDate.now().format(FORMATTER);
    }

    /**
     * Calcula o total de dias incluindo o último dia
     */
    public long calcularDiasTotais() {
        validarDatas();
        return ChronoUnit.DAYS.between(dataInicio, dataFim) + 1;
    }

    /**
     * Calcula o período completo (anos, meses, dias)
     * Inclui o dia final no cálculo
     */
    public Period calcularPeriodo() {
        validarDatas();
        return Period.between(dataInicio, dataFim.plusDays(1));
    }

    /**
     * Formata o período completo por extenso
     */
    public String formatarPeriodoCompleto() {
        Period periodo = calcularPeriodo();
        int anos = periodo.getYears();
        int meses = periodo.getMonths();
        int dias = periodo.getDays();

        if (meses == 0 && dias == 0) {
            return String.format("%d (%s) %s",
                    anos, converterNumeroParaExtenso(anos),
                    plural(anos, "ano completo", "anos completos"));
        }

        return String.format("%d (%s) %s, %d (%s) %s e %d (%s) %s",
                anos, converterNumeroParaExtenso(anos), plural(anos, "ano", "anos"),
                meses, converterNumeroParaExtenso(meses), plural(meses, "mês", "meses"),
                dias, converterNumeroParaExtenso(dias), plural(dias, "dia", "dias"));
    }

    /**
     * Formata dias totais + período completo
     */
    protected String formatarTempoServico() {
        return String.format("%d (%s) %s, equivalentes a %s",
                calcularDiasTotais(),
                converterNumeroParaExtenso(calcularDiasTotais()),
                plural(calcularDiasTotais(), "dia", "dias"),
                formatarPeriodoCompleto());
    }

    private void validarDatas() {
        if (dataInicio == null || dataFim == null) {
            throw new IllegalStateException("Datas de início e fim devem ser informadas");
        }
        if (dataInicio.isAfter(dataFim)) {
            throw new IllegalStateException("Data de início não pode ser posterior à data final");
        }
    }

    /**
     * Converte números para por extenso (0-9999)
     */
    public static String converterNumeroParaExtenso(long numero) {
        if (numero < 0 || numero > 9999) {
            return String.valueOf(numero);
        }

        String[] unidades = {"zero", "um", "dois", "três", "quatro", "cinco",
                "seis", "sete", "oito", "nove", "dez", "onze",
                "doze", "treze", "quatorze", "quinze", "dezesseis",
                "dezessete", "dezoito", "dezenove"};
        String[] dezenas = {"", "", "vinte", "trinta", "quarenta", "cinquenta",
                "sessenta", "setenta", "oitenta", "noventa"};
        String[] centenas = {"", "cento", "duzentos", "trezentos", "quatrocentos",
                "quinhentos", "seiscentos", "setecentos", "oitocentos",
                "novecentos"};

        if (numero < 20) return unidades[(int) numero];
        if (numero < 100) {
            return dezenas[(int) numero / 10] +
                    ((numero % 10 != 0) ? " e " + unidades[(int) numero % 10] : "");
        }
        if (numero == 100) return "cem";
        if (numero < 1000) {
            return centenas[(int) numero / 100] +
                    ((numero % 100 != 0) ? " e " + converterNumeroParaExtenso(numero % 100) : "");
        }
        return converterNumeroParaExtenso(numero / 1000) + " mil" +
                ((numero % 1000 != 0) ? " e " + converterNumeroParaExtenso(numero % 1000) : "");
    }

    protected String plural(long valor, String singular, String plural) {
        return valor == 1 ? singular : plural;
    }

    protected String centralizarTexto(String texto) {
        int espacos = (LARGURA_PAGINA - texto.length()) / 2;
        return " ".repeat(Math.max(0, espacos)) + texto;
    }

    protected String alinharDireita(String texto) {
        return " ".repeat(Math.max(0, LARGURA_PAGINA - texto.length())) + texto;
    }
}