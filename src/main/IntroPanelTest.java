package main;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import javax.swing.JFrame;

public class IntroPanelTest {

    @Test
    public void testIntroPanelCanBeCreated() {
        JFrame frame = new JFrame();
        IntroPanel introPanel = new IntroPanel(frame);

        assertNotNull(introPanel);
    }
}