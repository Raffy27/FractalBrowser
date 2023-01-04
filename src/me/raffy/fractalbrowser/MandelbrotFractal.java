package me.raffy.fractalbrowser;

import java.awt.*;
import me.raffy.fractalbrowser.math.Complex;

public class MandelbrotFractal extends FractalController {

    private static final double CONVERGENCE_RADIUS = 2;
    private static final double MIN_RE = -2, MAX_RE = 1, MIN_IM = -1, MAX_IM = 1;

    private Complex c = new Complex(0, 0);
    public void paint(Graphics g) {
        System.out.println("Painting Mandelbrot");
        c = c.add(new Complex(-1, 1));
        String str = c.toString();
        g.drawString(str, 20, 30);
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
