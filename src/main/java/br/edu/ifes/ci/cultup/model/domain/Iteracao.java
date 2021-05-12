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
 * @author rroliveira
 */
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Iteracao implements Serializable {

    private Integer itecodigo;

    private Boolean iteinteresse;

    private Boolean iteeuvou;

    private Boolean itefavorito;

    private Pessoa pesscodigo;

    private Evento evecodigo;

    public Iteracao(Pessoa pesscodigo, Evento evecodigo) {
        this.evecodigo = evecodigo;
        this.pesscodigo = pesscodigo;

    }

}
