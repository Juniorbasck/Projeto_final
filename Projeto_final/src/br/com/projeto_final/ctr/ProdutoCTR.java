
package br.com.projeto_final.ctr;

import java.sql.*;
import br.com.projeto_final.dto.FornecedorDTO;
import br.com.projeto_final.dto.ProdutoDTO;
import br.com.projeto_final.dao.ProdutoDAO;
import br.com.projeto_final.dao.ConexaoDAO;

public class ProdutoCTR {
    
    ProdutoDAO produtoDAO = new ProdutoDAO();
    
    /**
     * Método construtor da classe
     */
    public ProdutoCTR(){
    }
    
    public String inserirProduto(ProdutoDTO produtoDTO, FornecedorDTO fornecedorDTO){
        try{
            //Chama o metodo que esta na classe DAO aguardando uma resposta(true ou false)
            if(produtoDAO.inserirProduto(produtoDTO, fornecedorDTO)){
                return "Produto Cadastrado com Sucesso!!!";
            } else{
                return "Produto NÃO Cadastrado!!!";
            }
        }//Caso tenha algum erro no codigo acima é enviado uma mensagem no
         //console com o que esta acontecendo
        catch (Exception e){
            System.out.println(e.getMessage());
            return "Produto NÃO Cadastrado!!!";
        }
    }//Fecha o método inserirProduto
    
    public String alterarProduto(ProdutoDTO produtoDTO, FornecedorDTO fornecedorDTO){
        try{
            //Chama o metodo que esta na classe DAO aguardando uma resposta(true ou false)
            if(produtoDAO.alterarProduto(produtoDTO, fornecedorDTO)){
                return "Produto Alterado com Sucesso!!!";
            } else{
                return "Produto NÃO Alterado!!!";
            }
        }//Caso tenha algum erro no codigo acima é enviado uma mensagem no
         //console com o que esta acontecendo
        catch (Exception e){
            System.out.println(e.getMessage());
            return "Produto NÃO Alterado!!!";
        }
    }//Fecha o método alterarProduto
    
    public String excluirProduto(ProdutoDTO produtoDTO){
        try{
            //Chama o metodo que esta na classe DAO aguardando uma resposta(true ou false)
            if(produtoDAO.excluirProduto(produtoDTO)){
                return "Produto Excluído com Sucesso!!!";
            } else{
                return "Produto NÃO Excluído!!!";
            }
        }//Caso tenha algum erro no codigo acima é enviado uma mensagem no
         //console com o que esta acontecendo
        catch (Exception e){
            System.out.println(e.getMessage());
            return "Produto NÃO Excluído!!!";
        }
    }//Fecha o método excluirProduto
    
     public ResultSet consultarProduto(ProdutoDTO produtoDTO, int opcao){
         ResultSet rs = null;
         rs = produtoDAO.consultarProduto(produtoDTO, opcao);
         return rs;
     }//Fecha o método cosultarProduto
     
     public void CloseDB(){
         ConexaoDAO.CloseDB();
     }//Fecha o método CloseDB
    
    
}
