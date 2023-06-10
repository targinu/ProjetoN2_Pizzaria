package br.com.fatec.controller;

import br.com.fatec.App;
import br.com.fatec.DAO.ClienteDAO;
import br.com.fatec.model.Cliente;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;

public class TelaGerenciarClientesController implements Initializable {

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

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    @FXML
    private void btnPesquisar_Click(ActionEvent event) {
        //verifica se o campo de pesquisa está vazio
        if (txtTelefone.getText().isEmpty()) {
            Alert alerta = new Alert(Alert.AlertType.WARNING,
                    "Por favor, preencha o telefone do cliente e tente novamente.",
                    ButtonType.OK);
            alerta.showAndWait();
            return;
        }

        //cria um objeto Cliente com o telefone preenchido
        Cliente cliente = new Cliente();
        cliente.setTelefone(txtTelefone.getText());

        //cria uma instância do ClienteDAO
        ClienteDAO clienteDAO = new ClienteDAO();

        try {
            //busca o cliente pelo telefone
            Cliente clienteEncontrado = clienteDAO.buscaID(cliente);

            if (clienteEncontrado == null) {
                Alert alerta = new Alert(Alert.AlertType.WARNING,
                        "Não foi possível encontrar o cliente com o telefone informado.",
                        ButtonType.OK);
                alerta.showAndWait();
            } else {
                //preenche os campos com os dados do cliente encontrado
                txtNome.setText(clienteEncontrado.getNomeCliente());
                txtTelefone.setText(clienteEncontrado.getTelefone());
                txtEndereco.setText(clienteEncontrado.getEndereco());
                txtCidade.setText(clienteEncontrado.getCidade());
            }
        } catch (SQLException ex) {
            System.out.println("Erro ao buscar cliente: " + ex.getMessage());

            //trata o erro para exibir uma mensagem para o usuário
            switch (ex.getErrorCode()) {
                default:
                    Alert alerta = new Alert(Alert.AlertType.WARNING,
                            "Erro (" + ex.getErrorCode() + ") ao buscar cliente. Entre em contato com o suporte para mais informações.",
                            ButtonType.OK);
                    alerta.showAndWait();
                    break;
            }
        }
    }

