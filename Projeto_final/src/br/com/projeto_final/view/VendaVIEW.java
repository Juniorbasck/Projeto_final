/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.projeto_final.view;

import java.awt.*;
import javax.swing.*;
import java.sql.*;
import br.com.projeto_final.dto.VendaDTO;
import br.com.projeto_final.ctr.VendaCTR;
import br.com.projeto_final.dto.ProdutoDTO;
import br.com.projeto_final.dto.ClienteDTO;
import br.com.projeto_final.ctr.ClienteCTR;
import br.com.projeto_final.ctr.ProdutoCTR;

import java.util.Date;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author aluno
 */
public class VendaVIEW extends javax.swing.JInternalFrame {
    
    VendaCTR vendaCTR = new VendaCTR();
    VendaDTO vendaDTO = new VendaDTO();
    ProdutoCTR produtoCTR = new ProdutoCTR();
    ProdutoDTO produtoDTO = new ProdutoDTO();
    ClienteCTR clienteCTR = new ClienteCTR();
    ClienteDTO clienteDTO = new ClienteDTO();
    
    ResultSet rs;
    int gravar_alterar;
    DefaultTableModel modelo_jtl_consultar_cli;
    DefaultTableModel modelo_jtl_consultar_pro;
    DefaultTableModel modelo_jtl_consultar_selecionado;
    
    /**
     * Creates new form VendaVIEW
     */
    public VendaVIEW() {
        initComponents();
        
        liberaCampos(false);
        
        liberaBotoes(true, false, false, false, true);
        
        modelo_jtl_consultar_cli = (DefaultTableModel) jtl_consultar_cli.getModel();
        modelo_jtl_consultar_pro = (DefaultTableModel) jtl_consultar_pro.getModel();
        modelo_jtl_consultar_selecionado = (DefaultTableModel) jtl_consultar_pro_selecionado.getModel();
    }
    
    public void setPosicao(){
        Dimension d = this.getDesktopPane().getSize();
        this.setLocation((d.width - this.getSize().width) / 2, (d.height - this.getSize().height) / 2);
    }//Fecha o método setPosicao()
    
    private void gravar(){
        try{
            vendaDTO.setDat_vend(new Date());
            vendaDTO.setVal_vend(Double.parseDouble(TotalVenda.getText()));
            clienteDTO.setId_cliente(Integer.parseInt(String.valueOf(
                jtl_consultar_cli.getValueAt(jtl_consultar_cli.getSelectedRow(), 0))));
            
            
            JOptionPane.showMessageDialog(null,
                    vendaCTR.inserirVenda(vendaDTO, clienteDTO, jtl_consultar_pro_selecionado)
            );
        }
        catch(Exception e){
            System.out.println("Erro ao Gravar " + e.getMessage());
        }
    }//Fecha o método gravar()
    
    private void preencheTabelaCliente(String nome_cliente){
        
        try{
            
            modelo_jtl_consultar_cli.setNumRows(0);
            
            clienteDTO.setNome(nome_cliente);
            
            rs = clienteCTR.consultarCliente(clienteDTO, 1);
            
            while(rs.next()) {
                
                modelo_jtl_consultar_cli.addRow(new Object[]{
                    rs.getString("id_cliente"),
                    rs.getString("nome"),
                
                });
            }
        }
        
        catch(Exception erTab){
            System.out.println("ERRO SQL: "+erTab);
        }
        
        finally{
            clienteCTR.CloseDB();
        }
    }
    
    private void preencheTabelaProduto(String nome_prod){
        try{
            //Limpa todas as linhas
            modelo_jtl_consultar_pro.setNumRows(0);
            //Enquanto tiver linhas - faça
            produtoDTO.setNome_prod(nome_prod);
            rs = produtoCTR.consultarProduto(produtoDTO, 1);//1 = é a pesquisa por nome na classe DAO
            while(rs.next()){
                modelo_jtl_consultar_pro.addRow(new Object[]{
                    rs.getString("id_prod"),
                    rs.getString("nome_prod"),
                    rs.getDouble("p_venda_prod")
                });
            }
        }
        catch (Exception erTab){
            System.out.println("Erro SQL: "+erTab);
        }
        finally{
            produtoCTR.CloseDB();
        }
    }//Fecha o metodo preencheTabelaProduto(String nome_prod)
     
