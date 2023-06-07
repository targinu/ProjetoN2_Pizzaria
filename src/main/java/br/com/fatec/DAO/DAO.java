package br.com.fatec.DAO;

import java.sql.SQLException;
import java.util.Collection;


public interface DAO <MODEL> {
 
    public boolean insere(MODEL obj) 
            throws SQLException;
    public boolean remove(MODEL obj) 
            throws SQLException;
    public boolean altera(MODEL obj) 
            throws SQLException;
    public MODEL buscaID(MODEL obj) 
            throws SQLException;
    public Collection<MODEL> lista(String criterio) 
            throws SQLException;    
    
}
