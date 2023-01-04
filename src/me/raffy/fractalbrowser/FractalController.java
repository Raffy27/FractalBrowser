package me.raffy.fractalbrowser;

import java.awt.*;

public abstract class FractalController {
    private int iterations;
    private int hueShift;
    public abstract void paint(Graphics g);

    public int getIterations() {
        return iterations;
    }

    public void setIterations(int iterations) {
        this.iterations = iterations;
    }

    public int getHueShift() {
        return hueShift;
    }

    public void setHueShift(int hueShift) {
        this.hueShift = hueShift;
    }
}
