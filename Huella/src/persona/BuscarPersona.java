package persona;

import java.awt.Frame;
import java.awt.Image;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;

import db.MysqlConnect;
import utilities.Exporter;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.File;
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
	private JLabel lblProfesin;
	private JTextField textFieldProfesion;
	private JButton btnExportar;

	public BuscarPersona(){
		
		super("Buscar Persona");
		setIconImage(getToolkit().getImage(getClass().getResource("/person_icon.png")));
		
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
		lblApellido.setBounds(135, 20, 50, 20);
		getContentPane().add(lblApellido);
		
		JLabel lblNombre = new JLabel("Nombre::");
		lblNombre.setBounds(414, 20, 51, 20);
		getContentPane().add(lblNombre);
		
		comboBoxTD = new JComboBox<String>();
		comboBoxTD.setBounds(123, 63, 50, 20);
		this.cargarCombo();
		getContentPane().add(comboBoxTD);
		
		textFieldApellido = new JTextField();
		textFieldApellido.setBounds(198, 20, 150, 20);
		getContentPane().add(textFieldApellido);
		textFieldApellido.setColumns(10);
		
		textFieldNombre = new JTextField();
		textFieldNombre.setColumns(10);
		textFieldNombre.setBounds(475, 20, 150, 20);
		getContentPane().add(textFieldNombre);

		dtm.setColumnIdentifiers(new Object[]{"Id","Apellido","Nombre","Tipo doc", "Número", "Profesión"});
		
		JButton btnBuscar = new JButton("Buscar");
		btnBuscar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				DefaultTableModel dm = (DefaultTableModel)table.getModel();
				dm.getDataVector().removeAllElements();
				 	
				ResultSet personas = conection.findPersona(textFieldId.getText(), textFieldApellido.getText(), textFieldNombre.getText(),textFieldNumero.getText(),comboBoxTD.getSelectedItem().toString(),textFieldProfesion.getText());
				
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
		btnBuscar.setBounds(655, 13, 105, 23);
		getContentPane().add(btnBuscar);
		
		textFieldNumero = new JTextField();
		textFieldNumero.setBounds(245, 63, 150, 20);
		getContentPane().add(textFieldNumero);
		textFieldNumero.setColumns(10);
		
		lblTipoDoc = new JLabel("Tipo Documento:");
		lblTipoDoc.setBounds(22, 63, 101, 20);
		getContentPane().add(lblTipoDoc);
		
		lblNumero = new JLabel("N\u00FAmero:");
		lblNumero.setBounds(192, 63, 51, 20);
		getContentPane().add(lblNumero);
		
		JLabel lblId = new JLabel("Id:");
		lblId.setBounds(22, 20, 20, 20);
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
		btnCerrar.setBounds(655, 43, 105, 23);
		getContentPane().add(btnCerrar);
		
		lblProfesin = new JLabel("Profesi\u00F3n:");
		lblProfesin.setBounds(414, 63, 60, 20);
		getContentPane().add(lblProfesin);
		
		textFieldProfesion = new JTextField();
		textFieldProfesion.setColumns(10);
		textFieldProfesion.setBounds(475, 63, 150, 20);
		getContentPane().add(textFieldProfesion);
		
		btnExportar = new JButton("Exportar");
		Image imagen = getToolkit().getImage(getClass().getResource("/excel_icon.png"));
		btnExportar.setIcon(new ImageIcon(imagen));
		btnExportar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				exportarAExcel(arg0);
			}
		});
		btnExportar.setBounds(655, 73, 105, 23);
		getContentPane().add(btnExportar);
		
		
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
	
	/**
	 * Exportar a Excel
	 * @param evt
	 */
	private void exportarAExcel(java.awt.event.ActionEvent evt) {                                            

        if (this.table.getRowCount()==0) {
            JOptionPane.showMessageDialog (null, "No hay datos en la tabla para exportar.","Atención",
                JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        JFileChooser chooser=new JFileChooser();
        FileNameExtensionFilter filter=new FileNameExtensionFilter("Archivos de excel","xls");
        chooser.setFileFilter(filter);
        chooser.setDialogTitle("Guardar archivo");
        chooser.setMultiSelectionEnabled(false);
        chooser.setAcceptAllFileFilterUsed(false);
        
        if (chooser.showSaveDialog(null)==JFileChooser.APPROVE_OPTION){
            List<JTable> tb=new ArrayList<>();
            List<String>nom=new ArrayList<>();
            tb.add(table);
            nom.add("Personas");
            String file=chooser.getSelectedFile().toString().concat(".xls");
            try {
                Exporter e= new Exporter(new File(file),tb, nom);
                if (e.export()) {
                    JOptionPane.showMessageDialog(null, "Los datos fueron exportados a excel.","BCO",
                        JOptionPane.INFORMATION_MESSAGE);

                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null,"Hubo un error"+ex.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
            }
        }
	}
	
}
