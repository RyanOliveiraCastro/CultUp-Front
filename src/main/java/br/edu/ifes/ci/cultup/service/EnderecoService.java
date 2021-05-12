/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifes.ci.cultup.service;

import br.edu.ifes.ci.cultup.model.domain.Endereco;
import com.google.gson.Gson;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author RyanOliveira
 */
public class EnderecoService {

    private final String URL = "http://localhost:8080/CultupWebService/endereco/";
    private final Client client = ClientBuilder.newClient();

    public Endereco findById(Integer id) {
        WebTarget target = client.target(URL + "getCodigo/" + id);
        Gson gson = new Gson();
        String pessoaJson = target.request().get(String.class);
        return gson.fromJson(pessoaJson, Endereco.class);
    }

    public void merge(Endereco endereco) {
        WebTarget target = client.target(URL + "update/");
        Gson gson = new Gson();
        String json = gson.toJson(endereco);
        target.request().put(Entity.entity(json, MediaType.APPLICATION_JSON_TYPE));
    }

//    public void persist(Endereco endereco) {
//        WebTarget target = client.target(URL + "insert/");
//        Gson gson = new Gson();
//        String json = gson.toJson(endereco);
//        target.request().post(Entity.entity(json, MediaType.APPLICATION_JSON_TYPE));
//    }
    
    public Endereco persist(Endereco endereco){
        WebTarget target = client.target(URL + "insert/");
        Gson gson = new Gson();
        String json = gson.toJson(endereco);
        String enderecoJson = target.request().post(Entity.entity(json, MediaType.APPLICATION_JSON_TYPE)).readEntity(String.class);        
        return gson.fromJson(enderecoJson, Endereco.class);
    }
}