package me.raffy.fractalbrowser;

import javax.swing.*;
import java.awt.*;

public class FractalPreview extends JPanel {
    private FractalController controller;

    @Override
    protected void paintComponent(Graphics g) {
        // Only process full repaints.
        // If this is not a full repaint, i.e., a menu was just closed, and we need to repaint the area below it,
        // then force a full repaint to lift the burden of responsiveness off of the Controller.
        if (g.getClipBounds().equals(this.getBounds())) {
            super.paintComponent(g);
            if (this.controller != null) {
                this.controller.paint(g);
            }
        } else {
            this.repaint();
        }
    }

    public void setController(FractalController c) {
        this.controller = c;
    }

    public FractalController getController() {
        return this.controller;
    }
}
