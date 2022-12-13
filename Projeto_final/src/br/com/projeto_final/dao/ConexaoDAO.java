
package br.com.projeto_final.dao;
import java.sql.*;

/**
 * Essa classe contém os métodos para abrir e fechar o banco de dados
 * @author Aluno
 */
public class ConexaoDAO {
    //Criando um atributo do tipo Connection que servirá para guardar a conexao
    //com o banco de dados.
    public static Connection con = null;
    
    /**
     * Método construtor da classe
     */
    public ConexaoDAO(){
    }
    
    public static void ConectDB(){
        try{
            //Dados para conectar com o banco de dados Postgres
            String dsn = "tenis"; //nome do banco de dados(igual ao criado no Postgres)
            String user = "postgres"; //nome do usuario utilizado para se conectar
            String senha = "123"; //senha do usuario acima informado
            
            DriverManager.registerDriver(new org.postgresql.Driver());
            
            String url = "jdbc:postgresql://localhost:5432/" + dsn;
            
            con = DriverManager.getConnection(url, user, senha);
            
            con.setAutoCommit(false);
            if(con == null){
                System.out.println("erro ao abrir o banco");
            }
        }
        //Caso ocorra problemas para abrir o banco de dados é emitido a mensagem no console.
        catch(Exception e){
            System.out.println("Problema ao abrir a base de dados! " + e.getMessage());
        }
    }//Fecha o método ConectDB
    
    public static void CloseDB(){
        try{
            con.close();
        }
        //Caso ocorra problemas para fechar o banco de dados é emitido a mensagem no console.
        catch(Exception e){
            System.out.println("Problema ao fechar a base de dados! " + 
                e.getMessage());
        }
    }//Fecha o método CloseDB
}
