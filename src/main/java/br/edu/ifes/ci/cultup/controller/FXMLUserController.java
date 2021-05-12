/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifes.ci.cultup.controller;

import br.edu.ifes.ci.cultup.model.domain.Pessoa;
import br.edu.ifes.ci.cultup.model.domain.Mensagem;
import br.edu.ifes.ci.cultup.service.PessoaService;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;

/**
 * FXML Controller class
 *
 * @author RyanO
 */
public class FXMLUserController implements Initializable {

    @FXML
    private ImageView IVPerfil;
    @FXML
    private Label LBNome;
    @FXML
    private Label LBNomeCompleto;
    @FXML
    private Label LBEmail;
    @FXML
    private Label LBNascimento;
    @FXML
    private Label LBCidade;
    @FXML
    private Label LBEstado;
    @FXML
    private Label LBCidadeEstado;
    @FXML
    private Button BTNPerfil;
    @FXML
    private Button BTNSenha;
    @FXML
    private Button BTNSair;
    @FXML
    private Button BTNEventos;
    @FXML
    private Button BTNRelatorio;
    @FXML
    private AnchorPane anchorPane;
    @FXML
    private StackPane stackPane;

    Pessoa pessoa;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            loadUser();
        } catch (ParseException ex) {
            Logger.getLogger(FXMLUserController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void loadUser() throws ParseException {
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdf = new SimpleDateFormat("dd 'de' MMMM 'de' yyyy");
        PessoaService pessoaService = new PessoaService();               
        pessoa = pessoaService.findById(System.getProperty("ID"));
        LBNome.setText(pessoa.getPessnome());
        LBNomeCompleto.setText(pessoa.getPessnome());
        LBEmail.setText(pessoa.getPessemail());
        Date date = sdf1.parse(pessoa.getPessdtnasc());
        LBNascimento.setText(sdf.format(sdf1.parse(pessoa.getPessdtnasc())));
        LBCidade.setText(pessoa.getCidcodigo().getCidnome());
        LBEstado.setText(pessoa.getCidcodigo().getUfcodigo().getUfnome() + " - " + pessoa.getCidcodigo().getUfcodigo().getUfsigla());
        LBCidadeEstado.setText(pessoa.getCidcodigo().getCidnome() + " - " + pessoa.getCidcodigo().getUfcodigo().getUfsigla());
        if (pessoa.getPessimagem() != null) {
            Image image = new Image(pessoa.getPessimagem());
            IVPerfil.setImage(image);
        } else {
            stackPane.setStyle("-fx-background-image: url('/main/resources/imagem/cultup-logo-2x.png'); -fx-background-size: 200px 200px;");
        }
    }

    @FXML
    public void handleBTNMeusEventos() throws IOException {
        AnchorPane a = (AnchorPane) FXMLLoader.load(getClass().getResource("/fxml/FXMLUserEvento.fxml"));
        anchorPane.getChildren().setAll(a);
    }

    @FXML
    public void handleBTNRelatorio() throws IOException {
        AnchorPane a = (AnchorPane) FXMLLoader.load(getClass().getResource("/fxml/FXMLGrafico.fxml"));
        anchorPane.getChildren().setAll(a);
    }

    @FXML
    public void handleBTNPerfil() throws IOException, ParseException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(FXMLUserAlterarController.class.getResource("/fxml/FXMLUserAlterar.fxml"));
        AnchorPane page = (AnchorPane) loader.load();
        FXMLUserAlterarController controller = loader.getController();
        controller.setUserAlterar(pessoa);
        anchorPane.getChildren().setAll(page);
    }

    @FXML
    public void handleBTNSair() throws IOException, InterruptedException {

        Thread threadEnviaComando = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    PessoaService pessoaService = new PessoaService();        
                    desconectar(pessoaService.findById(System.getProperty("ID")).getPessnome());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        threadEnviaComando.start();
        threadEnviaComando.join();

        System.setProperty("ID", "0");
        AnchorPane a = (AnchorPane) FXMLLoader.load(getClass().getResource("/fxml/FXMLLogin.fxml"));
        anchorPane.getChildren().setAll(a);

    }

    public void desconectar(String cliente) throws IOException {
        Socket socket = new Socket("localhost", 12345);
        ObjectOutputStream saida = new ObjectOutputStream(socket.getOutputStream());
        Mensagem mensagem = new Mensagem();
        mensagem.setNomeCliente(cliente);
        mensagem.setAction(Mensagem.Action.DISCONNECT);
        saida.writeObject(mensagem);
    }
}
