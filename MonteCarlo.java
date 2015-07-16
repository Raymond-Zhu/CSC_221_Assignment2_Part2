import java.io.*;
import java.util.ArrayList;
import javax.swing.JFrame;

public class MonteCarlo {
	
	/* Main Method
	 * Creates a Histogram of Normally Distributed Random Numbers */
    public static void main(String[] args) {
        
    	// Create a simulation object to generate random numbers
    	Simulation sim = new Simulation(100000,10);
    	
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
        visuals.setSize(1200,800);
        
        // Make the JFrame window visible
        visuals.setVisible(true);
    }
}
