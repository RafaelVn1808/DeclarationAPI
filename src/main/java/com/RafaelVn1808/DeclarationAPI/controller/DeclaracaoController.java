package com.RafaelVn1808.DeclarationAPI.controller;

import com.RafaelVn1808.DeclarationAPI.DTOs.DeclaracaoEfetivoPtDTO;
import com.RafaelVn1808.DeclarationAPI.DTOs.DeclaracaoEstagioDTO;
import com.RafaelVn1808.DeclarationAPI.DTOs.DeclaracaoTemporarioDTO;
import com.RafaelVn1808.DeclarationAPI.service.*;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.nio.file.Files;
import java.nio.file.Paths;


@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/declaracoes")
public class DeclaracaoController {

    @Autowired
    private GerarPDFService gerarPDFService;

    @Autowired
    public DeclaracaoController(GerarPDFService gerarPDFService) {
        this.gerarPDFService = gerarPDFService;
    }

    @PostMapping("/estagio/pdf")
    public ResponseEntity<byte[]> gerarPdfDeclaracaoEstagio(@RequestBody DeclaracaoEstagioDTO dto) {
        try {
            byte[] pdfBytes = gerarPDFService.gerarDeclaracaoEstagio(dto);

            // Verificação crítica - o PDF foi gerado?
            if (pdfBytes == null || pdfBytes.length == 0) {
                throw new RuntimeException("O PDF gerado está vazio");
            }

            // DEBUG: Salva localmente para verificação
            Files.write(Paths.get("debug.pdf"), pdfBytes);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDisposition(
                    ContentDisposition.builder("attachment")
                            .filename("declaracao_estagio.pdf")
                            .build());
            headers.setContentLength(pdfBytes.length);

            return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body(("Erro ao gerar PDF: " + e.getMessage()).getBytes());
        }
    }


    @PostMapping("/efetivo/pdf")
    public ResponseEntity<?> gerarPdfDeclaracaoEfetivo(@RequestBody DeclaracaoEfetivoPtDTO dto) {
        try {
            // Validação adicional de datas
            if (dto.getDataInicio().isAfter(dto.getDataFim())) {
                throw new IllegalArgumentException("Data de início deve ser anterior à data final");
            }

            byte[] pdfBytes = gerarPDFService.gerarDeclaracaoEfetivo(dto);

            if (pdfBytes == null) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("Falha crítica ao gerar PDF");
            }


            return ResponseEntity.ok()
                    .header("Content-Type", "application/pdf")
                    .header("Content-Disposition", "attachment; filename=declaracao.pdf")
                    .body(pdfBytes);

        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body("Erro interno ao gerar PDF");
        }
    }

    @PostMapping("/temporario/pdf")
    public ResponseEntity<?> gerarPdfDeclaracaoTemporario(@RequestBody DeclaracaoTemporarioDTO dto) {
        try {
            // Validação adicional de datas
            if (dto.getDataInicio().isAfter(dto.getDataFim())) {
                throw new IllegalArgumentException("Data de início deve ser anterior à data final");
            }

            byte[] pdfBytes = gerarPDFService.gerarDeclaracaoTemporario(dto);

            if (pdfBytes == null) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("Falha crítica ao gerar PDF");
            }

            return ResponseEntity.ok()
                    .header("Content-Type", "application/pdf")
                    .header("Content-Disposition", "attachment; filename=declaracao_temporario.pdf")
                    .body(pdfBytes);

        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body("Erro interno ao gerar PDF");
        }
    }


}











