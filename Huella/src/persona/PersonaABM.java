package persona;

import java.awt.Frame;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

import db.MysqlConnect;

import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JTextField;
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
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setResizable(false);
		
		setBounds(100, 100, 500, 450);
		setLocationRelativeTo(null);
		getContentPane().setLayout(null);
		
		// traigo la conexion
		conection = MysqlConnect.getDbCon();
		
		JLabel lblApellido = new JLabel("Apellido:");
		lblApellido.setBounds(46, 32, 100, 20);
		getContentPane().add(lblApellido);
		
		JLabel lblNombre = new JLabel("Nombre:");
		lblNombre.setBounds(46, 63, 100, 20);
		getContentPane().add(lblNombre);
		
		JLabel lblTipoDoc = new JLabel("Tipo Documento:");
		lblTipoDoc.setBounds(46, 94, 120, 20);
		getContentPane().add(lblTipoDoc);
		
		JLabel lblNumero = new JLabel("N\u00FAmero:");
		lblNumero.setBounds(46, 125, 100, 20);
		getContentPane().add(lblNumero);
		
		cbTipoDoc = new JComboBox<String>();
		cbTipoDoc.setBounds(186, 94, 128, 20);
		this.cargarCombo();
		getContentPane().add(cbTipoDoc);
		
		textFieldApellido = new JTextField();
		textFieldApellido.setBounds(186, 32, 128, 20);
		getContentPane().add(textFieldApellido);
		textFieldApellido.setColumns(10);
		
		textFieldNombre = new JTextField();
		textFieldNombre.setColumns(10);
		textFieldNombre.setBounds(186, 63, 128, 20);
		getContentPane().add(textFieldNombre);
		
		textFieldNro = new JTextField();
		textFieldNro.setColumns(10);
		textFieldNro.setBounds(186, 125, 128, 20);
		getContentPane().add(textFieldNro);
		
		JButton btnGuardar = new JButton("Guardar");
		btnGuardar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				if (!hayVacios()){					
					if (conection.existePersona()){
						System.out.println("Existe la persona ");
					}
					else{
						conection.guardarPersona(textFieldApellido.getText(),textFieldNombre.getText(), textFieldProfesion.getText(), cbTipoDoc.getSelectedItem().toString(), textFieldNro.getText());
					}
				}
				
			}
		});
		btnGuardar.setBounds(73, 243, 100, 23);
		getContentPane().add(btnGuardar);
		
		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.setBounds(194, 243, 100, 23);
		getContentPane().add(btnCancelar);
		
		JLabel lblProfesion = new JLabel("Profesi\u00F3n:");
		lblProfesion.setBounds(46, 164, 100, 20);
		getContentPane().add(lblProfesion);
		
		textFieldProfesion = new JTextField();
		textFieldProfesion.setColumns(10);
		textFieldProfesion.setBounds(186, 164, 128, 20);
		getContentPane().add(textFieldProfesion);
		
		lblError = new JLabel("");
		lblError.setBounds(30, 196, 300, 20);
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
		boolean resultado = false;
		if (textFieldApellido.getText().length() == 0){
			lblError.setText(errorApellido);
			lblError.setVisible(true);
			resultado = true;
		}else if (textFieldNombre.getText().length() == 0){
			lblError.setText(errorNombre);
			lblError.setVisible(true);
			resultado = true;
		}else if (cbTipoDoc.getSelectedItem().toString().isEmpty()){
			lblError.setText(errorTipo);
			lblError.setVisible(true);
			resultado = true;
		}else if (textFieldNro.getText().length() == 0){
			lblError.setText(errorNro);
			lblError.setVisible(true);
			resultado = true;
		}
		
		return resultado;
	}
}
