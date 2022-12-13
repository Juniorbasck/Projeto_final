/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.projeto_final.dao;

import java.sql.*;
import br.com.projeto_final.dto.UsuarioDTO;
import javax.swing.JOptionPane;

/**
 *
 * @author lucas
 */
public class UsuarioDAO {

    public UsuarioDAO() {
    }

    private ResultSet rs = null;

    private Statement stmt = null;

    public boolean inserirUser(UsuarioDTO usuarioDTO) {

        try {

            ConexaoDAO.ConectDB();

            stmt = ConexaoDAO.con.createStatement();

            String comando = "Insert into login (usuario, senha)  values( "
                    + "'" + usuarioDTO.getUsuario() + "', "
                    + "'" + usuarioDTO.getSenha() + "')";

            stmt.execute(comando.toUpperCase());

            ConexaoDAO.con.commit();

            stmt.close();

            return true;
        } catch (SQLException e) {

            System.out.println(e.getMessage());
            return false;
        } finally {

            ConexaoDAO.CloseDB();
        }

    }

    public ResultSet autenticarUser2(UsuarioDTO usuarioDTO) {

        try {

            ConexaoDAO.ConectDB();

            stmt = ConexaoDAO.con.createStatement();

            String comando = "select * from login where usuario = "
                    + "'" + usuarioDTO.getUsuario() + "'" + " and "
                    + "senha = " + "'" + usuarioDTO.getSenha() + "'";

            rs = stmt.executeQuery(comando);
            return rs;

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "UsuarioDAO: " + e);
            return rs;
        } finally {

            ConexaoDAO.CloseDB();
        }

    }

}

//public ResultSet autenticacaoUser(UsuarioDTO usuarioDTO) {
//
//        ResultSet comando2;
//
//        try {
//
//            ConexaoDAO.ConectDB();
//
//            stmt = ConexaoDAO.con.createStatement();
//
//            String comando = "select * from login where usuario = "
//                    + "'" + usuarioDTO.getUsuario() + "'" + " and "
//                    + "senha = " + "'" + usuarioDTO.getSenha() + "'";
//
//             rs = stmt.executeQuery(comando.toUpperCase());
//            return rs;
//           
//        } catch (Exception e) {
//            JOptionPane.showMessageDialog(null, "UsuarioDAO: " + e);
//            return rs;
//        } finally {
//
//            ConexaoDAO.CloseDB();
//        }
//
//    }
//}
