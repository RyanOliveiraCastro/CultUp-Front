/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifes.ci.cultup.service;

import br.edu.ifes.ci.cultup.model.domain.Evento;
import br.edu.ifes.ci.cultup.model.domain.Iteracao;
import br.edu.ifes.ci.cultup.model.domain.Pessoa;
import com.google.gson.Gson;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author rroliveira
 */
public class IteracaoService {

    private final String URL = "http://localhost:8080/CultupWebService/iteracao/";
    private final Client client = ClientBuilder.newClient();

    public void merge(Iteracao iteracao) {
        WebTarget target = client.target(URL + "update/");
        Gson gson = new Gson();
        String json = gson.toJson(iteracao);
        target.request().put(Entity.entity(json, MediaType.APPLICATION_JSON_TYPE));
    }

    public Iteracao findIteracao(Integer pesscodigo, Integer evecodigo) {
        WebTarget target = client.target(URL + "getIteracao/" + pesscodigo + "/" + evecodigo);
        Gson gson = new Gson();
        String pessoaJson = target.request().get(String.class);
        return gson.fromJson(pessoaJson, Iteracao.class);
    }

}
