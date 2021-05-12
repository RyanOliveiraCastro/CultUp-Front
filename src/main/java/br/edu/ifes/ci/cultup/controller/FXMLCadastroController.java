/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifes.ci.cultup.controller;

import br.edu.ifes.ci.cultup.model.domain.Cidade;
import br.edu.ifes.ci.cultup.model.domain.Pessoa;
import br.edu.ifes.ci.cultup.model.domain.Uf;
import br.edu.ifes.ci.cultup.service.CidadeService;
import br.edu.ifes.ci.cultup.service.PessoaService;
import br.edu.ifes.ci.cultup.service.UfService;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author rroliveira
 */
public class FXMLCadastroController implements Initializable {

    @FXML
    private ToggleButton TBReturn;
    @FXML
    private ImageView Logo;
    @FXML
    private TextField TFNomeCompleto;
    @FXML
    private DatePicker DPDtnasc;
    @FXML
    private ComboBox<Uf> CBEstado;
    @FXML
    private ComboBox<Cidade> CBCidade;
    @FXML
    private TextField TFEmail;
    @FXML
    private PasswordField PFSenha;
    @FXML
    private Button BTNCadastrar;

    @FXML
    private AnchorPane anchorPane;

    private List<Uf> listUf;
    private ObservableList<Uf> observableListUf;
    private List<Cidade> listCidade;
    private ObservableList<Cidade> observableListCidade;

    PessoaService pessoaService = new PessoaService();
    UfService ufService = new UfService();
    CidadeService cidadeService = new CidadeService();
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        loadComboboxUf();

    }

    @FXML
    public void loadComboboxUf() {
         UfService ufService = new UfService();
        listUf = ufService.search();
        observableListUf = FXCollections.observableArrayList(listUf);
        CBEstado.setItems(observableListUf);
    }

    @FXML
    public void loadComboboxCidade() {
        CidadeService cidadeService = new CidadeService();
        listCidade = cidadeService.findbyUf(CBEstado.getSelectionModel().getSelectedItem().getUfcodigo());
        observableListCidade = FXCollections.observableArrayList(listCidade);
        CBCidade.setItems(observableListCidade);
    }

    @FXML
    public void handleButtonConfirmar() {
        //GenericDAO<Pessoa> pessoaDAO = new GenericDAO(Pessoa.class);
        Pessoa pessoa = new Pessoa();
        try {
            pessoa.setPessnome(TFNomeCompleto.getText());
            pessoa.setPessdtnasc(DPDtnasc.getValue().toString());
            pessoa.setCidcodigo(CBCidade.getValue());
            pessoa.setPessemail(TFEmail.getText());
            pessoa.setPesssenha(PFSenha.getText());
            Pessoa email = pessoaService.findByEmail(TFEmail.getText());
            if (email == null) { 
                pessoaService.persist(pessoa);
                alertSucesso();
                AnchorPane a = (AnchorPane) FXMLLoader.load(getClass().getResource("/fxml/FXMLLogin.fxml"));
                anchorPane.getChildren().setAll(a);
            } else {
                throw new Exception("Email já cadastrado!");
            }

        } catch (Exception e) {
            alertErro(e.getMessage());
            e.printStackTrace();
        }
    }

    public void alertSucesso() {
        Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
        alerta.setTitle("Sucesso");
        alerta.setContentText("Sucesso");
        alerta.show();
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
