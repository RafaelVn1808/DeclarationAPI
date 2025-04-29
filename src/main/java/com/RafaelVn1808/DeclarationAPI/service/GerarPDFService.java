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

    public byte[] gerarDeclaracaoEfetivo(DeclaracaoEfetivoPtDTO dto) {
        // Validação de campos obrigatórios
        if (dto.getNome() == null || dto.getMatricula() == null ||
                dto.getDataInicio() == null || dto.getDataFim() == null) {
            logger.error("Campos obrigatórios não fornecidos: nome={}, matricula={}, dataInicio={}, dataFim={}",
                    dto.getNome(), dto.getMatricula(), dto.getDataInicio(), dto.getDataFim());
            throw new IllegalArgumentException("Campos obrigatórios não fornecidos");
        }

        try (ByteArrayOutputStream baos = new ByteArrayOutputStream();
             PdfWriter writer = new PdfWriter(baos);
             PdfDocument pdfDoc = new PdfDocument(writer);
             Document document = new Document(pdfDoc)) {

            pdfDoc.setTagged();
            writer.setCloseStream(false);

            // Criação da declaração (lógica simplificada)
            DeclaracaoEfetivoService declaracao = new DeclaracaoEfetivoService(
                    dto.getNome(),
                    dto.getMatricula(),
                    dto.getVinculo(),
                    dto.getDataInicio(),
                    dto.getDataFim(),
                    dto.getNumberPort(),  // pode ser null
                    dto.getDataPtDt(),    // pode ser null
                    dto.getDataDoe(),     // pode ser null
                    dto.getNumberDoe(),   // pode ser null
                    dto.getCargo() != null ? dto.getCargo() : "Não informado",
                    dto.getPosse()        // pode ser null
            );

            // Adiciona todos os elementos diretamente (sem chamar gerarPdf separadamente)
            for (IBlockElement elemento : declaracao.gerarParagrafosPdf()) {
                document.add(elemento);
            }

            document.close();

            return baos.toByteArray();

        } catch (Exception e) {
            logger.error("Falha na geração do PDF para efetivo", e);
            throw new RuntimeException("Falha na geração do PDF: " + e.getMessage(), e);
        }
    }


    public byte[] gerarDeclaracaoTemporario(DeclaracaoTemporarioDTO dto) {
        // Validação de campos obrigatórios
        if (dto.getNome() == null || dto.getMatricula() == null ||
                dto.getDataInicio() == null || dto.getDataFim() == null ||
                dto.getCargo() == null) {
            logger.error("Campos obrigatórios não fornecidos: nome={}, matricula={}, dataInicio={}, dataFim={}, cargo={}",
                    dto.getNome(), dto.getMatricula(), dto.getDataInicio(), dto.getDataFim(), dto.getCargo());
            throw new IllegalArgumentException("Todos os campos obrigatórios devem ser fornecidos");
        }

        try (ByteArrayOutputStream baos = new ByteArrayOutputStream();
             PdfWriter writer = new PdfWriter(baos);
             PdfDocument pdfDoc = new PdfDocument(writer);
             Document document = new Document(pdfDoc)) {

            // Configurações essenciais do PDF
            pdfDoc.setTagged();
            writer.setCloseStream(false);

            // Criação da declaração
            DeclaracaoTemporarioService declaracao = new DeclaracaoTemporarioService(
                    dto.getNome(),
                    dto.getMatricula(),
                    dto.getVinculo() != null ? dto.getVinculo() : 0, // valor default
                    dto.getDataInicio(),
                    dto.getDataFim(),
                    dto.getCargo()
            );

            // Adiciona todos os elementos ao documento
            for (IBlockElement elemento : declaracao.gerarParagrafosPdf()) {
                document.add(elemento);
            }
            document.close();

            return baos.toByteArray();

        } catch (Exception e) {
            logger.error("Falha na geração do PDF temporário", e);
            throw new RuntimeException("Falha ao gerar PDF temporário: " + e.getMessage(), e);
        }
    }

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