package projetointegrador.dao;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import projetointegrador.objects.Autores;
import projetointegrador.objects.Movimentos;
import projetointegrador.panels.PnAutorBuscarAutor;
import projetointegrador.panels.PnAutorGerenciar;

public class AutoresDAO {
    private Connection con;
    private PreparedStatement st;
    private ResultSet rs;

    public AutoresDAO(Connection con) {
        this.con = con;
    }
    
    public ArrayList<PnAutorBuscarAutor> buscarAutores(String busca, String movimento){
        try{
            String stmt = "SELECT a.id, a.nome, m.nome AS movimento FROM autores a INNER JOIN movimentos m WHERE m.id = a.movimentos_id";
            if(!busca.equals("")){
                stmt = stmt + " AND a.nome LIKE ?";
            }
            if(!movimento.equals("")){
                stmt = stmt + " AND m.nome = ?";
            }
            stmt = stmt + " ORDER BY a.nome";
            
            st = con.prepareStatement(stmt);
            
            int i=1;
            if(!busca.equals("")){
                st.setString(i, "%"+busca+"%");
                i++;
            }
            if(!movimento.equals("")){
                st.setString(i, movimento);
            }
            
            rs = st.executeQuery();
            ArrayList<PnAutorBuscarAutor> lista = new ArrayList();
            while(rs.next()){
                PnAutorBuscarAutor a = new PnAutorBuscarAutor(rs.getInt("id"), rs.getString("nome"), rs.getString("movimento"));
                lista.add(a);
            }
            return lista;
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null, "Erro ao listar os autores.");
            return null;
        }
        finally{
            desconectar();
        }
    }
    
    public ArrayList<PnAutorGerenciar> listarGerenciar(String busca){
        try{
            String stmt = "SELECT a.id, a.nome, m.nome AS movimento FROM autores a INNER JOIN movimentos m WHERE m.id = a.movimentos_id";
            if(!busca.equals("")){
                stmt = stmt + " AND (a.nome LIKE ? OR a.id = ?)";
            }
            stmt = stmt + " ORDER BY id";
            st = con.prepareStatement(stmt);
            if(!busca.equals("")){
                st.setString(1, "%"+busca+"%");
                st.setString(2, busca);
            }
            rs = st.executeQuery();
            ArrayList<PnAutorGerenciar> lista = new ArrayList();
            while(rs.next()){
                PnAutorGerenciar a = new PnAutorGerenciar(rs.getInt("id"), rs.getString("nome"), rs.getString("movimento"));
                lista.add(a);
            }
            return lista;
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null, "Erro ao listar os autoress.");
            return null;
        }
        finally{
            desconectar();
        }
    }
    
    public Autores exibirAutor(int id){
        Autores a = new Autores();
        Movimentos m = new Movimentos();
        InputStream i;
        BufferedImage imagem;
        ImageIcon foto;
        Date d;
        LocalDate data;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");;
        
        try{
            st = con.prepareStatement("SELECT a.nome, a.data_nascimento, a.data_falecimento, m.nome AS movimento, a.biografia, a.foto FROM autores a INNER JOIN movimentos m WHERE m.id = a.movimentos_id AND a.id = ?");
            st.setInt(1, id);
            rs = st.executeQuery();
            if(rs.next()){
                m.setNome(rs.getString("movimento"));
                a.setMovimento(m);
                
                a.setNome(rs.getString("nome"));
                
                d = rs.getDate("data_nascimento");
                data = d.toLocalDate();
                a.setDataNascimento(data.format(formatter));

                if(rs.getDate("data_falecimento")!=null){
                    d = rs.getDate("data_falecimento");
                    data = d.toLocalDate();
                    a.setDataFalecimento(data.format(formatter));
                }
                a.setBiografia(rs.getString("biografia"));

                if(rs.getBinaryStream("foto")!=null){
                    i = rs.getBinaryStream("foto");
                    imagem = ImageIO.read(i);
                    Image ajuste = imagem.getScaledInstance(225, 225, Image.SCALE_SMOOTH);
                    foto = new ImageIcon(ajuste);
                    a.setFoto(foto);
                }

                return a;
            }
            else{
                return null;
            }
        }
        catch(Exception ex){
            JOptionPane.showMessageDialog(null,"Erro ao exibir autor.");
            return null;
        }
        finally{
            desconectar();
        }
    }
    
    public void excluir(int id){
        try{
            st = con.prepareStatement("DELETE FROM autores WHERE id = ?");
            st.setInt(1, id);
            st.executeUpdate();
            JOptionPane.showMessageDialog(null, "Autor excluído.");
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null, "Erro ao excluir autor.");
        }
        finally{
            desconectar();
        }
    }
    
    public void cadastrarAutor(String nome, int idmovimento, String nascimento, String falecimento, String biografia, File imagem){
        try{
            SimpleDateFormat entrada = new SimpleDateFormat("dd/MM/yyyy");
            entrada.setLenient(false);
            st = con.prepareStatement("INSERT INTO autores(nome, data_nascimento, data_falecimento, movimentos_id, biografia, foto) VALUES (?, ?, ?, ?, ?, ?)");
            java.util.Date data = entrada.parse(nascimento);
            Date dataNascimento = new Date(data.getTime());
            Date dataFalecimento=null;
            if(falecimento!=null){
                data = entrada.parse(falecimento);
                dataFalecimento = new Date(data.getTime());
            }
            st.setString(1, nome);
            st.setDate(2, dataNascimento);
            st.setDate(3, dataFalecimento);
            st.setInt(4, idmovimento);
            st.setString(5, biografia);
            FileInputStream in = null;
            if(imagem!=null){
                in = new FileInputStream(imagem);
            }
            st.setBinaryStream(6, in);
            st.executeUpdate();
            JOptionPane.showMessageDialog(null, "Autor cadastrado.");
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null, "Erro ao cadastrar autor.\n"+e.getMessage());
        }
        finally{
            desconectar();
        }
    }
    
    public void alterarAutor(int id, String nome, int idmovimento, String nascimento, String falecimento, String biografia, File imagem){
        try{
            SimpleDateFormat entrada = new SimpleDateFormat("dd/MM/yyyy");
            entrada.setLenient(false);
            if(imagem!=null){
                st = con.prepareStatement("UPDATE autores SET nome = ?, data_nascimento = ?, data_falecimento = ?, movimentos_id = ?, biografia = ?, foto = ? WHERE id = ?");
            }
            else{
                st = con.prepareStatement("UPDATE autores SET nome = ?, data_nascimento = ?, data_falecimento = ?, movimentos_id = ?, biografia = ? WHERE id = ?");
            }
            java.util.Date data = entrada.parse(nascimento);
            Date dataNascimento = new Date(data.getTime());
            Date dataFalecimento=null;
            if(falecimento!=null){
                data = entrada.parse(falecimento);
                dataFalecimento = new Date(data.getTime());
            }
            st.setString(1, nome);
            st.setDate(2, dataNascimento);
            st.setDate(3, dataFalecimento);
            st.setInt(4, idmovimento);
            st.setString(5, biografia);
            if(imagem!=null){
                FileInputStream in = new FileInputStream(imagem);
                st.setBinaryStream(6, in);
                st.setInt(7, id);
            }
            else{
                st.setInt(6, id);
            }
            st.executeUpdate();
            JOptionPane.showMessageDialog(null, "Autor alterado.");
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null, "Erro ao alterar autor.\n"+e.getMessage());
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
                st = con.prepareStatement("SELECT a.id, a.nome FROM autores a INNER JOIN livros l WHERE l.autores_id = a.id AND l.id = ?");
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
            st = con.prepareStatement("SELECT id, nome FROM autores WHERE id!= ? ORDER BY nome");
            st.setInt(1, idban);
            rs = st.executeQuery();
            while(rs.next()){
                String s = rs.getString("id")+"/"+rs.getString("nome");
                lista.add(s);
            }
            return lista;
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null, "Erro ao listar os autores.");
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
