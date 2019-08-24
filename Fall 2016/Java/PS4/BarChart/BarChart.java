package BarChart;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Brad on 10/29/2016.
 */
public class BarChart extends JFrame {
    public static final int WIDTH = 1024;
    public static final int HEIGHT = 768;
    public static final int DOWNSCALE_BY = 100000;
    public static final int RECT_WIDTH = 120;
    public static final int BOTTOM_Y = 650;
    public static final int CAPTION_Y = 680;
    private int values[] = {50_456_002, 50_999_897, 2_882_955, 448_895, 384_431};
    private int leftX[] = new int[5];

    public static void main(String[] args) {
        BarChart window = new BarChart("2000 Presidential Election Results");
        window.setVisible(true);
    }

    public BarChart(String title) {
        super(title);
        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setBackground(Color.BLACK);

        leftX[0] = 100;
        for (int i = 1; i < 5; i++) {
            leftX[i] = leftX[i - 1] + 180;
        }
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        g.setColor(Color.RED);
        g.fillRect(leftX[0], BOTTOM_Y - values[0]/DOWNSCALE_BY, RECT_WIDTH, values[0]/DOWNSCALE_BY);
        g.setColor(Color.BLUE);
        g.fillRect(leftX[1], BOTTOM_Y - values[1]/DOWNSCALE_BY, RECT_WIDTH, values[1]/DOWNSCALE_BY);
        g.setColor(Color.GREEN);
        g.fillRect(leftX[2], BOTTOM_Y - values[2]/DOWNSCALE_BY, RECT_WIDTH, values[2]/DOWNSCALE_BY);
        g.setColor(Color.MAGENTA);
        g.fillRect(leftX[3], BOTTOM_Y - values[3]/DOWNSCALE_BY, RECT_WIDTH, values[3]/DOWNSCALE_BY);
        g.setColor(Color.YELLOW);
        g.fillRect(leftX[4], BOTTOM_Y - values[4]/DOWNSCALE_BY, RECT_WIDTH, values[4]/DOWNSCALE_BY);
        g.setColor(Color.WHITE);
        g.setFont(new Font("Serif", Font.BOLD, 32));
        g.drawString("Bush", leftX[0], CAPTION_Y);
        g.drawString("Gore", leftX[1], CAPTION_Y);
        g.drawString("Nader", leftX[2], CAPTION_Y);
        g.drawString("Buchanan", leftX[3], CAPTION_Y);
        g.drawString("Browne", leftX[4], CAPTION_Y);
        g.setFont(new Font("Serif", Font.ITALIC, 26));
        g.drawString("Scale: 100,000 votes = 1 pixel", 300, 740);
        g.setFont(new Font("Serif", Font.BOLD, 48));
        g.drawString("2000 Presidential Election Results", 175, 100);
    }
}
