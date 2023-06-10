package br.com.fatec.model;

import java.util.Objects;

public class Pedido {

    private int idPedido;
    private String descricao;
    private double valor;
    private int clienteId;
    private String motoboyId;

    //usado na busca avançada
    private String clienteNome;
    private String entregadorNome;

    public int getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(int idPedido) {
        this.idPedido = idPedido;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public int getClienteId() {
        return clienteId;
    }

    public void setClienteId(int clienteId) {
        this.clienteId = clienteId;
    }

    public String getMotoboyId() {
        return motoboyId;
    }

    public void setMotoboyId(String motoboyId) {
        this.motoboyId = motoboyId;
    }

    public String getClienteNome() {
        return clienteNome;
    }

    public void setClienteNome(String clienteNome) {
        this.clienteNome = clienteNome;
    }

    public String getEntregadorNome() {
        return entregadorNome;
    }

    public void setEntregadorNome(String entregadorNome) {
        this.entregadorNome = entregadorNome;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 79 * hash + Objects.hashCode(this.idPedido);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }

        final Pedido other = (Pedido) obj;
        if (!Objects.equals(this.idPedido, other.idPedido)) {
            return false;
        }
        return Objects.equals(this.idPedido, other.idPedido);
    }

    //construtor padrão
    public Pedido() {

    }

    public Pedido(int idPedido) {
        this.idPedido = idPedido;
    }

    public Pedido(String descricao, double valor, int clienteId, String motoboyId) {
        this.descricao = descricao;
        this.valor = valor;
        this.clienteId = clienteId;
        this.motoboyId = motoboyId;
    }

    //construtor de tudo
    public Pedido(int idPedido, String descricao, double valor, int clienteId, String motoboyId) {
        this.idPedido = idPedido;
        this.descricao = descricao;
        this.valor = valor;
        this.clienteId = clienteId;
        this.motoboyId = motoboyId;
    }

}
