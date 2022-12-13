/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.projeto_final.ctr;

import br.com.projeto_final.dto.UsuarioDTO;
import br.com.projeto_final.dao.UsuarioDAO;
import br.com.projeto_final.dao.ConexaoDAO;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.sql.ResultSet;

/**
 *
 * @author lucas
 */
public class UsuarioCTR {

    UsuarioDAO usuarioDAO = new UsuarioDAO();

    public UsuarioCTR() {
    }

    public String inserirUsuario(UsuarioDTO usuarioDTO) {

        try {

            if (usuarioDAO.inserirUser(usuarioDTO)) {
                return "Usuário cadastrado com sucesso!";
            } else {
                return "Usuário NÃO cadastrado!";
            }

        } catch (Exception e) {

            System.out.println(e.getMessage());
            return "Dados não cadrastados!";
        }
    }

//    public ResultSet autenticacaoUser(UsuarioDTO usuarioDTO) {
//        ResultSet rs = null;
//        //O atributo rs recebe a consulta realizada pelo método da classe DAO
//        rs = usuarioDAO.autenticacaoUser(usuarioDTO);
//
//        return rs;
//    }
    
    public ResultSet autenticacaoUser2(UsuarioDTO usuarioDTO) {
        
       ResultSet rs = null;
       
       rs = usuarioDAO.autenticarUser2(usuarioDTO);
       
       return rs;
    }
    
    public static String encriptografar(String senha){
        String retorno = "";
        MessageDigest md;
        
        try {
            md = MessageDigest.getInstance("MD5");
            BigInteger hash = new BigInteger(1,md.digest(senha.getBytes()));
            retorno = hash.toString(16);
        } catch (Exception e) {
            return retorno;
        }
        return retorno;
    }

    public void CloseDB() {

        ConexaoDAO.CloseDB();
    }

}
