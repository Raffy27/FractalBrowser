package me.raffy.fractalbrowser;

import java.awt.*;

public class TreeFractal extends FractalController {
    private double delta = 0.3;
    private double length = 200;
    private double lengthFactor = 0.7;
    private static final int PADDING = 10;
    private static final double ITERATION_FACTOR = 1f / 6;
    public TreeFractal(int iterations, int hueShift) {
        super(iterations, hueShift);
    }
    public void paint(Graphics g) {
        Rectangle bounds = g.getClipBounds();
        g.setColor(Color.WHITE);
        paintBranch(g, 1, Math.PI / 2, new Point(bounds.width / 2, bounds.height - PADDING));
    }

    private void paintBranch(Graphics g, int iteration, double angle, Point start) {
        // Calculate length of branch
        long pxLength = Math.round(length * Math.pow(lengthFactor, iteration));
        // Calculate end coordinates
        int endX = start.x - (int) Math.round(Math.cos(angle) * pxLength);
        int endY = start.y - (int) Math.round(Math.sin(angle) * pxLength);
        g.drawLine(start.x, start.y, endX, endY);

        if (iteration + 1 <= this.getIterations() * ITERATION_FACTOR) {
            Point end = new Point(endX, endY);
            paintBranch(g, iteration + 1, angle - delta, end);
            paintBranch(g, iteration + 1, angle + delta, end);
        }
    }

    public double getDelta() {
        return delta;
    }

    public void setDelta(double delta) {
        this.delta = delta;
    }
}