    private void adicionaProdutoSelecionado(int id_prod, String nome_prod, double p_venda_prod){
        
        try{
            modelo_jtl_consultar_selecionado.addRow(new Object[]{
                id_prod,
                nome_prod,
                p_venda_prod
                
            });
        }
        catch(Exception erTab){
            System.out.println("Erro SQL: "+erTab);
        }
        finally{
            produtoCTR.CloseDB();
        }
    }

    private void removeProdutoSelecionado(int linha_selecionada) {
        try{
            if(linha_selecionada >= 0) {
                modelo_jtl_consultar_selecionado.removeRow(linha_selecionada);
                calculaTotalVenda();
            }
        }
        catch(Exception erTab){
            System.out.println("Erro SQL: "+erTab);
        }
    }
    
    private void calculaTotalVenda(){
        try{
            double total = 0;
            for(int cont = 0; cont < jtl_consultar_pro_selecionado.getRowCount(); cont++){
                total += (Double.parseDouble(String.valueOf(    
                    jtl_consultar_pro_selecionado.getValueAt(cont, 2))) *
                    Integer.parseInt(String.valueOf(
                    jtl_consultar_pro_selecionado.getValueAt(cont, 3))));
            }
            TotalVenda.setText(String.valueOf(total));
        }
        catch(Exception erTab){
            System.out.println("Erro SQL: "+erTab);
        }
    }
    private void liberaCampos(boolean a) {
        pesquisa_nome_cli.setEnabled(a);
        btnPesquisarCli.setEnabled(a);
        jtl_consultar_cli.setEnabled(a);
        pesquisa_nome_pro.setEnabled(a);
        btnPesquisarPro.setEnabled(a);
        jtl_consultar_pro.setEnabled(a);
        btnProAdd.setEnabled(a);
        btnProRem.setEnabled(a);
        jtl_consultar_pro_selecionado.setEnabled(a);
        TotalVenda.setText("0.00");
    }
    
    private void limpaCampos(){
        pesquisa_nome_cli.setText("");
        modelo_jtl_consultar_cli.setNumRows(0);
        pesquisa_nome_pro.setText("");
        modelo_jtl_consultar_pro.setNumRows(0);
        modelo_jtl_consultar_selecionado.setNumRows(0);
    }
    
    private void liberaBotoes(boolean a, boolean b, boolean c, boolean d, boolean e){
        btnNovo.setEnabled(a);
        btnSalvar.setEnabled(b);
        btnCancelar.setEnabled(c);
        btnSair.setEnabled(e);
    }
    
