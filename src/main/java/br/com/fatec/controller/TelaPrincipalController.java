package br.com.fatec.controller;

import br.com.fatec.App;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class TelaPrincipalController {

    @FXML
    private Button btnGerenciarPedidos;

    @FXML
    private Button btnGerenciarPizzas;

    @FXML
    private Button btnGerenciarMotoboys;

    @FXML
    private Button btnConsultarPedidos;

    @FXML
    private Button btnSair;

    @FXML
    //navega para a tela de gerenciamento de motoboys
    private void btnGerenciarMotoboys_Click(ActionEvent event) {
        String fxml = "TelaGerenciarMotoboys";
        try {
            App.setRoot(fxml);
        } catch (IOException ex) {
            System.out.println("Ocorreu o seguinte erro: " + ex.getMessage());
        }
    }

    @FXML
    //navega para a tela de gerenciamento de clientes
    private void handleBtnGerenciarClientes(ActionEvent event) {
        String fxml = "TelaGerenciarClientes";
        try {
            App.setRoot(fxml);
        } catch (IOException ex) {
            System.out.println("Ocorreu o seguinte erro: " + ex.getMessage());
        }
    }

    @FXML
    //navega para a tela de gerenciamento de pedidos
    private void handleBtnGerenciarPedidos(ActionEvent event) {
        String fxml = "TelaGerenciarPedidos";
        try {
            App.setRoot(fxml);
        } catch (IOException ex) {
            System.out.println("Ocorreu o seguinte erro: " + ex.getMessage());
        }
    }

    @FXML
    //navega para a tela de consulta avançada
    private void handleBtnConsultarPedidos(ActionEvent event) {
        String fxml = "TelaConsultarPedidos";
        try {
            App.setRoot(fxml);
        } catch (IOException ex) {
            System.out.println("Ocorreu o seguinte erro: " + ex.getMessage());
        }
    }

    @FXML
    //fecha o programa
    private void btnSair_Click(ActionEvent event) {
        Stage stage = (Stage) btnSair.getScene().getWindow();
        stage.close();
    }

}
