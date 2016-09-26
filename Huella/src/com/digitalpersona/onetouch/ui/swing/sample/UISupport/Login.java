package com.digitalpersona.onetouch.ui.swing.sample.UISupport;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.border.EmptyBorder;
import javax.swing.WindowConstants;
import db.MysqlConnect;

import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Font;

public class Login extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JTextField textFieldUser;
	private JTextField textFieldPass;
	public static String usuarioConectado;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			Login dialog = new Login();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public Login() {
		setTitle("Sistema de Ingreso");
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setResizable(false);
		setModalExclusionType(ModalExclusionType.APPLICATION_EXCLUDE);
		setBounds(100, 100, 280, 190);
		setLocationRelativeTo(null);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			JLabel lblUser = new JLabel("Usuario:");
			lblUser.setBounds(12, 24, 70, 20);
			contentPanel.add(lblUser);
		}
		{
			textFieldUser = new JTextField();
			textFieldUser.setBounds(111, 24, 130, 20);
			contentPanel.add(textFieldUser);
			textFieldUser.setColumns(10);
		}
		{
			JLabel lblPass = new JLabel("Clave:");
			lblPass.setBounds(12, 61, 70, 20);
			contentPanel.add(lblPass);
		}
		{
			textFieldPass = new JPasswordField();
			textFieldPass.setBounds(111, 61, 130, 20);
			contentPanel.add(textFieldPass);
			textFieldPass.setColumns(10);
		}
		
		JLabel lblError = new JLabel();
		lblError.setText("<html> <font color='red'> Error de usuario y/o clave </font></html>");
		lblError.setVisible(false);
		lblError.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblError.setBounds(40, 92, 174, 25);
		contentPanel.add(lblError);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setBorder(null);
			buttonPane.setLayout(new FlowLayout(FlowLayout.CENTER));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("Ingresar");
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						
						MysqlConnect conection = MysqlConnect.getDbCon();
						/*try {
							conection.saveUser(); // UNICA VEZ
						} catch (NoSuchAlgorithmException e1) {							
							e1.printStackTrace();
						} catch (InvalidKeySpecException e1) {
							e1.printStackTrace();
						}*/
						boolean result = conection.validateUser(textFieldUser.getText(),textFieldPass.getText());
						if (result){
							// Llamada al MAINFORM
							usuarioConectado = textFieldUser.getText();
							MainMenu menu = new MainMenu();
							menu.setUsuarioConectado(usuarioConectado);
							setVisible(false);								
						}else{
							lblError.setVisible(true);
							}
					}
				});
			}
			{
				JButton cancelButton = new JButton("Cancelar");
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						dispose();           //End Dialog
					}
				});
			}
		}
	}
}
