package persona;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import db.MysqlConnect;

public class UsuarioABM extends JDialog {
	
	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JTextField textFieldUser;
	private JTextField textFieldPass;
	public static String usuarioConectado;
	private JTextField passwordField;
	private MysqlConnect conection;
	
	public UsuarioABM(String prmUsuario) {
		
		usuarioConectado = prmUsuario;
		setTitle("Usuario");
		setResizable(false);
		setBounds(100, 100, 280, 240);
		setLocationRelativeTo(null);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		setIconImage(getToolkit().getImage(getClass().getResource("/user_icon.png")));
		
		// traigo la conexion
		conection = MysqlConnect.getDbCon();
		JLabel lblUser = new JLabel("Usuario:");
		lblUser.setBounds(12, 24, 70, 20);
		contentPanel.add(lblUser);

		textFieldUser = new JTextField();
		textFieldUser.setBounds(111, 24, 130, 20);
		contentPanel.add(textFieldUser);
		textFieldUser.setColumns(10);
		if (usuarioConectado != null){
			textFieldUser.setText(usuarioConectado);
			textFieldUser.setEnabled(false);
		}
//		if (!usuarioConectado.equals(null)){
//			textFieldUser.setText(usuarioConectado);
//			textFieldUser.setEnabled(false);
//		}

		JLabel lblPass = new JLabel("Clave:");
		lblPass.setBounds(12, 61, 70, 20);
		contentPanel.add(lblPass);

		textFieldPass = new JPasswordField();
		textFieldPass.setBounds(111, 61, 130, 20);
		contentPanel.add(textFieldPass);
		textFieldPass.setColumns(10);
				
		JLabel lblRepetirClave = new JLabel("Repetir clave:");
		lblRepetirClave.setBounds(12, 96, 96, 20);
		contentPanel.add(lblRepetirClave);
		
		passwordField = new JPasswordField();
		passwordField.setColumns(10);
		passwordField.setBounds(111, 96, 130, 20);
		contentPanel.add(passwordField);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setBorder(null);
			buttonPane.setLayout(new FlowLayout(FlowLayout.CENTER));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("Guardar");
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						// si es null es nuevo usuario
						if (usuarioConectado == null){							
							if (existeUsuario()){
								JOptionPane.showMessageDialog(getContentPane(),"Ya existe el usuario, seleccione otro","Error",JOptionPane.ERROR_MESSAGE);
							}
							else{
								if (passIguales()){								
									if (tamanioUsuario()){
										if (tamanioClave()){
											MysqlConnect conection = MysqlConnect.getDbCon();
											try {
												conection.saveUser(textFieldUser.getText(),textFieldPass.getText()); // UNICA VEZ
											} catch (NoSuchAlgorithmException e1) {							
												e1.printStackTrace();
											} catch (InvalidKeySpecException e1) {
												e1.printStackTrace();
											}
											JOptionPane.showMessageDialog(getContentPane(),"Usuario registrado","Usuario guardado",JOptionPane.INFORMATION_MESSAGE);
											dispose();
										} // else tamaño clave
										else{
											JOptionPane.showMessageDialog(getContentPane(),"La clave debe ser mayor a 4 letras","Error",JOptionPane.ERROR_MESSAGE);										
										}
									} // else tamaño usuario
									else{
										JOptionPane.showMessageDialog(getContentPane(),"El usuario debe ser mayor a 4 letras","Error",JOptionPane.ERROR_MESSAGE);
									}								
								}// else pass iguales
								else{
									JOptionPane.showMessageDialog(getContentPane(),"Las claves deben ser iguales","Error",JOptionPane.ERROR_MESSAGE);
								}
							}
						} // Usuario no null
						else{
							if (passIguales()){							
								if (tamanioClave()){
									MysqlConnect conection = MysqlConnect.getDbCon();
									try {
										conection.updateClave(textFieldUser.getText(),textFieldPass.getText()); // UNICA VEZ
									} catch (NoSuchAlgorithmException e1) {							
										e1.printStackTrace();
									} catch (InvalidKeySpecException e1) {
										e1.printStackTrace();
									}
									JOptionPane.showMessageDialog(getContentPane(),"Clave actualizada","Usuario guardado",JOptionPane.INFORMATION_MESSAGE);
									dispose();
								} // else tamaño clave
								else{
									JOptionPane.showMessageDialog(getContentPane(),"La clave debe ser mayor a 4 letras","Error",JOptionPane.ERROR_MESSAGE);										
								}															
							} // else pass iguales
							else{
								JOptionPane.showMessageDialog(getContentPane(),"Las claves deben ser iguales","Error",JOptionPane.ERROR_MESSAGE);
							}
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
		setVisible(true);
	}

	/**
	 * Devuelve true si son iguales ambos password
	 * @return boolean
	 */
	private boolean passIguales() {
		boolean resultado = false;
		if (textFieldPass.getText().equals(passwordField.getText())){
			resultado = true;			
		}
		return resultado;
	}
	
	/**
	 * Devuelve si existe el usuario o no
	 * @return boolean
	 */
	private boolean existeUsuario(){
		boolean resultado = false;
		ResultSet usuario = conection.getUser(textFieldUser.getText());
		try {
			resultado = usuario.first();
		} catch (SQLException e) {
			e.printStackTrace();
		}
			
		return resultado;
	}
	
	private boolean tamanioUsuario(){
		boolean resultado = false;
		if (textFieldUser.getText().length() > 4)
			resultado = true;
		return resultado;
	}
	
	private boolean tamanioClave(){		
		boolean resultado = false;
		if (textFieldPass.getText().length() > 4)
			resultado = true;
		return resultado;
	}
}
