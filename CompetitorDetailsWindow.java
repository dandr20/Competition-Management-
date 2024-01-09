import javax.swing.*;
import java.awt.*;
import java.util.Arrays;

public class CompetitorDetailsWindow extends JFrame {
    private Competitor competitor;

    public CompetitorDetailsWindow(Competitor competitor) {
        this.competitor = competitor;
        setTitle("Competitor Details");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        setupGUI();

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void setupGUI() {
        // Panel for individual row data
        JPanel detailsPanel = new JPanel(new GridLayout(0, 2));

        // Font for labels
        Font labelFont = new Font("Arial", Font.BOLD, 16);

        // Add labels and details for individual row data
        detailsPanel.add(createLabel("ID:", labelFont));
        detailsPanel.add(createLabel(competitor.getId(), labelFont));
        detailsPanel.add(createLabel("Name:", labelFont));
        detailsPanel.add(createLabel(competitor.getName().getFullName(), labelFont));
        detailsPanel.add(createLabel("Country:", labelFont));
        detailsPanel.add(createLabel(competitor.getCountry(), labelFont));
        detailsPanel.add(createLabel("Age:", labelFont));
        detailsPanel.add(createLabel(competitor.getAge(), labelFont));
        detailsPanel.add(createLabel("Level:", labelFont));
        detailsPanel.add(createLabel(competitor.getLevel(), labelFont));
        detailsPanel.add(createLabel("Scores:", labelFont));
        detailsPanel.add(createLabel(Arrays.toString(competitor.getScores()), labelFont));
        detailsPanel.add(createLabel("Overall Score:", labelFont));
        detailsPanel.add(createLabel(String.format("%.2f", competitor.getOverallScore()), labelFont));

        // Text area for full details
        JTextArea detailsTextArea = new JTextArea(10, 40);
        Font textAreaFont = new Font("Arial", Font.PLAIN, 18);
        detailsTextArea.setFont(textAreaFont);
        detailsTextArea.setEditable(false);

        // Set the full details text
        detailsTextArea.setText(competitor.getFullDetails());

        JScrollPane scrollPane = new JScrollPane(detailsTextArea);

        // Combine both panels in a split pane
        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, detailsPanel, scrollPane);
        getContentPane().add(splitPane, BorderLayout.CENTER);
    }

    private JLabel createLabel(String text, Font font) {
        JLabel label = new JLabel(text);
        label.setFont(font);
        return label;
    }
}
