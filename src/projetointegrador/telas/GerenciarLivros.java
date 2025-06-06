/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package projetointegrador.telas;

import projetointegrador.telas.InfoLivro;
import projetointegrador.telas.TelaPrincipal;
import java.awt.Color;
import javax.swing.JOptionPane;
import javax.swing.plaf.basic.BasicScrollBarUI;
import java.awt.Component;
import java.util.ArrayList;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import projetointegrador.dao.Conector;
import projetointegrador.dao.LivrosDAO;
import projetointegrador.panels.PnLivroGerenciar;
import interfaces.GlobalInterface;

/**
 *
 * @author Gato Sagrado
 */
public class GerenciarLivros extends javax.swing.JPanel implements GlobalInterface {

    /**
     * Creates new form GerenciarLivros
     */
    public GerenciarLivros() {
        initComponents();
        listar();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        PnBusca = new javax.swing.JPanel();
        BtBusca = new javax.swing.JButton();
        TxBusca = new javax.swing.JTextField();
        LbTutorial = new javax.swing.JLabel();
        BtCadastro = new javax.swing.JButton();
        LbTutorial1 = new javax.swing.JLabel();
        BtFechar = new javax.swing.JButton();
        LbTutorial4 = new javax.swing.JLabel();
        LbTutorial5 = new javax.swing.JLabel();
        PnScroll = new javax.swing.JScrollPane();
        PnAux = new javax.swing.JPanel();

        setBackground(new java.awt.Color(186, 186, 186));
        setMaximumSize(new java.awt.Dimension(1240, 1024));
        setMinimumSize(new java.awt.Dimension(1240, 1024));
        setPreferredSize(new java.awt.Dimension(1240, 1024));
        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        PnBusca.setBackground(new java.awt.Color(186, 186, 186));
        PnBusca.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        BtBusca.setBackground(new java.awt.Color(109, 37, 172));
        BtBusca.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/iconBuscar.png"))); // NOI18N
        BtBusca.setMnemonic('p');
        BtBusca.setToolTipText("Busca o ID ou título");
        BtBusca.setDisabledIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/iconBuscarDisabled.png"))); // NOI18N
        BtBusca.setDisabledSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/iconBuscarDisabled.png"))); // NOI18N
        BtBusca.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/iconBuscarDisabled.png"))); // NOI18N
        BtBusca.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/iconBuscarSelected.png"))); // NOI18N
        BtBusca.setRolloverSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/iconBuscarSelected.png"))); // NOI18N
        BtBusca.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/iconBuscarSelected.png"))); // NOI18N
        BtBusca.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtBuscaActionPerformed(evt);
            }
        });
        PnBusca.add(BtBusca, new org.netbeans.lib.awtextra.AbsoluteConstraints(1020, 30, 90, 90));

        TxBusca.setBackground(new java.awt.Color(153, 114, 188));
        TxBusca.setFont(new java.awt.Font("Arial", 0, 40)); // NOI18N
        TxBusca.setForeground(java.awt.Color.darkGray);
        TxBusca.setText("Buscar por ID ou título");
        TxBusca.setToolTipText("Digite o ID ou título do livro");
        TxBusca.setMargin(new java.awt.Insets(2, 15, 2, 15));
        TxBusca.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                TxBuscaFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                TxBuscaFocusLost(evt);
            }
        });
        TxBusca.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TxBuscaActionPerformed(evt);
            }
        });
        PnBusca.add(TxBusca, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 30, 880, 90));

        LbTutorial.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        LbTutorial.setText("Alt + P");
        PnBusca.add(LbTutorial, new org.netbeans.lib.awtextra.AbsoluteConstraints(1038, 5, -1, -1));

        BtCadastro.setBackground(new java.awt.Color(109, 37, 172));
        BtCadastro.setFont(new java.awt.Font("Arial", 0, 90)); // NOI18N
        BtCadastro.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/iconCadastro.png"))); // NOI18N
        BtCadastro.setMnemonic('n');
        BtCadastro.setToolTipText("Abre a tela de cadastro");
        BtCadastro.setDisabledIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/iconCadastroDisabled.png"))); // NOI18N
        BtCadastro.setDisabledSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/iconCadastroDisabled.png"))); // NOI18N
        BtCadastro.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/iconCadastroDisabled.png"))); // NOI18N
        BtCadastro.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/iconCadastroSelected.png"))); // NOI18N
        BtCadastro.setRolloverSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/iconCadastroSelected.png"))); // NOI18N
        BtCadastro.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/iconCadastroSelected.png"))); // NOI18N
        BtCadastro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtCadastroActionPerformed(evt);
            }
        });
        PnBusca.add(BtCadastro, new org.netbeans.lib.awtextra.AbsoluteConstraints(1120, 30, 90, 90));

        LbTutorial1.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        LbTutorial1.setText("Alt + N");
        PnBusca.add(LbTutorial1, new org.netbeans.lib.awtextra.AbsoluteConstraints(1138, 5, -1, -1));

        BtFechar.setBackground(new java.awt.Color(153, 114, 188));
        BtFechar.setFont(new java.awt.Font("Arial", 0, 75)); // NOI18N
        BtFechar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/iconXis.png"))); // NOI18N
        BtFechar.setMnemonic('x');
        BtFechar.setToolTipText("Volta para a tela anterior");
        BtFechar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtFecharActionPerformed(evt);
            }
        });
        PnBusca.add(BtFechar, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 30, 90, 90));

        LbTutorial4.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        LbTutorial4.setLabelFor(BtFechar);
        LbTutorial4.setText("Alt + X");
        PnBusca.add(LbTutorial4, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 5, -1, -1));

        LbTutorial5.setDisplayedMnemonic('i');
        LbTutorial5.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        LbTutorial5.setLabelFor(TxBusca);
        LbTutorial5.setText("Alt + I");
        PnBusca.add(LbTutorial5, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 5, -1, -1));

        add(PnBusca, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        PnScroll.setBackground(new java.awt.Color(186, 186, 186));
        PnScroll.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(186, 186, 186)));
        PnScroll.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        PnScroll.setAlignmentX(0.0F);
        PnScroll.setAlignmentY(0.0F);

        PnAux.setBackground(new java.awt.Color(186, 186, 186));
        PnAux.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        PnScroll.setViewportView(PnAux);

        add(PnScroll, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 120, 1240, 904));
        PnScroll.getVerticalScrollBar().setBackground(new java.awt.Color(186, 186, 186));
        PnScroll.getHorizontalScrollBar().setBackground(new java.awt.Color(186, 186, 186));
        PnScroll.getViewport().setBackground(new java.awt.Color(186, 186, 186));
        PnScroll.getVerticalScrollBar().setUI(new BasicScrollBarUI() {
            @Override
            protected void configureScrollBarColors() {
                this.thumbColor = new java.awt.Color(109, 37, 172);
            }
        });
    }// </editor-fold>//GEN-END:initComponents

    private void BtBuscaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtBuscaActionPerformed
        listar();
    }//GEN-LAST:event_BtBuscaActionPerformed

    private void TxBuscaFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_TxBuscaFocusGained
        if (TxBusca.getForeground().equals(Color.darkGray)){
            TxBusca.setText("");
            TxBusca.setForeground(Color.BLACK);
        }
    }//GEN-LAST:event_TxBuscaFocusGained

    private void TxBuscaFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_TxBuscaFocusLost
        if (TxBusca.getText().equals("")){
            TxBusca.setText("Buscar por ID ou título");
            TxBusca.setForeground(Color.darkGray);
        }
    }//GEN-LAST:event_TxBuscaFocusLost

    private void TxBuscaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TxBuscaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TxBuscaActionPerformed

    private void BtCadastroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtCadastroActionPerformed
        TelaPrincipal.sobrepor(new CadastroLivro(this, -1));
    }//GEN-LAST:event_BtCadastroActionPerformed

    private void BtFecharActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtFecharActionPerformed
        TelaPrincipal.trocar(new Gerenciar());
    }//GEN-LAST:event_BtFecharActionPerformed

    private void listar(){
        LivrosDAO dao = new LivrosDAO(Conector.conectar());
        String busca = "";
        if(!TxBusca.getForeground().equals(Color.darkGray)){
            busca = TxBusca.getText();
        }
        
        ArrayList<PnLivroGerenciar> lista = dao.listarGerenciar(busca);
        if(lista != null){
            if(lista.isEmpty()){
                JOptionPane.showMessageDialog(this, "Nenhum registro encontrado.");
            }

            PnAux.removeAll();
            PnAux.setLayout(new BoxLayout(PnAux, BoxLayout.Y_AXIS));

            PnAux.add(Box.createVerticalStrut(40));
            for (int i = 0; i < lista.size(); i++) {
                PnLivroGerenciar p = lista.get(i);
                p.setAlignmentX(Component.CENTER_ALIGNMENT);
                p.setListener(this);
                PnAux.add(p);
                if (i < lista.size() - 1) {
                    PnAux.add(Box.createVerticalStrut(40));
                }
            }

            PnAux.revalidate();
            PnAux.repaint();
        }
    }
    
    @Override
    public void sinalglobal(){
        listar();
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BtBusca;
    private javax.swing.JButton BtCadastro;
    private javax.swing.JButton BtFechar;
    private javax.swing.JLabel LbTutorial;
    private javax.swing.JLabel LbTutorial1;
    private javax.swing.JLabel LbTutorial4;
    private javax.swing.JLabel LbTutorial5;
    private javax.swing.JPanel PnAux;
    private javax.swing.JPanel PnBusca;
    private javax.swing.JScrollPane PnScroll;
    private javax.swing.JTextField TxBusca;
    // End of variables declaration//GEN-END:variables
}
