/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifes.ci.cultup.model.domain;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;

import lombok.NoArgsConstructor;

/**
 *
 * @author RyanO
 */
@Data
@AllArgsConstructor
@NoArgsConstructor

public class Pessoa implements Serializable {

    private Integer pesscodigo;

    private String pessnome;

    private String pessdtnasc;

    private String pessemail;

    private String pesssenha;

    private String pessimagem;

    private Cidade cidcodigo;

    public Pessoa(Integer pesscodigo) {
        this.pesscodigo = pesscodigo;
    }

    public Pessoa(String pessemail) {
        this.pessemail = pessemail;
    }

    public Pessoa(String pessemail, String pesssenha) {
        this.pessemail = pessemail;
        this.pesssenha = pesssenha;
    }

    public Integer getPesscodigo() {
        return this.pesscodigo;
    }
}
