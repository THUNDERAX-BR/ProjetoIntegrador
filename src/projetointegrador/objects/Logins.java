package projetointegrador.objects;

public class Logins {
    private int id;
    private String login;
    private String senha;
    private char acesso;

    public Logins() {
    }

    public Logins(String login, String senha, char acesso) {
        this.login = login;
        this.senha = senha;
        this.acesso = acesso;
    }

    public int getId() {
        return id;
    }
    
    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public char getAcesso() {
        return acesso;
    }

    public void setAcesso(char acesso) {
        this.acesso = acesso;
    }
}
