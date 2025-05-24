package projetointegrador.dao;

import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import projetointegrador.objects.Exemplares;
import projetointegrador.panels.PnExemplarGerenciar;
import projetointegrador.panels.PnExemplarInfoLivro;

public class ExemplaresDAO {
    private Connection con;
    private PreparedStatement st;
    private ResultSet rs;

    public ExemplaresDAO(Connection con) {
        this.con = con;
    }
    
    public ArrayList<PnExemplarInfoLivro> exemplaresLivro(int id){
        ArrayList<PnExemplarInfoLivro> lista = new ArrayList();
        try{
            st = con.prepareStatement("SELECT id, localizacao FROM exemplares WHERE livros_id = ?");
            st.setInt(1, id);
            rs = st.executeQuery();
            while(rs.next()){
                PnExemplarInfoLivro e = new PnExemplarInfoLivro(rs.getInt("id"), rs.getString("localizacao"));
                lista.add(e);
            }
            return lista;
        }
        catch(SQLException ex){
            JOptionPane.showMessageDialog(null,"Erro ao listar exemplares");
            return null;
        }
        finally{
            desconectar();
        }
    }
    
    public ArrayList<PnExemplarGerenciar> listarGerenciar(String busca){
        try{
            String stmt = "SELECT e.id, l.titulo, ed.nome AS editora, e.localizacao FROM exemplares e INNER JOIN livros l INNER JOIN editoras ed WHERE e.livros_id = l.id AND l.editoras_id = ed.id";
            if(!busca.equals("")){
                stmt = stmt + " AND (l.titulo LIKE ? OR e.id = ?)";
            }
            stmt = stmt + " ORDER BY id";
            st = con.prepareStatement(stmt);
            if(!busca.equals("")){
                st.setString(1, "%"+busca+"%");
                st.setString(2, busca);
            }
            rs = st.executeQuery();
            ArrayList<PnExemplarGerenciar> lista = new ArrayList();
            while(rs.next()){
                PnExemplarGerenciar a = new PnExemplarGerenciar(rs.getInt("id"), rs.getString("titulo"), rs.getString("editora"), rs.getString("localizacao"));
                lista.add(a);
            }
            return lista;
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null, "Erro ao listar os exemplares.");
            return null;
        }
        finally{
            desconectar();
        }
    }
    
    public Exemplares exemplar(int id){
        try{
            st = con.prepareStatement("SELECT localizacao FROM exemplares WHERE id = ?");
            st.setInt(1, id);
            rs = st.executeQuery();
            Exemplares e = new Exemplares();
            if(rs.next()){
                e.setLocalizacao(rs.getString("localizacao"));
            }
            return e;
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null, "Erro ao exibir o exemplar.");
            return null;
        }
        finally{
            desconectar();
        }
    }
    
    public int getLivro(int id){
        try{
            st = con.prepareStatement("SELECT livros_id FROM exemplares WHERE id = ?");
            st.setInt(1, id);
            rs = st.executeQuery();
            rs.next();
            return rs.getInt("livros_id");
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null, "Erro ao verificar o livro do exemplar.");
            return -1;
        }
        finally{
            desconectar();
        }
    }
    
    public void cadastrarExemplar(int idlivro, String local){
        try{
            st = con.prepareStatement("INSERT INTO exemplares(livros_id, localizacao) VALUES (?,?)");
            st.setInt(1, idlivro);
            st.setString(2, local);
            st.executeUpdate();
            JOptionPane.showMessageDialog(null, "Exemplar cadastrado.");
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null, "Erro ao cadastrar o exemplar.");
        }
        finally{
            desconectar();
        }
    }
    
    public void alterarExemplar(int id, int idlivro, String local){
        try{
            st = con.prepareStatement("UPDATE exemplares SET livros_id = ?, localizacao = ? WHERE id = ?");
            st.setInt(1, idlivro);
            st.setString(2, local);
            st.setInt(3, id);
            st.executeUpdate();
            JOptionPane.showMessageDialog(null, "Exemplar alterado.");
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null, "Erro ao alterar o exemplar.");
        }
        finally{
            desconectar();
        }
    }
    
    public void excluir(int id){
        try{
            st = con.prepareStatement("DELETE FROM exemplares WHERE id = ?");
            st.setInt(1, id);
            st.executeUpdate();
            JOptionPane.showMessageDialog(null, "Exemplar excluído.");
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null, "Erro ao excluir exemplar.");
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
