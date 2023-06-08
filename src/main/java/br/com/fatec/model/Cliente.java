package br.com.fatec.model;

import java.util.Objects;

public class Cliente {
    private int idCliente;
    private String nomeCliente;
    private String telefone;
    private String endereco;
    private String cidade;

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public String getNomeCliente() {
        return nomeCliente;
    }

    public void setNomeCliente(String nomeCliente) {
        this.nomeCliente = nomeCliente;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + Objects.hashCode(this.nomeCliente);
        hash = 67 * hash + Objects.hashCode(this.telefone);
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
        final Cliente other = (Cliente) obj;
        if (!Objects.equals(this.nomeCliente, other.nomeCliente)) {
            return false;
        }
        return Objects.equals(this.telefone, other.telefone);
    }
    
    //construtor padrão
    public Cliente() {
    }

    //construtor com todos os atributos
    public Cliente(int idCliente, String nomeCliente, String telefone, String endereco, String cidade) {
        this.idCliente = idCliente;
        this.nomeCliente = nomeCliente;
        this.telefone = telefone;
        this.endereco = endereco;
        this.cidade = cidade;
    }
    
    //construtor
    public Cliente (String telefone){
        this.telefone = telefone;
    }
    
    //retorna os dados para a cbCliente como string
    @Override
    public String toString() {
        return nomeCliente;
    }

}
