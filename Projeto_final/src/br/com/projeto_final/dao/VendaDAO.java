
package br.com.projeto_final.dao;
import java.sql.*;
import br.com.projeto_final.dto.FornecedorDTO;
import br.com.projeto_final.dto.VendaDTO;
import br.com.projeto_final.dto.ClienteDTO;
import java.text.SimpleDateFormat;
import javax.swing.*;

public class VendaDAO {
    /**
     * Método construtor da classe
     */
    
    public VendaDAO(){
    }
    
    SimpleDateFormat data_format= new SimpleDateFormat("dd/mm/yyyy");
    //Atributo do tipo Result utilizando para realizar consultas
    private ResultSet rs = null;
    //Manipular o banco de dados
    Statement stmt = null;
    Statement stmt1 = null;
    SimpleDateFormat date = new SimpleDateFormat("dd/MM/yyyy");
    
    public boolean inserirVenda(VendaDTO vendaDTO, ClienteDTO clienteDTO, JTable produtos){
        try{
            //Chama o metodo que esta na classe ConexaoDAO para abrir o banco de dados
            ConexaoDAO.ConectDB();
            //Instancia o Statement que sera responsavel por executar alguma coisa no banco de dados
            stmt = ConexaoDAO.con.createStatement();
            //Comando SQL que sera executado no banco de dados
            stmt1 = ConexaoDAO.con.createStatement();
            
            String comando1 = "Insert into venda (dat_vend, val_vend, "
                    + "id_cliente) values ( "
                    + "to_date('" + date.format(vendaDTO.getDat_vend()) +"', 'DD/MM/YYYY'), "
                    + vendaDTO.getVal_vend() + ", "
                    + clienteDTO.getId_cliente()+ ")";
            
            //Executa o comando SQL no banco de dados
            stmt.execute(comando1.toUpperCase(), Statement.RETURN_GENERATED_KEYS);
            rs = stmt.getGeneratedKeys();
            rs.next();
            
            for (int cont = 0; cont < produtos.getRowCount(); cont++) {
                String comando2 = "Insert into produto_venda (id_vend, id_prod, "
                        + "val_prod, qtd_prod) values ( "
                        + rs.getInt("id_vend") + ", "
                        + produtos.getValueAt(cont, 0) + ", "
                        + produtos.getValueAt(cont, 2) + ", "
                        + produtos.getValueAt(cont, 3) + "); ";
                
                stmt1.execute(comando2);
            }
            //Da um commit no banco de dados
            ConexaoDAO.con.commit();
            //Fecha o statement
            stmt.close();
            stmt1.close();
            rs.close();
            return true;
        } //Caso tenha algum erro no codigo acima é enviado uma mensagem no
          //console com o que esta acontecendo.
        catch(Exception e){
            System.out.println(e.getMessage());
            return false;
        }//Independente de dar erro ou não ele vai fechar o banco de dados.
        finally{
            //Chama o metodo da classe ConexaoDAO para fechar o banco de dados
            ConexaoDAO.CloseDB();
        }
        
    }//Fecha o método inserirVenda
    
}