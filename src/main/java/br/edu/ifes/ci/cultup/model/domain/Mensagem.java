/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifes.ci.cultup.model.domain;

import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author RyanOliveira
 */
public class Mensagem implements Serializable {

    private String nomeCliente;
    private Integer qtd_cliente;
    private Action action;
    private ArrayList<String> usuariosOnline;

    public String getNomeCliente() {
        return nomeCliente;
    }

    public void setNomeCliente(String nomeCliente) {
        this.nomeCliente = nomeCliente;
    }

    public Action getAction() {
        return action;
    }

    public void setAction(Action action) {
        this.action = action;
    }

    public ArrayList<String> getUsuariosOnline() {
        return usuariosOnline;
    }

    public void setUsuariosOnline(ArrayList<String> usuariosOnline) {
        this.usuariosOnline = usuariosOnline;
    }

    public enum Action {
        CONNECT, DISCONNECT, USERS
    }

    /**
     * @return the qtd_cliente
     */
    public Integer getQtd_cliente() {
        return qtd_cliente;
    }

    /**
     * @param qtd_cliente the qtd_cliente to set
     */
    public void setQtd_cliente(Integer qtd_cliente) {
        this.qtd_cliente = qtd_cliente;
    }

}