    private boolean verificaPreenchimento() {
        if(jtl_consultar_cli.getSelectedRow() <= 0){
            JOptionPane.showMessageDialog(null, "Deve ser selecionado um Cliente");
            jtl_consultar_cli.requestFocus();
            return false;
        }
        else{
            if(jtl_consultar_pro_selecionado.getRowCount() <= 0){
                JOptionPane.showMessageDialog(null, "É necessário adicionar pelo menos um produto no pedido");
                jtl_consultar_pro_selecionado.requestFocus();
                return false;
            }
            else{
                int verifica = 0;  
                for(int cont = 0; cont < jtl_consultar_pro_selecionado.getRowCount(); cont++) {
                    if(String.valueOf(jtl_consultar_pro_selecionado.getValueAt(
                            cont, 3)).equalsIgnoreCase("null")){
                            verifica++;
                    }
                }
                if(verifica > 0) {
                    JOptionPane.showMessageDialog(null, 
                    "A quantidade de cada produto vendido deve ser informado");
                    jtl_consultar_pro_selecionado.requestFocus();
                    return false;
                }
                else{
                    return true;
                }
            }
        }
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        pesquisa_nome_cli = new javax.swing.JTextField();
        jScrollPane3 = new javax.swing.JScrollPane();
        jtl_consultar_cli = new javax.swing.JTable();
        jLabel3 = new javax.swing.JLabel();
        TotalVenda = new javax.swing.JLabel();
        btnPesquisarCli = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        pesquisa_nome_pro = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        jtl_consultar_pro = new javax.swing.JTable();
        btnProAdd = new javax.swing.JButton();
        btnProRem = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jtl_consultar_pro_selecionado = new javax.swing.JTable();
        btnPesquisarPro = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        btnNovo = new javax.swing.JButton();
        btnSalvar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        btnSair = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Dados", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", 1, 14))); // NOI18N

        jLabel2.setText("Cliente:");

        pesquisa_nome_cli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pesquisa_nome_cliActionPerformed(evt);
            }
        });

        jtl_consultar_cli.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Nome"
            }
        ));
        jScrollPane3.setViewportView(jtl_consultar_cli);

        jLabel3.setFont(new java.awt.Font("Ubuntu", 1, 48)); // NOI18N
        jLabel3.setText("Total Venda:");

        TotalVenda.setFont(new java.awt.Font("Ubuntu", 0, 48)); // NOI18N
        TotalVenda.setText("0.00");

        btnPesquisarCli.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/projeto_final/view/imagens/procurar.png"))); // NOI18N
        btnPesquisarCli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPesquisarCliActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addGap(18, 18, 18)
                        .addComponent(TotalVenda, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 399, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                            .addComponent(jLabel2)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(pesquisa_nome_cli, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(btnPesquisarCli, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(10, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnPesquisarCli, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel2)
                        .addComponent(pesquisa_nome_cli, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 52, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(TotalVenda))
                .addGap(84, 84, 84))
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Produtos", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", 1, 14))); // NOI18N

        jLabel1.setText("Descrição:");

        pesquisa_nome_pro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pesquisa_nome_proActionPerformed(evt);
            }
        });

        jtl_consultar_pro.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Nome", "Valor"
            }
        ));
        jtl_consultar_pro.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jtl_consultar_proKeyPressed(evt);
            }
        });
        jScrollPane1.setViewportView(jtl_consultar_pro);

        btnProAdd.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/projeto_final/view/imagens/seta-para-baixo.png"))); // NOI18N
        btnProAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnProAddActionPerformed(evt);
            }
        });

        btnProRem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/projeto_final/view/imagens/seta-para-cima.png"))); // NOI18N
        btnProRem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnProRemActionPerformed(evt);
            }
        });

        jtl_consultar_pro_selecionado.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Nome", "Valor", "QTD"
            }
        ));
        jScrollPane2.setViewportView(jtl_consultar_pro_selecionado);

        btnPesquisarPro.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/projeto_final/view/imagens/procurar.png"))); // NOI18N
        btnPesquisarPro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPesquisarProActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(161, 161, 161)
                        .addComponent(btnProAdd)
                        .addGap(18, 18, 18)
                        .addComponent(btnProRem))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(pesquisa_nome_pro, javax.swing.GroupLayout.PREFERRED_SIZE, 290, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnPesquisarPro, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))))
                .addContainerGap(8, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnPesquisarPro, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel1)
                        .addComponent(pesquisa_nome_pro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnProRem)
                    .addComponent(btnProAdd, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 118, Short.MAX_VALUE)
                .addContainerGap())
        );

        btnNovo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/projeto_final/view/imagens/novo.png"))); // NOI18N
        btnNovo.setText("Novo");
        btnNovo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNovoActionPerformed(evt);
            }
        });

        btnSalvar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/projeto_final/view/imagens/opcao-de-salvar-arquivo.png"))); // NOI18N
        btnSalvar.setText("Salvar");
        btnSalvar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalvarActionPerformed(evt);
            }
        });

        btnCancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/projeto_final/view/imagens/cancelar.png"))); // NOI18N
        btnCancelar.setText("Cancelar");

        btnSair.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/projeto_final/view/imagens/sair.png"))); // NOI18N
        btnSair.setText("Sair");
        btnSair.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSairActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnNovo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnSalvar, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnSair, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(224, 224, 224))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnNovo, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSalvar, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSair, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addContainerGap(16, Short.MAX_VALUE))
        );

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/projeto_final/view/imagens/venda.png"))); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addGap(18, 18, 18)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addGap(0, 323, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jPanel2.getAccessibleContext().setAccessibleName("Pro");

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void pesquisa_nome_proActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pesquisa_nome_proActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_pesquisa_nome_proActionPerformed

    private void pesquisa_nome_cliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pesquisa_nome_cliActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_pesquisa_nome_cliActionPerformed

    private void btnNovoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNovoActionPerformed
        // TODO add your handling code here:
        liberaCampos(true);
        liberaBotoes(false, true, true, false, true);
    }//GEN-LAST:event_btnNovoActionPerformed

    private void btnSalvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalvarActionPerformed
        // TODO add your handling code here:
        if(verificaPreenchimento()){
            gravar();
            limpaCampos();
            liberaCampos(false);
            liberaBotoes(true, false, false, false, true);
        }
    }//GEN-LAST:event_btnSalvarActionPerformed

    private void btnSairActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSairActionPerformed
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_btnSairActionPerformed

    private void btnPesquisarCliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPesquisarCliActionPerformed
        // TODO add your handling code here:
        preencheTabelaCliente(pesquisa_nome_cli.getText());
    }//GEN-LAST:event_btnPesquisarCliActionPerformed

    private void btnPesquisarProActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPesquisarProActionPerformed
        // TODO add your handling code here:
        preencheTabelaProduto(pesquisa_nome_pro.getText());
    }//GEN-LAST:event_btnPesquisarProActionPerformed

    private void btnProAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnProAddActionPerformed
        // TODO add your handling code here:
        adicionaProdutoSelecionado(
            Integer.parseInt(String.valueOf(jtl_consultar_pro.getValueAt(
                    jtl_consultar_pro.getSelectedRow(), 0))),
                String.valueOf(jtl_consultar_pro.getValueAt(jtl_consultar_pro.getSelectedRow(), 1)),
                Double.parseDouble(String.valueOf(jtl_consultar_pro.getValueAt(
                        jtl_consultar_pro.getSelectedRow(), 2)))
        );                
    }//GEN-LAST:event_btnProAddActionPerformed

    private void btnProRemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnProRemActionPerformed
        // TODO add your handling code here:
        removeProdutoSelecionado(jtl_consultar_pro_selecionado.getSelectedRow());
    }//GEN-LAST:event_btnProRemActionPerformed

    private void jtl_consultar_proKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtl_consultar_proKeyPressed
        // TODO add your handling code here:
        if(evt.getKeyCode() == java.awt.event.KeyEvent.VK_ENTER) {
            calculaTotalVenda();
        }
    }//GEN-LAST:event_jtl_consultar_proKeyPressed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel TotalVenda;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnNovo;
    private javax.swing.JButton btnPesquisarCli;
    private javax.swing.JButton btnPesquisarPro;
    private javax.swing.JButton btnProAdd;
    private javax.swing.JButton btnProRem;
    private javax.swing.JButton btnSair;
    private javax.swing.JButton btnSalvar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTable jtl_consultar_cli;
    private javax.swing.JTable jtl_consultar_pro;
    private javax.swing.JTable jtl_consultar_pro_selecionado;
    private javax.swing.JTextField pesquisa_nome_cli;
    private javax.swing.JTextField pesquisa_nome_pro;
    // End of variables declaration//GEN-END:variables
}
