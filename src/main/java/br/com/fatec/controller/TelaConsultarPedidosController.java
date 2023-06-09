package br.com.fatec.controller;

import br.com.fatec.App;
import br.com.fatec.DAO.PedidosDAO;
import br.com.fatec.model.Pedido;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class TelaConsultarPedidosController implements Initializable {

    @FXML
    private Button btnVoltar;

    @FXML
    private TableView<Pedido> tblPedidos;

    @FXML
    private TableColumn<Pedido, Integer> colPedidoId;

    @FXML
    private TableColumn<Pedido, String> colDesc;

    @FXML
    private TableColumn<Pedido, Double> colValor;

    @FXML
    private TableColumn<Pedido, Integer> colClienteId;

    @FXML
    private TableColumn<Pedido, String> colPlaca;

    @FXML
    private TextField txtBusca;

    @FXML
    private Button btnFiltrar;

    @FXML
    private Button btnRestaurar;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        
        //configurar as colunas da tabela        
        colPedidoId.setCellValueFactory(new PropertyValueFactory<>("idPedido"));
        colDesc.setCellValueFactory(new PropertyValueFactory<>("descricao"));
        colValor.setCellValueFactory(new PropertyValueFactory<>("valor"));
        colClienteId.setCellValueFactory(new PropertyValueFactory<>("clienteId"));
        colPlaca.setCellValueFactory(new PropertyValueFactory<>("motoboyId"));

        //preencher a tabela com os pedidos existentes
        tblPedidos.setItems(preencheTabela());
    }

    @FXML
    private void btnFiltrar_Click() {
        //verificar se o campo de busca está vazio
        if (txtBusca.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Digite algo para ser filtrado.", ButtonType.OK);
            alert.showAndWait();
            return;
        }

        //cria um objeto dao para utilizar as funções do sql 
        PedidosDAO dao = new PedidosDAO();
        //cria uma list de Jogador
        ObservableList<Pedido> pedidos = FXCollections.observableArrayList();
        
        try {
            //adiciona todos os 'pedidos' com suas informações de acordo com a condição
            pedidos.addAll(dao.lista(" = '"+txtBusca.getText()+"'"));
        } catch (SQLException ex) {
            System.out.println("Erro: "+ ex.getErrorCode());
            Alert alerta = new Alert(Alert.AlertType.ERROR,
                    "Erro Preenche Tabela: " + ex.getMessage(),
                    ButtonType.OK);
            alerta.showAndWait();
        }
        //cria a nova tableView com os valores filtrados
        tblPedidos.setItems(pedidos);
        
        txtBusca.setText("");
    }

    @FXML
    private void btnRestaurar_Click() {
        // Restaurar a tabela exibindo todos os pedidos
        tblPedidos.setItems(preencheTabela());
        txtBusca.clear();
    }

    @FXML
    private void btnVoltar_Click() {
        // Voltar para a tela principal
        String fxml = "TelaPrincipal";
        try {
            App.setRoot(fxml);
        } catch (IOException ex) {
            System.out.println("Ocorreu o seguinte erro: " + ex.getMessage());
        }
    }

    private ObservableList<Pedido> preencheTabela() {
        //cria um objeto dao para utilizar as funções do sql 
        PedidosDAO dao = new PedidosDAO();
        //cria uma list de Pedido
        ObservableList<Pedido> pedidos = FXCollections.observableArrayList();
        
        try {
            //seleciona todos os pedidos da tabela Pedidos e adiciona a uma collection, sem nenhuma condição
            pedidos.addAll(dao.lista(""));
        } catch (SQLException ex) {
            Alert alerta = new Alert(Alert.AlertType.ERROR,
                    "Erro Preenche Tabela: " + ex.getMessage(),
                    ButtonType.OK);
            alerta.showAndWait();
        }
        //retorna a collection com os pedidos
        return pedidos;
    }

    /*
    private ObservableList<Pedido> getPedidosFiltrados(String busca) {
        // Obter a lista de pedidos filtrados com base na busca realizada
        PedidosDAO pedidoDAO = new PedidosDAO();
        try {
            return FXCollections.observableArrayList(pedidoDAO.getPedidosFiltrados(busca));
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR,
                    "Erro ao obter a lista de pedidos filtrados: " + e.getMessage(), ButtonType.OK);
            alert.showAndWait();
            return FXCollections.emptyObservableList();
        }
    }    
    */

}
