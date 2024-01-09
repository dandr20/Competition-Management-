import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class GUIDemo {
    private CompetitorList entries;

    public GUIDemo() {
        // Initialize an empty list of competitors
        entries = new CompetitorList();
        loadCompetitors();
    }

    private void loadCompetitors() {
        // Load competitor data from file
        BufferedReader bufferedReader = null;
        try {
            bufferedReader = new BufferedReader(new FileReader("CompetitorSample.csv"));

            String inputLine = bufferedReader.readLine(); // Read the first line
            while (inputLine != null) {
                processLine(inputLine);
                // Read the next line
                inputLine = bufferedReader.readLine();
            }
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
            System.exit(1);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        } finally {
            try {
                if (bufferedReader != null) {
                    bufferedReader.close();
                }
            } catch (IOException ioe) {
                // Ignore
            }
        }
    }

    // Split input line, create competitor object, and add to the list of entries
    private void processLine(String inputLine) {
        // Split the line into parts
        String[] data = inputLine.split(",");
        // Create a Name object
        Name name = new Name(data[1]);
        String level = data[4].trim();
        int[] scores = new int[5];
        for (int i = 0; i < Math.min(5, data.length - 5); i++) {
            scores[i] = Integer.parseInt(data[6 + i].trim());
        }
        // Create a Competitor object and add it to the list
        Competitor competitor = new Competitor(data[0], name, data[2], data[3], data[4], data[5], scores);
        entries.addDetails(competitor);
    }

    // Show GUI
    private void showGUI() {
        // Create the main GUI with the CompetitorList object
        Manager gui = new Manager(entries);
        gui.setVisible(true);
    }

    public static void main(String[] args) {
        // Create a GUIDemo object, which sets up the interface
        // Then just wait for user interaction
        GUIDemo demo = new GUIDemo();
        demo.showGUI();
    }
}
