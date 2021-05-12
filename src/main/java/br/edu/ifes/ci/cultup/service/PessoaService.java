/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifes.ci.cultup.service;

import br.edu.ifes.ci.cultup.model.domain.Pessoa;
import com.google.gson.Gson;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author RyanOliveira
 */
public class PessoaService {

    private final String URL = "http://localhost:8080/CultupWebService/pessoa/";
    private final Client client = ClientBuilder.newClient();

    public Pessoa findById(String id) {
        WebTarget target = client.target(URL + "getCodigo/" + id);
        Gson gson = new Gson();
        String pessoaJson = target.request().get(String.class);
        return gson.fromJson(pessoaJson, Pessoa.class);
    }

    public Pessoa findByEmail(String email) {
        WebTarget target = client.target(URL + "getEmail/" + email);
        Gson gson = new Gson();
        String pessoaJson = target.request().get(String.class);
        return gson.fromJson(pessoaJson, Pessoa.class);
    }

    public void merge(Pessoa pessoa) {
        WebTarget target = client.target(URL + "update/");
        Gson gson = new Gson();
        String json = gson.toJson(pessoa);
        target.request().put(Entity.entity(json, MediaType.APPLICATION_JSON_TYPE));
    }

    public void persist(Pessoa pessoa) {
        WebTarget target = client.target(URL + "insert/");
        Gson gson = new Gson();
        String json = gson.toJson(pessoa);
        target.request().post(Entity.entity(json, MediaType.APPLICATION_JSON_TYPE));
    }

}
