package persona;

import java.awt.Frame;

import javax.swing.JFrame;

import db.MysqlConnect;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import com.digitalpersona.onetouch.ui.swing.sample.UISupport.MainForm;

import java.awt.Toolkit;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class PersonaABM extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField textFieldApellido;
	private JTextField textFieldNombre;
	private JTextField textFieldNro;	
	private MysqlConnect conection;
	private JTextField textFieldProfesion;
	private JComboBox<String> cbTipoDoc;
	private JLabel lblError;
	private String errorApellido = "El apellido es obligatorio";
	private String errorNombre = "El mombre es obligatorio";
	private String errorTipo = "El tipo de documento es obligatorio";
	private String errorNro = "El número de documento es obligatorio";
	
	
	/**
	 * Create the Dialog
	 */
	public PersonaABM(){
		super("Crear Persona");
		setIconImage(Toolkit.getDefaultToolkit().getImage("C:\\Users\\Pablo\\git\\huella\\Huella\\dist\\person_icon.png"));
		
		setState(Frame.NORMAL);
		setResizable(false);
		
		setBounds(100, 100, 350, 330);
		setLocationRelativeTo(null);
		getContentPane().setLayout(null);
		
		// traigo la conexion
		conection = MysqlConnect.getDbCon();
		
		JLabel lblApellido = new JLabel("Apellido:");
		lblApellido.setBounds(36, 28, 100, 20);
		getContentPane().add(lblApellido);
		
		JLabel lblNombre = new JLabel("Nombre:");
		lblNombre.setBounds(36, 59, 100, 20);
		getContentPane().add(lblNombre);
		
		JLabel lblTipoDoc = new JLabel("Tipo Documento:");
		lblTipoDoc.setBounds(36, 90, 120, 20);
		getContentPane().add(lblTipoDoc);
		
		JLabel lblNumero = new JLabel("N\u00FAmero:");
		lblNumero.setBounds(36, 121, 100, 20);
		getContentPane().add(lblNumero);
		
		cbTipoDoc = new JComboBox<String>();
		cbTipoDoc.setBounds(176, 90, 128, 20);
		this.cargarCombo();
		getContentPane().add(cbTipoDoc);
		
		textFieldApellido = new JTextField();
		textFieldApellido.setBounds(176, 28, 128, 20);
		getContentPane().add(textFieldApellido);
		textFieldApellido.setColumns(10);
		
		textFieldNombre = new JTextField();
		textFieldNombre.setColumns(10);
		textFieldNombre.setBounds(176, 59, 128, 20);
		getContentPane().add(textFieldNombre);
		
		textFieldNro = new JTextField();
		textFieldNro.setColumns(10);
		textFieldNro.setBounds(176, 121, 128, 20);
		getContentPane().add(textFieldNro);
		
		JButton btnGuardar = new JButton("Guardar");
		btnGuardar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				if (!hayVacios()){					
					if (conection.existePersona(textFieldNro.getText(),cbTipoDoc.getSelectedItem().toString())){						
						JOptionPane.showMessageDialog(getContentPane(),"Existe el tipo y número de documento.","Error",JOptionPane.ERROR_MESSAGE);
					}
					else{
						int idPersona = conection.guardarPersona(textFieldApellido.getText(),textFieldNombre.getText(), textFieldProfesion.getText(), cbTipoDoc.getSelectedItem().toString(), textFieldNro.getText());
						JOptionPane.showMessageDialog(getContentPane(),"Persona registrada","Persona guardada",JOptionPane.INFORMATION_MESSAGE);
						MainForm formulario = new MainForm();
						formulario.setIdPersonaAEnrolar(idPersona);
						dispose();
					}
				}
				
			}
		});
		btnGuardar.setBounds(63, 239, 100, 23);
		getContentPane().add(btnGuardar);
		
		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.setBounds(184, 239, 100, 23);
		getContentPane().add(btnCancelar);
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();           //End Dialog
			}
		});
		
		JLabel lblProfesion = new JLabel("Profesi\u00F3n:");
		lblProfesion.setBounds(36, 160, 100, 20);
		getContentPane().add(lblProfesion);
		
		textFieldProfesion = new JTextField();
		textFieldProfesion.setColumns(10);
		textFieldProfesion.setBounds(176, 160, 128, 20);
		getContentPane().add(textFieldProfesion);
		
		lblError = new JLabel("",SwingConstants.CENTER);
		lblError.setBounds(23, 202, 300, 20);
		getContentPane().add(lblError);
		lblError.setVisible(false);
		setVisible(true);
	}

	private void cargarCombo() {
		
		ResultSet resultado = conection.getTipoDocumentos();
		try {
			while (resultado.next()){
				cbTipoDoc.addItem(resultado.getString(3));
			}
		} catch (SQLException e) {			
			e.printStackTrace();
		}
	}
	
	private boolean hayVacios(){
		//TODO: Alinear el texto 
		boolean resultado = false;
		if (textFieldApellido.getText().length() == 0){
			lblError.setText("<html> <font color='red'> " + errorApellido + " </font></html>");
			lblError.setVisible(true);
			resultado = true;
		}else if (textFieldNombre.getText().length() == 0){
			lblError.setText("<html> <font color='red'> " + errorNombre + " </font></html>");
			lblError.setVisible(true);
			resultado = true;
		}else if (cbTipoDoc.getSelectedItem().toString().isEmpty()){
			lblError.setText("<html> <font color='red'> " + errorTipo + " </font></html>");
			lblError.setVisible(true);
			resultado = true;
		}else if (textFieldNro.getText().length() == 0){
			lblError.setText("<html> <font color='red'> " + errorNro + " </font></html>");
			lblError.setVisible(true);
			resultado = true;
		}
		
		return resultado;
	}
}
