package br.com.fatec.DAO;

import br.com.fatec.model.Cliente;
import br.com.fatec.persistencia.Banco;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

public class ClienteDAO implements DAO<Cliente> {

    private java.sql.PreparedStatement pst;
    private java.sql.ResultSet rs;
    private Cliente cliente;

    @Override
    public boolean insere(Cliente obj) throws SQLException {
        String sql = "INSERT INTO CLIENTE (nomeCliente, telefone, endereco, cidade) " +
                "VALUES (?, ?, ?, ?)";
        
        Banco.conectar();
        pst = Banco.obterConexao().prepareStatement(sql);
        
        pst.setString(1, obj.getNomeCliente());
        pst.setString(2, obj.getTelefone());
        pst.setString(3, obj.getEndereco());
        pst.setString(4, obj.getCidade());
        
        int res = pst.executeUpdate();
        
        Banco.desconectar();
        
        return res != 0;
    }

    //alterado para a busca de clientes por telefone
    //para o nosso caso de uso não faz muito sentido
    //gerar ids manualmente para os clientes
    @Override
    public boolean remove(Cliente obj) throws SQLException {
        String sql =  "DELETE FROM CLIENTE WHERE telefone=?";
        
        Banco.conectar();
        pst = Banco.obterConexao().prepareStatement(sql);
        
        pst.setString(1, obj.getTelefone());
        
        int res = pst.executeUpdate();
        
        Banco.desconectar();
        
        return res != 0;
    }

    @Override
    public boolean altera(Cliente obj) throws SQLException {
        String sql = "UPDATE CLIENTE SET nomeCliente = ?, telefone = ?, endereco = ?, cidade = ? " +
                "WHERE telefone = ?";
        
        Banco.conectar();
        pst = Banco.obterConexao().prepareStatement(sql);
        
        pst.setString(1, obj.getNomeCliente());
        pst.setString(2, obj.getTelefone());
        pst.setString(3, obj.getEndereco());
        pst.setString(4, obj.getCidade());
        pst.setString(5, obj.getTelefone());
        
        int res = pst.executeUpdate();
        
        Banco.desconectar();
        
        return res != 0;
    }

    //alterado para a busca de clientes por telefone
    //para o nosso caso de uso não faz muito sentido
    //gerar ids manualmente para os clientes
    @Override
    public Cliente buscaID(Cliente obj) throws SQLException {
        String sql = "SELECT * FROM CLIENTE WHERE telefone = ?";
        
        Banco.conectar();
        pst = Banco.obterConexao().prepareStatement(sql);
        
        pst.setString(1, obj.getTelefone());
        
        rs = pst.executeQuery();
        
        if (rs.next()) {
            cliente = new Cliente();
            cliente.setIdCliente(rs.getInt("idCliente"));
            cliente.setNomeCliente(rs.getString("nomeCliente"));
            cliente.setTelefone(rs.getString("telefone"));
            cliente.setEndereco(rs.getString("endereco"));
            cliente.setCidade(rs.getString("cidade"));
        } else {
            cliente = null;
        }
                
        Banco.desconectar();
        
        return cliente;
    }

    @Override
    public Collection<Cliente> lista(String criterio) throws SQLException {
        ArrayList<Cliente> lista = new ArrayList<>();
        
        String sql = "SELECT * FROM CLIENTE ";
        
        if (criterio != null && criterio.length() > 0) {
            sql += " WHERE " + criterio;
        }
        
        Banco.conectar();
        pst = Banco.obterConexao().prepareStatement(sql);
        
        rs = pst.executeQuery();
        
        while (rs.next()) {
            cliente = new Cliente();
            cliente.setIdCliente(rs.getInt("idCliente"));
            cliente.setNomeCliente(rs.getString("nomeCliente"));
            cliente.setTelefone(rs.getString("telefone"));
            cliente.setEndereco(rs.getString("endereco"));
            cliente.setCidade(rs.getString("cidade"));
            
            lista.add(cliente);
        }
                
        Banco.desconectar();
        
        return lista;
    }
    
}
