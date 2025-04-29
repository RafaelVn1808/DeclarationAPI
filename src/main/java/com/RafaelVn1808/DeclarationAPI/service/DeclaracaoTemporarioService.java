package com.RafaelVn1808.DeclarationAPI.service;

import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.element.*;
import com.itextpdf.layout.properties.HorizontalAlignment;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class DeclaracaoTemporarioService extends DeclaracaoService {

    private static final String ORGAO = "Polícia Científica do Pará";
    private static final String GERENTE = "Nídia Catherine Martin Ferreira";
    private static final String CARGO_GERENTE = "Gerente de Pessoal";

    private final String cargo;

    public DeclaracaoTemporarioService(String nome, Integer matricula, Integer vinculo,
                                       LocalDate dataInicio, LocalDate dataFim, String cargo) {
        super(nome, matricula, vinculo, dataInicio, dataFim);
        if (cargo == null || cargo.trim().isEmpty()) {
            throw new IllegalArgumentException("Cargo não pode ser vazio");
        }
        this.cargo = cargo;
    }

    public List<IBlockElement> gerarParagrafosPdf() {
        List<IBlockElement> elementos = new ArrayList<>();

        // Validação de campos obrigatórios
        if (getNome() == null || getMatricula() == null) {
            throw new IllegalArgumentException("Nome e matrícula são obrigatórios");
        }

        try {
            // Cabeçalho com logos
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

        // Corpo do texto com cálculo preciso do período
        String tempoServico = formatarTempoServico();

        Paragraph paragrafoDeclaracao = new Paragraph()
                .add("Declaramos para os devidos fins que o Sr(a) ").add(getNome())
                .add(", cadastrado sob a matrícula n° ").add(getMatricula().toString()).add("/").add(getVinculo().toString())
                .add(", foi servidor temporário no período de ").add(getDatainicioFormatada())
                .add(" a ").add(getDatafimFormatada()).add(" como ").add(cargo)
                .add(", ").add(tempoServico).add(".")
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