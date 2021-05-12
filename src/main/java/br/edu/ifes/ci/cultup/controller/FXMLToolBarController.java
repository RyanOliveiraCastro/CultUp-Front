/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifes.ci.cultup.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author RyanO
 */
public class FXMLToolBarController implements Initializable {
    
    @FXML
    private AnchorPane anchorPane;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }
    
    @FXML
    public void handleTBHome() throws IOException {  
        anchorPane = (AnchorPane) anchorPane.getParent().getParent();
        Parent a = FXMLLoader.load(getClass().getResource("/fxml/FXMLMainEventos.fxml"));        
        anchorPane.getChildren().setAll(a);
    }
    
    @FXML
    public void handleTBUser() throws IOException {
         anchorPane = (AnchorPane) anchorPane.getParent().getParent();
        if (System.getProperty("ID").equals("0")) {
            AnchorPane a = (AnchorPane) FXMLLoader.load(getClass().getResource("/fxml/FXMLLogin.fxml"));
            anchorPane.getChildren().setAll(a);
        } else {
            AnchorPane a = (AnchorPane) FXMLLoader.load(getClass().getResource("/fxml/FXMLUser.fxml"));
            anchorPane.getChildren().setAll(a);
        }
    }
    
//    @FXML
//    public void handleTBgUser() throws IOException {
//        anchorPane = (AnchorPane) anchorPane.getParent().getParent();
//        AnchorPane a = (AnchorPane) FXMLLoader.load(getClass().getResource("/fxml/FXMLClientesConectados.fxml"));        
//        anchorPane.getChildren().setAll(a);
//    }
    
    
}
