import java.io.*;
import java.util.ArrayList;
import javax.swing.JFrame;

public class MonteCarlo {
	
	/* Main Method
	 * Creates a Histogram of Normally Distributed Random Numbers */
    public static void main(String[] args) {
        
    	/* Final Variables */
    	
    	// Number of random numbers
    	final int NUMBER_OF_RANDNUMS = 100000;
    	
    	// Number of bins
    	final int NUMBER_OF_BINS = 10;
    	
    	// JFRAME dimensions
    	final int JFRAME_WIDTH = 1200;
    	final int JFRAME_HEIGHT = 800;
    	
    	
    	// Create a simulation object to generate random numbers
    	Simulation sim = new Simulation();
    	
    	// Generate Normally distributed Random Numbers
    	sim.generateNormalRandomNumbers(NUMBER_OF_RANDNUMS);
    	
    	// Separate random numbers into bins
    	sim.makeBins(NUMBER_OF_BINS);
    	
    	// Create a Histogram Object and pass the simulation object as parameter
        Histogram h = new Histogram(sim);
        
        // Create a new JFrame object
        JFrame visuals = new JFrame();

        // Set Title
        visuals.setTitle("CSc 221 Histogram");
        
        // Exit Application when user initiates a close operation
        visuals.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // Add a JPanel object to JFrame
        visuals.add(h);
        
        // Set the size
        visuals.setSize(JFRAME_WIDTH,JFRAME_HEIGHT);
        
        // Make the JFrame window visible
        visuals.setVisible(true);
    }
}
