package br.com.fatec.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

public class TelaPedidosGestorController implements Initializable {
    
    @FXML
    private ListView<String> pedidoListView;

    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        pedidoListView.getItems().addAll("Pedido 1", "Pedido 2", "Pedido 3", "Pedido 4");
    }

    @FXML
    private void voltar(ActionEvent event) throws IOException {
        Parent telaOpcoesGestorParent = FXMLLoader.load(getClass().getResource("/br/com/fatec/view/TelaOpcoesGestor.fxml"));
        Scene telaOpcoesGestorScene = new Scene(telaOpcoesGestorParent);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(telaOpcoesGestorScene);
        stage.show();
    }
}
