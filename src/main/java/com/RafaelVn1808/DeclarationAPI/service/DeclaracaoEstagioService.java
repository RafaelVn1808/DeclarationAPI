package com.RafaelVn1808.DeclarationAPI.service;

import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.layout.element.*;
import com.itextpdf.layout.properties.HorizontalAlignment;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.properties.UnitValue;
import com.itextpdf.layout.properties.VerticalAlignment;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.time.LocalDate;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class DeclaracaoEstagioService extends DeclaracaoService {

    private String curso;

    public String getCurso() {
        return curso;
    }

    public void setCurso(String curso) {
        this.curso = curso;
    }

    public DeclaracaoEstagioService(String nome, Integer matricula, Integer vinculo,
                                    LocalDate dataInicio, LocalDate dataFim, String curso) {
        super(nome, matricula, vinculo, dataInicio, dataFim);
        this.curso = curso;
    }

   /* metodo antigo
   public long calcularHorasTotais() {
        long diasTotais = calcularDiasTotais();
        return (long) ((diasTotais / 30.0) * 120); // 120h por mês
    } */

    public long calcularHorasTotais() {
        long diasTotais = calcularDiasTotais();
        long mesesTotais = diasTotais / 30; // Descarta frações (24 meses)
        return mesesTotais * 120; // 2.880 horas
    }



    public List<IBlockElement> gerarParagrafosPdf() {
        List<IBlockElement> elementos = new ArrayList<>();

        try {
            Image logoEsquerdo = new Image(ImageDataFactory.create(getClass().getResource("/static/logo-policia-cientifica.png")))
                    .scaleToFit(80, 80);
            Image logoDireito = new Image(ImageDataFactory.create(getClass().getResource("/static/Pará-1.png")))
                    .scaleToFit(80, 80);

            Paragraph centroTexto = new Paragraph(
                    "Governo do Pará\n" +
                            "Secretaria de Segurança Pública e Defesa Social\n" +
                            "Polícia Científica do Pará\n" +
                            "COORDENADORIA DE ADMINISTRAÇÃO/GERÊNCIA DE PESSOAL")
                    .setTextAlignment(TextAlignment.CENTER)
                    .setFontSize(10)
                    .setBold();

            // Configuração corrigida da tabela
            Table cabecalho = new Table(new float[]{1, 3, 1}); // Proporções das colunas
            cabecalho.setWidth(UnitValue.createPercentValue(100)); // Ocupa 100% da largura

            // Célula esquerda (logo)
            Cell cellEsquerda = new Cell()
                    .add(logoEsquerdo)
                    .setBorder(Border.NO_BORDER)
                    .setHorizontalAlignment(HorizontalAlignment.LEFT)
                    .setVerticalAlignment(VerticalAlignment.MIDDLE);

            // Célula central (texto)
            Cell cellCentro = new Cell()
                    .add(centroTexto)
                    .setBorder(Border.NO_BORDER)
                    .setHorizontalAlignment(HorizontalAlignment.CENTER)
                    .setVerticalAlignment(VerticalAlignment.MIDDLE);

            // Célula direita (logo)
            Cell cellDireita = new Cell()
                    .add(logoDireito)
                    .setBorder(Border.NO_BORDER)
                    .setHorizontalAlignment(HorizontalAlignment.RIGHT)
                    .setVerticalAlignment(VerticalAlignment.MIDDLE);

            cabecalho.addCell(cellEsquerda);
            cabecalho.addCell(cellCentro);
            cabecalho.addCell(cellDireita);

            elementos.add(cabecalho);

        } catch (Exception e) {
            System.err.println("Erro ao carregar imagens do cabeçalho: " + e.getMessage());
            elementos.add(new Paragraph("POLÍCIA CIENTÍFICA DO PARÁ")
                    .setTextAlignment(TextAlignment.CENTER)
                    .setBold());
        }

        // Espaçamento e Título
        elementos.addAll(Collections.nCopies(3, new Paragraph("\n")));
        elementos.add(new Paragraph("DECLARAÇÃO DE CARGA HORÁRIA DE ESTÁGIO")
                .setBold()
                .setTextAlignment(TextAlignment.CENTER)
                .setFontSize(14));
        elementos.addAll(Collections.nCopies(2, new Paragraph("\n")));

        // Corpo do texto com concatenação usando os métodos da classe pai
        long horasTotais = calcularHorasTotais();
        String texto = "\tDeclaramos para os devidos fins que o(a) estagiário(a) " + getNome() +
                ", matriculado no curso de " + getCurso() +
                ", cadastrado sob matrícula n° " + getMatricula() + "/" + getVinculo() +
                " no período de " + getDatainicioFormatada() + " a " + getDatafimFormatada() +
                ", exerceu suas atividades de Segunda a Sexta-Feira totalizando " +
                horasTotais + " (" + converterNumeroParaExtenso(horasTotais) + ") horas de estágio nesta Autarquia.";

        Paragraph paragrafoDeclaracao = new Paragraph(texto)
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
        elementos.add(new Paragraph("Nídia Catherine Martin Ferreira")
                .setBold()
                .setTextAlignment(TextAlignment.CENTER)
                .setFontSize(12));
        elementos.add(new Paragraph("Gerente de Pessoal")
                .setTextAlignment(TextAlignment.CENTER)
                .setFontSize(10));

        return elementos;
    }
}