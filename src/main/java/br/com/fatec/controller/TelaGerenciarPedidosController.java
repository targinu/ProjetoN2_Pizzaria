package br.com.fatec.controller;

import br.com.fatec.App;
import br.com.fatec.DAO.ClienteDAO;
import br.com.fatec.DAO.PedidosDAO;
import br.com.fatec.model.Cliente;
import br.com.fatec.model.Motoboy;
import br.com.fatec.model.Pedido;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
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
    private ComboBox<Motoboy> cbEntregador;

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
    
    Motoboy m1 = new Motoboy("Bruno","ABC1A23");
    Motoboy m2 = new Motoboy("Carlos","DEF5B67");
    Motoboy m3 = new Motoboy("Zeca","GHI9C01");
    
    private ObservableList<Motoboy> motoboys = 
           FXCollections.observableArrayList(m1,m2,m3);
    
    //list criado para receber os dados que vem da tela GerenciarMotoboys
    public ObservableList<Motoboy> dado = 
           FXCollections.observableArrayList();

    //para pegar os dados;
    public ObservableList<Motoboy> getDado() {
        return dado;
    }
    
    //para setar os dados
    public void setDado(ObservableList<Motoboy> dado) {
        this.dado = dado;
    }
    
     private ObservableList<Cliente> clientes = 
           FXCollections.observableArrayList();
             
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        cbEntregador.setItems(motoboys);
        cbCliente.setItems(preencheTabela());
        
    }
    
    @FXML
    public void btnCadastrar_Click() {
        //verifica se todos os campos estão preenchidos
        if (!verificaCampos()) {
            return;
        }

        //cria um novo objeto Pedido com base nos valores dos campos
        String descricao = txtDesc.getText();
        double valor = Double.parseDouble(txtValor.getText());
        Cliente cliente = cbCliente.getValue();
        Motoboy motoboy = cbEntregador.getValue();
        Pedido novoPedido = new Pedido(descricao, valor, cliente.getIdCliente(), motoboy.getPlacaMoto());

        //chama o DAO para inserir o novo pedido no banco de dados
        PedidosDAO dao = new PedidosDAO();
        try {
            boolean inserido = dao.insere(novoPedido);
            if (inserido) {
                Alert alerta = new Alert(Alert.AlertType.INFORMATION,
                        "Pedido cadastrado com sucesso!",
                        ButtonType.OK);
                alerta.showAndWait();
                limpaCampos();
            } else {
                Alert alerta = new Alert(Alert.AlertType.WARNING,
                        "Erro ao cadastrar pedido.",
                        ButtonType.OK);
                alerta.showAndWait();
            }
        } catch (SQLException ex) {
            //erro detalhado
            System.out.println("Erro ao cadastrar pedido: " + ex.getMessage());
            //tratamento de erro para o usuário
            Alert alerta = new Alert(Alert.AlertType.WARNING,
                    "Erro ao cadastrar pedido. Código do erro: " + ex.getErrorCode(),
                    ButtonType.OK);
            alerta.showAndWait();
        }
    }

    @FXML
    private void btnAlterar_Click(ActionEvent event) {
        //verifica se o ID do pedido está preenchido
        if (txtId.getText().isEmpty()) {
            Alert alerta = new Alert(Alert.AlertType.INFORMATION,
                    "Por favor, preencha o ID do pedido.",
                    ButtonType.OK);
            alerta.showAndWait();
            return;
        }
        
      //verifica os outros campos em branco
        if(!verificaCampos())
            return;
        
        //objeto com as querys
        PedidosDAO dao = new PedidosDAO();
        
        //objeto para incluir no banco, dados que podem vir da tela
        Pedido pedido = new Pedido(Integer.parseInt(txtId.getText()),
                txtDesc.getText(), Double.parseDouble(txtValor.getText()),
                obterClienteId(),cbEntregador.getValue().getPlacaMoto());
                
        try {
            //verifica se existe uma siglaClube = ao txtSigla
            if(dao.buscaID(pedido) == null){
                //se nao existir
                Alert alerta = new Alert(Alert.AlertType.WARNING,
                    "Alteração não realizada. Não existe nenhum pedido no banco"
                            + " com o id: "+ pedido.getIdPedido(), 
                    ButtonType.OK);
                    alerta.showAndWait();
                 //retorna   
                return;
            }
            //realiza a alteração caso tudo esteja correto
            if(dao.altera(pedido)){
                Alert alerta = new Alert(Alert.AlertType.INFORMATION,
                    "Alterações em "+pedido.getIdPedido() +" realizadas com sucesso", 
                    ButtonType.OK);
                    alerta.showAndWait();
                    //limpa campos
                    limpaCampos();
            }
                
        } catch (SQLException ex) {
            //dando o erro expecificado
            System.out.println("Erro de alteração: " + ex.getMessage());
            //tratando o erro para o usuario entender
            switch (ex.getErrorCode()) {
                default:
                        Alert alerta1 = new Alert(Alert.AlertType.WARNING,
                    "Erro ("+ex.getErrorCode()+") ao alterar.", 
                    ButtonType.OK);
                    alerta1.showAndWait();
                    break;    
            }
            
        }
    }

    @FXML
    private void btnExcluir_Click(ActionEvent event) {
        //verifica se o ID do pedido está preenchido
        if (txtId.getText().isEmpty()) {
            Alert alerta = new Alert(Alert.AlertType.INFORMATION,
                    "Por favor, preencha o ID do pedido.",
                    ButtonType.OK);
            alerta.showAndWait();
            return;
        }

        //objeto para chamar as queries e objeto para pegar o ID do pedido
        PedidosDAO dao = new PedidosDAO();
        int idPedido = Integer.parseInt(txtId.getText());
        Pedido pedido = new Pedido(idPedido);

        //busca o pedido no banco de dados
        try {
            pedido = dao.buscaID(pedido);

            if (pedido == null) {
                Alert alerta = new Alert(Alert.AlertType.WARNING,
                        "Não foram encontrados dados correspondentes ao ID: " + txtId.getText(),
                        ButtonType.OK);
                alerta.showAndWait();
            } else {
                //exibe um diálogo de confirmação antes de excluir o pedido
                Alert confirmacao = new Alert(Alert.AlertType.CONFIRMATION,
                        "Deseja realmente excluir o pedido?",
                        ButtonType.YES, ButtonType.NO);
                confirmacao.showAndWait();

                if (confirmacao.getResult() == ButtonType.YES) {
                    //remove o pedido do banco de dados
                    if (dao.remove(pedido)) {
                        Alert alerta = new Alert(Alert.AlertType.INFORMATION,
                                "Pedido excluído com sucesso!",
                                ButtonType.OK);
                        alerta.showAndWait();
                        //limpa os campos após a exclusão
                        limpaCampos();
                    } else {
                        Alert alerta = new Alert(Alert.AlertType.WARNING,
                                "Não foi possível excluir o pedido.",
                                ButtonType.OK);
                        alerta.showAndWait();
                    }
                }
            }
        } catch (SQLException ex) {
            //erro detalhado
            System.out.println("Erro ao excluir pedido: " + ex.getMessage());
            //tratamento de erro para o usuário
            Alert alerta = new Alert(Alert.AlertType.WARNING,
                    "Erro ao excluir pedido. Código do erro: " + ex.getErrorCode(),
                    ButtonType.OK);
            alerta.showAndWait();
        }
    }

    @FXML
    public void btnPesquisar_Click() {
        //verifica se o ID do pedido está preenchido
        if (txtId.getText().isEmpty()) {
            Alert alerta = new Alert(Alert.AlertType.INFORMATION,
                    "Por favor, preencha o ID do pedido.",
                    ButtonType.OK);
            alerta.showAndWait();
            return;
        }

        //objeto para chamar as querys e objeto para pegar o ID do pedido
        PedidosDAO dao = new PedidosDAO();
        int idPedido = Integer.parseInt(txtId.getText());
        Pedido pedido = new Pedido(idPedido);

        try {
            //busca o pedido no banco de dados
            pedido = dao.buscaID(pedido);

            if (pedido == null) {
                Alert alerta = new Alert(Alert.AlertType.WARNING,
                        "Não foram encontrados dados correspondentes ao ID: " + txtId.getText(),
                        ButtonType.OK);
                alerta.showAndWait();
            } else {
                txtDesc.setText(pedido.getDescricao());
                txtValor.setText(String.valueOf(pedido.getValor()));

                for (Cliente cliente : cbCliente.getItems()) {
                    if (cliente.getIdCliente() == pedido.getClienteId()) {
                        cbCliente.getSelectionModel().select(cliente);
                        break;
                    }
                }

                for (Motoboy motoboy : cbEntregador.getItems()) {
                    if (motoboy.getPlacaMoto().equals(pedido.getMotoboyId())) {
                        cbEntregador.getSelectionModel().select(motoboy);
                        break;
                    }
                }
            }
        } catch (SQLException ex) {
            //erro detalhado
            System.out.println("Erro ao pesquisar pedido: " + ex.getMessage());
            //tratamento de erro para o usuário
            Alert alerta = new Alert(Alert.AlertType.WARNING,
                    "Erro ao pesquisar pedido. Código do erro: " + ex.getErrorCode(),
                    ButtonType.OK);
            alerta.showAndWait();
        }
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
    
    public boolean verificaCampos(){
        boolean condicao = true;
        //Verifica se existem campos em branco
        if(txtDesc.getText().isEmpty() || txtValor.getText().isEmpty() || 
                cbCliente.getSelectionModel().isEmpty() || cbEntregador.getSelectionModel().isEmpty()){
            //notifica os campos em branco
            Alert alerta = new Alert(Alert.AlertType.WARNING,
                    "Todos os campos devem sem preenchidos!", 
                    ButtonType.OK);
            alerta.showAndWait();
            //retorna falso
            condicao = false;       
        }
        return condicao;
    }
    
    public void limpaCampos(){
        txtDesc.setText("");
        txtValor.setText("");
        cbCliente.getSelectionModel().clearSelection();
        cbEntregador.getSelectionModel().clearSelection();                
    }
    
    //metodo para obter somente o id do cliente, sem o nome
    //já que no método toString da classe Cliente foi usado
    //concatenação para melhorar a exibição na comboBox
    private int obterClienteId() {
    String clienteSelecionado = cbCliente.getValue().toString();
    String[] partes = clienteSelecionado.split(" - ");
    int clienteId = Integer.parseInt(partes[0]);
    return clienteId;
}
    
}
