package br.com.fatec.model;
import java.util.Objects;

public class Motoboy {
    private String nomeEntregador;
    private String placaMoto;
    
    public Motoboy(String nome, String placa) {
        nomeEntregador = nome;
        placaMoto = placa;
    }
          
    public String getNomeEntregador() {
        return nomeEntregador;
    }

    public void setNomeEntregador(String nomeEntregador) {
        this.nomeEntregador = nomeEntregador;
    }
    
    public String getPlacaMoto() {
        return placaMoto;
    }

    public void setPlacaMoto(String placaMoto) {
        if (placaMoto.length() != 7) {
            System.out.println("A placa da moto deve ter 7 caracteres.");
        } else {
            // Verifica o formato da placa usando expressão regular
            if (!placaMoto.matches("[A-Z]{3}[A-Z0-9]{1}[0-9]{2}")) {
                System.out.println("Formato de placa inválido. Use o formato LLLNLNN, onde L é uma letra e N é um número.");
            } else {
                this.placaMoto = placaMoto;
            }
        }
    }
    
    @Override
    public int hashCode() {
        int hash = 5;
        hash = 79 * hash + Objects.hashCode(this.placaMoto);
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
        
        final Motoboy other = (Motoboy) obj;
        return placaMoto.equals(other.placaMoto);
        
        /**
        final Motoboy other = (Motoboy) obj;
        if (!Objects.equals(this.placaMoto, other.placaMoto)) {
            return false;
        }
        return true;**/
    }

    @Override
    public String toString() {
        return nomeEntregador;
    }
}
