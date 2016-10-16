package com.digitalpersona.onetouch.ui.swing.sample.UISupport;

import java.awt.EventQueue;
import java.awt.Frame;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.WindowConstants;

import javax.swing.JPanel;
import javax.swing.JLabel;

import persona.BuscarPersona;
import persona.PersonaABM;
import persona.RegistroMovimiento;
import persona.ReporteMovimientos;

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
		
		// ENROLAR
		JMenuItem mntmNewMenuItem = new JMenuItem("Enrolar");		
		ActionListener nuevaPersona = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	new PersonaABM();
            }   
        };
        mntmNewMenuItem.addActionListener(nuevaPersona);        
        mnInicio.add(mntmNewMenuItem);
        
        // BUSCAR PERSONA
        JMenuItem mntmMenuItemBuscarPersona = new JMenuItem("Buscar Persona");
        ActionListener buscarPersona = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	new BuscarPersona();
            }   
        };
        
        mntmMenuItemBuscarPersona.addActionListener(buscarPersona);
        mnInicio.add(mntmMenuItemBuscarPersona);
        
        
        // INGRESO
        JMenuItem mntmMenuItemIngreso = new JMenuItem("Ingreso");
        ActionListener ingreso = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	// Registro el ingreso
            	new RegistroMovimiento("E");
            }   
        };
        
        mntmMenuItemIngreso.addActionListener(ingreso);
        mnInicio.add(mntmMenuItemIngreso);
        
        
        // EGRESO
        JMenuItem mntmMenuItemEgreso = new JMenuItem("Egreso");
        ActionListener egreso = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	// Registro el ingreso
            	new RegistroMovimiento("S");
            }   
        };
        
        mntmMenuItemEgreso.addActionListener(egreso);
        mnInicio.add(mntmMenuItemEgreso);
        
		JMenu mnReportes = new JMenu("Reportes");
		menuBar.add(mnReportes);
		
		JMenuItem mntmListado = new JMenuItem("Movimentos");
		
		ActionListener listadoListener = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	// LLAMO AL LISTADO
            	new ReporteMovimientos();
            }   
        };
        
        mntmListado.addActionListener(listadoListener);		
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
		btnEnrolar.setBounds(75, 102, 130, 49);
		getContentPane().add(btnEnrolar);
		
		JButton btnEgreso = new JButton("Egreso");
		btnEgreso.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// Registro la salida
				new RegistroMovimiento("S");
			}
		});
		btnEgreso.setBounds(290, 237, 130, 49);
		getContentPane().add(btnEgreso);
		
		JButton btnIngreso = new JButton("Ingreso");
		btnIngreso.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// Registro la entrada
				new RegistroMovimiento("E");
			}
		});
		btnIngreso.setBounds(290, 102, 130, 49);
		getContentPane().add(btnIngreso);
		
		JButton btnBuscarPersona = new JButton("Buscar Persona");
		btnBuscarPersona.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				new BuscarPersona();				
			}
		});
		btnBuscarPersona.setBounds(75, 237, 130, 49);
		getContentPane().add(btnBuscarPersona);

		setVisible(true);
	}
}
