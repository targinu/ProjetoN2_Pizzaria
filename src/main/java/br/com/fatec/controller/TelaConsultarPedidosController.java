package br.com.fatec.controller;

import br.com.fatec.App;
import br.com.fatec.DAO.PedidosDAO;
import br.com.fatec.model.Pedido;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

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
    private TableColumn<Pedido, String> colClienteNome;

    @FXML
    private TableColumn<Pedido, String> colEntregador;

    @FXML
    private TableColumn<Pedido, String> colPlaca;

    @FXML
    private Button btnExcluir;

    @FXML
    private Button btnRestaurar;

    //armazena o pedido selecionado
    private Pedido pedidoSelecionado;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //configurar as colunas da tabela        
        colPedidoId.setCellValueFactory(new PropertyValueFactory<>("idPedido"));
        colDesc.setCellValueFactory(new PropertyValueFactory<>("descricao"));
        colValor.setCellValueFactory(new PropertyValueFactory<>("valor"));
        colClienteId.setCellValueFactory(new PropertyValueFactory<>("clienteId"));
        colClienteNome.setCellValueFactory(new PropertyValueFactory<>("clienteNome"));
        colEntregador.setCellValueFactory(new PropertyValueFactory<>("entregadorNome"));
        colPlaca.setCellValueFactory(new PropertyValueFactory<>("motoboyId"));

        //preencher a tabela com os pedidos existentes
        tblPedidos.setItems(preencheTabela());

        //adiciona o listener para capturar o pedido selecionado na tabela
        tblPedidos.getSelectionModel().selectedItemProperty().addListener((obs, antigoPedido, novoPedido) -> {
            pedidoSelecionado = novoPedido;
        });

    }

    @FXML
    private void btnExcluir_Click() {
        //verifica se o pedido está selecionado
        if (pedidoSelecionado != null) {
            //gera um alerta de confirmação
            Alert confirmacao = new Alert(Alert.AlertType.CONFIRMATION);
            confirmacao.setTitle("Confirmação de exclusão");
            confirmacao.setHeaderText("Tem certeza que deseja excluir o pedido?");
            confirmacao.setContentText("Essa ação não pode ser desfeita.");

            Optional<ButtonType> resultado = confirmacao.showAndWait();
            
            //se o usuario confirmar a exclusão
            if (resultado.isPresent() && resultado.get() == ButtonType.OK) {
                PedidosDAO dao = new PedidosDAO();
                try {
                    boolean excluiu = dao.remove(pedidoSelecionado);
                    if (excluiu) {
                        Alert sucesso = new Alert(Alert.AlertType.INFORMATION);
                        sucesso.setTitle("Sucesso");
                        sucesso.setHeaderText("Pedido excluído com sucesso.");
                        sucesso.showAndWait();
                        tblPedidos.setItems(preencheTabela()); // Atualiza a tabela após a exclusão
                    } else {
                        Alert erro = new Alert(Alert.AlertType.ERROR);
                        erro.setTitle("Erro");
                        erro.setHeaderText("Erro ao excluir o pedido.");
                        erro.setContentText("Não foi possível excluir o pedido.");
                        erro.showAndWait();
                    }
                } catch (SQLException ex) {
                    Alert erro = new Alert(Alert.AlertType.ERROR);
                    erro.setTitle("Erro");
                    erro.setHeaderText("Erro ao excluir o pedido.");
                    erro.setContentText("Ocorreu um erro durante a exclusão do pedido: " + ex.getMessage());
                    erro.showAndWait();
                }
            }
        } else {
            Alert aviso = new Alert(Alert.AlertType.WARNING);
            aviso.setTitle("Aviso");
            aviso.setHeaderText("Nenhum pedido selecionado.");
            aviso.setContentText("Por favor, selecione um pedido na tabela.");
            aviso.showAndWait();
        }
    }

    @FXML
    private void btnRestaurar_Click() {
        //restaurar a tabela exibindo todos os pedidos
        tblPedidos.setItems(preencheTabela());
    }

    @FXML
    private void btnVoltar_Click() {
        //voltar para a tela principal
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
                    "Erro ao Preencher Tabela: " + ex.getMessage(),
                    ButtonType.OK);
            alerta.showAndWait();
        }
        //retorna a collection com os pedidos
        return pedidos;
    }

    @FXML
    private void setButtonTrue(MouseEvent event) {
        btnExcluir.setDisable(false);
    }
}