    @FXML
    private void btnCadastrar_Click(ActionEvent event) {
        //verifica se existem campos em branco
        if (txtNome.getText().isEmpty() || txtTelefone.getText().isEmpty()
                || txtEndereco.getText().isEmpty() || txtCidade.getText().isEmpty()) {
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

        //verifica se o telefone já está cadastrado
        try {
            Cliente clienteExistente = new Cliente();
            clienteExistente.setTelefone(txtTelefone.getText());
            if (clienteDAO.buscaID(clienteExistente) != null) {
                Alert alerta = new Alert(Alert.AlertType.WARNING,
                        "O telefone informado já está cadastrado para outro cliente.",
                        ButtonType.OK);
                alerta.showAndWait();
                return;
            }

            //chama o método de inserção do cliente
            clienteDAO.insere(cliente);

            Alert alerta = new Alert(Alert.AlertType.INFORMATION,
                    "Cliente cadastrado com sucesso!",
                    ButtonType.OK);
            alerta.showAndWait();

            //limpa os campos após cadastrar
            limpaCampos();
        } catch (SQLException ex) {
            System.out.println("Erro de inclusão: " + ex.getMessage());

            //trata o erro para exibir uma mensagem para o usuário
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
    private void btnAlterar_Click(ActionEvent event) {
        //verifica se existem campos em branco
        if (txtNome.getText().isEmpty() || txtTelefone.getText().isEmpty()
                || txtEndereco.getText().isEmpty() || txtCidade.getText().isEmpty()) {
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
            //verifica se o cliente existe no banco de dados
            Cliente clienteExistente = clienteDAO.buscaID(cliente);
            if (clienteExistente == null) {
                Alert alerta = new Alert(Alert.AlertType.WARNING,
                        "Não foi possível encontrar o cliente informado.",
                        ButtonType.OK);
                alerta.showAndWait();
                return;
            }

            //realiza a alteração do cliente
            boolean alterado = clienteDAO.altera(cliente);
            if (alterado) {
                Alert alerta = new Alert(Alert.AlertType.INFORMATION,
                        "Cliente atualizado com sucesso!",
                        ButtonType.OK);
                alerta.showAndWait();

                //limpa os campos após atualizar
                limpaCampos();
            } else {
                Alert alerta = new Alert(Alert.AlertType.WARNING,
                        "Não foi possível atualizar o cliente. Verifique novamente e tente novamente.",
                        ButtonType.OK);
                alerta.showAndWait();
            }
        } catch (SQLException ex) {
            System.out.println("Erro de alteração: " + ex.getMessage());

            //trata o erro para exibir uma mensagem para o usuário
            switch (ex.getErrorCode()) {
                default:
                    Alert alerta1 = new Alert(Alert.AlertType.WARNING,
                            "Erro (" + ex.getErrorCode() + ") ao alterar. Entre em contato com o suporte para mais informações.",
                            ButtonType.OK);
                    alerta1.showAndWait();
                    break;
            }
        }
    }

    @FXML
    private void btnExcluir_Click(ActionEvent event) {
        //verifica se existem campos em branco
        if (txtNome.getText().isEmpty() || txtTelefone.getText().isEmpty()
                || txtEndereco.getText().isEmpty() || txtCidade.getText().isEmpty()) {
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

        //verifica se o cliente existe no banco de dados
        try {
            Cliente clienteExistente = clienteDAO.buscaID(cliente);
            if (clienteExistente == null) {
                Alert alerta = new Alert(Alert.AlertType.WARNING,
                        "Não foi possível encontrar o cliente informado.",
                        ButtonType.OK);
                alerta.showAndWait();
                return;
            }

            //chama o método de exclusão do cliente
            boolean removido = clienteDAO.remove(clienteExistente);
            if (removido) {
                Alert alerta = new Alert(Alert.AlertType.INFORMATION,
                        "Cliente removido com sucesso!",
                        ButtonType.OK);
                alerta.showAndWait();

                //limpa os campos após excluir
                limpaCampos();
            } else {
                Alert alerta = new Alert(Alert.AlertType.WARNING,
                        "Não foi possível remover o cliente. Verifique novamente e tente novamente.",
                        ButtonType.OK);
                alerta.showAndWait();
            }
        } catch (SQLException ex) {
            System.out.println("Erro de exclusão: " + ex.getMessage());

            //trata o erro para exibir uma mensagem para o usuário
            switch (ex.getErrorCode()) {
                case 1451:
                    Alert alerta = new Alert(Alert.AlertType.WARNING,
                            "Erro (" + ex.getErrorCode() + ") ao excluir. Para realizar a exclusão, nenhum cliente pode estar associado a este cliente.",
                            ButtonType.OK);
                    alerta.showAndWait();
                    break;
                default:
                    Alert alerta1 = new Alert(Alert.AlertType.WARNING,
                            "Erro (" + ex.getErrorCode() + ") ao excluir. Entre em contato com o suporte para mais informações.",
                            ButtonType.OK);
                    alerta1.showAndWait();
                    break;
            }
        }
    }

    //volta para a tela principal
    @FXML
    private void btnVoltar_Click(ActionEvent event) {
        String fxml = "TelaPrincipal";
        try {

            App.setRoot(fxml);
        } catch (IOException ex) {
            System.out.println("Ocorreu o seguinte erro: " + ex.getMessage());
        }
    }

    //metodo para limpar a tela
    public void limpaCampos() {
        txtNome.setText("");
        txtTelefone.setText("");
        txtEndereco.setText("");
        txtCidade.setText("");
    }
}
