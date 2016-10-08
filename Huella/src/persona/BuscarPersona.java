package persona;

import java.awt.Frame;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JFrame;

import db.MysqlConnect;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class BuscarPersona extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private MysqlConnect conection;
	private JTable table;
	private JComboBox<String> comboBoxTD;
	private JTextField textFieldApellido;
	private JTextField textFieldNombre;
	private JTextField textFieldNumero;
	private JLabel lblTipoDoc;
	private JLabel lblNumero;
	private JTextField textFieldId;

	public BuscarPersona(){
		
		super("Buscar Persona");
		//setIconImage(Toolkit.getDefaultToolkit().getImage("C:\\Users\\Pablo\\git\\huella\\Huella\\dist\\person_icon.png"));
		
		setState(Frame.NORMAL);
		setResizable(false);
		
		setBounds(100, 100, 800, 600);
		setLocationRelativeTo(null);
		getContentPane().setLayout(null);

		// traigo la conexion
		conection = MysqlConnect.getDbCon();
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(22, 114, 750, 446);
		getContentPane().add(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		
		DefaultTableModel dtm = new DefaultTableModel();
		table.setModel(dtm);
		
		JLabel lblApellido = new JLabel("Apellido:");
		lblApellido.setBounds(114, 20, 46, 20);
		getContentPane().add(lblApellido);
		
		JLabel lblNombre = new JLabel("Nombre::");
		lblNombre.setBounds(350, 20, 51, 20);
		getContentPane().add(lblNombre);
		
		comboBoxTD = new JComboBox<String>();
		comboBoxTD.setBounds(152, 63, 128, 20);
		this.cargarCombo();
		getContentPane().add(comboBoxTD);
		
		textFieldApellido = new JTextField();
		textFieldApellido.setBounds(168, 20, 170, 20);
		getContentPane().add(textFieldApellido);
		textFieldApellido.setColumns(10);
		
		textFieldNombre = new JTextField();
		textFieldNombre.setColumns(10);
		textFieldNombre.setBounds(409, 20, 170, 20);
		getContentPane().add(textFieldNombre);

		dtm.setColumnIdentifiers(new Object[]{"Id","Apellido","Nombre","Tipo doc", "Número", "Profesión"});
		
		JButton btnBuscar = new JButton("Buscar");
		btnBuscar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				DefaultTableModel dm = (DefaultTableModel)table.getModel();
				dm.getDataVector().removeAllElements();
				 	
				ResultSet personas = conection.findPersona(textFieldId.getText(), textFieldApellido.getText(), textFieldNombre.getText(),textFieldNumero.getText(),comboBoxTD.getSelectedItem().toString());
				
				try {
					while (personas.next()){
						dtm.addRow(new Object[]{personas.getInt(1),personas.getString(2), personas.getString(3),personas.getString(4),personas.getString(5),personas.getString(6)});
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
				dm.fireTableDataChanged();
			}
		});
		btnBuscar.setBounds(631, 19, 89, 23);
		getContentPane().add(btnBuscar);
		
		textFieldNumero = new JTextField();
		textFieldNumero.setBounds(409, 63, 170, 20);
		getContentPane().add(textFieldNumero);
		textFieldNumero.setColumns(10);
		
		lblTipoDoc = new JLabel("Tipo Documento:");
		lblTipoDoc.setBounds(26, 63, 101, 20);
		getContentPane().add(lblTipoDoc);
		
		lblNumero = new JLabel("N\u00FAmero:");
		lblNumero.setBounds(350, 63, 51, 20);
		getContentPane().add(lblNumero);
		
		JLabel lblId = new JLabel("Id:");
		lblId.setBounds(26, 20, 20, 20);
		getContentPane().add(lblId);
		
		textFieldId = new JTextField();
		textFieldId.setColumns(10);
		textFieldId.setBounds(47, 20, 40, 20);
		getContentPane().add(textFieldId);
		
		JButton btnCerrar = new JButton("Cerrar");
		btnCerrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose();
			}
		});
		btnCerrar.setBounds(631, 62, 89, 23);
		getContentPane().add(btnCerrar);
		
		
		setVisible(true);
	}
	
	/**
	 * Cargo los tipo de documentos en el combo
	 */
	private void cargarCombo() {
		
		ResultSet resultado = conection.getTipoDocumentos();
		try {
			while (resultado.next()){
				comboBoxTD.addItem(resultado.getString(3));
			}
		} catch (SQLException e) {			
			e.printStackTrace();
		}
	}
}
