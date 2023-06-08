package br.com.fatec.controller;

import br.com.fatec.App;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;

import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

public class TelaGerenciarPedidosController implements Initializable {

    @FXML
    private TextField txtDesc;

    @FXML
    private TextField txtValor;

    @FXML
    private ComboBox<String> cbCliente;

    @FXML
    private ComboBox<String> cbEntregador;

    @FXML
    private Button btnCadastrar;

    @FXML
    private Button btnAlterar;

    @FXML
    private Button btnExcluir;

    @FXML
    private TextField txtId;

    @FXML
    private Button btnPesquisar;

    @FXML
    private Button btnVoltar;

    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }
    
    @FXML
    public void btnCadastrar_Click() {
    }

    @FXML
    public void btnAlterar_Click() {
    }

    @FXML
    public void btnExcluir_Click() {
    }

    @FXML
    public void btnPesquisar_Click() {
    }

    @FXML
    public void btnVoltar_Click() {
        String fxml = "TelaPrincipal";
        try {

            App.setRoot(fxml);
        } catch (IOException ex) {
            System.out.println("Ocorreu o seguinte erro: " + ex.getMessage());
        }
    }
}
