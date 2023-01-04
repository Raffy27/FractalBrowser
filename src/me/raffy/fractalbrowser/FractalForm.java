package me.raffy.fractalbrowser;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.event.*;

public class FractalForm {
    private FractalPreview panelFractal;
    private JPanel panelMain;
    private JButton btnPaint;
    private JComboBox cbxFractalType;
    private JSlider sldIterations;

    public FractalForm() {
        panelFractal.setController(new MandelbrotFractal());
        cbxFractalType.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent itemEvent) {
                if (itemEvent.getStateChange() == ItemEvent.SELECTED) {

                    switch (cbxFractalType.getSelectedIndex()) {
                        case 0:
                            System.out.println("Mandelbrot selected");
                            panelFractal.setController(new MandelbrotFractal());
                            break;
                        case 1:
                            System.out.println("Tree selected");
                            break;
                        case 2:
                            System.out.println("Sierpinski selected");
                            break;
                    }
                }
            }
        });
        btnPaint.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                panelFractal.repaint();
            }
        });
        sldIterations.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent changeEvent) {
                int value = sldIterations.getValue();
                panelFractal.getController().setIterations(value);
                panelFractal.repaint();
            }
        });
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Fractal Browser");
        frame.setContentPane(new FractalForm().panelMain);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
