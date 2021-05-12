/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifes.ci.cultup.service;

import br.edu.ifes.ci.cultup.model.domain.Evento;
import br.edu.ifes.ci.cultup.model.domain.Uf;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author RyanOliveira
 */
public class EventoService {

    private final String URL = "http://localhost:8080/CultupWebService/evento/";
    private final Client client = ClientBuilder.newClient();

    public List<Evento> findByPessoa(String pesscodigo) {
        WebTarget target = client.target(URL + "getEventosPessoa/" + pesscodigo);
        String json = target.request().get(String.class);
        Gson gson = new Gson();
        Type listType = new TypeToken<ArrayList<Evento>>() {
        }.getType();
        List<Evento> list = gson.fromJson(json, listType);
        return list;
    }

    public List<Evento> search() {
        WebTarget target = client.target(URL + "get");
        String json = target.request().get(String.class);
        Gson gson = new Gson();
        Type listType = new TypeToken<ArrayList<Evento>>() {
        }.getType();
        List<Evento> list = gson.fromJson(json, listType);
        return list;
    }

    public void merge(Evento evento) {
        WebTarget target = client.target(URL + "update/");
        Gson gson = new Gson();
        String json = gson.toJson(evento);
        target.request().put(Entity.entity(json, MediaType.APPLICATION_JSON_TYPE));
    }

    public void persist(Evento evento) {
        WebTarget target = client.target(URL + "insert/");
        Gson gson = new Gson();
        String json = gson.toJson(evento);
        target.request().post(Entity.entity(json, MediaType.APPLICATION_JSON_TYPE));
    }

}
