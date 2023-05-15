/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.fatec.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;

/**
 * FXML Controller class
 *
 * @author targinu
 */
public class TelaPedidosController implements Initializable {
    
     @FXML
    private ListView<String> pedidoListView;


    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        pedidoListView.getItems().addAll("Pedido 1", "Pedido 2", "Pedido 3", "Pedido 4");
    }    
    
}
