package projetointegrador.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import projetointegrador.objects.Editoras;
import projetointegrador.panels.PnEditoraGerenciar;

public class EditorasDAO {
    private Connection con;
    private PreparedStatement st;
    private ResultSet rs;

    public EditorasDAO(Connection con) {
        this.con = con;
    }
    
    public ArrayList<PnEditoraGerenciar> listarGerenciar(String busca){
        try{
            String stmt = "SELECT id, nome FROM editoras";
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
            ArrayList<PnEditoraGerenciar> lista = new ArrayList<>();
            while(rs.next()){
                PnEditoraGerenciar a = new PnEditoraGerenciar(rs.getInt("id"), rs.getString("nome"));
                lista.add(a);
            }
            return lista;
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null, "Erro ao listar as editoras.");
            return null;
        }
        finally{
            desconectar();
        }
    }
    
    public void excluir(int id){
        try{
            st = con.prepareStatement("DELETE FROM editoras WHERE id = ?");
            st.setInt(1, id);
            st.executeUpdate();
            JOptionPane.showMessageDialog(null, "Editora excluída.");
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null, "Erro ao excluir editora.");
        }
        finally{
            desconectar();
        }
    }
    
    public Editoras editora(int id){
        try{
            st = con.prepareStatement("SELECT nome FROM editoras WHERE id = ?");
            st.setInt(1, id);
            rs = st.executeQuery();
            Editoras e = new Editoras();
            if(rs.next()){
                e.setNome(rs.getString("nome"));
            }
            return e;
        }
        catch(SQLException ex){
            JOptionPane.showMessageDialog(null,"Erro ao exibir editora.");
            return null;
        }
        finally{
            desconectar();
        }
    }
    
    public void cadastrarEditora(String nome){
        try{
            st = con.prepareStatement("SELECT id FROM editoras WHERE nome = ?");
            st.setString(1, nome);
            rs = st.executeQuery();
            if(rs.next()){
                JOptionPane.showMessageDialog(null, "Editora já existe.");
            }
            else{
                st = con.prepareStatement("INSERT INTO editoras(nome) VALUES (?)");
                st.setString(1, nome);
                st.executeUpdate();
                JOptionPane.showMessageDialog(null, "Editora cadastrada.");
            }
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null, "Erro ao cadastrar editora.");
        }
        finally{
            desconectar();
        }
    }
    
    public void alterarEditora(int id, String nome){
        try{
            st = con.prepareStatement("SELECT id FROM editoras WHERE nome = ?");
            st.setString(1, nome);
            rs = st.executeQuery();
            if(rs.next()){
                JOptionPane.showMessageDialog(null, "Editora já existe.");
            }
            else{
                st = con.prepareStatement("UPDATE editoras SET nome = ? WHERE id = ?");
                st.setString(1, nome);
                st.setInt(2, id);
                st.executeUpdate();
                JOptionPane.showMessageDialog(null, "Editora alterada.");
            }
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null, "Erro ao alterar editora.");
        }
        finally{
            desconectar();
        }
    }
    
    public ArrayList<String> listarCadastroLivro(int id){
        try{
            int idban;
            ArrayList<String> lista = new ArrayList();
            if(id!=-1){
                st = con.prepareStatement("SELECT e.id, e.nome FROM editoras e INNER JOIN livros l WHERE l.editoras_id = e.id AND l.id = ?");
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
            st = con.prepareStatement("SELECT id, nome FROM editoras WHERE id!= ? ORDER BY nome");
            st.setInt(1, idban);
            rs = st.executeQuery();
            while(rs.next()){
                String s = rs.getString("id")+"/"+rs.getString("nome");
                lista.add(s);
            }
            return lista;
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null, "Erro ao listar as editoras.");
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
