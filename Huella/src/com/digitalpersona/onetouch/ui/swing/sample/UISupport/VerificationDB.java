package com.digitalpersona.onetouch.ui.swing.sample.UISupport;

import com.digitalpersona.onetouch.*;
import com.digitalpersona.onetouch.verification.*;

import db.MysqlConnect;

import com.digitalpersona.onetouch.ui.swing.DPFPVerificationControl;
import com.digitalpersona.onetouch.ui.swing.DPFPVerificationEvent;
import com.digitalpersona.onetouch.ui.swing.DPFPVerificationListener;
import com.digitalpersona.onetouch.ui.swing.DPFPVerificationVetoException;

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Enrollment control test
 */
public class VerificationDB extends JDialog
{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
//	private EnumMap<DPFPFingerIndex, DPFPTemplate> templates;
//    private int farRequested;
    private int farAchieved;
    private DPFPVerificationControl verificationControl;
    private boolean matched;
    private MysqlConnect conection;
    public int idHuella;
    public int idPersona;
    
    public int getIdHuella() {
		return idHuella;
	}

	public void setIdHuella(int idHuella) {
		this.idHuella = idHuella;
	}

	public int getIdPersona() {
		return idPersona;
	}

	public void setIdPersona(int idPersona) {
		this.idPersona = idPersona;
	}
    
    static final String FAR_PROPERTY = "FAR";
    static final String MATCHED_PROPERTY = "Matched";
    static final int FAR = 21474;

    public VerificationDB(Frame owner) {
		super(owner, true);
//		this.templates = templates;
//		this.farRequested = farRequested;
		// traigo la conexion
		conection = MysqlConnect.getDbCon();
		setTitle("Verificar Huella");
    	setResizable(false);
    	// Traigo todas las huellas
    	

		verificationControl = new DPFPVerificationControl();
		verificationControl.addVerificationListener(new DPFPVerificationListener()
		{
			public void captureCompleted(DPFPVerificationEvent e) throws DPFPVerificationVetoException
			{
				DPFPVerification verification = 
					DPFPGlobal.getVerificationFactory().createVerification(FAR);
				e.setStopCapture(false);	// we want to continue capture until the dialog is closed
				int bestFAR = DPFPVerification.PROBABILITY_ONE;
				boolean hasMatch = false;
				
				/**  Por la forma que esta hecho hay que traer todas las huellas e ir verificando
				* y hacer el corte si la encuentra (VER SI HAY OTRA FORMA MAS EFECTIVA)
				**/
				ResultSet huellas = conection.getAllFingers();
				try {
					while (huellas.next()) {
						System.out.println("Huella de: " + huellas.getInt(2) + " \n");
						// Leo el bytte de la base
						byte[] huellaByte = huellas.getBytes(3);
						DPFPTemplate huellaTemplate = DPFPGlobal.getTemplateFactory().createTemplate();
						// Lo deserializo
						huellaTemplate.deserialize(huellaByte);
						// uso el verify de la api
						DPFPVerificationResult result = verification.verify(e.getFeatureSet(), huellaTemplate);
						e.setMatched(result.isVerified());		// report matching status
						bestFAR = Math.min(bestFAR, result.getFalseAcceptRate());
						if (e.getMatched()) {
							System.out.println("Existe la huella");
							setIdHuella(huellas.getInt(1));
							setIdPersona(huellas.getInt(2));
							hasMatch = true;
							setVisible(false);
							break;
						}else{
							System.out.println("NO COINCIDE");
//							break;
						}
					}
				} catch (SQLException e1) {					
					e1.printStackTrace();
				}
				
				setMatched(hasMatch);
				setFAR(bestFAR);
			}
		});

		getContentPane().setLayout(new BorderLayout());

		JButton closeButton = new JButton("Close");
		closeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false); 		//End Dialog
			}
		});

		JPanel center = new JPanel();
		center.add(verificationControl);
		center.add(new JLabel("Para verificar su identidad, coloque su dedo en el lector de huellas"));

		JPanel bottom = new JPanel();
		bottom.add(closeButton);

		add(center, BorderLayout.CENTER);
		add(bottom, BorderLayout.PAGE_END);

		pack();
        setLocationRelativeTo(null);         
    }

    public int getFAR() {
    	return farAchieved;
    }
    
    protected void setFAR(int far) {
		final int old = getFAR();
		farAchieved = far;
		firePropertyChange(FAR_PROPERTY, old, getFAR());
    }

    public boolean getMatched() {
    	return matched;
    }
    
    protected void setMatched(boolean matched) {
		final boolean old = getMatched();
		this.matched = matched;
   		firePropertyChange(MATCHED_PROPERTY, old, getMatched());
     }
    
    /**
     * Shows or hides this component depending on the value of parameter
     * <code>b</code>.
     *
     * @param b if <code>true</code>, shows this component;
     *          otherwise, hides this component
     * @see #isVisible
     * @since JDK1.1
     */
    public void setVisible(boolean b) {
        if (b) {
            matched = false;
            verificationControl.start();
        } else {
            if (!matched)
                verificationControl.stop();
        }
        super.setVisible(b);
    }
}