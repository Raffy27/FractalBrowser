package me.raffy.fractalbrowser;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URI;

public class FractalForm {
    private FractalPreview panelFractal;
    private JPanel panelMain;
    private JButton btnPaint;
    private JComboBox cbxFractalType;
    private JSlider sldIterations;
    private JSlider sldHue;
    private JSlider sldDelta;
    private JSpinner spnLength;
    private JSlider sldLengthFactor;
    private JPanel panelSpecial;
    private JSpinner spnReMin;
    private JSpinner spnReMax;
    private JSpinner spnImMin;
    private JSpinner spnImMax;
    private Point dragStart;
    private boolean mouseDown;

    public FractalForm() {
        setupMandelbrot();
        spnLength.setModel(new SpinnerNumberModel(200d, 0.0, 1000.0, 1.0));
        spnReMin.setModel(new SpinnerNumberModel(MandelbrotFractal.START_RECT.getMinX(), -10.0, 10.0, 0.1));
        spnReMax.setModel(new SpinnerNumberModel(MandelbrotFractal.START_RECT.getMaxX(), -10.0, 10.0, 0.1));
        spnImMin.setModel(new SpinnerNumberModel(MandelbrotFractal.START_RECT.getMinY(), -10.0, 10.0, 0.1));
        spnImMax.setModel(new SpinnerNumberModel(MandelbrotFractal.START_RECT.getMaxY(), -10.0, 10.0, 0.1));

        ChangeListener coordsChange = (ChangeEvent event) -> {
            MandelbrotFractal fractal = (MandelbrotFractal) panelFractal.getController();
            fractal.setDimensions(new Rectangle2D.Double(
                    (double) spnReMin.getValue(),
                    (double) spnImMin.getValue(),
                    (double) spnReMax.getValue() - (double) spnReMin.getValue(),
                    (double) spnImMax.getValue() - (double) spnImMin.getValue()
            ));
            panelFractal.repaint();
        };
        spnReMin.addChangeListener(coordsChange);
        spnReMax.addChangeListener(coordsChange);
        spnImMin.addChangeListener(coordsChange);
        spnImMax.addChangeListener(coordsChange);
        cbxFractalType.addItemListener(itemEvent -> {
            if (itemEvent.getStateChange() == ItemEvent.SELECTED) {
                CardLayout layout = (CardLayout) panelSpecial.getLayout();
                switch (cbxFractalType.getSelectedIndex()) {
                    case 0:
                        System.out.println("Mandelbrot selected");
                        setupMandelbrot();
                        layout.show(panelSpecial, "MandelbrotCard");
                        panelFractal.repaint();
                        break;
                    case 1:
                        System.out.println("Tree selected");
                        setupTree();
                        layout.show(panelSpecial, "TreeCard");
                        panelFractal.repaint();
                        break;
                    case 2:
                        System.out.println("Sierpinski selected");
                        setupSierpinski();
                        layout.show(panelSpecial, "EmptyCard");
                        panelFractal.repaint();
                        break;
                }
            }
        });
        btnPaint.addActionListener(actionEvent -> panelFractal.repaint());
        sldIterations.addChangeListener(changeEvent -> {
            int value = sldIterations.getValue();
            panelFractal.getController().setIterations(value);
            panelFractal.repaint();
        });
        sldHue.addChangeListener(changeEvent -> {
            int value = sldHue.getValue();
            panelFractal.getController().setHueShift(value);
            panelFractal.repaint();
        });
        panelFractal.addMouseWheelListener(mouseWheelEvent -> {
            if (cbxFractalType.getSelectedIndex() == 0) {
                MandelbrotFractal fractal = (MandelbrotFractal) panelFractal.getController();
                fractal.zoom(mouseWheelEvent.getWheelRotation(), mouseWheelEvent.getPoint());
                updateCoordsSpinners();
                panelFractal.repaint();
            }
        });
        panelFractal.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent mouseEvent) {
                super.mouseDragged(mouseEvent);
                if (mouseDown && cbxFractalType.getSelectedIndex() == 0) {
                    MandelbrotFractal fractal = (MandelbrotFractal) panelFractal.getController();
                    fractal.pan(dragStart, mouseEvent.getPoint());
                    updateCoordsSpinners();
                    dragStart = mouseEvent.getPoint();
                    panelFractal.repaint();
                }
            }
        });
        panelFractal.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent mouseEvent) {
                super.mousePressed(mouseEvent);
                dragStart = mouseEvent.getPoint();
                mouseDown = true;
                panelFractal.setCursor(Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR));
            }

            @Override
            public void mouseReleased(MouseEvent mouseEvent) {
                super.mouseReleased(mouseEvent);
                mouseDown = false;
                panelFractal.setCursor(Cursor.getDefaultCursor());
            }
        });
        sldDelta.addChangeListener(changeEvent -> {
            double value = sldDelta.getValue() / 100d;
            TreeFractal fractal = (TreeFractal) panelFractal.getController();
            fractal.setDelta(value);
            panelFractal.repaint();
        });
        spnLength.addChangeListener(changeEvent -> {
            double value = (double) spnLength.getValue();
            TreeFractal fractal = (TreeFractal) panelFractal.getController();
            fractal.setLength(value);
            panelFractal.repaint();
        });
        sldLengthFactor.addChangeListener(changeEvent -> {
            double value = sldLengthFactor.getValue() / 100d;
            TreeFractal fractal = (TreeFractal) panelFractal.getController();
            fractal.setLengthFactor(value);
            panelFractal.repaint();
        });
    }

    private void updateCoordsSpinners() {
        Rectangle2D.Double dimensions = ((MandelbrotFractal) panelFractal.getController()).getDimensions();
        spnReMin.setValue(dimensions.getMinX());
        spnReMax.setValue(dimensions.getMaxX());
        spnImMin.setValue(dimensions.getMinY());
        spnImMax.setValue(dimensions.getMaxY());
    }

    private void setupMandelbrot() {
        FractalController controller = new MandelbrotFractal(
                MandelbrotFractal.START_RECT,
                sldIterations.getValue(),
                sldHue.getValue()
        );
        panelFractal.setController(controller);
    }
    private void setupTree() {
        FractalController controller = new TreeFractal(
                sldIterations.getValue(),
                sldHue.getValue()
        );
        panelFractal.setController(controller);
    }
    private void setupSierpinski() {
        FractalController controller = new SierpinskiFractal(
                sldIterations.getValue(),
                sldHue.getValue()
        );
        panelFractal.setController(controller);
    }

    private JMenuBar createTopMenu(final JFrame frame) {
        JMenuBar menuBar = new JMenuBar();

        JMenu fileMenu = new JMenu("File");
        fileMenu.setMnemonic('F');
        menuBar.add(fileMenu);
        JMenuItem exportItem = new JMenuItem("Export Image", 'E');
        exportItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                JFileChooser fileChooser = new JFileChooser();
                int option = fileChooser.showSaveDialog(frame);
                if (option == JFileChooser.APPROVE_OPTION) {
                    BufferedImage image = new BufferedImage(panelFractal.getWidth(), panelFractal.getHeight(), BufferedImage.TYPE_INT_RGB);
                    Graphics2D graphics = image.createGraphics();
                    panelFractal.paintAll(graphics);
                    try {
                        if (ImageIO.write(image, "png", fileChooser.getSelectedFile())) {
                            JOptionPane.showMessageDialog(null, "Image exported successfully!");
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                        JOptionPane.showMessageDialog(null, "Failed to write image!");
                    }
                }
            }
        });
        fileMenu.add(exportItem);
        fileMenu.add(new JSeparator());
        JMenuItem exitItem = new JMenuItem("Exit");
        exitItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                System.exit(0);
            }
        });
        fileMenu.add(exitItem);

        JMenu helpMenu = new JMenu("Help");
        helpMenu.setMnemonic('H');
        menuBar.add(helpMenu);
        JMenuItem howToUseItem = new JMenuItem("How to use");
        howToUseItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                JOptionPane.showMessageDialog(null, "You're a smart boi, figure it out");
            }
        });
        helpMenu.add(howToUseItem);
        JMenuItem fractalsItem = new JMenuItem("More about fractals");
        fractalsItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    Desktop desktop = Desktop.getDesktop();
                    desktop.browse(new URI("https://mathigon.org/course/fractals/introduction"));
                } catch (Exception ignored) {}
            }
        });
        helpMenu.add(fractalsItem);
        JMenuItem aboutItem =  new JMenuItem("About");
        aboutItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                JOptionPane.showMessageDialog(null, "Made with ♡ by Raffy");
            }
        });
        helpMenu.add(aboutItem);

        return menuBar;
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Fractal Browser");
        FractalForm form = new FractalForm();
        frame.setContentPane(form.panelMain);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setJMenuBar(form.createTopMenu(frame));
        frame.pack();
        frame.setVisible(true);
    }
}
