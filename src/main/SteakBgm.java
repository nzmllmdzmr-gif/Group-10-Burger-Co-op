package main;

import javax.sound.sampled.*;
import java.net.URL;

public class SteakBgm {
    private Clip sizzleClip;

    public void startSizzle() {
        try {
            if (sizzleClip == null) {
                URL url = getClass().getResource("/main/SteakBgm.wav"); 
                if (url == null) {
                
                    System.out.println("Error");
                    return;
                }
                AudioInputStream ais = AudioSystem.getAudioInputStream(url);
                sizzleClip = AudioSystem.getClip();
                sizzleClip.open(ais);
            }
            
            if (!sizzleClip.isRunning()) {
                sizzleClip.setFramePosition(0);
                sizzleClip.start();
            }
        } catch (Exception e) {
            e.printStackTrace(); 
        }
        
    }
    public void stopSizzle() {
        
        if (sizzleClip != null && sizzleClip.isRunning()) {
            sizzleClip.stop();
        }
    }
}