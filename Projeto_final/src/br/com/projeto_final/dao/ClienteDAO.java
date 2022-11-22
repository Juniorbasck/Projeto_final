/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.projeto_final.dao;

import java.sql.*;
import br.com.projeto_final.dto.ClienteDTO;

/**
 *
 * @author aluno
 */
public class ClienteDAO {
    
    public ClienteDAO() {
        
    }
    
    private ResultSet rs = null;
    
    private Statement stmt = null;
    
    public boolean inserirCliente(ClienteDTO clienteDTO){
        
        try{
            
            ConexaoDAO.ConectDB();
            
            stmt = ConexaoDAO.con.createStatement();
            
            String comando = "insert into cliente (nome, logradouro, numero,"
                    + "bairro, cidade, estado, cep, cpf, rg) values ( "
                    + "'" + clienteDTO.getNome() + "', "
                    + "'" + clienteDTO.getLogradouro()+ "', "
                    + clienteDTO.getNumero()+ ", "
                    + "'" + clienteDTO.getBairro()+ "', "
                    + "'" + clienteDTO.getCidade()+ "', "
                    + "'" + clienteDTO.getEstado()+ "', "
                    + "'" + clienteDTO.getCep()+ "', "
                    + "'" + clienteDTO.getCpf()+ "', "
                    + "'" + clienteDTO.getRg()+ "') ";
            
            stmt.execute(comando.toUpperCase());
            
            ConexaoDAO.con.commit();
            
            stmt.close();
            
            return true;
        }
        
        catch(SQLException e) {
            
            System.out.println(e.getMessage());
            return false;
              
        }
        
        finally {
            
            ConexaoDAO.CloseDB();
        }
        
    }
    
    public boolean excluirCliente(ClienteDTO clienteDTO) {
        
        try{
            
            ConexaoDAO.ConectDB();
            
            stmt = ConexaoDAO.con.createStatement();
            
            String comando = "Delete from cliente where id_cliente = " + clienteDTO.getId_cliente();
            
            stmt.execute(comando);
            
            ConexaoDAO.con.commit();
            
            stmt.close();
            
            return true;
        }
        
        catch(Exception e){
            System.out.println(e.getMessage());
            return false;
        }
        
        finally{
            
            ConexaoDAO.CloseDB();
        }
    }
    
    public boolean alterarCliente(ClienteDTO clienteDTO) {
        
        try{
            
            ConexaoDAO.ConectDB();
            
            stmt = ConexaoDAO.con.createStatement();
            
            String comando = "Update cliente set "
                        + "nome = '" + clienteDTO.getNome()+ "',"
                        + "logradouro = '" + clienteDTO.getLogradouro()+ "',"
                        + "numero = '" + clienteDTO.getNumero()+ "',"
                        + "bairro = '" + clienteDTO.getBairro()+ "',"
                        + "cidade = '" + clienteDTO.getCidade()+ "',"
                        + "estado = '" + clienteDTO.getEstado()+ "',"
                        + "cep = '" + clienteDTO.getCep()+ "',"
                        + "cpf = '" + clienteDTO.getCpf()+ "',"
                        + "rg = '" + clienteDTO.getRg()+ "'"
                        + "where id_cliente = " + clienteDTO.getId_cliente();
            
            stmt.execute(comando.toUpperCase());
            
            ConexaoDAO.con.commit();
            
            stmt.close();
            
            return true;
            
        }
        
        catch(Exception e){
            
            System.out.println(e.getMessage());
            return false;
            
        }
        
        finally {
            
            ConexaoDAO.CloseDB();
        }
    }
    
   public ResultSet consultarCliente(ClienteDTO clienteDTO, int opcao) {
        try {
           
            ConexaoDAO.ConectDB();
            
            stmt = ConexaoDAO.con.createStatement();
            
            String comando = "";
            switch (opcao){
                case 1:
                    comando = "Select c.* "+
                              "from cliente c "+
                              "where nome like '" + clienteDTO.getNome()+ "%' " +
                              "order by c.nome";    
                break;
                case 2:
                    comando = "Select c.* "+
                              "from cliente c " +
                              "where c.id_cliente = " + clienteDTO.getId_cliente();
                break;
                case 3:
                    comando = "Select c.id_cliente, c.nome "+
                              "from cliente c ";
                break;
                
            }
            
            rs = stmt.executeQuery(comando.toUpperCase());
            return rs;
        } 
        catch (Exception e) {
            System.out.println(e.getMessage());
            return rs;
        }
    }
}
