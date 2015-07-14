import java.io.*;
import java.util.ArrayList;
import javax.swing.JFrame;

public class MonteCarlo {
    public static void main(String[] args) {
        Simulation sim = new Simulation(100000,10);
        Histogram h = new Histogram(sim);
        JFrame visuals = new JFrame();

        visuals.setTitle("CSc 221 Histogram");
        visuals.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        visuals.add(h);
        visuals.setSize(1200,800);
        visuals.setVisible(true);
    }
}
