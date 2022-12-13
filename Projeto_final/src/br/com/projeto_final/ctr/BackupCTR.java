/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.projeto_final.ctr;

import br.com.projeto_final.dao.BackupDAO;

/**
 *
 * @author aluno
 */
public class BackupCTR {
    
	BackupDAO backupDAO = new BackupDAO();

	/**
	 * Função para realizar backup no backupDAO
	 */
	public void confirmaBackup() {
		try {
			this.backupDAO.confirmaBackup();
		} catch (Exception err) {
			System.out.println(err.getMessage());
		}
	}

	/**
	 * Função para restaurar backup no backupDAO
	 */
	public void confirmaRestaurarBackup() {
		try {
			this.backupDAO.confirmaRestaurarBackup();
		} catch (Exception err) {
			System.out.println(err.getMessage());
		}
	}

}