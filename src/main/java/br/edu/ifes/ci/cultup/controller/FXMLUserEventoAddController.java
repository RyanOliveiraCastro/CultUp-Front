/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifes.ci.cultup.controller;


import br.edu.ifes.ci.cultup.model.domain.Cidade;
import br.edu.ifes.ci.cultup.model.domain.Endereco;
import br.edu.ifes.ci.cultup.model.domain.Evento;
import br.edu.ifes.ci.cultup.model.domain.Pessoa;
import br.edu.ifes.ci.cultup.model.domain.Uf;
import br.edu.ifes.ci.cultup.service.CidadeService;
import br.edu.ifes.ci.cultup.service.EnderecoService;
import br.edu.ifes.ci.cultup.service.EventoService;
import br.edu.ifes.ci.cultup.service.UfService;
import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import java.sql.Time;
import java.time.LocalDate;
import java.util.regex.Pattern;
import javafx.collections.FXCollections;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author RyanO
 */
public class FXMLUserEventoAddController implements Initializable {

    @FXML
    private ImageView IVEvento;
    @FXML
    private ToggleButton TBEvento;
    @FXML
    private TextField TFTitulo;
    @FXML
    private TextField TFSubTitulo;
    @FXML
    private DatePicker DPData;
    @FXML
    private TextField TFTime;
    @FXML
    private TextField TFLocal;
    @FXML
    private TextField TFCep;
    @FXML
    private ComboBox<Cidade> CBCidade;
    @FXML
    private ComboBox<Uf> CBEstado;
    @FXML
    private TextField TFRua;
    @FXML
    private TextField TFBairro;
    @FXML
    private TextField TFPReferencia;
    @FXML
    private TextField TFNumero;
    @FXML
    private TextField TFDescricao;
    @FXML
    private Button BTNCadastrar;
    @FXML
    private ToggleButton TBReturn;
    @FXML
    private AnchorPane anchorPane;
    @FXML
    private AnchorPane anchorPane2;
    @FXML
    private ScrollPane scrollPane;

    private List<Uf> listUf;
    private ObservableList<Uf> observableListUf;
    private List<Cidade> listCidade;
    private ObservableList<Cidade> observableListCidade;

    final FileChooser fileChooser = new FileChooser();
    private Desktop desktop = Desktop.getDesktop();
    private File file;
    private Evento evento = new Evento();
    private Endereco endereco = new Endereco();
    EventoService eventoService = new EventoService();
    EnderecoService enderecoService = new EnderecoService();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        loadComboboxUf();
        TBEvento.setOnMouseClicked((event) -> {
            file = fileChooser.showOpenDialog(new Stage());
            Image image = new Image(file.toURI().toString());
            IVEvento.setImage(image);
        });
    }

    @FXML
    public void handleButtonConfirmar() throws Exception {
        if (evento.getEndcodigo() == null) {

        } else {
            endereco = enderecoService.findById(evento.getEndcodigo().getEndcodigo());
        }
        try {
            valida();
            if (evento.getEveimagem() == null) {
                evento.setEveimagem(file.toURI().toString());
            }
            evento.setPesscodigo(new Pessoa(Integer.valueOf(System.getProperty("ID"))));
            evento.setEvetitulo(TFTitulo.getText());
            evento.setEvesubtitulo(TFSubTitulo.getText());
            evento.setEvedata(DPData.getValue().toString());
            evento.setEvehora(TFTime.getText());
            evento.setEvelocal(TFLocal.getText());
            evento.setEvedescricao(TFDescricao.getText());
            endereco.setEndlogradouro(TFRua.getText());
            endereco.setEndbairro(TFBairro.getText());
            endereco.setEndpontoreferencia(TFPReferencia.getText());
            endereco.setEndcep(TFCep.getText());
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            endereco.setEnddate(sdf.format(new Date()));
            endereco.setEndnumero(Integer.valueOf(TFNumero.getText()));
            endereco.setCidcodigo(CBCidade.getValue());
            if (evento.getEndcodigo() == null) {
                endereco = enderecoService.persist(endereco);
                evento.setEndcodigo(endereco);
            } else {
                enderecoService.merge(endereco);                
            }
            
            eventoService.merge(evento);            
            handleTBVoltar();

        } catch (Exception e) {
            e.printStackTrace();
            e.getMessage();
            throw new Exception("Erro ao cadastrar Evento");
        }

    }

    public void valida() throws Exception {
        String msg = "";

        if (evento.getEveimagem() == null && file == null) {
            msg += "Imagem\n";
        }
        if (TFTitulo.getText().isEmpty()) {
            msg += "Titulo\n";
        }
        if (TFSubTitulo.getText().isEmpty()) {
            msg += "SubTitulo\n";
        }
        if (DPData.getValue() == null) {
            msg += "Data\n";
        }
        try {
            Time time = new Time(new SimpleDateFormat("HH:mm").parse(TFTime.getText()).getTime());
        } catch (Exception e) {
            msg += "Hora\n";
        }
        if (TFLocal.getText().isEmpty()) {
            msg += "Local\n";
        }
        if (TFDescricao.getText().isEmpty()) {
            msg += "Descricao\n";
        }
        if (TFRua.getText().isEmpty()) {
            msg += "Rua\n";
        }
        if (TFBairro.getText().isEmpty()) {
            msg += "Bairro\n";
        }
        if (TFPReferencia.getText().isEmpty()) {
            msg += "Referencia\n";
        }
        Pattern p = Pattern.compile("[0-9]");
        if (TFCep.getText().isEmpty() || p.matcher(TFCep.getText()).matches() == true || TFCep.getText().length() != 8) {
            msg += "CEP\n";
        }
        if (TFNumero.getText().isEmpty() && p.matcher(TFNumero.getText()).matches() == false) {
            msg += "Numero\n";
        }
        if (CBCidade.getValue() == null) {
            msg += "Cidade";
        }

        if (!msg.isEmpty()) {
            Alert alerta = new Alert(Alert.AlertType.ERROR);
            alerta.setContentText("Preencha os campos Obrigatorios: \n" + msg);
            alerta.showAndWait();
            throw new Exception("Preencha os campos Obrigatorios: \n" + msg);
        }

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

    public void setEvento(Evento evento) {
        this.evento = evento;
        Image image = new Image(evento.getEveimagem());
        IVEvento.setImage(image);
        TFTitulo.setText(evento.getEvetitulo());
        TFSubTitulo.setText(evento.getEvesubtitulo());        
        DPData.setValue(LocalDate.parse(evento.getEvedata()));
        TFTime.setText(evento.getEvehora());
        TFLocal.setText(evento.getEvelocal());
        TFDescricao.setText(evento.getEvedescricao());
        TFRua.setText(evento.getEndcodigo().getEndlogradouro());
        TFBairro.setText(evento.getEndcodigo().getEndbairro());
        TFPReferencia.setText(evento.getEndcodigo().getEndpontoreferencia());
        TFCep.setText(evento.getEndcodigo().getEndcep());
        TFNumero.setText(evento.getEndcodigo().getEndnumero().toString());
        CBCidade.setValue(evento.getEndcodigo().getCidcodigo());
        CBEstado.setValue(evento.getEndcodigo().getCidcodigo().getUfcodigo());
    }

    @FXML
    public void handleTBVoltar() throws IOException {
        AnchorPane a = (AnchorPane) FXMLLoader.load(getClass().getResource("/fxml/FXMLUserEvento.fxml"));
        anchorPane.getChildren().setAll(a);
    }

}
