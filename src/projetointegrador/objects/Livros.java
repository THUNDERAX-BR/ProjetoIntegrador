package projetointegrador.objects;

import java.util.ArrayList;
import javax.swing.ImageIcon;

public class Livros{
    private int id;
    private String titulo;
    private Autores autor;
    private int ano;
    private String descricao;
    private ArrayList<Categorias> categorias;
    private Editoras editora;
    private ImageIcon capa;

    public Livros() {
    }

    public Livros(int id, String titulo, Autores autor, int ano, String descricao, ArrayList<Categorias> categorias, Editoras editora) {
        this.id = id;
        this.titulo = titulo;
        this.autor = autor;
        this.ano = ano;
        this.descricao = descricao;
        this.categorias = categorias;
        this.editora = editora;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Autores getAutor() {
        return autor;
    }

    public void setAutor(Autores autor) {
        this.autor = autor;
    }

    public int getAno() {
        return ano;
    }

    public void setAno(int ano) {
        this.ano = ano;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public ArrayList<Categorias> getCategorias() {
        return categorias;
    }

    public void setCategorias(ArrayList<Categorias> categorias) {
        this.categorias = categorias;
    }

    public Editoras getEditora() {
        return editora;
    }

    public void setEditora(Editoras editora) {
        this.editora = editora;
    }
    
    public String getNomeAutor(){
        return autor.getNome();
    }
    
    public int getIdAutor(){
        return autor.getId();
    }
    
    public String getNomeEditora(){
        return editora.getNome();
    }

    public ImageIcon getCapa() {
        return capa;
    }

    public void setCapa(ImageIcon capa) {
        this.capa = capa;
    }
}
