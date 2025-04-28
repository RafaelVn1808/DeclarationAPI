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


    /*@PostMapping("/efetivo/pdf")
    public ResponseEntity<byte[]> gerarPdfDeclaracaoEfetivo(@RequestBody DeclaracaoEfetivoPtDTO dto) {
        byte[] pdfBytes = gerarPDFService.gerarDeclaracaoEfetivo(dto);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDisposition(ContentDisposition.builder("attachment")
                .filename("declaracao-efetivo.pdf")
                .build());

        return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);
    }*/

    /*@PostMapping("/temporario/pdf")
    public ResponseEntity<byte[]> gerarPdfDeclaracaoTemporario(@RequestBody DeclaracaoTemporarioDTO dto){
        byte[] pdfBytes = gerarPDFService.gerarDeclaracaoTemporario(dto);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDisposition(ContentDisposition.builder("attachment")
                .filename("declaracao-temporario.pdf")
                .build());

        return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);
    }*/



    /* @PostMapping("/estagio1/pdf")
    public ResponseEntity<byte[]> gerarPdfDeclaracaoEstagio(@RequestBody DeclaracaoEstagioDTO dto) {
        // Converter DTO para entidade (pode ser manual ou usando ModelMapper, MapStruct, etc.)
        DeclaracaoEstagioService estagio = converterParaEntidade(dto);

        // Gerar PDF
        byte[] pdfBytes = GerarPDFService.gerarPdfDeclaracaoEstagio(estagio);

        // Headers para retorno como PDF
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDisposition(ContentDisposition.builder("attachment")
                .filename("declaracao-estagio.pdf")
                .build());

        return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);
    }*/
}











