package me.raffy.fractalbrowser;

import java.awt.*;
import me.raffy.fractalbrowser.math.Complex;

public class MandelbrotFractal extends FractalController {

    private static final double CONVERGENCE_RADIUS = 2;
    private static final double MIN_RE = -2, MAX_RE = 1, MIN_IM = -1, MAX_IM = 1;

    public void paint(Graphics g) {
        //System.out.println("Painting Mandelbrot");

        Rectangle bounds = g.getClipBounds();
        double reStep = (MAX_RE - MIN_RE) / bounds.width;
        double imStep = (MAX_IM - MIN_IM) / bounds.height;
        for (double re = MIN_RE; re < MAX_RE; re += reStep) {
            for (double im = MIN_IM; im < MAX_IM; im += imStep) {
                int x = (int) ((re - MIN_RE) / reStep);
                int y = (int) ((im - MIN_IM) / imStep);
                int result = diverges(im, re, this.getIterations());
                if (result == this.getIterations()) {
                    drawPixel(g, x, y, Color.BLACK);
                } else {
                    int hue = result * 200 / this.getIterations();
                    Color color = Color.getHSBColor(hue / 359f, 1f, .8f);
                    drawPixel(g, x, y, color);
                }
            }
        }
    }

    private void drawPixel(Graphics g, int x, int y, Color color) {
        g.setColor(color);
        g.drawLine(x, y, x, y);
    }

    private int diverges(double im, double re, int iterations) {
        Complex z = new Complex(0, 0);
        Complex c = new Complex(im, re);
        int i = 0;
        while (z.getNorm() < CONVERGENCE_RADIUS && i < iterations) {
            z = z.mul(z).add(c);
            i++;
        }
        return i;
    }
}
