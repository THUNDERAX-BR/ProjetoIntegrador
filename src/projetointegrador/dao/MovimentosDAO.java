package projetointegrador.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import projetointegrador.objects.Movimentos;
import projetointegrador.panels.PnMovimentoGerenciar;

public class MovimentosDAO {
    private Connection con;
    private PreparedStatement st;
    private ResultSet rs;

    public MovimentosDAO(Connection con) {
        this.con = con;
    }
    
    public ArrayList<String> getMovimentos(){
        ArrayList<String> lista = new ArrayList<>();
        try{
            st = con.prepareStatement("SELECT DISTINCT(nome) FROM movimentos ORDER BY nome");
            rs = st.executeQuery();
            while(rs.next()){
                lista.add(rs.getString("nome"));
            }
            return lista;
        }
        catch(SQLException ex){
            JOptionPane.showMessageDialog(null,"Erro ao listar movimentos");
            return null;
        }
        finally{
            desconectar();
        }
    }
    
    public ArrayList<PnMovimentoGerenciar> listarGerenciar(String busca){
        try{
            String stmt = "SELECT id, nome FROM movimentos";
            if(!busca.equals("")){
                stmt = stmt + " WHERE (nome LIKE ? OR id = ?)";
            }
            stmt = stmt + " ORDER BY id";
            st = con.prepareStatement(stmt);
            if(!busca.equals("")){
                st.setString(1, "%"+busca+"%");
                st.setString(2, busca);
            }
            rs = st.executeQuery();
            ArrayList<PnMovimentoGerenciar> lista = new ArrayList<>();
            while(rs.next()){
                PnMovimentoGerenciar a = new PnMovimentoGerenciar(rs.getInt("id"), rs.getString("nome"));
                lista.add(a);
            }
            return lista;
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null, "Erro ao listar os movimentos.");
            return null;
        }
        finally{
            desconectar();
        }
    }
    
    public Movimentos movimento(int id){
        try{
            st = con.prepareStatement("SELECT nome FROM movimentos WHERE id = ?");
            st.setInt(1, id);
            rs = st.executeQuery();
            Movimentos m = new Movimentos();
            if(rs.next()){
                m.setNome(rs.getString("nome"));
            }
            return m;
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null, "Erro ao exibir o movimento.");
            return null;
        }
        finally{
            desconectar();
        }
    }
    
    public void cadastrarMovimento(String nome){
        try{
            st = con.prepareStatement("SELECT id FROM movimentos WHERE nome = ?");
            st.setString(1, nome);
            rs = st.executeQuery();
            if(rs.next()){
                JOptionPane.showMessageDialog(null, "Movimento já existe.");
            }
            else{
                st = con.prepareStatement("INSERT INTO movimentos(nome) VALUES (?)");
                st.setString(1, nome);
                st.executeUpdate();
                JOptionPane.showMessageDialog(null, "Movimento cadastrado.");
            }
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null, "Erro ao cadastrar movimento.");
        }
        finally{
            desconectar();
        }
    }
    
    public void alterarMovimento(int id, String nome){
        try{
            st = con.prepareStatement("SELECT id FROM movimentos WHERE nome = ?");
            st.setString(1, nome);
            rs = st.executeQuery();
            if(rs.next()){
                JOptionPane.showMessageDialog(null, "Movimento já existe.");
            }
            else{
                st = con.prepareStatement("UPDATE movimentos SET nome = ? WHERE id = ?");
                st.setString(1, nome);
                st.setInt(2, id);
                st.executeUpdate();
                JOptionPane.showMessageDialog(null, "Movimento alterado.");
            }
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null, "Erro ao alterar movimento.");
        }
        finally{
            desconectar();
        }
    }
    
    public void excluir(int id){
        try{
            st = con.prepareStatement("DELETE FROM movimentos WHERE id = ?");
            st.setInt(1, id);
            st.executeUpdate();
            JOptionPane.showMessageDialog(null, "Movimento excluído.");
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null, "Erro ao excluir movimento.");
        }
        finally{
            desconectar();
        }
    }
    
    public ArrayList<String> listarCadastroAutor(int id){
        try{
            int idban;
            ArrayList<String> lista = new ArrayList();
            if(id!=-1){
                st = con.prepareStatement("SELECT m.id, m.nome FROM movimentos m INNER JOIN autores a WHERE a.movimentos_id = m.id AND a.id = ?");
                st.setInt(1, id);
                rs = st.executeQuery();
                rs.next();
                idban = rs.getInt("id");
                String s1 = idban+"/"+rs.getString("nome");
                lista.add(s1);
            }
            else{
                idban = -1;
            }
            st = con.prepareStatement("SELECT id, nome FROM movimentos WHERE id!= ? ORDER BY nome");
            st.setInt(1, idban);
            rs = st.executeQuery();
            while(rs.next()){
                String s = rs.getString("id")+"/"+rs.getString("nome");
                lista.add(s);
            }
            return lista;
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null, "Erro ao listar os movimentos.");
                return null;
        }
        finally{
            desconectar();
        }
    }
    
    private void desconectar(){
        try {
            con.close();
        } catch (SQLException ex) {}
    }
}
