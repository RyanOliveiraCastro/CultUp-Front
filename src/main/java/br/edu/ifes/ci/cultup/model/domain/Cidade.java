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
@NoArgsConstructor
@AllArgsConstructor
public class Cidade implements Serializable {

    private Integer cidcodigo;
    private String cidnome;
    private Uf ufcodigo;

    @Override
    public String toString() {
        return this.cidnome;
    }
}
