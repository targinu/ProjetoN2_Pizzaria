package br.com.fatec.DAO;

import br.com.fatec.model.Pedido;
import br.com.fatec.persistencia.Banco;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

public class PedidosDAO implements DAO<Pedido> {

    private java.sql.PreparedStatement pst;
    private java.sql.ResultSet rs;
    private Pedido pedido;

    @Override
    public boolean insere(Pedido obj) throws SQLException {
        String sql = "INSERT INTO PEDIDOS (descricao, valor, clienteId, motoboyId) "
                + "VALUES (?, ?, ?, ?)";

        Banco.conectar();
        pst = Banco.obterConexao().prepareStatement(sql);

        pst.setString(1, obj.getDescricao());
        pst.setDouble(2, obj.getValor());
        pst.setInt(3, obj.getClienteId());
        pst.setString(4, obj.getMotoboyId());

        int res = pst.executeUpdate();

        Banco.desconectar();

        return res != 0;
    }

    @Override
    public boolean remove(Pedido obj) throws SQLException {
        String sql = "DELETE FROM PEDIDOS WHERE idPedido=?";

        Banco.conectar();
        pst = Banco.obterConexao().prepareStatement(sql);

        pst.setInt(1, obj.getIdPedido());

        int res = pst.executeUpdate();

        Banco.desconectar();

        return res != 0;
    }

    @Override
    public boolean altera(Pedido obj) throws SQLException {
        String sql = "UPDATE PEDIDOS SET descricao = ?, valor = ?, clienteId = ?, motoboyId = ? "
                + "WHERE idPedido = ?";

        Banco.conectar();
        pst = Banco.obterConexao().prepareStatement(sql);

        pst.setString(1, obj.getDescricao());
        pst.setDouble(2, obj.getValor());
        pst.setInt(3, obj.getClienteId());
        pst.setString(4, obj.getMotoboyId());
        pst.setInt(5, obj.getIdPedido());

        int res = pst.executeUpdate();

        Banco.desconectar();

        return res != 0;
    }

    @Override
    public Pedido buscaID(Pedido obj) throws SQLException {
        String sql = "SELECT * FROM PEDIDOS WHERE idPedido = ?";

        Banco.conectar();
        pst = Banco.obterConexao().prepareStatement(sql);

        pst.setInt(1, obj.getIdPedido());

        rs = pst.executeQuery();

        if (rs.next()) {
            pedido = new Pedido();
            pedido.setIdPedido(rs.getInt("idPedido"));
            pedido.setDescricao(rs.getString("descricao"));
            pedido.setValor(rs.getDouble("valor"));
            pedido.setClienteId(rs.getInt("clienteId"));
            pedido.setMotoboyId(rs.getString("motoboyId"));
        } else {
            pedido = null;
        }

        Banco.desconectar();

        return pedido;
    }

    @Override
    public Collection<Pedido> lista(String criterio) throws SQLException {
        ArrayList<Pedido> lista = new ArrayList<>();

        String sql = "SELECT p.*, c.nomeCliente AS clienteNome, m.nomeEntregador AS entregadorNome "
                + "FROM PEDIDOS p "
                + "JOIN CLIENTE c ON p.clienteId = c.idCliente "
                + "JOIN MOTOBOY m ON p.motoboyId = m.placaMoto ";

        if (criterio != null && criterio.length() > 0) {
            sql += " WHERE " + criterio;
        }

        Banco.conectar();
        pst = Banco.obterConexao().prepareStatement(sql);

        rs = pst.executeQuery();

        while (rs.next()) {
            pedido = new Pedido();
            pedido.setIdPedido(rs.getInt("idPedido"));
            pedido.setDescricao(rs.getString("descricao"));
            pedido.setValor(rs.getDouble("valor"));
            pedido.setClienteId(rs.getInt("clienteId"));
            pedido.setMotoboyId(rs.getString("motoboyId"));
            pedido.setClienteNome(rs.getString("clienteNome"));
            pedido.setEntregadorNome(rs.getString("entregadorNome"));

            lista.add(pedido);
        }

        Banco.desconectar();

        return lista;
    }

}
