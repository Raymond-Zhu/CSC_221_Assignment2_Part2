import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Simulation {
	
	/* Instance Variables */
	
	// Array of random numbers
    private ArrayList<Double> array;
    
    // Frequency bin array
    private int[] bins;

    /* Creates Random numbers and organizes them into bins
     * Class's Constructor */
    public Simulation(int size, int numOfBins) {
        // Initializes and populates the array
        array = new ArrayList<Double>();
        generateNormalRandomNumbers(size);
        
        // Makes bins from the array
        bins = makeBins(numOfBins);
    }
    
    /* Getter Method for array of random numbers */
    public ArrayList<Double> getArray() {
        return array;
    }
    
    /* Getter Method for frequency bin array */
    public int[] getBinsArray() {
        return bins;
    }

    /* Generates the normally distributed random numbers to populate the array*/
    public void generateNormalRandomNumbers(int size) {
        // Initializes a new Random class 
        Random rand = new Random();

        // Fills array list with random numbers 
        for (int i = 0; i < size; i++) {
            double n = rand.nextGaussian();
            array.add(n);
        }
    }
    
    /* Sorts the random array into bins */
    public int[] makeBins(int numOfBins) {
    	
    	// Array for holding count of numbers belonging in the bin
        int [] bins = new int[numOfBins]; 
        
        // Gets minimum value of the array
        final double min = getMin(); 
        
        // Gets Maximum value of array
        final double max = getMax(); 
        
        // Range of the values belonging in each bin
        final double range = getRange() / numOfBins; 

        // Goes through the array and increments the respective bin the random number belongs to. 
        for (Double d : array) {
        	
        	// Get bin calculates which bin the random number belongs to
           bins[getBin(d,min,max,range)]++; 
           
        }
        
        // Return frequency bin array
        return bins;
    }

    /* Returns max of array */
    public double getMax() {
        return Collections.max(array); 
    }

    /* Calculates the maximum value in bins */
    public int getBinMax() {
    	
    	// Sets max to the first element in bin
        int max = bins[0];
        
        // Iterates through the bin
        for(int b : bins) {
        	// If any frequency is greater than our max - set max equal to it
            if(b > max) {
                max = b;
            }
        }
        
        // Return our calculated max
        return max;
    }

    /* Returns min of array */
    public double getMin() {
        return Collections.min(array); 
    }

    /* Returns the difference of the max and min */
    public double getRange() {
       return getMax() - getMin(); 
    }

    /* Input a random number (num) and returns the index of the bin it belongs to */
    public int getBin(double num, double min, double max, double range) {
       
    	// i is the "upper bound" of the bins.
    	double i = min + range; 
    	
    	// The index of the bin the random number goes in to
        int binNum = 0;  

        /* Special case where the random number is equal to the first bin's "upper bound".
         * The loop will not run since i is not less than num and will return bin number 0.
         * However it belongs in bin number 1 since bin number 0's upper bound is not inclusive. */
        if(num == i) {
            return ++binNum;
        }
        
        /* Since i is the upper bound, if the number is greater than i, we will increase the upper bound
         * by the range and go to the next the bin number */
        while(i < num) {
                // Increment "upper bound"
        		i += range;
                
                /* The calculations cause the numbers to be off by a very very very small amount. i theoretically should not be greater than max but because of these small
                 * offsets, it does happen and as a result, I break the loop if this happens so that the binNum doesn't increment one more time going out of bounds. */
                if(i > max) {
                    break;
                }
                
               // Increment bin number 
               binNum++;
        }
        
        // Return bin number
        return binNum;
    }
}
