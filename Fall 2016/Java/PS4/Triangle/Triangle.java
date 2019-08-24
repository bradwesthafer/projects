package Triangle;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Brad on 10/29/2016.
 */
public class Triangle extends JFrame implements ActionListener {
    public static final int WIDTH = 1024;
    public static final int HEIGHT = 768;
    public static final int MAX_SIDE = 700;
    public static final int FIRST_X = 150;
    public static final int FIRST_Y = 675;
    private int startOrder = 1;

    public static void main(String[] args) {
        Triangle window = new Triangle("Triangle");
        window.setVisible(true);
    }

    public Triangle(String title) {
        super(title);

        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        JPanel orderPanel = new JPanel();
        JLabel label = new JLabel("Enter order: ");
        JTextField order = new JTextField();
        order.setHorizontalAlignment(JTextField.RIGHT);
        order.setText("1");
        order.addActionListener(this);
        orderPanel.add(label);
        orderPanel.add(order);
        order.setPreferredSize(new Dimension(WIDTH/20, 25));
        add(orderPanel, BorderLayout.SOUTH);


    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        drawTriangle(g, FIRST_X, FIRST_Y, MAX_SIDE, startOrder);
    }

    public void drawTriangle(Graphics g, int x_init, int y_init, int side_length, int order) {
        if (order == 0) {
            return;
        }
        else {
            int x_second = x_init + side_length, y_second = y_init;
            int x_third = x_init + side_length/2, y_third = y_init - (int) (side_length * Math.sin(Math.PI/3));
            g.drawLine(x_init, y_init, x_second, y_second);
            g.drawLine(x_second, y_second, x_third, y_third);
            g.drawLine(x_third, y_third, x_init, y_init);
            drawTriangle(g, x_init, y_init, side_length / 2, order - 1);
            drawTriangle(g, (x_init + x_second)/2, y_init, side_length / 2, order - 1);
            drawTriangle(g, (x_init * 3 + x_second)/4, (y_init + y_third)/2, side_length / 2, order - 1);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String actionCommand = e.getActionCommand();
        try {
            startOrder = Integer.valueOf(actionCommand);
            repaint();
        } catch(Exception ex) {
            System.err.println("Non-Numeric Input for Order");
            ex.printStackTrace();
            System.exit(42);
        }
    }
}
