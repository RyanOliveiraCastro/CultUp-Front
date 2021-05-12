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

public class Evento implements Serializable {

    private Integer evecodigo;

    private String evetitulo;

    private String evesubtitulo;

    private String evedescricao;

    private String evelocal;

    private String evedata;

    private String evehora;

    private String eveimagem;

    private Endereco endcodigo;

    private Pessoa pesscodigo;
}
