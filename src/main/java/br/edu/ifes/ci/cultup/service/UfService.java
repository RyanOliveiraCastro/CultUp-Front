/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifes.ci.cultup.service;

import br.edu.ifes.ci.cultup.model.domain.Pessoa;
import br.edu.ifes.ci.cultup.model.domain.Uf;
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
public class UfService {
    
    private final String URL = "http://localhost:8080/CultupWebService/uf/";
    private final Client client = ClientBuilder.newClient();
    
    public List<Uf> search(){
        WebTarget target = client.target(URL + "get");
        String json = target.request().get(String.class);
        Gson gson = new Gson();
        Type listType = new TypeToken<ArrayList<Uf>>(){}.getType();
        List<Uf> list = gson.fromJson(json, listType);
        return list;
    }
    
}
