/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifes.ci.cultup.controller;

import br.edu.ifes.ci.cultup.model.domain.Evento;
import br.edu.ifes.ci.cultup.service.EventoService;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

/**
 * FXML Controller class
 *
 * @author rroliveira
 */
public class FXMLUserEventoController implements Initializable {

    @FXML
    private AnchorPane anchorPane;
    @FXML
    private AnchorPane anchorPane2;
    @FXML
    private BorderPane borderPane;
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox vboxEventos;
    @FXML
    private ToggleButton TBAdd;
    
    EventoService eventoService = new EventoService();
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        vboxEventos.setSpacing(5.0);
        
        List<Evento> list = eventoService.findByPessoa(System.getProperty("ID"));
        for (Evento listEvento : list) {
            newPane(new Pane(), listEvento);
        }
    }   
    @FXML
    public void newPane(Pane pane, Evento evento) {
        Image image = new Image(evento.getEveimagem());
        ImageView imageEvento = new ImageView(image);
        Label titulo = new Label();
        Label subtitulo = new Label();

        anchorPane.setRightAnchor(pane, 20.0);
        anchorPane.setLeftAnchor(pane, 5.0);
        anchorPane.setTopAnchor(pane, 5.0);
        pane.setId(evento.getEvecodigo().toString());
        pane.setStyle("-fx-border-color: black");
        pane.setPrefHeight(80.0);
        pane.setPrefWidth(360.0);
        pane.setLayoutX(0.0);
        pane.setLayoutY(0.0);

        imageEvento.setFitHeight(70.0);
        imageEvento.setFitWidth(95.0);
        imageEvento.setLayoutX(5.0);
        imageEvento.setLayoutY(5.0);
        imageEvento.setPreserveRatio(false);
        imageEvento.setPickOnBounds(true);

        titulo.setText(evento.getEvetitulo());
        titulo.setFont(new Font(16.0));
        titulo.setLayoutX(116.0);
        titulo.setLayoutY(5.0);
        titulo.setPrefHeight(25.00);
        titulo.setPrefWidth(220.0);

        subtitulo.setText(evento.getEvesubtitulo());
        subtitulo.setWrapText(Boolean.TRUE);
        subtitulo.setFont(new Font(14.0));
        subtitulo.setLayoutX(116.0);
        subtitulo.setLayoutY(30.0);
        subtitulo.setPrefHeight(45.0);
        subtitulo.setPrefWidth(220.00);

        pane.getChildren().setAll(imageEvento, titulo, subtitulo);
        pane.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent t) {
                try {
                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(FXMLEventoController.class.getResource("/fxml/FXMLEvento.fxml"));
                    AnchorPane page = (AnchorPane) loader.load();
                    FXMLEventoController controller = loader.getController();
                    controller.setEvento(evento);
                    controller.setAlterarEvento();
                    anchorPane = (AnchorPane) scrollPane.getParent().getParent();
                    anchorPane.getChildren().setAll(page);

                } catch (IOException ex) {
                    Logger.getLogger(FXMLEventoController.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        });
        vboxEventos.getChildren().add(pane);
    }

    @FXML
    public void handleTBAdd() throws IOException {
        anchorPane = (AnchorPane) scrollPane.getParent().getParent();
        AnchorPane a = (AnchorPane) FXMLLoader.load(getClass().getResource("/fxml/FXMLUserEventoAdd.fxml"));
        anchorPane.getChildren().setAll(a);
    }

    @FXML
    public void handleTBVoltar() throws IOException {
        anchorPane = (AnchorPane) scrollPane.getParent().getParent();
        AnchorPane a = (AnchorPane) FXMLLoader.load(getClass().getResource("/fxml/FXMLUser.fxml"));
        anchorPane.getChildren().setAll(a);
    }

}
