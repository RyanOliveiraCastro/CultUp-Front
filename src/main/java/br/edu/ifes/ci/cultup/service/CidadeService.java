/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifes.ci.cultup.service;

import br.edu.ifes.ci.cultup.model.domain.Cidade;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;

/**
 *
 * @author RyanOliveira
 */
public class CidadeService {

    private final String URL = "http://localhost:8080/CultupWebService/cidade/";
    private final Client client = ClientBuilder.newClient();

    public List<Cidade> findbyUf(Integer ufcodigo) {
        WebTarget target = client.target(URL + "getUf/" + ufcodigo);
        String json = target.request().get(String.class);
        Gson gson = new Gson();
        Type listType = new TypeToken<ArrayList<Cidade>>() {}.getType();
        List<Cidade> list = gson.fromJson(json, listType);
        return list;
    }
}
