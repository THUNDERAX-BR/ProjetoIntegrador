package projetointegrador.dao;

import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import projetointegrador.objects.Categorias;
import projetointegrador.panels.PnCategoriaGerenciar;

public class CategoriasDAO {
    private Connection con;
    private PreparedStatement st;
    private ResultSet rs;

    public CategoriasDAO(Connection con) {
        this.con = con;
    }
    
    public ArrayList<String> getCategorias(){
        ArrayList<String> lista = new ArrayList<>();
        try{
            st = con.prepareStatement("SELECT DISTINCT(nome) FROM categorias ORDER BY nome");
            rs = st.executeQuery();
            while(rs.next()){
                lista.add(rs.getString("nome"));
            }
            return lista;
        }
        catch(SQLException ex){
            JOptionPane.showMessageDialog(null,"Erro ao listar as categorias");
            return null;
        }
        finally{
            desconectar();
        }
    }
    
    public ArrayList<String> listarCadastroLivro(){
        try{
            ArrayList<String> lista = new ArrayList();
            st = con.prepareStatement("SELECT id, nome FROM categorias ORDER BY nome");
            rs = st.executeQuery();
            while(rs.next()){
                String s = rs.getString("id")+"/"+rs.getString("nome");
                lista.add(s);
            }
            return lista;
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null,"Erro ao listar as categorias");
            return null;
        }
        finally{
            desconectar();
        }
    }
    
    public ArrayList<Categorias> categoriasLivro(int id){
        ArrayList<Categorias> lista = new ArrayList();
        try{
            st = con.prepareStatement("SELECT c.id, c.nome FROM categorias c INNER JOIN categorias_livros cl INNER JOIN livros l WHERE cl.categorias_id = c.id AND cl.livros_id = l.id AND l.id = ? ORDER BY c.nome");
            st.setInt(1, id);
            rs = st.executeQuery();
            while(rs.next()){
                Categorias c = new Categorias();
                c.setId(rs.getInt("id"));
                c.setNome(rs.getString("nome"));
                lista.add(c);
            }
            return lista;
        }
        catch(SQLException ex){
            JOptionPane.showMessageDialog(null,"Erro ao listar categorias");
            return null;
        }
        finally{
            desconectar();
        }
    }
    
    public Categorias categoria(int id){
        try{
            st = con.prepareStatement("SELECT nome FROM categorias WHERE id = ?");
            st.setInt(1, id);
            rs = st.executeQuery();
            Categorias c = new Categorias();
            if(rs.next()){
                c.setNome(rs.getString("nome"));
            }
            return c;
        }
        catch(SQLException ex){
            JOptionPane.showMessageDialog(null,"Erro ao exibir categoria.");
            return null;
        }
        finally{
            desconectar();
        }
    }
    
    public ArrayList<PnCategoriaGerenciar> listarGerenciar(String busca){
        try{
            String stmt = "SELECT id, nome FROM categorias";
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
            ArrayList<PnCategoriaGerenciar> lista = new ArrayList<>();
            while(rs.next()){
                PnCategoriaGerenciar a = new PnCategoriaGerenciar(rs.getInt("id"), rs.getString("nome"));
                lista.add(a);
            }
            return lista;
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null, "Erro ao listar as categorias.");
            return null;
        }
        finally{
            desconectar();
        }
    }
    
    public void excluir(int id){
        try{
            st = con.prepareStatement("DELETE FROM categorias WHERE id = ?");
            st.setInt(1, id);
            st.executeUpdate();
            JOptionPane.showMessageDialog(null, "Categoria excluído.");
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null, "Erro ao excluir categoria.");
        }
        finally{
            desconectar();
        }
    }
    
    public void cadastrarCategoria(String nome){
        try{
            st = con.prepareStatement("SELECT id FROM categorias WHERE nome = ?");
            st.setString(1, nome);
            rs = st.executeQuery();
            if(rs.next()){
                JOptionPane.showMessageDialog(null, "Categoria já existe.");
            }
            else{
                st = con.prepareStatement("INSERT INTO categorias(nome) VALUES (?)");
                st.setString(1, nome);
                st.executeUpdate();
                JOptionPane.showMessageDialog(null, "Categoria cadastrada.");
            }
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null, "Erro ao cadastrar categoria.");
        }
        finally{
            desconectar();
        }
    }
    
    public void alterarCategoria(int id, String nome){
        try{
            st = con.prepareStatement("SELECT id FROM categorias WHERE nome = ?");
            st.setString(1, nome);
            rs = st.executeQuery();
            if(rs.next()){
                JOptionPane.showMessageDialog(null, "Categoria já existe.");
            }
            else{
                st = con.prepareStatement("UPDATE categorias SET nome = ? WHERE id = ?");
                st.setString(1, nome);
                st.setInt(2, id);
                st.executeUpdate();
                JOptionPane.showMessageDialog(null, "Categoria alterada.");
            }
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null, "Erro ao alterar categoria.");
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
    
    public void reconectar(Connection con){
        this.con = con;
    }
}
