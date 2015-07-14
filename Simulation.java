import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Simulation {
    private ArrayList<Double> array;
    private int[] bins;

    public Simulation(int size, int numOfBins) {
        /* Initializes and populates the array */
        array = new ArrayList<Double>();
        generateNormalRandomNumbers(size);
        bins = makeBins(numOfBins);
    }
    public ArrayList<Double> getArray() {
        return array;
    }
    public int[] getBinsArray() {
        return bins;
    }

    public void generateNormalRandomNumbers(int size) {
        /* Initializes a new Random class */
        Random rand = new Random();

        /* Fills array list with random numbers */
        for (int i = 0; i < size; i++) {
            double n = rand.nextGaussian();
            array.add(n);
        }
    }
    public int[] makeBins(int numOfBins) {
        int [] bins = new int[numOfBins]; /* Array for holding count of numbers belonging in the bin */
        final double min = getMin(); /* Gets minimum value of the array */
        final double max = getMax(); /* Gets Maximum value of array */
        final double range = getRange() / numOfBins; /* Range of the values belonging in each bin */

        /* Goes through the array and increments the respective bin the random number belongs to. */
        for (Double d : array) {
           bins[getBin(d,min,max,range)]++; /* Get bin calculates which bin the random number belongs to */
        }
        return bins;
    }

    public double getMax() {
        return Collections.max(array); /* Returns max of array */
    }

    /* Calculates the maximum value in bins */
    public int getBinMax() {
        int max = bins[0];
        for(int b : bins) {
            if(b > max) {
                max = b;
            }
        }
        return max;
    }

    public double getMin() {
        return Collections.min(array); /* Returns min of array */
    }

    public double getRange() {
       return getMax() - getMin(); /* Returns the difference of the max and min */
    }

    public int getBin(double num, double min, double max, double range) {
        double i = min + range; /* i is the "upper bound" of the bins. */
        int binNum = 0;  /* The index of the bin the random number oges in to */

        /* Special case where the random number is equal to the first bin's "upper bound".
         * The loop will not run since i is not less than num and will return bin number 0.
         * However it belongs in bin number 1 since bin number 0's upper bound is not inclusive. */
        if(num == i) {
            return ++binNum;
        }
        /* Since i is the upper bound, if the number is greater than i, we will increase the upper bound
         * by the range and go to the next the bin number */
        while(i < num) {
                i += range;
                /* The calculations cause the numbers to be off by a very very very small amount. i theoretically should not be greater than max but because of these small
                 * offsets, it does happen and as a result, I break the loop if this happens so that the binNum doesn't increment one more time going out of bounds. */
                if(i > max) {
                    break;
                }
               binNum++;
        }
        return binNum;
    }
}
