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
        bins = s.getBinsArray();                /* Populates a copy of the int array bins */
        binSize = s.getRange() / bins.length;   /* Calculates the range of the bins */
        binMax = (double)s.getBinMax();         /* Finds the maximum value in the bins */
        min = s.getMin();                       /* Finds the minimum value of the data */
        binWidth = (1200 / bins.length);        /* 1200 is the width of the graph */
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
        /* Sets the initial x and y values to the bottom left */
        int x = LEFT_MARGIN;
        int y = getHeight() - BOTTOM_MARGIN;

        /* Goes through each bins and draws the rectangle */
        for(int b : bins) {
            int height = (int)scaleY(b); /* Scales the value inside the bin to a manageable y value on the screen */
            y -= height;                 /* Since the top left is (0,0), we subtract the height from the initial y at the bottom left to get the top left coordinate of the rectangle */
            g.fill3DRect(x,y,binWidth,height,true);
            x += binWidth;               /* Adds the width of the rectangle to x to get the x coordinate of the next rectangle */
            y = getHeight() - BOTTOM_MARGIN; /* Resets the y value every time */
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

    /* The drawYLabels looks very similar to drawBins. The coordinate calculation is the same */
    private void drawYLabels(Graphics g) {
        g.setColor(Color.BLUE);

        DecimalFormat formatter = new DecimalFormat("#,###");
        int x = LEFT_MARGIN;
        int y = getHeight() - BOTTOM_MARGIN;
        for(int b : bins) {
            String label = formatter.format(b);
            y -= (int)scaleY(b);
            g.drawString(label,x + binWidth/3,y - 5); /* Slight adjustment to the coordinates when drawing to position it better */
            x += binWidth;
            y = getHeight() - BOTTOM_MARGIN;
        }
    }
    /* To scale the Y value, I took the height of the graph and divided it by the maximum value in the bins array to get the ratio. I then multiply the value passed in as a parameter with the scale */
    private double scaleY(int value) {
        double scale = (double)(getHeight() - (TOP_MARGIN + BOTTOM_MARGIN))/ binMax;
        return (value * scale) ;
    }
}
