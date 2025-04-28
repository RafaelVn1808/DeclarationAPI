package com.RafaelVn1808.DeclarationAPI.service;

import com.RafaelVn1808.DeclarationAPI.DTOs.DeclaracaoEfetivoPtDTO;
import com.RafaelVn1808.DeclarationAPI.DTOs.DeclaracaoEstagioDTO;
import com.RafaelVn1808.DeclarationAPI.DTOs.DeclaracaoTemporarioDTO;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.IBlockElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.util.List;

@Service
public class GerarPDFService {
    private static final Logger logger = LoggerFactory.getLogger(GerarPDFService.class);

    public byte[] gerarDeclaracaoEstagio(DeclaracaoEstagioDTO dto) {
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream();
             PdfWriter writer = new PdfWriter(baos);
             PdfDocument pdfDoc = new PdfDocument(writer);
             Document document = new Document(pdfDoc)) {

            // Configurações essenciais do PDF
            pdfDoc.setTagged();
            writer.setCloseStream(false);

            DeclaracaoEstagioService declaracao = new DeclaracaoEstagioService(
                    dto.getNome(), dto.getMatricula(), dto.getVinculo(),
                    dto.getDataInicio(), dto.getDataFim(), dto.getCurso());

            // Gera os elementos do PDF
            List<IBlockElement> elementos = declaracao.gerarParagrafosPdf();

            // Adiciona cada elemento ao documento
            for (IBlockElement elemento : elementos) {
                document.add(elemento);
            }

            // Fecha o documento para garantir que tudo foi escrito
            document.close();

            // Retorna os bytes do PDF
            return baos.toByteArray();
        } catch (Exception e) {
            logger.error("Falha na geração do PDF", e);
            throw new RuntimeException("Falha na geração do PDF", e);
        }
    }

    /*public byte[] gerarDeclaracaoEfetivo(DeclaracaoEfetivoPtDTO dto) {
        try {
            DeclaracaoEfetivoService declaracao = new DeclaracaoEfetivoService(
                    dto.getNome(),
                    dto.getMatricula(),
                    dto.getVinculo(),
                    dto.getDataInicio(),
                    dto.getDataFim(),
                    dto.getNumberPort(),
                    dto.getDataPtDt(),
                    dto.getDataDoe(),
                    dto.getNumberDoe(),
                    dto.getCargo(),
                    dto.getPosse()
            );

            return gerarPdf(declaracao.gerarElementosPdf());
        } catch (Exception e) {
            logger.error("Erro ao gerar PDF de efetivo", e);
            throw new RuntimeException("Erro ao gerar PDF de efetivo", e);
        }
    }

    public byte[] gerarDeclaracaoTemporario(DeclaracaoTemporarioDTO dto) {
        try {
            DeclaracaoTemporarioService declaracao = new DeclaracaoTemporarioService(
                    dto.getNome(),
                    dto.getMatricula(),
                    dto.getVinculo(),
                    dto.getDataInicio(),
                    dto.getDataFim(),
                    dto.getCargo()
            );

            return gerarPdf(declaracao.gerarElementosPdf());
        } catch (Exception e) {
            logger.error("Erro ao gerar PDF de temporário", e);
            throw new RuntimeException("Erro ao gerar PDF de temporário", e);
        }
    }*/

    private byte[] gerarPdf(List<IBlockElement> elementos) {
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream();
             PdfWriter writer = new PdfWriter(baos);
             PdfDocument pdfDoc = new PdfDocument(writer);
             Document document = new Document(pdfDoc)) {

            for (IBlockElement elemento : elementos) {
                document.add(elemento);
            }

            return baos.toByteArray();
        } catch (Exception e) {
            logger.error("Erro ao gerar PDF", e);
            throw new RuntimeException("Erro ao gerar PDF", e);
        }
    }
}