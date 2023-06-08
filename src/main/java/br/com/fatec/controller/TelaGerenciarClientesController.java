package br.com.fatec.controller;

import br.com.fatec.DAO.ClienteDAO;
import br.com.fatec.model.Cliente;
import java.sql.SQLException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;

public class TelaGerenciarClientesController {
    
    @FXML
    private Button btnPesquisar;
    @FXML
    private Button btnCadastrar;
    @FXML
    private Button btnAlterar;
    @FXML
    private Button btnExcluir;
    @FXML
    private Button btnVoltar;
    @FXML
    private TextField txtNome;
    @FXML
    private TextField txtTelefone;
    @FXML
    private TextField txtEndereco;
    @FXML
    private TextField txtCidade;

    @FXML
    private void btnPesquisar_Click(ActionEvent event) {
  
    }

    @FXML
    private void btnCadastrar_Click(ActionEvent event) {
        //verifica se existem campos em branco
        if (txtNome.getText().isEmpty() || txtTelefone.getText().isEmpty() ||
                txtEndereco.getText().isEmpty() || txtCidade.getText().isEmpty()) {
            Alert alerta = new Alert(Alert.AlertType.WARNING,
                    "Por favor, preencha todos os campos e tente novamente.",
                    ButtonType.OK);
            alerta.showAndWait();
            return;
        }

        //cria um objeto Cliente com os dados preenchidos
        Cliente cliente = new Cliente();
        cliente.setNomeCliente(txtNome.getText());
        cliente.setTelefone(txtTelefone.getText());
        cliente.setEndereco(txtEndereco.getText());
        cliente.setCidade(txtCidade.getText());

        //cria uma instância do ClienteDAO
        ClienteDAO clienteDAO = new ClienteDAO();

        try {
            //chama o método de inserção do cliente
            clienteDAO.insere(cliente);

            Alert alerta = new Alert(Alert.AlertType.INFORMATION,
                    "Cliente cadastrado com sucesso!",
                    ButtonType.OK);
            alerta.showAndWait();

            // Limpa os campos após cadastrar
            limpaCampos();
        } catch (SQLException ex) {
            System.out.println("Erro de inclusão: " + ex.getMessage());

            // Trata o erro para exibir uma mensagem amigável ao usuário
            switch (ex.getErrorCode()) {
                default:
                    Alert alerta1 = new Alert(Alert.AlertType.WARNING,
                            "Erro (" + ex.getErrorCode() + ") ao incluir. Entre em contato com o suporte para mais informações.",
                            ButtonType.OK);
                    alerta1.showAndWait();
                    break;
            }
        }
    }

    
    @FXML
    private void btnAlterar_Click() {

    }
    
    @FXML
    private void btnExcluir_Click() {

    }
    
    public void limpaCampos(){
        txtNome.setText("");
        txtTelefone.setText("");
        txtEndereco.setText("");
        txtCidade.setText("");       
    }
    
}
