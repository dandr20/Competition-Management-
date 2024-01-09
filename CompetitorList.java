import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Objects;

public class CompetitorList {

    private static ArrayList<Competitor> CompetitorList;
    private static final int MAX_CAPACITY = 10; // Adjust the capacity as needed
    public static final int MAX_SCORE = 10;
    private Competitor[] competitors;

    // Constructor
    public CompetitorList() {
        CompetitorList = new ArrayList<>();
        competitors = new Competitor[MAX_CAPACITY]; // Initialize with your desired capacity
    }

    // Get competitor by ID
    public static Competitor getCompetitorById(String id) {
        return CompetitorList.stream()
                .filter(competitor -> competitor.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    // Get competitor by index
    public Competitor getByIndex(int n) {
        return CompetitorList.get(n);
    }

    // Get the size of the CompetitorList
    public int getSize() {
        return CompetitorList.size();
    }

    // Remove a competitor
    public void removeCompetitor(Competitor competitor) {
        CompetitorList.remove(competitor);
    }

    // Find competitor by ID
    public Competitor findById(String id) {
        for (Competitor s : CompetitorList) {
            if (s.getId().equals(id)) {
                return s;
            }
        }
        return null;
    }

    // Add competitor details
    public void addDetails(Competitor details) {
        CompetitorList.add(details);
    }

    // Get the number of entries in the CompetitorList
    public static int getNumberOfEntries() {
        return CompetitorList.size();
    }

    // List all details
    public String listDetails() {
        StringBuffer allEntries = new StringBuffer();
        for (Competitor details : CompetitorList) {
            allEntries.append(details);
            allEntries.append('\n');
        }
        return allEntries.toString();
    }

    // Print competitor report
    public void printCompetitorReport() {
        System.out.println("    Competitor             Level         Scores          Overall");
        for (Competitor competitor : CompetitorList) {
            System.out.printf("%-5s %-20s %-12s %-16s %.1f%n",
                    competitor.getId(),
                    competitor.getName().getFullName(),
                    competitor.getLevel(),
                    Arrays.toString(competitor.getScores()).replaceAll("[\\[\\]]", ""),
                    competitor.getOverallScore());
        }
    }

    // List competitors by name
    public String listByName() {
        Collections.sort(CompetitorList, new CompetitorNameComparator());
        return listDetails();
    }

    // List competitors by ID
    public String listByID() {
        Collections.sort(CompetitorList);
        return listDetails();
    }

    // Generate final report
    public String generateFinalReport() {
        StringBuilder report = new StringBuilder();

        report.append("Competitors' Full Details:\n\n").append(listDetails()).append("\n");

        Competitor highestScorer = getCompetitorWithHighestScore();
        report.append("Competitor with the Highest Overall Score:\n")
                .append(highestScorer != null ? highestScorer.getFullDetails() : "No competitors found.")
                .append("\n\n");

        Competitor lowestScorer = getCompetitorWithLowestScore();
        report.append("Competitor with the Lowest Overall Score:\n")
                .append(lowestScorer != null ? lowestScorer.getFullDetails() : "No competitors found.")
                .append("\n\n");

        Competitor highestLevelCompetitor = getCompetitorWithHighestLevel();
        report.append("Competitor with the Highest Level:\n")
                .append(highestLevelCompetitor != null ? highestLevelCompetitor.getFullDetails() : "No competitors found.")
                .append("\n\n");

        Competitor lowestLevelCompetitor = getCompetitorWithLowestLevel();
        report.append("Competitor with the Lowest Level:\n")
                .append(lowestLevelCompetitor != null ? lowestLevelCompetitor.getFullDetails() : "No competitors found.")
                .append("\n\n");

        report.append("Summary Statistics:\n\n");
        report.append("1. Total Number of Competitors: ").append(getNumberOfEntries()).append("\n\n");
        report.append("2. Average Overall Score: ").append(String.format("%.2f", getAverageOverallScore())).append("\n\n");

        Competitor competitorWithLowestScore = getCompetitorWithLowestScore();
        report.append("3. Competitor with the Lowest Overall Score:\n")
                .append(competitorWithLowestScore != null ? competitorWithLowestScore.getFullDetails() : "No competitors found.")
                .append("\n\n");

        Competitor competitorWithHighestLevel = getCompetitorWithHighestLevel();
        report.append("4. Competitor with the Highest Level:\n")
                .append(competitorWithHighestLevel != null ? competitorWithHighestLevel.getFullDetails() : "No competitors found.")
                .append("\n\n");

        report.append("Frequency Report for Individual Scores:\n\n");
        int[] scoreFrequencies = getScoreFrequencies();
        for (int i = 1; i <= MAX_SCORE; i++) {
            report.append("Score ").append(i).append(": ").append(scoreFrequencies[i]).append(" times\n");
        }

        report.append("\n");

        report.append("Additional Statistics:\n");
        report.append("- Highest Score: ").append(getHighestScore()).append("\n");
        report.append("- Lowest Score: ").append(getLowestScore()).append("\n");
        report.append("- Highest Level: ").append(getHighestLevel()).append("\n");
        report.append("- Lowest Level: ").append(getLowestLevel()).append("\n");
        report.append("- Oldest Competitor: ").append(getOldestCompetitor().getFullDetails()).append("\n");
        report.append("- Youngest Competitor: ").append(getYoungestCompetitor().getFullDetails()).append("\n");

        return report.toString();
    }

    // Get competitor with the lowest level
    public Competitor getCompetitorWithLowestLevel() {
        if (CompetitorList.isEmpty()) {
            return null;
        }

        return CompetitorList.stream()
                .filter(competitor -> competitor != null && competitor.getLevel() != null)
                .min(Comparator.comparing(Competitor::getLevel, Comparator.nullsLast(String.CASE_INSENSITIVE_ORDER)))
                .orElse(null);
    }

    // Get competitor with the highest overall score
    public static Competitor getCompetitorWithHighestScore() {
        if (CompetitorList.isEmpty()) {
            return null;
        }

        return CompetitorList.stream()
                .filter(competitor -> competitor != null && competitor.getOverallScore() != -1.0)
                .max(Comparator.comparingDouble(Competitor::getOverallScore))
                .orElse(null);
    }

    // Get competitor with the lowest overall score
    public Competitor getCompetitorWithLowestScore() {
        return CompetitorList.stream()
                .filter(Objects::nonNull)
                .min(Comparator.comparing(Competitor::getOverallScore))
                .orElse(null);
    }

    // Get competitor with the highest level
    public Competitor getCompetitorWithHighestLevel() {
        return CompetitorList.stream()
                .filter(Objects::nonNull)
                .max(Comparator.comparing(competitor -> {
                    String level = competitor.getLevel();
                    return (level != null) ? level : "";
                }))
                .orElse(null);
    }

    // Filter competitors by criteria
    public String filterByCriteria(String genderFilter, String countryFilter, String levelFilter) {
        StringBuilder filteredList = new StringBuilder();

        for (Competitor competitor : CompetitorList) {
            if (competitorMatchesFilter(competitor, genderFilter, countryFilter, levelFilter)) {
                filteredList.append(competitor).append('\n');
            }
        }

        return filteredList.toString();
    }

    // Helper method to check if a competitor matches the filter criteria
    private boolean competitorMatchesFilter(Competitor competitor, String genderFilter, String countryFilter, String levelFilter) {
        return (genderFilter.isEmpty() || competitor.getGender().equalsIgnoreCase(genderFilter)) &&
                (countryFilter.isEmpty() || competitor.getCountry().equalsIgnoreCase(countryFilter)) &&
                (levelFilter.isEmpty() || competitor.getLevel().equalsIgnoreCase(levelFilter));
    }

    // Get the average overall score of all competitors
    public double getAverageOverallScore() {
        double sum = 0;
        int count = 0;

        for (Competitor competitor : CompetitorList) {
            if (competitor != null && competitor.getOverallScore() != -1.0) {
                sum += competitor.getOverallScore();
                count++;
            }
        }

        return count > 0 ? sum / count : 0.0;
    }

    // Get the frequencies of individual scores
    public static int[] getScoreFrequencies() {
        int[] frequencies = new int[MAX_SCORE + 1];

        for (Competitor competitor : CompetitorList) {
            int[] scores = competitor.getScores();
            for (int score : scores) {
                if (score >= 0 && score <= MAX_SCORE) {
                    frequencies[score]++;
                }
            }
        }

        return frequencies;
    }

    // Get the highest score among all competitors
    public int getHighestScore() {
        return CompetitorList.stream()
                .mapToInt(competitor -> Arrays.stream(competitor.getScores()).max().orElse(0))
                .max()
                .orElse(0);
    }

    // Get the lowest score among all competitors
    public int getLowestScore() {
        return CompetitorList.stream()
                .mapToInt(competitor -> Arrays.stream(competitor.getScores()).min().orElse(0))
                .min()
                .orElse(0);
    }

    // Get the highest level among all competitors
    public String getHighestLevel() {
        return CompetitorList.stream()
                .filter(competitor -> competitor.getLevel() != null)
                .max(Comparator.comparing(Competitor::getLevel))
                .map(Competitor::getLevel)
                .orElse("N/A");
    }

    // Get the lowest level among all competitors
    public String getLowestLevel() {
        return CompetitorList.stream()
                .filter(competitor -> competitor.getLevel() != null)
                .min(Comparator.comparing(Competitor::getLevel))
                .map(Competitor::getLevel)
                .orElse("N/A");
    }

    // Get the oldest competitor
    public Competitor getOldestCompetitor() {
        return CompetitorList.stream()
                .max(Comparator.comparing(competitor -> Integer.parseInt(competitor.getAge())))
                .orElse(null);
    }

    // Get the youngest competitor
    public Competitor getYoungestCompetitor() {
        return CompetitorList.stream()
                .min(Comparator.comparing(competitor -> Integer.parseInt(competitor.getAge())))
                .orElse(null);
    }
}
