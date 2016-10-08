package com.digitalpersona.onetouch.ui.swing.sample.UISupport;

import java.util.EnumMap;

import com.digitalpersona.onetouch.DPFPFingerIndex;

class Utilities {

    private static final EnumMap<DPFPFingerIndex, String> fingerNames;
    static {
    	fingerNames = new EnumMap<DPFPFingerIndex, String>(DPFPFingerIndex.class);
    	fingerNames.put(DPFPFingerIndex.LEFT_PINKY,	  "meñique izquierdo");
    	fingerNames.put(DPFPFingerIndex.LEFT_RING,    "anular izquierdo");
    	fingerNames.put(DPFPFingerIndex.LEFT_MIDDLE,  "medio izquierdo");
    	fingerNames.put(DPFPFingerIndex.LEFT_INDEX,   "índice izquierdo");
    	fingerNames.put(DPFPFingerIndex.LEFT_THUMB,   "pulgar izquierdo");
    	
    	fingerNames.put(DPFPFingerIndex.RIGHT_PINKY,  "meñique derecho");
    	fingerNames.put(DPFPFingerIndex.RIGHT_RING,   "anular derecho");
    	fingerNames.put(DPFPFingerIndex.RIGHT_MIDDLE, "medio derecho");
    	fingerNames.put(DPFPFingerIndex.RIGHT_INDEX,  "índice derecho");
    	fingerNames.put(DPFPFingerIndex.RIGHT_THUMB,  "pulgar derecho");
    }

    public static String fingerName(DPFPFingerIndex finger) {
    	return fingerNames.get(finger); 
    }
    public static String fingerprintName(DPFPFingerIndex finger) {
    	return fingerNames.get(finger) + " fingerprint"; 
    }
    
}
