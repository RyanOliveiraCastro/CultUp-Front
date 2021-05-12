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
public class Endereco implements Serializable {

    private Integer endcodigo;

    private String endcep;

    private String endlogradouro;

    private String endbairro;

    private String endpontoreferencia;

    private Integer endnumero;

    private String enddate;

    private Cidade cidcodigo;
}
