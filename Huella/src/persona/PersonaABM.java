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
	private String errorNroDire = "El número en domicilio es obligatorio";
	private String errorCalle = "La calle es obligatoria";
	private JTextField textFieldCalle;
	private JTextField textFieldNroDire;
	private JTextField textFieldPiso;
	private JTextField textFieldDpto;
	
	
	/**
	 * Create the Dialog
	 */
	public PersonaABM(){
		super("Crear Persona");
		setIconImage(Toolkit.getDefaultToolkit().getImage("C:\\Users\\Pablo\\git\\huella\\Huella\\dist\\person_icon.png"));
		
		setState(Frame.NORMAL);
		setResizable(false);
		
		setBounds(100, 100, 350, 400);
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
						int idPersona = conection.guardarPersona(textFieldApellido.getText(),textFieldNombre.getText(), textFieldProfesion.getText(), cbTipoDoc.getSelectedItem().toString(), textFieldNro.getText(),textFieldCalle.getText(),textFieldNroDire.getText(),textFieldDpto.getText(), textFieldPiso.getText());
						JOptionPane.showMessageDialog(getContentPane(),"Persona registrada","Persona guardada",JOptionPane.INFORMATION_MESSAGE);
						MainForm formulario = new MainForm();
						formulario.setIdPersonaAEnrolar(idPersona);
						dispose();
					}
				}
				
			}
		});
		btnGuardar.setBounds(62, 320, 100, 23);
		getContentPane().add(btnGuardar);
		
		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.setBounds(183, 320, 100, 23);
		getContentPane().add(btnCancelar);
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();           //End Dialog
			}
		});
		
		JLabel lblProfesion = new JLabel("Profesi\u00F3n:");
		lblProfesion.setBounds(36, 152, 100, 20);
		getContentPane().add(lblProfesion);
		
		textFieldProfesion = new JTextField();
		textFieldProfesion.setColumns(10);
		textFieldProfesion.setBounds(176, 152, 128, 20);
		getContentPane().add(textFieldProfesion);
		
		lblError = new JLabel("",SwingConstants.CENTER);
		lblError.setBounds(22, 289, 300, 20);
		getContentPane().add(lblError);
		
		JLabel lblCalle = new JLabel("Calle:");
		lblCalle.setBounds(36, 200, 55, 20);
		getContentPane().add(lblCalle);
		
		textFieldCalle = new JTextField();
		textFieldCalle.setBounds(90, 200, 214, 20);
		getContentPane().add(textFieldCalle);
		textFieldCalle.setColumns(10);
		
		JLabel lblNro = new JLabel("N\u00FAmero:");
		lblNro.setBounds(36, 233, 55, 16);
		getContentPane().add(lblNro);
		
		textFieldNroDire = new JTextField();
		textFieldNroDire.setBounds(90, 231, 86, 20);
		getContentPane().add(textFieldNroDire);
		textFieldNroDire.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("Piso:");
		lblNewLabel_1.setBounds(193, 234, 35, 16);
		getContentPane().add(lblNewLabel_1);
		
		textFieldPiso = new JTextField();
		textFieldPiso.setBounds(227, 231, 77, 20);
		getContentPane().add(textFieldPiso);
		textFieldPiso.setColumns(10);
		
		JLabel lblDpto = new JLabel("Departamento:");
		lblDpto.setBounds(36, 268, 90, 16);
		getContentPane().add(lblDpto);
		
		textFieldDpto = new JTextField();
		textFieldDpto.setBounds(123, 266, 70, 20);
		getContentPane().add(textFieldDpto);
		textFieldDpto.setColumns(10);
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
		}else if (textFieldCalle.getText().length() == 0){
			lblError.setText("<html> <font color='red'> " + errorCalle + " </font></html>");
			lblError.setVisible(true);
			resultado = true;
		}else if (textFieldNroDire.getText().length() == 0){
			lblError.setText("<html> <font color='red'> " + errorNroDire + " </font></html>");
			lblError.setVisible(true);
			resultado = true;
		}
		return resultado;
	}
}
