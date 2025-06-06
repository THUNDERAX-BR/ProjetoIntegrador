/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package projetointegrador.panels;

import projetointegrador.telas.InfoLivro;
import projetointegrador.telas.TelaPrincipal;

/**
 *
 * @author Gato Sagrado
 */
public class PnLivroInfoAutor extends javax.swing.JPanel {
    
    public int id;

    /**
     * Creates new form PnLivroInfoAutor
     */
    public PnLivroInfoAutor(int id, String titulo, String editora) {
        initComponents();
        this.id = id;
        LbTitulo.setText(titulo);
        LbEditora.setText(editora);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        BtLivro = new javax.swing.JButton();
        LbEditora = new javax.swing.JLabel();
        LbTitulo = new javax.swing.JLabel();

        setBackground(new java.awt.Color(144, 144, 144));
        setMaximumSize(new java.awt.Dimension(1180, 65));
        setMinimumSize(new java.awt.Dimension(1180, 65));
        setPreferredSize(new java.awt.Dimension(1180, 65));
        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        BtLivro.setBackground(new java.awt.Color(144, 144, 144));
        BtLivro.setFont(new java.awt.Font("Segoe UI", 1, 40)); // NOI18N
        BtLivro.setForeground(new java.awt.Color(109, 37, 172));
        BtLivro.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/iconMais.png"))); // NOI18N
        BtLivro.setToolTipText("Abre as informações do livro");
        BtLivro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtLivroActionPerformed(evt);
            }
        });
        add(BtLivro, new org.netbeans.lib.awtextra.AbsoluteConstraints(1125, 18, 30, 30));

        LbEditora.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        LbEditora.setText("Nome da editora");
        add(LbEditora, new org.netbeans.lib.awtextra.AbsoluteConstraints(710, 20, -1, -1));

        LbTitulo.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        LbTitulo.setText("Titulo do livro");
        add(LbTitulo, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, -1, -1));
    }// </editor-fold>//GEN-END:initComponents

    private void BtLivroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtLivroActionPerformed
        TelaPrincipal.sobrepor(new InfoLivro(id));
    }//GEN-LAST:event_BtLivroActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BtLivro;
    private javax.swing.JLabel LbEditora;
    private javax.swing.JLabel LbTitulo;
    // End of variables declaration//GEN-END:variables
}
