package projetointegrador.dao;

import java.sql.Statement;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import projetointegrador.objects.Autores;
import projetointegrador.objects.Categorias;
import projetointegrador.objects.Editoras;
import projetointegrador.objects.Livros;
import projetointegrador.panels.PnLivroBuscarLivros;
import projetointegrador.panels.PnLivroGerenciar;
import projetointegrador.panels.PnLivroInfoAutor;

public class LivrosDAO {
    private Connection con;
    private PreparedStatement st;
    private ResultSet rs;

    public LivrosDAO(Connection con) {
        this.con = con;
    }
    
    public ArrayList<PnLivroBuscarLivros> listarBuscarLivros(String busca, String anoin, String anofin, String cat){
        try{
            String stmt = "SELECT l.id, l.titulo, a.nome AS autor, e.nome AS editora FROM livros l INNER JOIN autores a INNER JOIN editoras e LEFT JOIN categorias_livros cl ON l.id = cl.livros_id LEFT JOIN categorias c ON c.id = cl.categorias_id WHERE l.autores_id = a.id AND l.editoras_id = e.id";
            if(!busca.equals("")){
                stmt = stmt + " AND l.titulo LIKE ?";
            }
            if(!anoin.equals("")){
                stmt = stmt + " AND l.ano >= ?";
            }
            if(!anofin.equals("")){
                stmt = stmt + " AND l.ano <= ?";
            }
            if(!cat.equals("")){
                stmt = stmt + " AND c.nome = ?";
            }
            
            stmt = stmt + " GROUP BY id ORDER BY titulo";
            st = con.prepareStatement(stmt);
            
            int i=1;
            if(!busca.equals("")){
                st.setString(i, "%"+busca+"%");
                i++;
            }
            if(!anoin.equals("")){
                st.setString(i, anoin);
                i++;
            }
            if(!anofin.equals("")){
                st.setString(i, anofin);
                i++;
            }
            if(!cat.equals("")){
                st.setString(i, cat);
                i++;
            }
            
            rs = st.executeQuery();
            ArrayList<PnLivroBuscarLivros> lista = new ArrayList<>();
            while(rs.next()){
                PnLivroBuscarLivros a = new PnLivroBuscarLivros(rs.getInt("id"), rs.getString("titulo"), rs.getString("autor"), rs.getString("editora"));
                lista.add(a);
            }
            return lista;
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null, "Erro ao listar os livros.");
            return null;
        }
        finally{
            desconectar();
        }
    }
    
    public ArrayList<PnLivroGerenciar> listarGerenciar(String busca){
        try{
            String stmt = "SELECT l.id, l.titulo, a.nome AS autor, e.nome AS editora FROM livros l INNER JOIN autores a INNER JOIN editoras e WHERE l.autores_id = a.id AND l.editoras_id = e.id";
            if(!busca.equals("")){
                stmt = stmt + " AND (l.titulo LIKE ? OR l.id = ?)";
            }
            stmt = stmt + " ORDER BY id";
            st = con.prepareStatement(stmt);
            if(!busca.equals("")){
                st.setString(1, "%"+busca+"%");
                st.setString(2, busca);
            }
            rs = st.executeQuery();
            ArrayList<PnLivroGerenciar> lista = new ArrayList<>();
            while(rs.next()){
                PnLivroGerenciar a = new PnLivroGerenciar(rs.getInt("id"), rs.getString("titulo"), rs.getString("autor"), rs.getString("editora"));
                lista.add(a);
            }
            return lista;
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null, "Erro ao listar os livros.");
            return null;
        }
        finally{
            desconectar();
        }
    }
    
    public ArrayList<PnLivroInfoAutor> exbirLivrosAutor(int id){
        try{
            st = con.prepareStatement("SELECT l.id, l.titulo, e.nome FROM livros l INNER JOIN editoras e INNER JOIN autores a WHERE l.editoras_id = e.id AND l.autores_id = a.id AND a.id = ?");
            st.setInt(1, id);
            rs = st.executeQuery();
            ArrayList<PnLivroInfoAutor> lista = new ArrayList<>();
            while(rs.next()){
                PnLivroInfoAutor a = new PnLivroInfoAutor(rs.getInt("id"), rs.getString("titulo"), rs.getString("nome"));
                lista.add(a);
            }
            return lista;
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null, "Erro ao listar os livros.");
            return null;
        }
        finally{
            desconectar();
        }
    }
    
    public Livros exibirLivro(int id){
        Livros l = new Livros();
        Autores a = new Autores();
        Editoras e = new Editoras();
        InputStream i;
        BufferedImage imagem;
        ImageIcon capa;
        try{
            st = con.prepareStatement("SELECT l.titulo, l.ano, l.descricao, a.id, a.nome AS autor, e.nome AS editora, l.capa FROM livros l INNER JOIN autores a INNER JOIN editoras e WHERE l.id = ? AND a.id = l.autores_id AND e.id = l.editoras_id");
            st.setInt(1, id);
            rs = st.executeQuery();
            if(rs.next()){
                e.setNome(rs.getString("editora"));
                l.setEditora(e);
                a.setId(rs.getInt("id"));
                a.setNome(rs.getString("autor"));
                l.setAutor(a);
                l.setId(id);
                l.setTitulo(rs.getString("titulo"));
                l.setAno(rs.getInt("ano"));
                l.setDescricao(rs.getString("descricao"));
                if(rs.getBinaryStream("capa")!=null){
                    i = rs.getBinaryStream("capa");
                    imagem = ImageIO.read(i);
                    Image ajuste = imagem.getScaledInstance(225, 300, Image.SCALE_SMOOTH);
                    capa = new ImageIcon(ajuste);
                    l.setCapa(capa);
                }

                return l;
            }
            else{
                return null;
            }
        }
        catch(Exception ex){
            JOptionPane.showMessageDialog(null,"Erro ao exibir livro.");
            return null;
        }
        finally{
            desconectar();
        }
    }
    
    public void cadastrarLivro(String titulo, int ano, int autor, int editora, String sinopse, File capa, ArrayList<Categorias> categorias){
        try{
            st = con.prepareStatement("INSERT INTO livros(titulo, ano, autores_id, editoras_id, descricao, capa) VALUES (?, ?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            st.setString(1, titulo);
            st.setInt(2, ano);
            st.setInt(3, autor);
            st.setInt(4, editora);
            st.setString(5, sinopse);
            FileInputStream in = null;
            if(capa!=null){
                in = new FileInputStream(capa);
            }
            st.setBinaryStream(6, in);
            st.executeUpdate();
            rs = st.getGeneratedKeys();
            rs.next();
            int id = rs.getInt(1);
            for(Categorias c : categorias){
                st = con.prepareStatement("INSERT INTO categorias_livros(livros_id, categorias_id) VALUES (?,?)");
                st.setInt(1, id);
                st.setInt(2, c.getId());
                st.executeUpdate();
            }
            JOptionPane.showMessageDialog(null, "Livro cadastrado.");
        }
        catch(Exception ex){
            JOptionPane.showMessageDialog(null,"Erro ao cadastrar livro.");
        }
        finally{
            desconectar();
        }
    }
    
    public void alterarLivro(int id, String titulo, int ano, int autor, int editora, String sinopse, File capa, ArrayList<Categorias> categorias){
        try{
            if(capa!=null){
                st = con.prepareStatement("UPDATE livros SET titulo = ?, ano = ?, autores_id = ?, editoras_id = ?, descricao = ?, capa = ? WHERE id = ?");
            }
            else{
                st = con.prepareStatement("UPDATE livros SET titulo = ?, ano = ?, autores_id = ?, editoras_id = ?, descricao = ? WHERE id = ?");
            }
            st.setString(1, titulo);
            st.setInt(2, ano);
            st.setInt(3, autor);
            st.setInt(4, editora);
            st.setString(5, sinopse);
            if(capa!=null){
                FileInputStream in = new FileInputStream(capa);
                st.setBinaryStream(6, in);
                st.setInt(7, id);
            }
            else{
                st.setInt(6, id);
            }
            st.executeUpdate();
            st = con.prepareStatement("DELETE FROM categorias_livros WHERE livros_id = ?");
            st.setInt(1, id);
            st.executeUpdate();
            for(Categorias c : categorias){
                st = con.prepareStatement("INSERT INTO categorias_livros(livros_id, categorias_id) VALUES (?,?)");
                st.setInt(1, id);
                st.setInt(2, c.getId());
                st.executeUpdate();
            }
            JOptionPane.showMessageDialog(null, "Livro alterado.");
        }
        catch(Exception ex){
            JOptionPane.showMessageDialog(null,"Erro ao alterar livro.");
        }
        finally{
            desconectar();
        }
    }
    
    public void excluir(int id){
        try{
            st = con.prepareStatement("DELETE FROM livros WHERE id = ?");
            st.setInt(1, id);
            st.executeUpdate();
            JOptionPane.showMessageDialog(null, "Livro excluído.");
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null, "Erro ao excluir livro.");
        }
        finally{
            desconectar();
        }
    }
    
    public ArrayList<String> listarGerenciarExemplares(int id){
        try{
            int idban;
            ArrayList<String> lista = new ArrayList();
            if(id!=-1){
                st = con.prepareStatement("SELECT l.id, l.titulo, e.nome AS editora FROM exemplares ed INNER JOIN livros l INNER JOIN editoras e WHERE l.editoras_id = e.id AND l.id = ed.livros_id AND ed.id = ? ORDER BY titulo");
                st.setInt(1, id);
                rs = st.executeQuery();
                rs.next();
                idban = rs.getInt("id");
                String s1 = idban+"/"+rs.getString("titulo")+"/"+rs.getString("editora");
                lista.add(s1);
            }
            else{
                idban = -1;
            }
            st = con.prepareStatement("SELECT l.id, l.titulo, e.nome AS editora FROM livros l INNER JOIN editoras e WHERE l.editoras_id = e.id AND l.id != ? ORDER BY titulo");
            st.setInt(1, idban);
            rs = st.executeQuery();
            while(rs.next()){
                String s = rs.getString("id")+"/"+rs.getString("titulo")+"/"+rs.getString("editora");
                lista.add(s);
            }
            return lista;
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null, "Erro ao listar livros.");
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
