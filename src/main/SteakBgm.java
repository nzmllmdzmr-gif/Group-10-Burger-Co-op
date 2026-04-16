package main;

import javax.sound.sampled.*;
import java.net.URL;

public class SteakBgm {
    private Clip sizzleClip;

    public void startSizzle() {
        try {
            if (sizzleClip == null) {
                URL url = getClass().getResource("/res/SteakBgm.wav");
                AudioInputStream ais = AudioSystem.getAudioInputStream(url);
                sizzleClip = AudioSystem.getClip();
                sizzleClip.open(ais);
            }
            //no replay,the bgm is long enough
            if (!sizzleClip.isRunning()) {
                sizzleClip.setFramePosition(0);
                sizzleClip.start();
            }
        } catch (Exception e) {
            System.out.println("Steak sizzle Error");
        }
    }

    public void stopSizzle() {
        if (sizzleClip != null && sizzleClip.isRunning()) {
            sizzleClip.stop();
        }
    }
}