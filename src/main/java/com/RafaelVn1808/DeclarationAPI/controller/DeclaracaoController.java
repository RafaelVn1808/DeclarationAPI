package com.RafaelVn1808.DeclarationAPI.controller;

import com.RafaelVn1808.DeclarationAPI.DTOs.DeclaracaoEfetivoPtDTO;
import com.RafaelVn1808.DeclarationAPI.DTOs.DeclaracaoEstagioDTO;
import com.RafaelVn1808.DeclarationAPI.DTOs.DeclaracaoTemporarioDTO;
import com.RafaelVn1808.DeclarationAPI.service.*;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;


@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/declaracoes")
public class DeclaracaoController {

    @Autowired
    private GerarPDFService gerarPDFService;


    @PostMapping("/estagio/pdf")
    public ResponseEntity<byte[]> gerarPdfDeclaracaoEstagio(@RequestBody DeclaracaoEstagioDTO dto) {
        byte[] pdfBytes = gerarPDFService.gerarDeclaracaoEstagio(dto);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDisposition(ContentDisposition.builder("attachment")
                .filename("declaracao-estagio.pdf")
                .build());

        return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);
    }

    @PostMapping("/efetivo/pdf")
    public ResponseEntity<byte[]> gerarPdfDeclaracaoEfetivo(@RequestBody DeclaracaoEfetivoPtDTO dto) {
        byte[] pdfBytes = gerarPDFService.gerarDeclaracaoEfetivo(dto);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDisposition(ContentDisposition.builder("attachment")
                .filename("declaracao-efetivo.pdf")
                .build());

        return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);
    }

    @PostMapping("/temporario/pdf")
    public ResponseEntity<byte[]> gerarPdfDeclaracaoTemporario(@RequestBody DeclaracaoTemporarioDTO dto){
        byte[] pdfBytes = gerarPDFService.gerarDeclaracaoTemporario(dto);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDisposition(ContentDisposition.builder("attachment")
                .filename("declaracao-temporário.pdf")
                .build());

        return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);
    }
}











