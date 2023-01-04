package me.raffy.fractalbrowser;

import java.awt.*;

public class MandelbrotFractal extends FractalController {

    private int num = 0;
    public void paint(Graphics g) {
        System.out.println("Painting Mandelbrot");
        num++;
        String str = Integer.toString(num);
        g.drawString(str, 20, 30);
    }
}
