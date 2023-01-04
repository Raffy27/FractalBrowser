package me.raffy.fractalbrowser;

import java.awt.*;
import me.raffy.fractalbrowser.math.Complex;

public class MandelbrotFractal extends FractalController {

    private static final double CONVERGENCE_RADIUS = 2;
    private static final double MIN_RE = -2, MAX_RE = 1, MIN_IM = -1, MAX_IM = 1;

    private Complex c = new Complex(0, 0);
    public void paint(Graphics g) {
        //System.out.println("Painting Mandelbrot");

        Rectangle bounds = g.getClipBounds();
        double reStep = (MAX_RE - MIN_RE) / bounds.width;
        double imStep = (MAX_IM - MIN_IM) / bounds.height;
        for (double re = MIN_RE; re < MAX_RE; re += reStep) {
            for (double im = MIN_IM; im < MAX_IM; im += imStep) {
                boolean verdict = diverges(im, re, this.getIterations());
                int x = (int) ((re - MIN_RE) / reStep);
                int y = (int) ((im - MIN_IM) / imStep);
                if (verdict) {
                    drawPixel(g, x, y, Color.BLACK);
                }
            }
        }
    }

    private void drawPixel(Graphics g, int x, int y, Color color) {
        g.setColor(color);
        g.drawLine(x, y, x, y);
    }

    private boolean diverges(double im, double re, int iterations) {
        Complex z = new Complex(0, 0);
        Complex c = new Complex(im, re);
        int i = 0;
        while (z.getNorm() < CONVERGENCE_RADIUS && i < iterations) {
            z = z.mul(z).add(c);
            i++;
        }
        return i != iterations;
    }
}
