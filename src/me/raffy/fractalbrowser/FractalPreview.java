package me.raffy.fractalbrowser;

import javax.swing.*;
import java.awt.*;

public class FractalPreview extends JPanel {
    private FractalController controller;

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (this.controller != null) {
            this.controller.paint(g);
        }
    }

    public void setController(FractalController c) {
        this.controller = c;
    }

    public FractalController getController() {
        return this.controller;
    }
}
