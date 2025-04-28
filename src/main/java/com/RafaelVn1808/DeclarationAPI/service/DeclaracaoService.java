package com.RafaelVn1808.DeclarationAPI.service;

import com.fasterxml.jackson.annotation.JsonFormat;


import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class DeclaracaoService {
    protected static final int DEFAULT_CHARS_PER_LINE = 70;
    protected static final int DEFAULT_LINES_PER_PAGE = 50;
    public static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    protected static final int LARGURA_PAGINA = 80;

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

    protected String formatarParaA4(String texto, int maxCaracteresPorLinha, int maxLinhasPorPagina) {
        StringBuilder resultado = new StringBuilder();
        String[] paragrafos = texto.split("\n");
        int linhasNaPagina = 0;

        for (String paragrafo : paragrafos) {
            if (paragrafo.contains("DECLARAÇÃO") || paragrafo.contains("Governo") ||
                    paragrafo.contains("Secretaria") || paragrafo.contains("Polícia") ||
                    paragrafo.contains("COORDENADORIA")) {
                int espacos = (maxCaracteresPorLinha - paragrafo.length()) / 2;
                resultado.append(" ".repeat(Math.max(0, espacos))).append(paragrafo).append("\n");
                linhasNaPagina++;
                continue;
            }

            if (paragrafo.trim().startsWith("Belém,")) {
                int espacos = maxCaracteresPorLinha - paragrafo.length();
                resultado.append(" ".repeat(Math.max(0, espacos))).append(paragrafo).append("\n");
                linhasNaPagina++;
                continue;
            }

            String[] palavras = paragrafo.split(" ");
            StringBuilder linhaAtual = new StringBuilder();
            int charsNaLinha = 0;

            for (String palavra : palavras) {
                if (palavra.isEmpty()) continue;

                int espacoNecessario = (charsNaLinha > 0) ? palavra.length() + 1 : palavra.length();

                if (charsNaLinha + espacoNecessario > maxCaracteresPorLinha) {
                    resultado.append(linhaAtual).append("\n");
                    if (++linhasNaPagina >= maxLinhasPorPagina) {
                        resultado.append("\f");
                        linhasNaPagina = 0;
                    }
                    linhaAtual = new StringBuilder(palavra);
                    charsNaLinha = palavra.length();
                } else {
                    if (charsNaLinha > 0) {
                        linhaAtual.append(" ");
                        charsNaLinha++;
                    }
                    linhaAtual.append(palavra);
                    charsNaLinha += palavra.length();
                }
            }

            if (linhaAtual.length() > 0) {
                resultado.append(linhaAtual).append("\n");
                if (++linhasNaPagina >= maxLinhasPorPagina) {
                    resultado.append("\f");
                    linhasNaPagina = 0;
                }
            }
        }

        return resultado.toString();
    }

    protected String formatarParaA4(String texto) {
        return formatarParaA4(texto, DEFAULT_CHARS_PER_LINE, DEFAULT_LINES_PER_PAGE);
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
        return ChronoUnit.DAYS.between(dataInicio, dataFim) + 1;
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

    public static String converterNumeroParaExtenso(long numero) {
        if (numero == 0) return "zero";
        String[] unidades = {"", "um", "dois", "três", "quatro", "cinco",
                "seis", "sete", "oito", "nove"};
        String[] dezenas = {"", "", "vinte", "trinta", "quarenta", "cinquenta",
                "sessenta", "setenta", "oitenta", "noventa"};
        String[] especiais = {"dez", "onze", "doze", "treze", "quatorze", "quinze",
                "dezesseis", "dezessete", "dezoito", "dezenove"};
        String[] centenas = {"", "cento", "duzentos", "trezentos", "quatrocentos", "quinhentos",
                "seiscentos", "setecentos", "oitocentos", "novecentos"};

        StringBuilder resultado = new StringBuilder();

        if (numero >= 1000) {
            long milhar = numero / 1000;
            resultado.append(converterNumeroParaExtenso(milhar)).append(" mil");
            numero %= 1000;
            if (numero > 0) resultado.append(" e ");
        }

        if (numero == 100) {
            resultado.append("cem");
            return resultado.toString();
        }

        if (numero >= 100) {
            long centena = numero / 100;
            resultado.append(centenas[(int) centena]);
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

    protected String plural(long valor, String singular, String plural) {
        return valor == 1 ? singular : plural;
    }

    protected String centralizarTexto(String texto) {
        int larguraTotal = LARGURA_PAGINA;
        StringBuilder resultado = new StringBuilder();
        String[] linhas = texto.split("\n");
        for (String linha : linhas) {
            int espacos = (larguraTotal - linha.length()) / 2;
            resultado.append(" ".repeat(Math.max(0, espacos))).append(linha).append("\n");
        }
        return resultado.toString();
    }

    protected String alinharDireita(String texto) {
        int larguraTotal = LARGURA_PAGINA;
        return " ".repeat(Math.max(0, larguraTotal - texto.length())) + texto + "\n";
    }


}
