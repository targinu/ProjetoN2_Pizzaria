package br.com.fatec.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class TelaPrincipalController implements Initializable {

    @FXML
    private Button btnClientes;

    @FXML
    private Button btnGestor;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    @FXML
    private void handleClientesButton(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/br/com/fatec/view/TelaOpcoesCliente.fxml"));
        Parent root = loader.load();

        Stage stage = (Stage) btnClientes.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
    }

    @FXML
    private void handleGestorButton(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/br/com/fatec/view/TelaOpcoesGestor.fxml"));
        Parent root = loader.load();

        Stage stage = (Stage) btnGestor.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
    }
}
