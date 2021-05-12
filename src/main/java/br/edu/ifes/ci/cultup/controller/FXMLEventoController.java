/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifes.ci.cultup.controller;

import br.edu.ifes.ci.cultup.model.domain.Evento;
import br.edu.ifes.ci.cultup.model.domain.Iteracao;
import br.edu.ifes.ci.cultup.model.domain.Pessoa;
import br.edu.ifes.ci.cultup.service.IteracaoService;
import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javax.swing.text.MaskFormatter;

/**
 * FXML Controller class
 *
 * @author rroliveira
 */
public class FXMLEventoController implements Initializable {

    @FXML
    private ToggleButton TBReturn;
    @FXML
    private AnchorPane anchorPane;
    @FXML
    private AnchorPane anchorPane2;
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private ImageView imageviewEvento;
    @FXML
    private ToggleButton TBInteresse;
    @FXML
    private ToggleButton TBEuvou;
    @FXML
    private ToggleButton TBCompartilhar;
    @FXML
    private Label tituloEvento;
    @FXML
    private Label localEvento;
    @FXML
    private Label enderecoEvento;
    @FXML
    private Label dataHoraEvento;
    @FXML
    private Label descricaoEvento;
    @FXML
    private Button BTNAlterarEvento;

    public Evento evento;


    IteracaoService iteracaoService = new IteracaoService();
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        TBEuvou.setOnMouseClicked((MouseEvent event) -> {
            if (System.getProperty("ID").equals("0")) {
                alertWarning("É Necessario logar");
            } else {
                Iteracao iteracao = iteracaoService.findIteracao(Integer.valueOf(System.getProperty("ID")), evento.getEvecodigo());
                if (iteracao == null) {
                    iteracao = new Iteracao(new Pessoa(Integer.valueOf(System.getProperty("ID"))), evento);
                }
                if (iteracao.getIteeuvou() != null && iteracao.getIteeuvou()) {
                    iteracao.setIteeuvou(Boolean.FALSE);
                    iteracaoService.merge(iteracao);
                } else {
                    iteracao.setIteeuvou(Boolean.TRUE);
                    iteracaoService.merge(iteracao);
                }
                setIteracaoEuvou(iteracao);
            }
        });
        TBInteresse.setOnMouseClicked((MouseEvent event) -> {
            if (System.getProperty("ID").equals("0")) {                
                alertWarning("É Necessario logar");
            } else {
                Iteracao iteracao = iteracaoService.findIteracao(Integer.valueOf(System.getProperty("ID")), evento.getEvecodigo());
                if (iteracao == null) {
                    iteracao = new Iteracao(new Pessoa(Integer.valueOf(System.getProperty("ID"))), evento);
                }
                if (iteracao.getIteinteresse() != null && iteracao.getIteinteresse()) {
                    iteracao.setIteinteresse(Boolean.FALSE);
                    iteracaoService.merge(iteracao);
                } else {
                    iteracao.setIteinteresse(Boolean.TRUE);
                    iteracaoService.merge(iteracao);
                }
                setIteracaoTenhoInteresse(iteracao);
            }
        });
    }

    @FXML

    public void setIteracaoTenhoInteresse(Iteracao iteracao) {
        //Iteracao iteracao = iteracaoDAO.findIteracao(new Pessoa(Integer.valueOf(System.getProperty("ID"))), evento);
        if (iteracao != null) {
            if (iteracao.getIteinteresse() != null && iteracao.getIteinteresse()) {
                TBInteresse.getStyleClass().remove("starBorder");
                TBInteresse.getStyleClass().add("star");
            } else {
                TBInteresse.getStyleClass().remove("star");
                TBInteresse.getStyleClass().add("starBorder");
            }
        }
    }

    @FXML
    public void setIteracaoEuvou(Iteracao iteracao) {
        //Iteracao iteracao = iteracaoDAO.findIteracao(new Pessoa(Integer.valueOf(System.getProperty("ID"))), evento);
        if (iteracao != null) {
            if (iteracao.getIteeuvou() != null && iteracao.getIteeuvou()) {
                TBEuvou.getStyleClass().remove("checkBorder");
                TBEuvou.getStyleClass().add("check");
            } else {
                TBEuvou.getStyleClass().remove("check");
                TBEuvou.getStyleClass().add("checkBorder");
            }
        }
    }

    public void setEvento(Evento evento){
        this.evento = evento;
        SimpleDateFormat sdata = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat data = new SimpleDateFormat("dd' de 'MMMM' de 'yyyy' as '");
        String endereco = evento.getEndcodigo().getEndlogradouro();
        endereco += ", " + evento.getEndcodigo().getEndbairro();
        endereco += ", " + evento.getEndcodigo().getEndnumero();
        this.tituloEvento.setText(evento.getEvetitulo());
        this.localEvento.setText(evento.getEvelocal());
        this.enderecoEvento.setText(endereco);
        try {
            this.dataHoraEvento.setText(data.format(sdata.parse(evento.getEvedata())) + evento.getEvehora());
        } catch (ParseException ex) {
            Logger.getLogger(FXMLEventoController.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.descricaoEvento.setText(evento.getEvedescricao());
        Image image = new Image(evento.getEveimagem());
        this.imageviewEvento.setImage(image);
        Iteracao iteracao = iteracaoService.findIteracao(Integer.valueOf(System.getProperty("ID")), evento.getEvecodigo());
        setIteracaoEuvou(iteracao);
        setIteracaoTenhoInteresse(iteracao);
    }

    @FXML
    public void setAlterarEvento() {
        BTNAlterarEvento.setOpacity(1);
        BTNAlterarEvento.setDisable(false);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPane.setVmax(1);
    }

    @FXML
    public void handleBTNAlterarEvento() throws IOException, ParseException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(FXMLUserEventoAddController.class
                .getResource("/fxml/FXMLUserEventoAdd.fxml"));
        AnchorPane page = (AnchorPane) loader.load();
        FXMLUserEventoAddController controller = loader.getController();
        controller.setEvento(evento);
        anchorPane.getChildren().setAll(page);
    }

    @FXML
    public void handleTBVoltar() throws IOException {
        AnchorPane a = (AnchorPane) FXMLLoader.load(getClass().getResource("/fxml/FXMLMainEventos.fxml"));
        anchorPane.getChildren().setAll(a);
    }
    
    public void alertWarning(String msg){
        Alert alerta = new Alert(Alert.AlertType.WARNING);
        alerta.setTitle("Atenção");
        alerta.setContentText(msg);
        alerta.showAndWait();
    }
    
    public static String formatString(String value, String pattern) {
        MaskFormatter mf;
        try {
            mf = new MaskFormatter(pattern);
            mf.setValueContainsLiteralCharacters(false);
            return mf.valueToString(value);
        } catch (ParseException ex) {
            return value;
        }
    }
}
