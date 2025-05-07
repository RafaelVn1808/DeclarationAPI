package com.RafaelVn1808.DeclarationAPI.DTOs;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;

public class DeclaracaoEfetivoPtDTO {
    public String nome;
    public Integer matricula;
    public Integer vinculo;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    public LocalDate dataInicio;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    public LocalDate dataFim;
    public Integer numberPort;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    public LocalDate dataPtDt;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    public LocalDate dataDoe;
    public Integer numberDoe;
    public String cargo;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    public LocalDate posse;


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

    public Integer getNumberPort() {
        return numberPort;
    }

    public void setNumberPort(Integer numberPort) {
        this.numberPort = numberPort;
    }

    public LocalDate getDataPtDt() {
        return dataPtDt;
    }

    public void setDataPtDt(LocalDate dataPtDt) {
        this.dataPtDt = dataPtDt;
    }

    public LocalDate getDataDoe() {
        return dataDoe;
    }

    public void setDataDoe(LocalDate dataDoe) {
        this.dataDoe = dataDoe;
    }

    public Integer getNumberDoe() {
        return numberDoe;
    }

    public void setNumberDoe(Integer numberDoe) {
        this.numberDoe = numberDoe;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public LocalDate getPosse() {
        return posse;
    }

    public void setPosse(LocalDate posse) {
        this.posse = posse;
    }
}


