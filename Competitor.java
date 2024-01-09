import java.util.Arrays;

// A simple class to contain and manage competitor details
// (id, name, country, age, qualLevel, scores)
public class Competitor implements Comparable<Competitor> {
    private String id;
    private Name name;
    private String country;
    private String age;
    private String gender;
    private String qualLevel;
    private int[] scores;

    // Constructor to set up competitor details
    public Competitor(String id, Name name, String country, String age, String gender, String qualLevel, int[] scores) {
        // id and name MUST be provided
        if (name == null || id.trim().length() == 0) {
            throw new IllegalStateException("Cannot have blank id or name");
        }
        this.id = id.trim();
        this.name = name;
        this.country = country.trim();
        this.age = age.trim();
        this.gender = gender.trim();
        this.qualLevel = qualLevel;
        this.scores = Arrays.copyOf(scores, scores.length); // Copy the array to avoid external modification
    }

    // Getter methods
    public String getId() { return id; }
    public Name getName() { return name; }
    public String getCountry() { return country; }
    public String getAge() { return age; }
    public String getLevel() { return qualLevel; }
    public String getGender() { return gender; }
    public int[] getScores() { return Arrays.copyOf(scores, scores.length); }

    // Setter methods
    public void setId(String id) { this.id = id; }
    public void setName(Name name) { this.name = name; }
    public void setCountry(String country) { this.country = country; }
    public void setAge(String age) { this.age = age; }
    public void setLevel(String qualLevel) { this.qualLevel = qualLevel; }
    public void setGender(String gender) { this.gender = gender; }
    public void setScores(int[] scores) { this.scores = Arrays.copyOf(scores, scores.length); }

    // Test for content equality between two objects
    public boolean equals(Object other) {
        if (other instanceof Competitor) {
            Competitor otherCompetitor = (Competitor) other;
            return id.equals(otherCompetitor.getId());
        } else {
            return false;
        }
    }

    // Compare this Competitor object against another for sorting
    // Fields are compared by id
    public int compareTo(Competitor otherDetails) {
        return id.compareTo(otherDetails.getId());
    }

    // Returns a multi-line string containing full competitor details
    public String getFullDetails() {
        String pronoun = (gender.equalsIgnoreCase("male")) ? "him" : "her";
        return String.format("Competitor number %s, name %s, country %s.\n%s is a %s aged %s and received these scores: %s\nThis gives %s an overall score of %.1f.",
                id, name.getFullName(), country, name.getFirstName(), getLevel(), getAge(), Arrays.toString(scores).replaceAll("[\\[\\]]", ""), pronoun.toLowerCase(), getOverallScore());
    }

    // Returns a string with short competitor details
    public String getShortDetails() {
        return String.format("CN %s (%s) has an overall score of %.2f.", id, getInitials(), getOverallScore());
    }

    // Returns a string representation of the object
    public String toString() {
        return String.format("        %-8s %-17s %-11s %-10s %-10s %-9s %-15s %.1f%s ",
                getId(),
                getName().getFullName(),
                getCountry(),
                getAge(),
                getGender(),
                getLevel(),
                Arrays.toString(getScores()).replaceAll("[\\[\\]]", ""),
                getOverallScore(),
                System.lineSeparator());
    }

    // Returns the initials of the competitor
    private String getInitials() {
        StringBuilder initials = new StringBuilder();
        initials.append(name.getFirstName().charAt(0));
        if (!name.getMiddleName().isEmpty()) {
            initials.append(name.getMiddleName().charAt(0));
        }
        initials.append(name.getLastName().charAt(0));
        return initials.toString();
    }

    // Returns the overall score of the competitor
    public double getOverallScore() {
        // Your logic to calculate the overall score based on the array of scores
        // Example: Calculating the average
        double sum = 0;
        for (int score : scores) {
            sum += score;
        }
        return sum / scores.length;
    }
}
