/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifes.ci.cultup.controller;

import br.edu.ifes.ci.cultup.model.domain.Pessoa;
import br.edu.ifes.ci.cultup.service.PessoaService;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author RyanO
 */
public class FXMLLoginController implements Initializable {

    @FXML
    private ImageView Logo;
    @FXML
    private TextField TFEmail;
    @FXML
    private PasswordField PFSenha;
    @FXML
    private Hyperlink hyperlinkSenha;
    @FXML
    private Hyperlink hyperlinkCadastro;
    @FXML
    private Button buttonLogin;
    @FXML
    private AnchorPane anchorPane;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        anchorPane.setOnKeyPressed((event) -> {
            if (event.getCode() == KeyCode.ENTER) {
                try {
                    handleButtonLogin();
                } catch (IOException ex) {
                    Logger.getLogger(FXMLLoginController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    @FXML
    public void handleHyperLinkAlterarSenha() throws IOException {
        AnchorPane a = (AnchorPane) FXMLLoader.load(getClass().getResource("/fxml/FXMLAlterarsenha.fxml"));
        anchorPane.getChildren().setAll(a);
    }

    @FXML
    public void handleHyperLinkCadastrarUsuario() throws IOException {
        AnchorPane a = (AnchorPane) FXMLLoader.load(getClass().getResource("/fxml/FXMLCadastro.fxml"));
        anchorPane.getChildren().setAll(a);
    }

    @FXML
    public void handleButtonLogin() throws IOException {
        PessoaService pessoaService = new PessoaService();
        Pessoa email = pessoaService.findByEmail(TFEmail.getText());
        
        Pessoa pessoa = new Pessoa(TFEmail.getText(), PFSenha.getText());
        //Pessoa email = pessoaDAO.findEmail(TFEmail.getText());

        if (email == null) {
            alertErro("Email não cadastrado");
        } else {
            if (pessoa.getPesssenha().equals(email.getPesssenha())) {                
                System.setProperty("ID", String.valueOf(email.getPesscodigo()));
                AnchorPane a = (AnchorPane) FXMLLoader.load(getClass().getResource("/fxml/FXMLUser.fxml"));
                anchorPane.getChildren().setAll(a);
            } else {
                alertErro("Senha Incorreta");
            }
        }
    }

    public void alertErro(String msg) {
        Alert alerta = new Alert(Alert.AlertType.ERROR);
        alerta.setTitle("Erro!");
        alerta.setContentText(msg);
        alerta.show();
    }

    @FXML
    public void handleTBVoltar() throws IOException {
        AnchorPane a = (AnchorPane) FXMLLoader.load(getClass().getResource("/fxml/FXMLLogin.fxml"));
        anchorPane.getChildren().setAll(a);
    }
}
