import java.util.Comparator;

public class CompetitorNameComparator implements Comparator<Competitor> {
    @Override
    public int compare(Competitor c1, Competitor c2) {
        // Assuming getName() returns a Name object with a getFirstName() method
        String firstName1 = c1.getName().getFirstName();
        String firstName2 = c2.getName().getFirstName();

        // Compare the first names
        return firstName1.compareTo(firstName2);
    }
}
