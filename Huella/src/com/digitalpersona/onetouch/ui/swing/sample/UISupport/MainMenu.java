package com.digitalpersona.onetouch.ui.swing.sample.UISupport;

import java.awt.EventQueue;
import java.awt.Frame;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.WindowConstants;

import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JPanel;
import javax.swing.JLabel;

import persona.BuscarPersona;
import persona.PersonaABM;
import persona.RegistroMovimiento;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Toolkit;

public class MainMenu extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JLabel lblUsuarioConectado;


	public void setUsuarioConectado(String lblUsuarioConectado) {
		this.lblUsuarioConectado.setText(lblUsuarioConectado);
	}

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainMenu dialog = new MainMenu();
					dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
					dialog.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the dialog.
	 */
	public MainMenu() {
		
		super("Sistema de Control de ingreso");
		setIconImage(Toolkit.getDefaultToolkit().getImage("C:\\Users\\Pablo\\git\\huella\\Huella\\dist\\huellaBinario.png"));
		setState(Frame.NORMAL);
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setResizable(false);
		
		setBounds(100, 100, 500, 450);
		setLocationRelativeTo(null);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnInicio = new JMenu("Inicio");
		menuBar.add(mnInicio);
		
		JMenuItem mntmNewMenuItem = new JMenuItem("Nueva Persona");
		
		ActionListener nuevaPersona = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	new PersonaABM();
            }   
        };
        mntmNewMenuItem.addActionListener(nuevaPersona);        
        mnInicio.add(mntmNewMenuItem);
        
        JMenuItem mntmMenuItemBuscarPersona = new JMenuItem("Buscar Persona");
        ActionListener buscarPersona = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	new BuscarPersona();
            }   
        };
        
        mntmMenuItemBuscarPersona.addActionListener(buscarPersona);
        mnInicio.add(mntmMenuItemBuscarPersona);
        
		JMenu mnReportes = new JMenu("Reportes");
		menuBar.add(mnReportes);
		
		JMenuItem mntmListado = new JMenuItem("Listado");
		mnReportes.add(mntmListado);
		getContentPane().setLayout(null);
		
		JPanel panel = new JPanel();
		getContentPane().add(panel);
		panel.setLayout(null);
		panel.setBounds(2, 372, 490, 24);
		
		JLabel lblUsuario = new JLabel("Usuario:");
		lblUsuario.setBounds(10, 4, 150, 15);
		panel.add(lblUsuario);
		
		lblUsuarioConectado = new JLabel();		
		lblUsuarioConectado.setBounds(176, 4, 150, 15);
		panel.add(lblUsuarioConectado);
		
		JButton btnEnrolar = new JButton("Enrolar");
		btnEnrolar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				new PersonaABM();
			}
		});
		btnEnrolar.setBounds(75, 110, 130, 49);
		getContentPane().add(btnEnrolar);
		
		JButton btnEgreso = new JButton("Egreso");
		btnEgreso.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// Registro la salida
				new RegistroMovimiento("S");
			}
		});
		btnEgreso.setBounds(290, 245, 130, 49);
		getContentPane().add(btnEgreso);
		
		JButton btnIngreso = new JButton("Ingreso");
		btnIngreso.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// Registro la entrada
				new RegistroMovimiento("E");
			}
		});
		btnIngreso.setBounds(290, 110, 130, 49);
		getContentPane().add(btnIngreso);
		
		JButton btnBuscarPersona = new JButton("Buscar Persona");
		btnBuscarPersona.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				new BuscarPersona();				
			}
		});
		btnBuscarPersona.setBounds(75, 245, 130, 49);
		getContentPane().add(btnBuscarPersona);

		setVisible(true);
	}

	private static void addPopup(Component component, final JPopupMenu popup) {
		component.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}
			public void mouseReleased(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}
			private void showMenu(MouseEvent e) {
				popup.show(e.getComponent(), e.getX(), e.getY());
			}
		});
	}
}
