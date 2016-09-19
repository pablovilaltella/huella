package persona;

import java.awt.Frame;

import javax.swing.JFrame;

import db.MysqlConnect;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.ImageIcon;

public class RegistroIngreso extends JFrame{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public RegistroIngreso (){
		super("Registro de Ingreso");
		
		setState(Frame.NORMAL);
		setResizable(false);
		
		setBounds(100, 100, 350, 330);
		setLocationRelativeTo(null);
		getContentPane().setLayout(null);
		
		JLabel lblApellido = new JLabel("Apellido:");
		lblApellido.setBounds(25, 90, 100, 20);
		getContentPane().add(lblApellido);
		
		JLabel lblNombre = new JLabel("Nombre:");
		lblNombre.setBounds(25, 121, 100, 20);
		getContentPane().add(lblNombre);
		
		JLabel lblTipoDoc = new JLabel("Tipo Documento:");
		lblTipoDoc.setBounds(25, 152, 120, 20);
		getContentPane().add(lblTipoDoc);
		
		JLabel lblNro = new JLabel("N\u00FAmero:");
		lblNro.setBounds(25, 183, 100, 20);
		getContentPane().add(lblNro);
		
		JLabel lblProfesion = new JLabel("Profesi\u00F3n:");
		lblProfesion.setBounds(25, 214, 100, 20);
		getContentPane().add(lblProfesion);
		
		JLabel lblDatoApellido = new JLabel("");
		lblDatoApellido.setBounds(178, 90, 140, 20);
		getContentPane().add(lblDatoApellido);
		
		JLabel lblDatoNombre = new JLabel("");
		lblDatoNombre.setBounds(178, 121, 140, 20);
		getContentPane().add(lblDatoNombre);
		
		JLabel lblDatoTipoDoc = new JLabel("");
		lblDatoTipoDoc.setBounds(178, 152, 140, 20);
		getContentPane().add(lblDatoTipoDoc);
		
		JLabel lblDatoNro = new JLabel("");
		lblDatoNro.setBounds(178, 183, 140, 20);
		getContentPane().add(lblDatoNro);
		
		JLabel lblDatoProfesion = new JLabel("");
		lblDatoProfesion.setBounds(178, 214, 140, 20);
		getContentPane().add(lblDatoProfesion);
		
		JButton btnBuscarHuella = new JButton("Buscar Huella");
		btnBuscarHuella.setBounds(122, 29, 100, 25);
		getContentPane().add(btnBuscarHuella);
		
		JButton btnNewButton = new JButton("Detalle Persona");
		btnNewButton.setIcon(null);
		btnNewButton.setBounds(112, 254, 120, 25);
		getContentPane().add(btnNewButton);
		
		// traigo la conexion
		MysqlConnect conection = MysqlConnect.getDbCon();
		
	}
}
