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
	private MysqlConnect conection;
	private JTextField textFieldProfesion;
	
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
		lblApellido.setBounds(22, 34, 100, 20);
		getContentPane().add(lblApellido);
		
		JLabel lblNombre = new JLabel("Nombre:");
		lblNombre.setBounds(22, 65, 100, 20);
		getContentPane().add(lblNombre);
		
		JLabel lblTipoDoc = new JLabel("Tipo Documento:");
		lblTipoDoc.setBounds(22, 96, 120, 20);
		getContentPane().add(lblTipoDoc);
		
		JLabel lblNumero = new JLabel("N\u00FAmero:");
		lblNumero.setBounds(22, 127, 100, 20);
		getContentPane().add(lblNumero);
		
		JComboBox cbTipoDoc = new JComboBox();
		cbTipoDoc.setBounds(162, 96, 128, 20);
		this.cargarCombo(cbTipoDoc);
		getContentPane().add(cbTipoDoc);
		
		textFieldApellido = new JTextField();
		textFieldApellido.setBounds(162, 34, 128, 20);
		getContentPane().add(textFieldApellido);
		textFieldApellido.setColumns(10);
		
		textFieldNombre = new JTextField();
		textFieldNombre.setColumns(10);
		textFieldNombre.setBounds(162, 65, 128, 20);
		getContentPane().add(textFieldNombre);
		
		JTextField textFieldNro = new JTextField();
		textFieldNro.setColumns(10);
		textFieldNro.setBounds(162, 127, 128, 20);
		getContentPane().add(textFieldNro);
		
		JButton btnGuardar = new JButton("Guardar");
		btnGuardar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (conection.existePersona()){
					System.out.println("Existe la persona ");
				}
				else{
					conection.guardarPersona(textFieldApellido.getText(),textFieldNombre.getText(), textFieldProfesion.getText(), cbTipoDoc.getSelectedItem().toString(), textFieldNro.getText());
				}
				
			}
		});
		btnGuardar.setBounds(46, 209, 100, 23);
		getContentPane().add(btnGuardar);
		
		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.setBounds(167, 209, 100, 23);
		getContentPane().add(btnCancelar);
		
		JLabel lblProfesion = new JLabel("Profesi\u00F3n:");
		lblProfesion.setBounds(22, 166, 100, 20);
		getContentPane().add(lblProfesion);
		
		textFieldProfesion = new JTextField();
		textFieldProfesion.setColumns(10);
		textFieldProfesion.setBounds(162, 166, 128, 20);
		getContentPane().add(textFieldProfesion);
		setVisible(true);
	}

	private void cargarCombo(JComboBox cbTipoDoc) {
		
		ResultSet resultado = conection.getTipoDocumentos();
		try {
			while (resultado.next()){
				cbTipoDoc.addItem(resultado.getString(3));
			}
		} catch (SQLException e) {			
			e.printStackTrace();
		}
	}
}
