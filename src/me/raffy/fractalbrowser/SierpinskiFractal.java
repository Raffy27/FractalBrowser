package me.raffy.fractalbrowser;

import java.awt.*;

public class SierpinskiFractal extends FractalController {
    private static final int PADDING = 10;

    public SierpinskiFractal() {
        super();
    }
    public void paint(Graphics g) {
        Rectangle bounds = g.getClipBounds();
        Point top = new Point(bounds.width / 2, bounds.y + PADDING);
        Point left = new Point(bounds.width / 6, bounds.height - PADDING);
        Point right = new Point(5 * bounds.width / 6, bounds.height - PADDING);
        drawTriangle(g, top, left, right, getColorForIteration(1));
        paintSierpinski(g, top, left, right, 1);
    }

    private Color getColorForIteration(int iteration) {
        float brightness = iteration * .92f / (this.getIterations() / 7f);
        float hue = ((267f + this.getHueShift()) / 360) % 360;
        return Color.getHSBColor(hue, .98f, brightness);
    }

    private void paintSierpinski(Graphics g, Point a, Point b, Point c, int iteration) {
        Point midpointAB = new Point((a.x + b.x) / 2, (a.y + b.y) / 2);
        Point midpointAC = new Point((a.x + c.x) / 2, (a.y + c.y) / 2);
        Point midpointBC = new Point((c.x + b.x) / 2, (c.y + b.y) / 2);
        drawTriangle(g, midpointAB, midpointAC, midpointBC, getColorForIteration(iteration));
        if (iteration + 1 < this.getIterations() / 7) {
            paintSierpinski(g, a, midpointAB, midpointAC, iteration + 1);
            paintSierpinski(g, midpointAB, b, midpointBC, iteration + 1);
            paintSierpinski(g, midpointAC, midpointBC, c, iteration + 1);
        }
    }

    private void drawTriangle(Graphics g, Point a, Point b, Point c, Color color) {
        int[] x = { a.x, b.x, c.x };
        int[] y = { a.y, b.y, c.y };
        g.setColor(color);
        g.drawPolygon(x, y, 3);
    }
}
