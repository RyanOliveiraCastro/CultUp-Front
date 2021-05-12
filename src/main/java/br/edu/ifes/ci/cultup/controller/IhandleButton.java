/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifes.ci.cultup.controller;

import java.io.IOException;
import javafx.scene.layout.AnchorPane;

/**
 *
 * @author RyanO
 */
public interface IhandleButton {
    public void handleTBUser(AnchorPane anchorPane) throws IOException;
    public void handleTBHome(AnchorPane anchorPane) throws IOException;    
    public void handleTBVoltar(AnchorPane anchorPane) throws IOException;
}
