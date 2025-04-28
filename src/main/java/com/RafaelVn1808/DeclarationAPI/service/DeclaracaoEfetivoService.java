package com.RafaelVn1808.DeclarationAPI.service;

import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.element.*;
import com.itextpdf.layout.properties.HorizontalAlignment;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;
import lombok.*;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
public class DeclaracaoEfetivoService extends DeclaracaoService {

    private static final String ORGAO = "Polícia Científica do Pará";
    private static final String GERENTE = "Nídia Catherine Martin Ferreira";
    private static final String CARGO_GERENTE = "Gerente de Pessoal";

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
        try {
            return dataPtDt != null ? dataPtDt.format(FORMATTER) : "Data não informada";
        } catch (Exception e) {
            return "Data inválida";
        }
    }

    public String dataDoeFormatada() {
        try {
            return dataDoe != null ? dataDoe.format(FORMATTER) : "Data não informada";
        } catch (Exception e) {
            return "Data inválida";
        }
    }

    public String posseFormatada() {
        try {
            return posse != null ? posse.format(FORMATTER) : "Data não informada";
        } catch (Exception e) {
            return "Data inválida";
        }
    }

    public List<IBlockElement> gerarParagrafosPdf() {
        List<IBlockElement> elementos = new ArrayList<>();

        // Validação de campos obrigatórios
        if (getNome() == null || getMatricula() == null) {
            throw new IllegalArgumentException("Nome e matrícula são obrigatórios");
        }

        try {
            Image logoEsquerdo = new Image(ImageDataFactory.create(getClass().getResource("/static/logo-policia-cientifica.png")))
                    .scaleToFit(80, 80);
            Image logoDireito = new Image(ImageDataFactory.create(getClass().getResource("/static/Pará-1.png")))
                    .scaleToFit(80, 80);

            Paragraph centroTexto = new Paragraph(
                    "Governo do Pará\n" +
                            "Secretaria de Segurança Pública e Defesa Social\n" +
                            ORGAO + "\n" +
                            "COORDENADORIA DE ADMINISTRAÇÃO/GERÊNCIA DE PESSOAL")
                    .setTextAlignment(TextAlignment.CENTER)
                    .setFontSize(10)
                    .setBold();

            Table cabecalho = new Table(new float[]{1, 3, 1});
            cabecalho.setWidth(UnitValue.createPercentValue(100));

            cabecalho.addCell(new Cell()
                    .add(logoEsquerdo)
                    .setBorder(Border.NO_BORDER)
                    .setHorizontalAlignment(HorizontalAlignment.LEFT));

            cabecalho.addCell(new Cell()
                    .add(centroTexto)
                    .setBorder(Border.NO_BORDER)
                    .setHorizontalAlignment(HorizontalAlignment.CENTER));

            cabecalho.addCell(new Cell()
                    .add(logoDireito)
                    .setBorder(Border.NO_BORDER)
                    .setHorizontalAlignment(HorizontalAlignment.RIGHT));

            elementos.add(cabecalho);

        } catch (Exception e) {
            System.err.println("Erro ao carregar imagens do cabeçalho: " + e.getMessage());
            elementos.add(new Paragraph(ORGAO)
                    .setTextAlignment(TextAlignment.CENTER)
                    .setBold());
        }

        // Espaçamento e Título
        elementos.addAll(Collections.nCopies(3, new Paragraph("\n")));
        elementos.add(new Paragraph("DECLARAÇÃO DE TEMPO DE SERVIÇO")
                .setBold()
                .setTextAlignment(TextAlignment.CENTER)
                .setFontSize(14));
        elementos.addAll(Collections.nCopies(2, new Paragraph("\n")));

        // Corpo do texto
        StringBuilder texto = new StringBuilder()
                .append("\tDeclaramos para os devidos fins que o Sr.(a) ").append(getNome())
                .append(", cadastrado sob a matrícula n° ").append(getMatricula()).append("/").append(getVinculo())
                .append(" é servidor Efetivo desta ").append(ORGAO).append(". ");

        if (numberPort != null) {
            texto.append("\nFoi nomeado pela portaria nº ").append(numberPort).append(" de ")
                    .append(dataPtDtFormatada()).append(" - GAB, publicado no diário oficial DOE ")
                    .append(numberDoe).append(" de ").append(dataDoeFormatada()).append(", ");
        } else {
            texto.append("\nFoi nomeado através do Decreto Governamental de ").append(dataPtDtFormatada())
                    .append(" publicado no diário oficial D.O.E ").append(numberDoe).append(" de ")
                    .append(dataDoeFormatada()).append(", ");
        }

        texto.append("tomou posse e entrou em exercício do cargo");

        if (getDataInicio() != null && getDataFim() != null) {
            Period periodo = calcularPeriodo();
            long diasTotais = calcularDiasTotais();
            texto.append(" no período de ").append(getDatainicioFormatada()).append(" até ").append(getDatafimFormatada())
                    .append(", totalizando ").append(diasTotais).append(" dias (")
                    .append(periodo.getYears()).append(" anos, ")
                    .append(periodo.getMonths()).append(" meses e ")
                    .append(periodo.getDays()).append(" dias).");
        }

        Paragraph paragrafoDeclaracao = new Paragraph(texto.toString())
                .setTextAlignment(TextAlignment.JUSTIFIED)
                .setFontSize(12)
                .setFirstLineIndent(20f);

        elementos.add(paragrafoDeclaracao);

        // Rodapé com data e assinatura
        elementos.add(new Paragraph("\n\n"));
        elementos.add(new Paragraph("Belém, " + getCurrentDate() + ".")
                .setTextAlignment(TextAlignment.RIGHT)
                .setFontSize(12));
        elementos.add(new Paragraph("\n\n\n"));
        elementos.add(new Paragraph(GERENTE)
                .setBold()
                .setTextAlignment(TextAlignment.CENTER)
                .setFontSize(12));
        elementos.add(new Paragraph(CARGO_GERENTE)
                .setTextAlignment(TextAlignment.CENTER)
                .setFontSize(10));

        return elementos;
    }
}