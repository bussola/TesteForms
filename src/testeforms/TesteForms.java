
package testeforms;

import java.io.File;
import java.sql.*;


public class TesteForms {
    
     public static int numUsuarios = 0;
     public static String usuarioAtual = null;
     
     public static int qntUsu(){
    //Pegar qnt de usuarios
        Connection c = null;
        Statement stmt = null;
        ResultSet res = null;
        String sql = null;
        int qntNomes = 0;
        
          try {
      Class.forName("org.sqlite.JDBC");
      c = DriverManager.getConnection("jdbc:sqlite:tabela.db");
      c.setAutoCommit(false);
      stmt = c.createStatement();
        sql = "select count(*) from tabela";
        res = stmt.executeQuery(sql);
        qntNomes = res.getInt("count(*)");
               // System.out.println(qntNomes);
        c.commit();
        res.close();        
        stmt.close();        
        c.close();      
      } catch ( Exception e ) {
        System.err.println( e.getClass().getName() + ": " + e.getMessage() );
        System.exit(0);
      }
        numUsuarios = qntNomes;
        return numUsuarios;
   }
      
        
    


    public static void main(String[] args) {
        
        
        
        Connection c = null;
        Statement stmt = null;
    try {
      Class.forName("org.sqlite.JDBC");
      c = DriverManager.getConnection("jdbc:sqlite:tabela.db");
                //Verifica se a tabela existe
      DatabaseMetaData meta = c.getMetaData();
      ResultSet res = meta.getTables(null, null, "tabela", null);
              if(!res.next()){
                //table does not exist.         
      stmt = c.createStatement();
      String sql = "CREATE TABLE tabela" +
                   "([ID]           INTEGER  NOT NULL PRIMARY KEY AUTOINCREMENT,  " +
                   " NAME           VARCHAR2(50)    NOT NULL,  " + 
                   " ALUGUEL        NUMBER(15),                " + 
                   " EMAIL          VARCHAR2(40)    NOT NULL,  " +
                   " SENHA          VARCHAR2(20)    NOT NULL  )"; 
      stmt.executeUpdate(sql);
      //System.out.println("Foi criada a tabela");
      stmt.close();
      c.close();
      res.close();
       } else{
           System.err.println("oi");
           c.close();
           res.close();
              }

    } catch ( Exception e ) {
      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
      System.exit(0);
    }
    
    
    String sql = null;
        c = null;
        stmt = null;
    try {
      Class.forName("org.sqlite.JDBC");
      c = DriverManager.getConnection("jdbc:sqlite:tabelaDois.db");
                //Verifica se a tabela existe
      DatabaseMetaData meta = c.getMetaData();
      ResultSet res = meta.getTables(null, null, "tabelaDois", null);
              if(!res.next()){
                //table does not exist.         
      stmt = c.createStatement();
      sql = "CREATE TABLE tabelaDois" +
                   "([ID]          INTEGER         NOT NULL PRIMARY KEY AUTOINCREMENT,  " +
                   " NAME_LANCADOR VARCHAR2(30)    NOT NULL,  " + 
                   " NAME          VARCHAR2(30)    NOT NULL,  " + 
                   " DESC          VARCHAR2(10)    NOT NULL,  " +
                   " DESCDOIS      VARCHAR2(10)    NOT NULL,  " +
                   " VALOR         NUMBER(10)      NOT NULL  );"; 
      stmt.executeUpdate(sql);
      //System.out.println("Foi criada a tabela");
      stmt.close();
      c.close();
      res.close();
       } else{
                 //System.err.println("oi Dois ");
                 c.close();
                 res.close();
              }
      
    } catch ( Exception e ) {
      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
      System.exit(0);
    }
        //System.out.println(qntUsu());
            NewJDialog1 frmJanela = new NewJDialog1(null, true);
        frmJanela.setVisible(true);
    }       
    
    
    
    
}
    

