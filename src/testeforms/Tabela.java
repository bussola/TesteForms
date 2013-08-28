/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package testeforms;

import com.sun.org.apache.bcel.internal.generic.BREAKPOINT;
import java.sql.*;
import javax.swing.JOptionPane;

/**
 *
 * @author Bussola1
 */
public class Tabela extends javax.swing.JDialog {
    TesteForms testF = new TesteForms();
    //Aluguel frmAluguel = new Aluguel(null,true);
    
    public static String[] usuNomes = new String[TesteForms.qntUsu()];
    public static String[] valor = new String[TesteForms.qntUsu()];      //Valores do lancamento feito
    public static int[] valorTotal = new int[100]; //Total de valores da coluna VALOR do sql
    public static String[] nomeLanTab = new String[100]; //
    public static String[] nomeDesc = new String[100]; //Descricoes que vao na tabela3
    public static int[] aluguelUsu = new int[TesteForms.qntUsu()]; //Aluguel da cada Usuario
    public static int[] total = new int[pegaQntColuna()]; //Total do valor de cada coluna na tabela3
    
    public static int pegaQntTabDois(){
        Connection c = null;
        Statement stmt = null;
        int qntTabDois = 0;
          try {
      Class.forName("org.sqlite.JDBC");
      c = DriverManager.getConnection("jdbc:sqlite:tabelaDois.db");
      c.setAutoCommit(false);
      stmt = c.createStatement();
                //Encontra a quantidade total de linhas existentes na tabelaDois do SQL
        String sql = "select count(*) from tabelaDois";
        ResultSet res = stmt.executeQuery(sql);
        qntTabDois = res.getInt("count(*)");
        c.commit();
        res.close();        
        stmt.close();        
        c.close();      
      } catch ( Exception e ) {
        System.err.println( e.getClass().getName() + ": " + e.getMessage() );
        System.exit(0);
      }
        return qntTabDois;
    }
    
    public static int pegaQntColuna() {
            int qntColunas = (pegaQntTabDois()/TesteForms.qntUsu());
        return qntColunas;
    }
    
    public void imprime() {
        Connection c = null;
        Statement stmt = null;

        String[] aux = new String[testF.qntUsu()];
        for(int i = 0; i<=testF.qntUsu()-1; i++) {
          try {
      Class.forName("org.sqlite.JDBC");
      c = DriverManager.getConnection("jdbc:sqlite:tabela.db");
      c.setAutoCommit(false);
      stmt = c.createStatement();
                //Aluguel de cada usuario
        String sql = "SELECT ALUGUEL FROM tabela where ID = " + (i+1) + ";";
        ResultSet res = stmt.executeQuery(sql);
        aux[i] = res.getString("ALUGUEL");
        aluguelUsu[i] = Integer.parseInt(aux[i]);
        c.commit();
        res.close();        
        stmt.close();        
        c.close();      
      } catch ( Exception e ) {
        System.err.println( e.getClass().getName() + ": " + e.getMessage() );
        System.exit(0);
      }
    }
        for(int i=0;i<testF.qntUsu();i++){     
            jTable4.getModel().setValueAt(aluguelUsu[i], i, 1);        
            System.out.println(aluguelUsu[i]);
    }
        
        c = null;
        stmt = null;
        
          try {
      Class.forName("org.sqlite.JDBC");
      c = DriverManager.getConnection("jdbc:sqlite:tabelaDois.db");
      c.setAutoCommit(false);
      stmt = c.createStatement();
                //
        int[] totDebUsu = new int[testF.numUsuarios];
        int[] totCredUsu = new int[testF.numUsuarios];
        int[] totUsu = new int[testF.numUsuarios];
        
               for( int i = 0; i < testF.numUsuarios; i++) {
               String sql = "SELECT SUM(VALOR) FROM tabelaDois where NAME_LANCADOR = '" + usuNomes[i] + "';";
        ResultSet res = stmt.executeQuery(sql);
        totCredUsu[i] = res.getInt("SUM(VALOR)");
               sql = "SELECT SUM(VALOR) FROM tabelaDois where NAME = '" + usuNomes[i] + "';";
        res = stmt.executeQuery(sql);
        totDebUsu[i] = res.getInt("SUM(VALOR)");
        totUsu[i] = (totCredUsu[i]-totDebUsu[i]-aluguelUsu[i]);
        jTable4.getModel().setValueAt(totUsu[i], i, 2);
               }
        c.commit();
        //res.close();        
        stmt.close();        
        c.close();      
      } catch ( Exception e ) {
        System.err.println( e.getClass().getName() + ": " + e.getMessage() );
        System.exit(0);
      }
    
        
             c = null;
             stmt = null;
             //Cria a tabela se ela nao existe
        String sql = null;
        c = null;
        stmt = null;
    
        try {
      Class.forName("org.sqlite.JDBC");
      c = DriverManager.getConnection("jdbc:sqlite:tabelaDois.db");
      c.setAutoCommit(false);
      stmt = c.createStatement();
                //Pega todos o VALORES do sql
        String[] valores = new String[pegaQntTabDois()];
        String[] desc = new String[pegaQntTabDois()];
        int[] valorTotInt = new int[pegaQntTabDois()];
               for( int i = 0; i < pegaQntTabDois() ; i++) {
               sql = "SELECT VALOR FROM tabelaDois where ID = " + (i+1) + ";";
        ResultSet res = stmt.executeQuery(sql);
        valores[i] = res.getString("VALOR");
        valorTotInt[i] = Integer.parseInt(valores[i]);
        valorTotal[i] = valorTotInt[i]; 
        //Nome dos Lancadores
               sql = "SELECT NAME_LANCADOR FROM tabelaDois where ID = " + (i+1) + ";";
        res = stmt.executeQuery(sql);
        desc[i] = res.getString("NAME_LANCADOR");
        nomeLanTab[i] = desc[i];
        //Descricao
               sql = "SELECT DESC FROM tabelaDois where ID = " + (i+1) + ";";
        res = stmt.executeQuery(sql);
        desc[i] = res.getString("DESC");
        nomeDesc[i] = desc[i];   
        //Pega aluguel do SQL
               }
        c.commit();
        //res.close();        
        stmt.close();        
        c.close();      
      } catch ( Exception e ) {
        System.err.println( e.getClass().getName() + ": " + e.getMessage() );
        System.exit(0);
      }
          //Preenche a tabela3 do programa
          int aux1=0;
          for(int j=0;j<pegaQntColuna();j++){
              total[j] = 0;
              jTable3.getModel().setValueAt(nomeLanTab[aux1], (0), (j+1));
              jTable3.getModel().setValueAt(nomeDesc[aux1], (1), (j+1));
              aux1 += testF.qntUsu();
            for(int i=0;i<testF.qntUsu();i++){
             jTable3.getModel().setValueAt(valorTotal[(i+(j*testF.qntUsu()))], (i+2), (j+1));
             total[j] += valorTotal[(i+(j*testF.qntUsu()))];
             jTable3.getModel().setValueAt(total[j], (2+testF.qntUsu()), (j+1));
            }
        }
    }
        
