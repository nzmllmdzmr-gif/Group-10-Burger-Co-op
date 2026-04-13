package main;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class MainBgmTest {

    @Test
    public void testMainBgmInstance() {
        //check main bgm is entity
        MainBgm mb = new MainBgm();
        assertNotNull(mb, "MainBgm instance should not be null");
    }

    @Test
    public void testPlayMusicMethod() {
        MainBgm mb = new MainBgm();
        //check if can work
        try {
            mb.playMusic();
        } catch (Exception e) {
            fail("playMusic threw an exception: " + e.getMessage());
        }
    }
}
