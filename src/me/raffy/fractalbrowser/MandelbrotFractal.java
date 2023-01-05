package me.raffy.fractalbrowser;

import java.awt.*;
import java.awt.geom.Rectangle2D;

import me.raffy.fractalbrowser.math.Complex;

public class MandelbrotFractal extends FractalController {
    //private static final double MIN_RE = -2, MAX_RE = 1, MIN_IM = -1, MAX_IM = 1;
    public static final Rectangle2D.Double START_RECT = new Rectangle2D.Double(-2, -1, 3, 2);
    private static final double CONVERGENCE_RADIUS = 2;
    private static final int HUE_RANGE = 200;
    private static final double ZOOM_AMOUNT = 0.9;
    private Rectangle2D.Double dimensions;
    private Rectangle bounds;

    public MandelbrotFractal(Rectangle2D.Double dimensions, int iterations, int hueShift) {
        super(iterations, hueShift);
        this.dimensions = dimensions;
        System.out.println(dimensions);
    }

    public void zoom(int direction, Point center) {
        if (bounds != null) {
            // Transform the coordinates of the point
            Complex c = new Complex(
                    ((double) center.y / bounds.height) * dimensions.height + dimensions.y,
                    ((double) center.x / bounds.width) * dimensions.width + dimensions.x
            );
            double factor = direction == -1 ? ZOOM_AMOUNT : (1 / ZOOM_AMOUNT);
            double newWidth = dimensions.width * factor, newHeight = dimensions.height * factor;
            dimensions = new Rectangle2D.Double(
                    c.getRe() - newWidth * ((double) center.x / bounds.width),
                    c.getIm() - newHeight * ((double) center.y / bounds.height),
                    newWidth,
                    newHeight
            );
        }
    }

    public void paint(Graphics g) {
        //System.out.println("Painting Mandelbrot");

        bounds = g.getClipBounds();
        double reStep = dimensions.width / bounds.width;
        double imStep = dimensions.height / bounds.height;
        for (double re = dimensions.x; re < dimensions.x + dimensions.width; re += reStep) {
            for (double im = dimensions.y; im < dimensions.y + dimensions.height; im += imStep) {
                int x = (int) ((re - dimensions.x) / reStep);
                int y = (int) ((im - dimensions.y) / imStep);
                int result = diverges(im, re, this.getIterations());
                if (result == this.getIterations()) {
                    drawPixel(g, x, y, Color.BLACK);
                } else {
                    // TODO: implement hue shift
                    int hue = result * HUE_RANGE / this.getIterations();
                    Color color = Color.getHSBColor(hue / 360f, 1f, .8f);
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
