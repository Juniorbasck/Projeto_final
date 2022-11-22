/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.projeto_final.ctr;

import java.sql.ResultSet;
import br.com.projeto_final.dto.ClienteDTO;
import br.com.projeto_final.dao.ClienteDAO;
import br.com.projeto_final.dao.ConexaoDAO;

/**
 *
 * @author aluno
 */
public class ClienteCTR {
    
    ClienteDAO clienteDAO = new ClienteDAO();
    
    public ClienteCTR(){
        
    }
    
    public String inserirCliente(ClienteDTO clienteDTO) {
        
        try {
            
            if (clienteDAO.inserirCliente(clienteDTO)) {               
                return "Cliente cadastrado com sucesso!";
            }
            else {
                return "Cliente NÃO cadastrado!";
            }
            
        }
        
        catch (Exception e) {
            
            System.out.println(e.getMessage());
            return "Dados não cadrastados!";
        }
    }
    
    public ResultSet consultarCliente(ClienteDTO clienteDTO, int opcao) {
        
        ResultSet rs = null;
        
        rs = clienteDAO.consultarCliente(clienteDTO, opcao);
        
        return rs;
    }
    public String alterarCliente(ClienteDTO clienteDTO) {
        
        try{
            
            if(clienteDAO.alterarCliente(clienteDTO)) {
                return "Cliente alterado com sucesso!";
            }else {
                return "Cliente NÂO alterado!";
            }
        }
        
        catch(Exception e){
            System.out.println(e.getMessage());
            return"Cliente NÂO Alterado";
        }
    }
    
    public void CloseDB() {
        
        ConexaoDAO.CloseDB();
    }
    
    public String excluirCliente(ClienteDTO clienteDTO) {
        
        try{
            
            if(clienteDAO.excluirCliente(clienteDTO)) {
                return "Cliente Excluido com Sucesso!";
            } else {
                return "Clietne Não Excluido!";
            }
        }
        
        catch (Exception e) {
            System.out.println(e.getMessage());
            return "Cliente Não Excluido!";
        }
    }
}
