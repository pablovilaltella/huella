package persona;

import java.awt.Frame;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JFrame;

import db.MysqlConnect;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JScrollPane;

public class BuscarPersona extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private MysqlConnect conection;
	private JTable table;

	public BuscarPersona(){
		
		super("Buscar Persona");
		//setIconImage(Toolkit.getDefaultToolkit().getImage("C:\\Users\\Pablo\\git\\huella\\Huella\\dist\\person_icon.png"));
		
		setState(Frame.NORMAL);
		setResizable(false);
		
		setBounds(100, 100, 600, 600);
		setLocationRelativeTo(null);
		getContentPane().setLayout(null);

		// traigo la conexion
		conection = MysqlConnect.getDbCon();
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(22, 204, 553, 372);
		getContentPane().add(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		
		
		DefaultTableModel dtm = new DefaultTableModel();
		table.setModel(dtm);
		
		dtm.setColumnIdentifiers(new Object[]{"Id","Apellido","Nombre",});
		
		ResultSet personas = conection.findPersona("pepe", "");
		
		try {
			while (personas.next()){
				dtm.addRow(new Object[]{personas.getInt(1),personas.getString(2), personas.getString(3)});
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		setVisible(true);
	}
}
