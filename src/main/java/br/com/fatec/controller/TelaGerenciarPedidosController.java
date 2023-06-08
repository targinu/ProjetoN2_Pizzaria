package br.com.fatec.controller;

import br.com.fatec.App;
import br.com.fatec.DAO.ClienteDAO;
import br.com.fatec.model.Cliente;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;

import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

public class TelaGerenciarPedidosController implements Initializable {

    @FXML
    private TextField txtDesc;

    @FXML
    private TextField txtValor;

    @FXML
    private ComboBox<Cliente> cbCliente;

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

     private ObservableList<Cliente> clientes = 
           FXCollections.observableArrayList();
         
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        cbCliente.setItems(preencheTabela());
        
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
    
    //método para trazer informaçôes do banco para a comboBox cbCliente
    private ObservableList<Cliente> preencheTabela() {
        ClienteDAO dao = new ClienteDAO();
        ObservableList<Cliente> clientes
            = FXCollections.observableArrayList();
        
        try {
            clientes.addAll(dao.lista(""));
        } catch (SQLException ex) {
            Alert alerta = new Alert(Alert.AlertType.ERROR,
                    "Erro Preenche Tabela: " + ex.getMessage(),
                    ButtonType.OK);
            alerta.showAndWait();
        }
        
        return clientes;
    }
}
