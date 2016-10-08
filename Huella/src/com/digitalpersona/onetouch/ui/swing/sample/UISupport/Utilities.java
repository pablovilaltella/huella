package com.digitalpersona.onetouch.ui.swing.sample.UISupport;

import java.util.EnumMap;

import com.digitalpersona.onetouch.DPFPFingerIndex;

class Utilities {

    private static final EnumMap<DPFPFingerIndex, String> fingerNames;
    static {
    	fingerNames = new EnumMap<DPFPFingerIndex, String>(DPFPFingerIndex.class);
    	fingerNames.put(DPFPFingerIndex.LEFT_PINKY,	  "me�ique izquierdo");
    	fingerNames.put(DPFPFingerIndex.LEFT_RING,    "anular izquierdo");
    	fingerNames.put(DPFPFingerIndex.LEFT_MIDDLE,  "medio izquierdo");
    	fingerNames.put(DPFPFingerIndex.LEFT_INDEX,   "�ndice izquierdo");
    	fingerNames.put(DPFPFingerIndex.LEFT_THUMB,   "pulgar izquierdo");
    	
    	fingerNames.put(DPFPFingerIndex.RIGHT_PINKY,  "me�ique derecho");
    	fingerNames.put(DPFPFingerIndex.RIGHT_RING,   "anular derecho");
    	fingerNames.put(DPFPFingerIndex.RIGHT_MIDDLE, "medio derecho");
    	fingerNames.put(DPFPFingerIndex.RIGHT_INDEX,  "�ndice derecho");
    	fingerNames.put(DPFPFingerIndex.RIGHT_THUMB,  "pulgar derecho");
    }

    public static String fingerName(DPFPFingerIndex finger) {
    	return fingerNames.get(finger); 
    }
    public static String fingerprintName(DPFPFingerIndex finger) {
    	return fingerNames.get(finger) + " fingerprint"; 
    }
    
}
