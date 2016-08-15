package com.digitalpersona.onetouch.ui.swing.sample.UISupport;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.border.EmptyBorder;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import javax.swing.border.LineBorder;

import db.MysqlConnect;

import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.Dialog.ModalExclusionType;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.Font;

public class Login extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField textFieldUser;
	private JTextField textFieldPass;

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
		setBounds(100, 100, 280, 180);
		setLocationRelativeTo(null);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			JLabel lblUser = new JLabel("Usuario:");
			lblUser.setBounds(33, 24, 55, 20);
			contentPanel.add(lblUser);
		}
		{
			textFieldUser = new JTextField();
			textFieldUser.setBounds(95, 24, 130, 20);
			contentPanel.add(textFieldUser);
			textFieldUser.setColumns(10);
		}
		{
			JLabel lblPass = new JLabel("Clave:");
			lblPass.setBounds(33, 61, 55, 20);
			contentPanel.add(lblPass);
		}
		{
			textFieldPass = new JPasswordField();
			textFieldPass.setBounds(95, 61, 130, 20);
			contentPanel.add(textFieldPass);
			textFieldPass.setColumns(10);
		}
		
		JLabel lblError = new JLabel();
		lblError.setText("<html> <font color='red'> Error de usuario y/o clave </font></html>");
		lblError.setVisible(false);
		lblError.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblError.setBounds(60, 92, 154, 20);
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
						//setVisible(false);
						// TODO: LOGUEAR
						
						MysqlConnect conection = MysqlConnect.getDbCon();
						/*try {
							conection.saveUser(); // UNICA VEZ
						} catch (NoSuchAlgorithmException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						} catch (InvalidKeySpecException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}*/
						boolean result = conection.validateUser(textFieldUser.getText(),textFieldPass.getText());
						if (result){
							// Llamada al MAINFORM
							 new MainForm();
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
