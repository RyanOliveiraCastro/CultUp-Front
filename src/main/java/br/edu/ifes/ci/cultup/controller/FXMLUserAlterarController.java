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
import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author RyanO
 */
public class FXMLUserAlterarController implements Initializable {

    @FXML
    private ImageView IVPerfil;
    @FXML
    private ToggleButton TBImagem;
    @FXML
    private Label LBNomeCompleto;
    @FXML
    private Label LBCidadeEstado;
    @FXML
    private TextField TFNome;
    @FXML
    private TextField TFEmail;
    @FXML
    private DatePicker DPNascimento;
    @FXML
    private ComboBox<Uf> CBEstado;
    @FXML
    private ComboBox<Cidade> CBCidade;
    @FXML
    private AnchorPane anchorPane;
    @FXML
    private StackPane stackPane;

    private List<Uf> listUf;
    private ObservableList<Uf> observableListUf;
    private List<Cidade> listCidade;
    private ObservableList<Cidade> observableListCidade;

    final FileChooser fileChooser = new FileChooser();
    private Desktop desktop = Desktop.getDesktop();
    private File file;

    PessoaService pessoaService = new PessoaService();

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    public void setUserAlterar(Pessoa pessoa) throws ParseException {
        if (pessoa.getPessimagem() != null) {
            Image image = new Image(pessoa.getPessimagem());
            IVPerfil.setImage(image);
        } else {
            stackPane.setStyle("-fx-background-image: url('/main/resources/imagem/cultup-logo-2x.png'); -fx-background-size: 200px 200px;");
        }
        LBNomeCompleto.setText(pessoa.getPessnome());
        LBCidadeEstado.setText(pessoa.getCidcodigo().getCidnome() + " - " + pessoa.getCidcodigo().getUfcodigo().getUfsigla());
        TFNome.setText(pessoa.getPessnome());
        TFEmail.setText(pessoa.getPessemail());
        LocalDate date = LocalDate.parse(pessoa.getPessdtnasc());
        DPNascimento.setValue(date);
        loadComboboxUf();
        CBEstado.getSelectionModel().select(pessoa.getCidcodigo().getUfcodigo());
        CBEstado.setOnInputMethodTextChanged(new EventHandler<InputMethodEvent>() {
            @Override
            public void handle(InputMethodEvent t) {
                CBCidade.getSelectionModel().clearSelection();
            }
        });
        loadComboboxCidade();
        CBCidade.getSelectionModel().select(pessoa.getCidcodigo());

        TBImagem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                file = fileChooser.showOpenDialog(new Stage());
                Image image = new Image(file.toURI().toString());
                IVPerfil.setImage(image);
            }
        });
    }

    @FXML
    public void handleButtonImagem() {
        file = fileChooser.showOpenDialog(new Stage());
    }

    @FXML
    public void handleButtonSalvar() throws IOException, ParseException {

        Pessoa pessoa = pessoaService.findById(System.getProperty("ID"));
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        pessoa.setPessnome(TFNome.getText());
        pessoa.setPessemail(TFEmail.getText());
        pessoa.setPessdtnasc(DPNascimento.getValue().toString());
        pessoa.setCidcodigo(CBCidade.getValue());

        if (file != null && file.getCanonicalPath() != null && !file.getAbsolutePath().isEmpty()) {
            pessoa.setPessimagem(file.toURI().toString());
        } else {
            pessoa.setPessimagem(pessoa.getPessimagem());
        }

        pessoaService.merge(pessoa);
        
        AnchorPane a = (AnchorPane) FXMLLoader.load(getClass().getResource("/fxml/FXMLUser.fxml"));
        anchorPane.getChildren().setAll(a);
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
    public void handleTBHome() throws IOException {
        AnchorPane a = (AnchorPane) FXMLLoader.load(getClass().getResource("/fxml/FXMLMainEventos.fxml"));
        anchorPane.getChildren().setAll(a);
    }

    @FXML
    public void handleTBUser() throws IOException {
        if (System.getProperty("ID").equals("0")) {
            AnchorPane a = (AnchorPane) FXMLLoader.load(getClass().getResource("/fxml/FXMLLogin.fxml"));
            anchorPane.getChildren().setAll(a);
        } else {
            AnchorPane a = (AnchorPane) FXMLLoader.load(getClass().getResource("/fxml/FXMLUser.fxml"));
            anchorPane.getChildren().setAll(a);
        }
    }

    @FXML
    public void handleTBVoltar() throws IOException {
        AnchorPane a = (AnchorPane) FXMLLoader.load(getClass().getResource("/fxml/FXMLMainEventos.fxml"));
        anchorPane.getChildren().setAll(a);
    }
}