    //public void calcula() {
        
    
    public void setaAluguel() {
                
        
}
  
    
    
    /**
     * Creates new form Tabela
     */
    public Tabela(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
       
        //Colocar os nomes nas tabelas
        TesteForms testFo = new TesteForms();
        Connection c = null;
        Statement stmt = null;
        ResultSet res = null;
        String sql = null;
        int i = 0;
          
            for( i = 0; i < testFo.qntUsu(); i++) {
            try {
      Class.forName("org.sqlite.JDBC");
      c = DriverManager.getConnection("jdbc:sqlite:tabela.db");
      c.setAutoCommit(false);
      stmt = c.createStatement();
      //System.out.println("Opened database successfully");     
        sql = "SELECT NAME FROM tabela where ID = " + (i+1) + ";";
        res = stmt.executeQuery(sql);
        usuNomes[i] = res.getString("NAME");
                //System.out.println(usuarios[i]);
        c.commit();
        res.close();        
        stmt.close();        
        c.close();      
      } catch ( Exception e ) {
        System.err.println( e.getClass().getName() + ": " + e.getMessage() );
        System.exit(0);
      }       
        
    }   
            //Coloca as palavras lancador, descricao e total na tabela de baixo
        jTable3.getModel().setValueAt("Lançador", 0, 0);
        jTable3.getModel().setValueAt("Descrição", 1, 0);
        jTable3.getModel().setValueAt("Total", (2+testF.qntUsu()), 0);
        for(i = 0; i < testFo.qntUsu(); i++) {  
            //Coloca o nome dos usuarios nas 3 tabelas
        jTable4.getModel().setValueAt(usuNomes[i], i, 0);
        jTable6.getModel().setValueAt(usuNomes[i], i, 0);
        jTable3.getModel().setValueAt(usuNomes[i], (i+2), 0);
        }
       imprime();
        setaAluguel();
        
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane5 = new javax.swing.JScrollPane();
        jTable5 = new javax.swing.JTable();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTable3 = new javax.swing.JTable();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTable4 = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jScrollPane6 = new javax.swing.JScrollPane();
        jTable6 = new javax.swing.JTable();
        jButton3 = new javax.swing.JButton();
        jComboBox1 = new javax.swing.JComboBox();
        jLabel2 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jButton4 = new javax.swing.JButton();

        jTable5.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "Nome", "Lançamento"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane5.setViewportView(jTable5);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Planilha");
        setBackground(new java.awt.Color(153, 153, 0));
        setBounds(new java.awt.Rectangle(640, 400, 0, 0));
        setResizable(false);

        jTable3.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "Nome", "Lançamento", "null", "null", "null", "Título 6", "Título 7"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane3.setViewportView(jTable3);

        jTable4.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Nome", "Aluguel", "Total"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.Object.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane4.setViewportView(jTable4);

        jButton1.setText("Lançar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("Voltar");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jTable6.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "Nome", "Lançamento"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane6.setViewportView(jTable6);

        jButton3.setText("Config");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Agua", "Energia", "Particular", "Outros" }));
        jComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox1ActionPerformed(evt);
            }
        });

        jLabel2.setText("Descrição");

        jTextField1.setText("Descrição");

        jButton4.setText("Atualizar planilha");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(26, 26, 26)
                        .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 204, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(26, 26, 26)
                                .addComponent(jLabel2))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(25, 25, 25)
                                .addComponent(jButton4))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(52, 52, 52)
                                .addComponent(jButton1))))
                    .addComponent(jScrollPane3))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(16, 16, 16)
                        .addComponent(jButton3))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton2)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(11, 11, 11)
                        .addComponent(jLabel2)
                        .addGap(1, 1, 1)
                        .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButton4)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(74, 74, 74)
                        .addComponent(jButton3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton2))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(32, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
       dispose();
        NewJDialog1 frmJanela = new NewJDialog1(null, true);
        frmJanela.setVisible(true);
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed

        //Abre caixa de confirma
        int confirma = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja lancar?");
        if(confirma == JOptionPane.YES_OPTION) {
        
        
        String sql = null;
        Connection c = null;
        Statement stmt = null;
                     
        //pega dados do lancamento
        String[] lancamentoValor = new String[testF.qntUsu()];
        for(int i=0;i<testF.qntUsu();i++) {
            Object aux = jTable6.getModel().getValueAt(i, 1);
            if(aux == null) {
                lancamentoValor[i] = "0";
                valor[i] = lancamentoValor[i];
                            }
            else {
                lancamentoValor[i] = (String)aux;
                valor[i] = lancamentoValor[i];
                 }
            
  }

        //Insere os dados no sql
    c = null;
    stmt = null;
    try {
        Class.forName("org.sqlite.JDBC");
        c = DriverManager.getConnection("jdbc:sqlite:tabelaDois.db");
        c.setAutoCommit(false);
        //System.out.println("Opened database successfully2");
        stmt = c.createStatement();
        String[] lancamentos = new String[testF.qntUsu()];
        
        for(int i =0;i<testF.qntUsu();i++) {
        lancamentos[i] = "INSERT INTO tabelaDois (NAME_LANCADOR, NAME, DESC, DESCDOIS, VALOR) " +
                           "VALUES ('" + testF.usuarioAtual + "', '" +usuNomes[i] + "', '" + jComboBox1.getModel().getSelectedItem() + 
                            "', '" + jTextField1.getText() + "', '" + valor[i] +"');";
        stmt.executeUpdate(lancamentos[i]);
        }
        stmt.close();
        c.commit();
        c.close();
        
        
    } catch ( Exception e ) {
      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
      System.exit(0);
    }  
    imprime();
    setaAluguel();
        
/*                    //Joga os dados do sql na planilha
         for(int j=0;j<qntColunas;j++){
            for(int i=0;i<testF.qntUsu();i++){
            jTable3.getModel().setValueAt(valorTotal[(i+(j*testF.qntUsu()))], i, (j+1));
            }
        }
*/      

    } 
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        Config frmConfig = new Config(null, true);
         frmConfig.setVisible(true);
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed
        //jComboBox1.get
    }//GEN-LAST:event_jComboBox1ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
                imprime();
                setaAluguel();
                //frmAluguel.pegarAluguel();
    }//GEN-LAST:event_jButton4ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Tabela.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Tabela.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Tabela.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Tabela.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                Tabela dialog = new Tabela(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JComboBox jComboBox1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JTable jTable3;
    public javax.swing.JTable jTable4;
    private javax.swing.JTable jTable5;
    private javax.swing.JTable jTable6;
    private javax.swing.JTextField jTextField1;
    // End of variables declaration//GEN-END:variables
}
