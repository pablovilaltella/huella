package com.digitalpersona.onetouch.ui.swing.sample.UISupport;

import com.digitalpersona.onetouch.*;
import com.digitalpersona.onetouch.ui.swing.*;
import db.MysqlConnect;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

/**
 * Enrollment control test 
 */
public class EnrollmentDialog
	extends JDialog
{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private EnumMap<DPFPFingerIndex, DPFPTemplate> templates;

    public EnrollmentDialog(Frame owner, int maxCount, final String reasonToFail, EnumMap<DPFPFingerIndex, DPFPTemplate> templates) {
        super (owner, true);
        this.templates = templates;

        setTitle("Fingerprint Enrollment");

        DPFPEnrollmentControl enrollmentControl = new DPFPEnrollmentControl();

        EnumSet<DPFPFingerIndex> fingers = EnumSet.noneOf(DPFPFingerIndex.class);
        fingers.addAll(templates.keySet());
        enrollmentControl.setEnrolledFingers(fingers);
        enrollmentControl.setMaxEnrollFingerCount(maxCount);

        enrollmentControl.addEnrollmentListener(new DPFPEnrollmentListener()
        {
            public void fingerDeleted(DPFPEnrollmentEvent e) throws DPFPEnrollmentVetoException {
                if (reasonToFail != null) {
                    throw new DPFPEnrollmentVetoException(reasonToFail);
                } else {
                    EnrollmentDialog.this.templates.remove(e.getFingerIndex());
                }
            }

            public void fingerEnrolled(DPFPEnrollmentEvent e) throws DPFPEnrollmentVetoException {
                if (reasonToFail != null) {
//                  e.setStopCapture(false);
                    throw new DPFPEnrollmentVetoException(reasonToFail);
                } else{
                    EnrollmentDialog.this.templates.put(e.getFingerIndex(), e.getTemplate());
                    System.out.println("Enrolo?");
                    
                    //TODO:: ACA GUARDO DIRECTAMENTE ????
                    DPFPTemplate dedo = e.getTemplate();
                    DPFPFingerIndex indiceDedo = e.getFingerIndex();
                    
                    System.out.println(indiceDedo);
                    byte[] dedoSerialized = null;
                    
                    if(dedo != null){
                    	dedoSerialized = dedo.serialize();                       
                    }
                    MysqlConnect conection = MysqlConnect.getDbCon();
                    conection.saveFinger(dedoSerialized, indiceDedo.toString());                    
                }                
            }
            
            /*public void fingerToSave(OnEnroll e){
            	
            }*/
        });

		getContentPane().setLayout(new BorderLayout());

		JButton closeButton = new JButton("Close");
        closeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                setVisible(false);                //End Dialog
            }
        });

		JPanel bottom = new JPanel();
		bottom.add(closeButton);
		add(enrollmentControl, BorderLayout.CENTER);
		add(bottom, BorderLayout.PAGE_END);

		pack();
        setLocationRelativeTo(null);         
   }
}