package br.com.fatec.controller;

import br.com.fatec.App;
import br.com.fatec.Motoboy;
import java.io.IOException;
import java.net.URL;
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

public class TelaGerenciarMotoboysController implements Initializable {
    
     //variaveis auxiliares usadas para alterar objeto
    static String nomeAux, placaAux;
    //cria uma coleção para armazenar todos os motoboys que aparecerão na combo e cria alguns objetos com valores defaults
    Motoboy m1 = new Motoboy("João", "ABC1A23");
    Motoboy m2 = new Motoboy("Pedro", "DEF5B67");
    Motoboy m3 = new Motoboy("Mario", "GHI9C01");
    
    //Observable list Motoboys
    private ObservableList<Motoboy> motoboys = FXCollections.observableArrayList(m1, m2, m3);

    @FXML
    private TextField txtPlaca;
    
    @FXML
    private TextField txtNome;
    
    @FXML
    private ComboBox<Motoboy> cbMotoboy;
    
    @FXML
    private Button btnSalvar;
    
    @FXML
    private Button btnCadastrar;
     
    @FXML
    private Button btnExcluir;
    
    @FXML
    private Button btnAlterar;
    
     @FXML
    private Button btn_voltar;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //coloca a coleção dentro da combo
        cbMotoboy.setItems(motoboys);

