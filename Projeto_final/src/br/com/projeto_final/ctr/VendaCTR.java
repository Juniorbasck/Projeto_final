
package br.com.projeto_final.ctr;

import java.sql.*;
import br.com.projeto_final.dto.FornecedorDTO;
import br.com.projeto_final.dto.VendaDTO;
import br.com.projeto_final.dao.VendaDAO;
import br.com.projeto_final.dto.ClienteDTO;
import br.com.projeto_final.dao.ConexaoDAO;
import javax.swing.JTable;

public class VendaCTR {
    
    VendaDAO vendaDAO = new VendaDAO();
    
    /**
     * Método construtor da classe
     */
    public VendaCTR(){
    }
    
    public String inserirVenda(VendaDTO vendaDTO, ClienteDTO clienteDTO, JTable produtos){
        try{
            //Chama o metodo que esta na classe DAO aguardando uma resposta(true ou false)
            if(vendaDAO.inserirVenda(vendaDTO, clienteDTO, produtos)){
                return "Venda Cadastrada com Sucesso!!!";
            } else{
                return "Venda NÃO Cadastrada!!!";
            }
        }//Caso tenha algum erro no codigo acima é enviado uma mensagem no
         //console com o que esta acontecendo
        catch (Exception e){
            System.out.println(e.getMessage());
            return "Produto NÃO Cadastrado!!!";
        }
    }//Fecha o método inserirVenda
    
    public void CloseDB() {
        ConexaoDAO.CloseDB();
    }
}    
  