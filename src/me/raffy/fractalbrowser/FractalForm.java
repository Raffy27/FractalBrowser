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
    private JSlider sldHue;

    public FractalForm() {
        setupMandelbrot();
        cbxFractalType.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent itemEvent) {
                if (itemEvent.getStateChange() == ItemEvent.SELECTED) {

                    switch (cbxFractalType.getSelectedIndex()) {
                        case 0:
                            System.out.println("Mandelbrot selected");
                            setupMandelbrot();
                            panelFractal.repaint();
                            break;
                        case 1:
                            System.out.println("Tree selected");
                            panelFractal.repaint();
                            setupTree();
                            break;
                        case 2:
                            System.out.println("Sierpinski selected");
                            setupSierpinksi();
                            panelFractal.repaint();
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
        sldHue.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent changeEvent) {
                int value = sldHue.getValue();
                panelFractal.getController().setHueShift(value);
                panelFractal.repaint();
            }
        });
    }

    private void setupMandelbrot() {
        FractalController controller = new MandelbrotFractal();
        controller.setIterations(sldIterations.getValue());
        panelFractal.setController(controller);
    }

    private void setupTree() {
        FractalController controller = new TreeFractal();
        controller.setIterations(sldIterations.getValue());
        panelFractal.setController(controller);
    }

    private void setupSierpinksi() {

    }

    private JMenuBar createTopMenu() {
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        fileMenu.setMnemonic('F');
        menuBar.add(fileMenu);
        JMenuItem exportItem = new JMenuItem("Export", 'E');
        fileMenu.add(exportItem);

        return menuBar;
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Fractal Browser");
        FractalForm form = new FractalForm();
        frame.setContentPane(form.panelMain);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setJMenuBar(form.createTopMenu());
        frame.pack();
        frame.setVisible(true);
    }
}
