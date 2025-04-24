package com.RafaelVn1808.DeclarationAPI.DTOs;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;

public class DeclaracaoTemporarioDTO {
    public String nome;
    public Integer matricula;
    public Integer vinculo;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    public LocalDate dataInicio;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    public LocalDate dataFim;
    public String cargo;
}
