import java.awt.Color;
import java.awt.Graphics;
import java.text.DecimalFormat;
import javax.swing.JPanel;

public class Histogram extends JPanel {
	
	/* Declare and Initialize margin variables outside constructor as these are constants */ 
	
	// Define top margin to be 20 
    final int TOP_MARGIN = 20;
    
    // Define bottom margin to be 20 
    final int BOTTOM_MARGIN = 20;
    
    // Define left margin to be 20
    final int LEFT_MARGIN = 20;
    
    // Define right margin to be 20
    final int RIGHT_MARGIN = 20;

    /* Declare instance variables outside constructor  */ 
    
    // Pixel size of width of bar in histogram corresponding to bin
    private final int binWidth;
    
    // Numerical size of bin
    private final double binSize;
    
    // Bin Frequency Array
    private final int[] bins;
    
    // Minimum of random number
    private final double min;
    
    // Index of bin with greatest frequency
    private final double binMax;

    /* Histogram Constructor - Initializes instance variables */
    public Histogram(Simulation s) {
    	
    	// White Background
        setBackground(Color.WHITE);
        
        //Populates a copy of the frequency bin array
        bins = s.getBinsArray(); 
        
        // Calculates the range of the bins
        binSize = s.getRange() / bins.length;
        
        // Finds the maximum value in the bins
        binMax = (double)s.getBinMax();        
        
        //Finds the minimum value of the data
        min = s.getMin();    
        
        // Width of Graph = WIDTH_OF_IMAGE - LEFT_MARGIN - RIGHT_MARGIN
        binWidth = ((1200 - LEFT_MARGIN - RIGHT_MARGIN) / (bins.length));       

    }
    
    /* Draws all components of image */
    public void paintComponent(Graphics g) {
        
    	// Calls the superclass's paintComponent's method
    	super.paintComponent(g);
    	
    	// Draw the X-axis
        drawXAxis(g);
        
        // Draw the Y-axis
        drawYAxis(g);
        
        // Draw the rectangles which represent the bins 
        drawBins(g);
        
        // Draw the labels of the x-axis
        drawXLabels(g);
        
        // Draw the values of the bin frequencies
        drawYLabels(g);
    }
    
    /* Draws a Straight Line from (x1, y1) to (x2, y2) - Horizontal */
    private void drawXAxis(Graphics g) {
        
    	// 20 pixels from the left
    	int x1 = LEFT_MARGIN;
    	
    	// 20 pixels from the bottom
        int y1 = getHeight() - BOTTOM_MARGIN;
        
        // 20 pixels from the right
        int x2 = getWidth() - RIGHT_MARGIN;
        
        // Horizontal Line as y doesn't change
        int y2 = y1;
        
        // Draw the line
        g.drawLine(x1, y1, x2, y2);
    }
    
    /* Draws a Straight Line from (x1, y1) to (x2, y2) - Vertical */
    private void drawYAxis(Graphics g) {
    	
    	// 20 pixels from the left
        int x1 = LEFT_MARGIN;
        
        // 20 pixels from the bottom
        int y1 = getHeight()- BOTTOM_MARGIN;
        
        // Vertical line as x doesn't change
        int x2 = x1;
        
        // 20 pixels from the top
        int y2 = TOP_MARGIN;
        
        // Draw the line
        g.drawLine(x1,y1,x2,y2);
    }
    
    /* Draws the rectangular bins of the histogram */
    private void drawBins(Graphics g) {
    	
    	// Grey Rectangles
        g.setColor(Color.GRAY);
        
        // Sets the initial x and y values to the bottom left 
        int x = LEFT_MARGIN;
        int y = getHeight() - BOTTOM_MARGIN;

        // Goes through each bins and draws the rectangle 
        for(int b : bins) {
        	
        	// Scales the value inside the bin to a manageable y value on the screen
            int height = (int)scaleY(b); 
            
            // Since the top left is (0,0), we subtract the height from the initial y at the bottom left to get the top left coordinate of the rectangle
            y -= height; 
            
            // Fill the rectangle
            g.fill3DRect(x,y,binWidth,height,true);
            
            // Adds the width of the rectangle to x to get the x coordinate of the next rectangle
            x += binWidth;               
            
            // Resets the y value every time
            y = getHeight() - BOTTOM_MARGIN;
        }
    }
    
    /* Numerical Labels for Bin Ranges */
    private void drawXLabels(Graphics g) {
        
    	// Black Text
    	g.setColor(Color.BLACK);

    	// Create a DecimalFormat object
        DecimalFormat formatter = new DecimalFormat();
        
        // Set the number of digits after decimal point to 2 - min and max
        formatter.setMinimumFractionDigits(2);
        formatter.setMaximumFractionDigits(2);
        
        // Numerical value of label
        double labelVal = min;
        
        // Use DecimalFormat Object to convert to String in proper form
        String label = formatter.format(labelVal);
        
        // Set x coordinate to pin the label 
        int x = LEFT_MARGIN;
        
        // Set y coordinate to pin label - make it 12 pixels lower than x-axis
        int y = getHeight() - BOTTOM_MARGIN +12;
        
        // For each bin
        for(int b: bins) {
        	
        	// Draw the Label
            g.drawString(label,x-12,y);
            
            // Increment the x position of label
            x += binWidth;
            
            // Increment Value of label by the numerical size of bin
            labelVal += binSize;
            
            // DecimalFormat object converts the double to a string
            label = formatter.format(labelVal);
        }
        
        // Draw the last label
        g.drawString(label, x-12, y);
    }
 
    /* The drawYLabels looks very similar to drawBins. The coordinate calculation is the same 
     *	Labels frequency on top of bins in histogram
    */
    private void drawYLabels(Graphics g) {
    	
    	// Blue Text
        g.setColor(Color.BLUE);

        // Use DecimalFormat Object to convert to String in proper form
        DecimalFormat formatter = new DecimalFormat("#,###");
        
        // x coordinate of label 
        int x = LEFT_MARGIN;
        
        // Set y to base of the rectangle bin
        int y = getHeight() - BOTTOM_MARGIN;
        
        // For each bin
        for(int b : bins) {
        	
        	// Use DecimalFormat object to format the integer frequency into a String 
            String label = formatter.format(b);
            
            // Set y-coordinate to be on top of rectangle
            y -= (int)scaleY(b);
            
            // Draw the labels - Slight adjustment to the coordinates when drawing to position it better
            g.drawString(label,x + binWidth/3,y - 5);
            
            // Increment the x coordinate
            x += binWidth;
            
            // Reset y coordinate to base of rectangular bin
            y = getHeight() - BOTTOM_MARGIN;
        }
    }
    /* To scale the Y value, I took the height of the graph and divided it by the maximum value in the bins array to get the ratio. 
     * I then multiply the value passed in as a parameter with the scale */
    private double scaleY(int value) {
    	
    	// Scale = Height of Graph / (Maximum Frequency of bin array)
        double scale = (double)(getHeight() - (TOP_MARGIN + BOTTOM_MARGIN))/ binMax;
        
        // Value is a frequency in bin - So full formula is (frequency value)*(graph height) / (maximum frequency)
        return (value * scale) ;
    }
}
