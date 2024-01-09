import java.awt.*;
import java.awt.event.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.*;

/**
 * Simple GUI for CompetitorList application
 */
public class Manager extends JFrame implements ActionListener {

    private static final long serialVersionUID = 1L;

    private CompetitorList competitorList;

    // GUI components
    private JTextField result;
    private JTextField searchField;
    private JButton search;
    private JScrollPane scrollList;
    private JButton showListById, showListByName, close, showDetailsButton, addCompetitorButton;
    private JTextArea displayList;
    private JButton editCompetitorButton;
    private JButton deleteCompetitorButton;
    private JButton updateScoresButton;

    /**
     * Create the frame with its panels.
     *
     * @param list The staff list to be searched.
     */
    public Manager(CompetitorList list) {
        this.competitorList = list;

        // set up window title
        setTitle("Competitor Management system");
        // disable standard close button
        setDefaultCloseOperation(this.DO_NOTHING_ON_CLOSE);

        setupSouthPanel();
        setupNorthPanel();
        setupCenterPanel();

        // pack and set visible
        pack();
        setLocationRelativeTo(null); // Center the frame on the screen

        setVisible(true);
    }

    private void setupCenterPanel() {
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BorderLayout());

        // Create a panel for labels
        JPanel labelPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 85, 12));

        // Add labels to the label panel
        Font labelFont = new Font("Arial", Font.BOLD, 16);

        JLabel numberid = new JLabel(" ID ");
        numberid.setFont(labelFont);
        JLabel nameLabel = new JLabel(" Competitor ");
        nameLabel.setFont(labelFont);
        JLabel countryLabel = new JLabel("Country");
        countryLabel.setFont(labelFont);
        JLabel ageLabel = new JLabel("Age");
        ageLabel.setFont(labelFont);
        JLabel genderLabel = new JLabel("Gender");
        genderLabel.setFont(labelFont);
        JLabel levelLabel = new JLabel("Level");
        levelLabel.setFont(labelFont);
        JLabel scoresLabel = new JLabel("Scores");
        scoresLabel.setFont(labelFont);
        JLabel overallLabel = new JLabel("Overall");
        overallLabel.setFont(labelFont);

        labelPanel.add(numberid);
        labelPanel.add(nameLabel);
        labelPanel.add(countryLabel);
        labelPanel.add(ageLabel);
        labelPanel.add(genderLabel);
        labelPanel.add(levelLabel);
        labelPanel.add(scoresLabel);
        labelPanel.add(overallLabel);

        centerPanel.add(labelPanel, BorderLayout.NORTH);

        // Create a panel for the display list
        JPanel displayPanel = new JPanel(new BorderLayout());

        displayList = new JTextArea(25, 55);
        displayList.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 18));
        displayList.setEditable(false);
        JScrollPane scrollList = new JScrollPane(displayList);
        displayPanel.add(scrollList, BorderLayout.CENTER);

        centerPanel.add(displayPanel, BorderLayout.CENTER);

        // Add the combined panel to the main frame
        this.add(centerPanel, BorderLayout.CENTER);

        // Set up initial content for the displayList
        displayList.setText("");
    }

    private void setupSouthPanel() {
        JPanel searchPanel = new JPanel();
        searchPanel.setLayout(new GridLayout(1, 3));

        Font labelFont = new Font("Arial", Font.BOLD, 14);
        Font textFieldFont = new Font("Arial", Font.ITALIC, 16);
        Font buttonFont = new Font("Arial", Font.BOLD, 14);

        JPanel labelPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel label = new JLabel("Enter ID");
        label.setFont(labelFont);
        labelPanel.add(label);
        searchPanel.add(labelPanel);

        searchField = new JTextField(10);
        searchField.setFont(textFieldFont);
        searchPanel.add(searchField);

        search = new JButton("Search");
        search.setFont(buttonFont);
        searchPanel.add(search);

        search.addActionListener(this);

        result = new JTextField(50);
        result.setEditable(false);

        Font resultFont = new Font("Arial", Font.PLAIN, 18);
        result.setFont(resultFont);
        result.setPreferredSize(new Dimension(result.getPreferredSize().width, 30));

        showDetailsButton = new JButton("Show Details");
        showDetailsButton.setFont(buttonFont);
        showDetailsButton.addActionListener(this);
        searchPanel.add(showDetailsButton);

        JPanel southPanel = new JPanel();
        southPanel.setLayout(new GridLayout(2, 1));
        southPanel.add(searchPanel);
        southPanel.add(result);

        this.add(southPanel, BorderLayout.SOUTH);
    }

    private void setupNorthPanel() {
        JPanel northPanel = new JPanel(new BorderLayout());

        JLabel titleLabel = new JLabel("TOTAL NUMBER OF COMPETITORS: " + competitorList.getSize());
        Font titleFont = new Font("Helvetica", Font.BOLD, 16);  // Increase font size for better visibility
        titleLabel.setFont(titleFont);
        titleLabel.setForeground(Color.BLUE);  // Set text color to BLUE 

        titleLabel.setHorizontalAlignment(JLabel.CENTER);  // Center-align the text
        titleLabel.setVerticalAlignment(JLabel.CENTER);  // Center-align the text vertically

        northPanel.add(titleLabel, BorderLayout.CENTER);


        JPanel leftButtonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        addCompetitorButton = new JButton("Add Competitor");
        addCompetitorButton.setFont(new Font("Arial", Font.BOLD, 14));
        addCompetitorButton.addActionListener(this);
        leftButtonPanel.add(addCompetitorButton);

        editCompetitorButton = new JButton("Edit Competitor");
        editCompetitorButton.setFont(new Font("Arial", Font.BOLD, 14));
        editCompetitorButton.addActionListener(this);
        leftButtonPanel.add(editCompetitorButton);

        deleteCompetitorButton = new JButton("Delete Competitor");
        deleteCompetitorButton.setFont(new Font("Arial", Font.BOLD, 14));
        deleteCompetitorButton.addActionListener(this);
        leftButtonPanel.add(deleteCompetitorButton);

        northPanel.add(leftButtonPanel, BorderLayout.WEST);

        JPanel rightButtonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        showListById = new JButton("List By ID");
        showListById.addActionListener(this);

        showListByName = new JButton("List By Name");
        showListByName.addActionListener(this);

        updateScoresButton = new JButton("Update Scores");
        updateScoresButton.addActionListener(this);

        close = new JButton("Close");
        close.addActionListener(this);

        rightButtonPanel.add(showListById);
        rightButtonPanel.add(showListByName);
        rightButtonPanel.add(updateScoresButton);
        rightButtonPanel.add(close);

        northPanel.add(rightButtonPanel, BorderLayout.EAST);

        this.add(northPanel, BorderLayout.NORTH);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == search) {
            search();
        } else if (e.getSource() == showListById) {
            displayList.setText(competitorList.listByID());
        } else if (e.getSource() == showListByName) {
            displayList.setText(competitorList.listByName());
        } else if (e.getSource() == close) {
            writeCompetitorReportToFile();
            JOptionPane.showMessageDialog(this, "Competitor report written to file.\nClosing down ...");
            System.exit(0);
        } else if (e.getSource() == showDetailsButton) {
            showCompetitorDetails();
        } else if (e.getSource() == addCompetitorButton) {
            addCompetitor();
        } else if (e.getSource() == editCompetitorButton) {
            editCompetitor();
        } else if (e.getSource() == deleteCompetitorButton) {
            deleteCompetitor();
        } else if (e.getSource() == updateScoresButton) {
            updateScores();
        }
    }

    private void addCompetitor() {
        JTextField idField = new JTextField();
        JTextField firstNameField = new JTextField();
        JTextField lastNameField = new JTextField();
        JTextField countryField = new JTextField();
        JTextField ageField = new JTextField();
        JTextField genderField = new JTextField();
        JTextField levelField = new JTextField();
        JTextField scoreField1 = new JTextField();
        JTextField scoreField2 = new JTextField();
        JTextField scoreField3 = new JTextField();
        JTextField scoreField4 = new JTextField();
        JTextField scoreField5 = new JTextField();

        Object[] message = {
                "ID:", idField,
                "First Name:", firstNameField,
                "Last Name:", lastNameField,
                "Country:", countryField,
                "Age:", ageField,
                "Gender:", genderField,
                "Level:", levelField,
                "Score 1:", scoreField1,
                "Score 2:", scoreField2,
                "Score 3:", scoreField3,
                "Score 4:", scoreField4,
                "Score 5:", scoreField5
        };

        int option = JOptionPane.showConfirmDialog(this, message, "Enter Competitor Details", JOptionPane.OK_CANCEL_OPTION);

        if (option == JOptionPane.OK_OPTION) {
            String id = idField.getText().trim();
            String firstName = firstNameField.getText().trim();
            String lastName = lastNameField.getText().trim();
            String country = countryField.getText().trim();
            String age = ageField.getText().trim();
            String gender = ageField.getText().trim();
            String level = levelField.getText().trim();
            int[] scores = {
                    Integer.parseInt(scoreField1.getText().trim()),
                    Integer.parseInt(scoreField2.getText().trim()),
                    Integer.parseInt(scoreField3.getText().trim()),
                    Integer.parseInt(scoreField4.getText().trim()),
                    Integer.parseInt(scoreField5.getText().trim())
            };

            Name name = new Name(firstName, "", lastName);
            Competitor newCompetitor = new Competitor(id, name, country, age, gender, level, scores);

            competitorList.addDetails(newCompetitor);
            displayList.setText(competitorList.listDetails());
        }
    }

    private void editCompetitor() {
        String competitorId = JOptionPane.showInputDialog(this, "Enter Competitor ID to Edit:");

        if (competitorId != null) {
            Competitor competitorToEdit = competitorList.findById(competitorId);

            if (competitorToEdit != null) {
                JTextField firstNameField = new JTextField(competitorToEdit.getName().getFirstName());
                JTextField lastNameField = new JTextField(competitorToEdit.getName().getLastName());
                JTextField countryField = new JTextField(competitorToEdit.getCountry());
                JTextField ageField = new JTextField(competitorToEdit.getAge());
                JTextField genderField = new JTextField(competitorToEdit.getGender());
                JTextField levelField = new JTextField(competitorToEdit.getLevel());

                Object[] message = {
                        "First Name:", firstNameField,
                        "Last Name:", lastNameField,
                        "Country:", countryField,
                        "Age:", ageField,
                        "Gender:", genderField,
                        "Level:", levelField
                };

                int option = JOptionPane.showConfirmDialog(this, message, "Edit Competitor Details", JOptionPane.OK_CANCEL_OPTION);

                if (option == JOptionPane.OK_OPTION) {
                    competitorToEdit.getName().setFirstName(firstNameField.getText().trim());
                    competitorToEdit.getName().setLastName(lastNameField.getText().trim());
                    competitorToEdit.setCountry(countryField.getText().trim());
                    competitorToEdit.setAge(ageField.getText().trim());
                    competitorToEdit.setGender(genderField.getText().trim());
                    competitorToEdit.setLevel(levelField.getText().trim());

                    displayList.setText(competitorList.listDetails());
                }
            } else {
                JOptionPane.showMessageDialog(this, "Competitor not found");
            }
        }
    }

    private void deleteCompetitor() {
        String competitorId = JOptionPane.showInputDialog(this, "Enter Competitor ID to Delete:");

        if (competitorId != null) {
            Competitor competitorToDelete = competitorList.findById(competitorId);

            if (competitorToDelete != null) {
                int option = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this competitor?",
                        "Confirm Deletion", JOptionPane.YES_NO_OPTION);

                if (option == JOptionPane.YES_OPTION) {
                    competitorList.removeCompetitor(competitorToDelete);

                    displayList.setText(competitorList.listDetails());
                }
            } else {
                JOptionPane.showMessageDialog(this, "Competitor not found");
            }
        }
    }

    public void writeCompetitorReportToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("competitor_report.txt"))) {
            String finalReport = competitorList.generateFinalReport();
            writer.write(finalReport);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void search() {
        String searchString = searchField.getText().trim();
        if (searchString.length() > 0) {
            Competitor person = competitorList.findById(searchString);
            if (person != null) {
                result.setText(person.getShortDetails());

            } else
                result.setText("not found");
        } else
            result.setText("no text entered");
    }

    private void updateScores() {
        String competitorId = JOptionPane.showInputDialog(this, "Enter Competitor ID to Update Scores:");

        if (competitorId != null) {
            Competitor competitorToUpdate = competitorList.findById(competitorId);

            if (competitorToUpdate != null) {
                JTextField scoreField1 = new JTextField(String.valueOf(competitorToUpdate.getScores()[0]));
                JTextField scoreField2 = new JTextField(String.valueOf(competitorToUpdate.getScores()[1]));
                JTextField scoreField3 = new JTextField(String.valueOf(competitorToUpdate.getScores()[2]));
                JTextField scoreField4 = new JTextField(String.valueOf(competitorToUpdate.getScores()[3]));
                JTextField scoreField5 = new JTextField(String.valueOf(competitorToUpdate.getScores()[4]));

                Object[] message = {
                        "Score 1:", scoreField1,
                        "Score 2:", scoreField2,
                        "Score 3:", scoreField3,
                        "Score 4:", scoreField4,
                        "Score 5:", scoreField5
                };

                int option = JOptionPane.showConfirmDialog(
                        this,
                        message,
                        "Update Scores",
                        JOptionPane.OK_CANCEL_OPTION,
                        JOptionPane.QUESTION_MESSAGE
                );

                if (option == JOptionPane.OK_OPTION) {
                    int[] updatedScores = {
                            Integer.parseInt(scoreField1.getText().trim()),
                            Integer.parseInt(scoreField2.getText().trim()),
                            Integer.parseInt(scoreField3.getText().trim()),
                            Integer.parseInt(scoreField4.getText().trim()),
                            Integer.parseInt(scoreField5.getText().trim())
                    };

                    competitorToUpdate.setScores(updatedScores);
                    displayList.setText(competitorList.listDetails());
                }
            } else {
                JOptionPane.showMessageDialog(this, "Competitor not found");
            }
        }
    }

    

    private void showCompetitorDetails() {
        String selectedCompetitorId = searchField.getText().trim();
        if (!selectedCompetitorId.isEmpty()) {
            Competitor selectedCompetitor = competitorList.findById(selectedCompetitorId);
            if (selectedCompetitor != null) {
                new CompetitorDetailsWindow(selectedCompetitor);
            } else {
                JOptionPane.showMessageDialog(this, "Competitor not found");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Enter a competitor ID to view details");
        }
    }

}
