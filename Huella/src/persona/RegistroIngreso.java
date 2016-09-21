package persona;

import java.awt.Frame;

import javax.swing.JFrame;

import db.MysqlConnect;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class RegistroIngreso extends JFrame{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JLabel lblDatoApellido;
	private JLabel lblDatoNombre;
	private JLabel lblDatoTipoDoc;
	private JLabel lblDatoNro;
	private JLabel lblDatoProfesion;
	private JLabel lblDatoId;

	public RegistroIngreso (){
		super("Registro de Ingreso");
		
		setState(Frame.NORMAL);
		setResizable(false);
		
		setBounds(100, 100, 350, 330);
		setLocationRelativeTo(null);
		getContentPane().setLayout(null);
		
		// traigo la conexion
		MysqlConnect conection = MysqlConnect.getDbCon();
		
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
		
		lblDatoApellido = new JLabel("");
		lblDatoApellido.setBounds(178, 90, 140, 20);
		getContentPane().add(lblDatoApellido);
		
		lblDatoNombre = new JLabel("");
		lblDatoNombre.setBounds(178, 121, 140, 20);
		getContentPane().add(lblDatoNombre);
		
		lblDatoTipoDoc = new JLabel("");
		lblDatoTipoDoc.setBounds(178, 152, 140, 20);
		getContentPane().add(lblDatoTipoDoc);
		
		lblDatoNro = new JLabel("");
		lblDatoNro.setBounds(178, 183, 140, 20);
		getContentPane().add(lblDatoNro);
		
		lblDatoProfesion = new JLabel("");
		lblDatoProfesion.setBounds(178, 214, 140, 20);
		getContentPane().add(lblDatoProfesion);
		
		JButton btnBuscarHuella = new JButton("Buscar Huella");
		btnBuscarHuella.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// TODO: Buscar por la huella
			}
		});
		btnBuscarHuella.setBounds(25, 30, 120, 25);
		getContentPane().add(btnBuscarHuella);
		
		JButton btnNewButton = new JButton("Detalle Persona");
		btnNewButton.setIcon(null);
		btnNewButton.setBounds(102, 254, 140, 25);
		getContentPane().add(btnNewButton);
		
		JButton btnGuardarIngreso = new JButton("Guardar Ingreso");
		btnGuardarIngreso.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println(lblDatoApellido.getText());
				if (existeHuella()){
					//TODO: GUARDO EL INGRESO
					System.out.println("GUARDO EL INGRESO");
					
				}
				else{
					JOptionPane.showMessageDialog(getContentPane(),"No se ha recuperado ninguna persona","Error",JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		btnGuardarIngreso.setBounds(185, 30, 130, 25);
		getContentPane().add(btnGuardarIngreso);
		
		JLabel lblId = new JLabel("Id:");
		lblId.setBounds(25, 66, 54, 20);
		getContentPane().add(lblId);
		
		lblDatoId = new JLabel("");
		lblDatoId.setBounds(178, 69, 140, 20);
		getContentPane().add(lblDatoId);
		
		setVisible(true);
	}

	protected boolean existeHuella() {
		boolean retorno = false;
		if (lblDatoId.getText() != ""){
			retorno = true;
		}
		
		return retorno;
	}
}