        //trata do evento change da comboBox
        cbMotoboy.valueProperty().addListener((value, velho, novo) -> {
            // coloca os dados do objeto NOVO selecionado dentro dos texts
            txtNome.setText(novo.getNomeEntregador());
            txtPlaca.setText(novo.getPlacaMoto());
            // define o nome selecionado como a variável principal
            nomeAux = novo.getNomeEntregador();
        });
    }

    @FXML
    private void btnCadastrar_Click(ActionEvent event) {
        // Verifica se existem campos em branco
        if (txtNome.getText().isEmpty() || txtPlaca.getText().isEmpty()) {
            Alert alerta = new Alert(Alert.AlertType.WARNING,
                    "Existem campos em branco que devem ser preenchidos!",
                    ButtonType.OK);
            alerta.showAndWait();
            return;
        }

        // Criando objeto para ser adicionado
        Motoboy novoMotoboy = new Motoboy(txtNome.getText(), txtPlaca.getText());

        // Verifica se o objeto já existe no ArrayList, para não aceitar valores repetidos
        for (Motoboy motoboy : motoboys) {
            if (motoboy.equals(novoMotoboy)) {
                // Caso exista, o usuário é notificado
                Alert alerta = new Alert(Alert.AlertType.INFORMATION,
                        "Motoboy já cadastrado no sistema, verifique a placa\ne tente novamente...",
                        ButtonType.OK);
                alerta.showAndWait();
                // Limpa os campos
                limpaCampos();
                return;
            }
        }

        // Adiciona o Motoboy criado no ArrayList
        motoboys.add(novoMotoboy);
        // Notifica a inclusão
        Alert alerta = new Alert(Alert.AlertType.INFORMATION,
                "Novo motoboy adicionado com sucesso!",
                ButtonType.OK);
        alerta.showAndWait();

        // Define o novo motoboy como o selecionado na ComboBox
        cbMotoboy.getSelectionModel().select(novoMotoboy);
        // Limpa os campos
        limpaCampos();
    }
    
    @FXML
    private void btnExcluir_Click(ActionEvent event) {
        // Verifica se existem campos em branco
        if (txtNome.getText().isEmpty() || txtPlaca.getText().isEmpty()) {
            // Envia uma mensagem notificando
            Alert alerta = new Alert(Alert.AlertType.WARNING,
                    "Preencha os campos em branco!",
                    ButtonType.OK);
            alerta.showAndWait();
            return;
        }

        // Cria um objeto auxiliar para realizar comparação
        Motoboy motoboyAux = new Motoboy(txtNome.getText(), txtPlaca.getText());

        // Realiza comparação para possível exclusão
        for (Motoboy motoboy : motoboys) {
            if (motoboy.equals(motoboyAux)) {
                // Exclui o objeto
                cbMotoboy.getItems().remove(motoboy);
                // Caso exista, o usuário é notificado
                Alert alerta = new Alert(Alert.AlertType.INFORMATION,
                        "Motoboy excluído com sucesso!",
                        ButtonType.OK);
                alerta.showAndWait();
                // Limpa os campos
                limpaCampos();
                return;
            }
        }
        // Notifica o usuário que não foi encontrado um motoboy
        Alert alerta = new Alert(Alert.AlertType.WARNING,
                "Motoboy não encontrado, verifique as informações...",
                ButtonType.OK);
        alerta.showAndWait();
    }
    
    @FXML
    private void btnAlterar_Click(ActionEvent event) {
        // Verifica se o usuário já salvou as informações
        if (placaAux == null) {
            Alert alerta = new Alert(Alert.AlertType.WARNING,
                    "Clique no botão 'Salvar' antes de prosseguir.",
                    ButtonType.OK);
            alerta.showAndWait();
            return;
        }

        // Objeto auxiliar para realizar comparação
        Motoboy motoboyAux = new Motoboy(nomeAux, txtPlaca.getText());

        // Realiza comparação para possível alteração
        for (int i = 0; i < motoboys.size(); i++) {
            if (motoboys.get(i).getNomeEntregador().equals(nomeAux)) {
                // Alterando apenas a placa
                Motoboy motoboyAtualizado = new Motoboy(motoboys.get(i).getNomeEntregador(), txtPlaca.getText());
                motoboys.set(i, motoboyAtualizado);
                // Notificando a mudança
                Alert alerta = new Alert(Alert.AlertType.INFORMATION,
                        "Alterações realizadas com sucesso!",
                        ButtonType.OK);
                alerta.showAndWait();
                // Restaura campos
                limpaCampos();
                // Retorna
                return;
            }
        }

        // Caso não seja encontrado o elemento para alteração
        Alert alerta = new Alert(Alert.AlertType.WARNING,
                "Elemento não encontrado para alteração.",
                ButtonType.OK);
        alerta.showAndWait();
    }
    
    @FXML
    private void btnSalvar_Click(ActionEvent event) {
        //verifica se existem campos em branco
        if(txtPlaca.getText().isEmpty() || txtNome.getText().isEmpty()){
            //notifica o usuário dos campos vazios
            Alert alerta = new Alert(Alert.AlertType.WARNING,
                    "Existem campos em branco que devem ser preenchidos!",
                    ButtonType.OK);
            alerta.showAndWait();
            return;
        }

        //verifica se a placa tem exatamente 7 caracteres
        else if(txtPlaca.getText().length() != 7){
            Alert alerta = new Alert(Alert.AlertType.WARNING,
                    "A placa deve conter exatamente 7 caracteres!",
                    ButtonType.OK);
            alerta.showAndWait();

            return;
        }

        //caso esteja tudo certo, ele salva os atributos
        placaAux = txtPlaca.getText();
        nomeAux = cbMotoboy.getValue().getNomeEntregador();

        //notifica o usuário
        Alert alerta = new Alert(Alert.AlertType.INFORMATION,
                    "O elemento foi salvo e está pronto para mudança",
                    ButtonType.OK);
        alerta.showAndWait();
    }



    
    public void limpaCampos() {
        //limpa campos e seleciona o primeiro elemento da comboBox
        cbMotoboy.getSelectionModel().selectFirst();
        txtNome.setText("");
        txtPlaca.setText("");
    }
    
    @FXML
    private void btnVoltar_Click(ActionEvent event) {
        String fxml = "TelaPrincipal";
        try {

            App.setRoot(fxml);
        } catch (IOException ex) {
            System.out.println("Ocorreu o seguinte erro: " + ex.getMessage());
        }

    }
    
}
