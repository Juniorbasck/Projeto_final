package br.com.projeto_final.ctr;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.logging.Logger;

import javax.swing.JDialog;
import javax.swing.JOptionPane;

public class windowsBKP {

	private static final Logger log = Logger.getLogger(windowsBKP.class.getName());

	public static void executeCommand(final String command) throws IOException {

		final ArrayList<String> commands = new ArrayList<String>();
		commands.add("/bin/bash");
		commands.add("-c");
		commands.add(command);

		BufferedReader br = null;

		try {
			final ProcessBuilder p = new ProcessBuilder(commands);
			final Process process = p.start();
			final InputStream is = process.getInputStream();
			final InputStreamReader isr = new InputStreamReader(is);
			br = new BufferedReader(isr);

			String line;
			while ((line = br.readLine()) != null) {
				System.out.println("Retorno do comando = [" + line + "]");
			}
		} catch (IOException ioe) {
			log.severe("Erro ao executar comando shell" + ioe.getMessage());
			throw ioe;
		} finally {
			secureClose(br);
		}
	}

	private static void secureClose(final Closeable resource) {
		try {
			if (resource != null) {
				resource.close();
			}
		} catch (IOException ex) {
			log.severe("Erro = " + ex.getMessage());
		}
	}

	public static void main(String[] args) throws IOException {
		// final LocalShell shell = new LocalShell();
		// shell.executeCommand("clear");
	}

	public static void confirmaBackup() throws IOException {
		JDialog.setDefaultLookAndFeelDecorated(false);

		int response = JOptionPane.showConfirmDialog(null, "Deseja Realmente Realizar o Backup?", "Confirmar",
				JOptionPane.YES_NO_OPTION);

		if (response == JOptionPane.NO_OPTION) {
			JOptionPane.getDefaultLocale();
		} else if (response == JOptionPane.YES_OPTION) {
			LinuxBKP.executeCommand("zip -r /Clinica/Clinica.zip /Clinica/");
			JOptionPane.getDefaultLocale();
		}
	}

	public static void confirmaRestaurarBackup() throws IOException {
		JDialog.setDefaultLookAndFeelDecorated(false);

		int response = JOptionPane.showConfirmDialog(null, "Deseja Realmente Restaurar o Backup?", "Confirmar",
				JOptionPane.YES_NO_OPTION);

		if (response == JOptionPane.NO_OPTION) {
			JOptionPane.getDefaultLocale();
		} else if (response == JOptionPane.YES_OPTION) {
			windowsBKP.executeCommand("unzip -o /Clinica/Clinica.zip -d /");
			JOptionPane.getDefaultLocale();
		}
	}
}
