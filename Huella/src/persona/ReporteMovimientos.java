package persona;

import java.awt.Frame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.Format;
import java.text.SimpleDateFormat;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import db.MysqlConnect;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.UtilDateModel;

import java.util.Date;
import java.util.Properties; 

public class ReporteMovimientos extends JFrame {

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
	private JDatePickerImpl datePickerFI;
	private JDatePickerImpl datePickerFF;
	
	public ReporteMovimientos(){
		
		super("Movimientos");
		//setIconImage(Toolkit.getDefaultToolkit().getImage("C:\\Users\\Pablo\\git\\huella\\Huella\\dist\\person_icon.png"));
		
		setState(Frame.NORMAL);
		setResizable(false);

		// traigo la conexion
		conection = MysqlConnect.getDbCon();
		
		setBounds(100, 100, 800, 600);
		setLocationRelativeTo(null);
		getContentPane().setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(22, 137, 750, 423);
		getContentPane().add(scrollPane);
		
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
		
		textFieldNumero = new JTextField();
		textFieldNumero.setBounds(251, 63, 150, 20);
		getContentPane().add(textFieldNumero);
		textFieldNumero.setColumns(10);
		
		lblTipoDoc = new JLabel("Tipo Documento:");
		lblTipoDoc.setBounds(22, 63, 101, 20);
		getContentPane().add(lblTipoDoc);
		
		lblNumero = new JLabel("N\u00FAmero:");
		lblNumero.setBounds(198, 63, 51, 20);
		getContentPane().add(lblNumero);
		
		JLabel lblId = new JLabel("Id:");
		lblId.setBounds(22, 20, 20, 20);
		getContentPane().add(lblId);
		
		textFieldId = new JTextField();
		textFieldId.setColumns(10);
		textFieldId.setBounds(47, 20, 40, 20);
		getContentPane().add(textFieldId);
		
		JLabel lblFechaIni =new JLabel("Fecha Inicio: ");
		lblFechaIni.setBounds(100,350,100,20);
	    getContentPane().add(lblFechaIni);
		
	    // FECHAS
	    UtilDateModel modelFI = new UtilDateModel();
	    modelFI.setSelected(true);
	    Properties p = new Properties();
	    p.put("text.today", "Hoy");
	    p.put("text.month", "Mes");
	    p.put("text.year", "Año");
        JDatePanelImpl datePanel = new JDatePanelImpl(modelFI, p);
        
        datePickerFI = new JDatePickerImpl(datePanel, null);
        datePickerFI.setBounds(122,102,156,23);
        getContentPane().add(datePickerFI);
        Date selectedDate = (Date) datePickerFI.getModel().getValue();
        Format formatter = new SimpleDateFormat("dd/MM/yyyy");
		String stringDate = formatter.format(selectedDate);
		datePickerFI.getJFormattedTextField().setText(stringDate);
		
//        System.out.println(stringDate);
        datePickerFI.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {

				Date selectedDate = (Date) datePickerFI.getModel().getValue();
//				System.out.println(selectedDate);
				Format formatter = new SimpleDateFormat("dd/MM/yyyy");
				String stringDate = null;
				if (selectedDate != null){
					stringDate = formatter.format(selectedDate);					
				}
				datePickerFI.getJFormattedTextField().setText(stringDate);
			}
		});

                
        UtilDateModel modelFF = new UtilDateModel();
	    modelFF.setSelected(true);
        JDatePanelImpl datePanelFF = new JDatePanelImpl(modelFF, p);
        
        datePickerFF = new JDatePickerImpl(datePanelFF, null);
        datePickerFF.setBounds(400,102,156,23);
        getContentPane().add(datePickerFF);
        Date selectedDateFF = (Date) datePickerFF.getModel().getValue();
		String stringDateFF = formatter.format(selectedDateFF);
		datePickerFF.getJFormattedTextField().setText(stringDateFF);
		
        datePickerFF.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {

				Date selectedDate = (Date) datePickerFF.getModel().getValue();
//				System.out.println(selectedDate);
				Format formatter = new SimpleDateFormat("dd/MM/yyyy");
				String stringDate = null;
				if (selectedDate != null){
					stringDate = formatter.format(selectedDate);					
				}
				datePickerFF.getJFormattedTextField().setText(stringDate);
				
			}
		});

        
		table = new JTable();
		scrollPane.setViewportView(table);
		
		DefaultTableModel dtm = new DefaultTableModel();
		table.setModel(dtm);
		
		dtm.setColumnIdentifiers(new Object[]{"Id","Apellido","Nombre","Tipo doc", "Número", "Dedo", "Movimiento", "Fecha"});
		
		JButton btnBuscar = new JButton("Buscar");
		btnBuscar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				if (verificar()){
				
					DefaultTableModel dm = (DefaultTableModel)table.getModel();
					dm.getDataVector().removeAllElements();
					
					Date selectedDateFechaFin = (Date) datePickerFF.getModel().getValue();
					Date selectedDateFechaIni = (Date) datePickerFI.getModel().getValue();
			
					ResultSet movimientos = conection.getMovimientos(textFieldId.getText(), textFieldApellido.getText(), textFieldNombre.getText(),textFieldNumero.getText(),comboBoxTD.getSelectedItem().toString(), selectedDateFechaIni, selectedDateFechaFin);
					
					try {
						while (movimientos.next()){
							dtm.addRow(new Object[]{movimientos.getInt(1),movimientos.getString(2), movimientos.getString(3),movimientos.getString(4),movimientos.getString(5),movimientos.getString(6),movimientos.getString(7),movimientos.getString(8)});
						}
					} catch (SQLException e) {
						e.printStackTrace();
					}
					dm.fireTableDataChanged();
				}
			}
		});
		btnBuscar.setBounds(655, 19, 89, 23);
		getContentPane().add(btnBuscar);
		
		JButton btnCerrar = new JButton("Cerrar");
		btnCerrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose();
			}
		});
		btnCerrar.setBounds(655, 62, 89, 23);
		getContentPane().add(btnCerrar);
		
		JLabel lblFechaInicio = new JLabel("Fecha Inicio:");
		lblFechaInicio.setBounds(22, 102, 79, 20);
		getContentPane().add(lblFechaInicio);
		
		JLabel lblFechaFin = new JLabel("Fecha Fin:");
		lblFechaFin.setBounds(322, 102, 79, 20);
		getContentPane().add(lblFechaFin);
		
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
	 * Verifica si las fechas son correctas
	 * @return boolean
	 */
	private boolean verificar(){
		boolean verifico = true;
		Date fechaIni = (Date) datePickerFI.getModel().getValue();
		Date fechaFin = (Date) datePickerFF.getModel().getValue();
//		System.out.println("Verificar fecha Ini = " + fechaIni);
//		System.out.println("Verificar fecha FIN = " + fechaFin);
		if (fechaFin.before(fechaIni)){
			JOptionPane.showMessageDialog(getContentPane(),"La Fecha fin es menor a la fecha de inicio.","Error",JOptionPane.ERROR_MESSAGE);
			verifico = false;
		}
		return verifico;
	}
}
