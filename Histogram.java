import java.awt.Color;
import java.awt.Graphics;
import java.text.DecimalFormat;
import javax.swing.JPanel;

public class Histogram extends JPanel {
    final int TOP_MARGIN = 20;
    final int BOTTOM_MARGIN = 20;
    final int LEFT_MARGIN = 20;
    final int RIGHT_MARGIN = 20;

    private final int binWidth;
    private final double binSize;
    private final int[] bins;
    private final double min;
    private final double binMax;

    public Histogram(Simulation s) {
        setBackground(Color.WHITE);
        bins = s.getBinsArray();
        binSize = s.getRange() / bins.length;
        binMax = (double)s.getBinMax();
        min = s.getMin();
        binWidth = (1200 / bins.length); //1200 is the width of the graph
    }
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawXAxis(g);
        drawYAxis(g);
        drawBins(g);
        drawXLabels(g);
        drawYLabels(g);
    }
    private void drawXAxis(Graphics g) {
        int x1 = LEFT_MARGIN;
        int y1 = getHeight() - BOTTOM_MARGIN;
        int x2 = getWidth() - RIGHT_MARGIN;
        int y2 = y1;
        g.drawLine(x1, y1, x2, y2);
    }
    private void drawYAxis(Graphics g) {
        int x1 = LEFT_MARGIN;
        int y1 = getHeight()- BOTTOM_MARGIN;
        int x2 = x1;
        int y2 = TOP_MARGIN;
        g.drawLine(x1,y1,x2,y2);
    }
    private void drawBins(Graphics g) {
        g.setColor(Color.GRAY);
        int x = LEFT_MARGIN;
        int y = getHeight() - BOTTOM_MARGIN;
        for(int b : bins) {
            int height = (int)scaleY(b);
            y -= height;
            x += binWidth;
            g.fill3DRect(x,y,binWidth,height,true);
            y = getHeight() - BOTTOM_MARGIN;
        }
    }
    private void drawXLabels(Graphics g) {
        g.setColor(Color.BLACK);

        DecimalFormat formatter = new DecimalFormat();
        formatter.setMinimumFractionDigits(2);
        formatter.setMaximumFractionDigits(2);
        double labelVal = min;
        String label = formatter.format(labelVal);
        int x = LEFT_MARGIN;
        int y = getHeight() - BOTTOM_MARGIN +12;
        for(int b : bins) {
            g.drawString(label,x-12,y);
            x += binWidth;
            labelVal += binSize;
            label = formatter.format(labelVal);
        }
        g.drawString(label, x-12, y);
    }
    private void drawYLabels(Graphics g) {
        g.setColor(Color.BLUE);

        DecimalFormat formatter = new DecimalFormat("#,###");
        int x = LEFT_MARGIN + binWidth;
        int y = getHeight() - BOTTOM_MARGIN;
        for(int b : bins) {
            String label = formatter.format(b);
            y -= (int)scaleY(b);
            g.drawString(label,x + binWidth/3,y - 5);
            x += binWidth;
            y = getHeight() - BOTTOM_MARGIN;
        }
    }
    private double scaleY(int value) {
        double scale = (double)(getHeight() - (TOP_MARGIN + BOTTOM_MARGIN))/ binMax;
        return (value * scale) ;
    }
}
