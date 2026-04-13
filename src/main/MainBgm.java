package main;

import javax.sound.sampled.*;
import java.net.URL;

public class MainBgm {
    private Clip bgmClip;
    private Clip sizzleClip;
//main bgm for the whole game游戏主背景音
    public void playMusic() {
        try {
            URL url = getClass().getResource("/mainbgm.wav");
            AudioInputStream ais = AudioSystem.getAudioInputStream(url);
            bgmClip = AudioSystem.getClip();
            bgmClip.open(ais);
            bgmClip.loop(Clip.LOOP_CONTINUOUSLY);
            bgmClip.start();
        } catch (Exception e) {
            System.out.println("BGM Error");
        }
    }
}
