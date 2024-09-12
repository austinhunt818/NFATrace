import javax.swing.*;
import java.awt.*;

class NFADisplayPanel extends JPanel {
    private final String[] states = {};
    private final int[][] positions = {};
    private final int[][] transitions = {};

    @Override
    protected void paintComponent(Graphics g) {}

    private void drawState(Graphics2D g2d, String stateLabel, int x, int y) {}

    private void drawTransition(Graphics2D g2d, int[] from, int[] to) {}
}