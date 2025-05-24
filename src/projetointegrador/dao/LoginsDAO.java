package projetointegrador.dao;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import projetointegrador.objects.Logins;
import projetointegrador.panels.PnLoginGerenciar;

public class LoginsDAO {
    private Connection con;
    private PreparedStatement st;
    private ResultSet rs;

    public LoginsDAO(Connection con) {
        this.con = con;
    }
    
    public Logins login(String login, String senha){
        try{
            st = con.prepareStatement("SELECT * FROM Logins WHERE login = ? AND senha = ?");
            st.setString(1, login);
            st.setString(2, senha);
            rs = st.executeQuery();
            if(rs.next()){
                Logins l = new Logins();
                l.setLogin(rs.getString("login"));
                l.setSenha(rs.getString("senha"));
                l.setAcesso(rs.getString("acesso").charAt(0));
                return l;
            }
            else{
                return null;
            }
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null, "Erro ao realizar login.");
            return null;
        }
        finally{
            desconectar();
        }
    }
    public static String md5(String senha){
        try{
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(senha.getBytes());
            BigInteger no = new BigInteger(1, messageDigest);
            String hashtext = no.toString(16);
            while (hashtext.length() < 32) {
                hashtext = "0" + hashtext;
            }
            return hashtext;
        }
        catch(NoSuchAlgorithmException e){
            JOptionPane.showMessageDialog(null, "Ocorreu um erro ao criptografar.");
            return null;
        }
    }
    
    public ArrayList<PnLoginGerenciar> listarGerenciar(String busca){
        try{
            String stmt = "SELECT id, login, acesso FROM logins";
            if(!busca.equals("")){
                stmt = stmt + " WHERE (login LIKE ? OR id = ?)";
            }
            stmt = stmt + " ORDER BY id";
            st = con.prepareStatement(stmt);
            if(!busca.equals("")){
                st.setString(1, "%"+busca+"%");
                st.setString(2, busca);
            }
            rs = st.executeQuery();
            ArrayList<PnLoginGerenciar> lista = new ArrayList();
            while(rs.next()){
                String acesso="";
                switch(rs.getString("acesso")){
                    case "a" -> acesso = "Administrador";
                    case "g" -> acesso = "Gerente";
                }
                PnLoginGerenciar a = new PnLoginGerenciar(rs.getInt("id"), rs.getString("login"), acesso);
                lista.add(a);
            }
            return lista;
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null, "Erro ao listar os logins.");
            return null;
        }
        finally{
            desconectar();
        }
    }
    public void excluir(int id){
        try{
            st = con.prepareStatement("DELETE FROM logins WHERE id = ?");
            st.setInt(1, id);
            st.executeUpdate();
            JOptionPane.showMessageDialog(null, "Login excluído.");
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null, "Erro ao excluir login.");
        }
        finally{
            desconectar();
        }
    }
    
    public void cadastrarLogin(String login, String senha, String acesso){
        try{
            st = con.prepareStatement("SELECT id FROM logins WHERE login = ?");
            st.setString(1, login);
            rs = st.executeQuery();
            if(rs.next()){
                JOptionPane.showMessageDialog(null, "Login já existe.");
            }
            else{
                String senhamd = md5(senha);
                st = con.prepareStatement("INSERT INTO logins(login, senha, acesso) VALUES (?, ?, ?)");
                st.setString(1, login);
                st.setString(2, senhamd);
                st.setString(3, acesso);
                st.executeUpdate();
                JOptionPane.showMessageDialog(null, "Login cadastrado.");
            }
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null, "Erro ao cadastrar login.");
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
