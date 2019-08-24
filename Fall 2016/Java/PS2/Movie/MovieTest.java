package Movie;

/**
 * Created by Brad on 9/27/2016.
 */
public class MovieTest {
    public static void main (String[] args) {

        Movie a = new Action(Movie.MPAA.PG13, 1, "Star Wars: Remake Masquerading as Sequel");
        Movie b = new Comedy(Movie.MPAA.R, 2, "50 Shades of Crap");
        Movie c = new Drama(Movie.MPAA.G, 3, "Generic Disney Princess Movie #123,456,789");
        Movie d = new Action(Movie.MPAA.PG13, 4, "Generic Action Movie");
        Movie e = new Comedy(Movie.MPAA.PG, 5, "Why Can't We Come Up With New Ideas?");
        Movie f = new Drama(Movie.MPAA.NC17, 6, "Twilight: The Uncensored Version");
        Movie g = new Action(Movie.MPAA.PG13, 1, "Star Wars: Starring Mary Sue Skywalker");

        if (a.equals(g)) {
            System.out.println(String.format("%s is the same movie as %s.", a.getTitle(), g.getTitle()));
        }
        else {
            System.out.println(String.format("%s is not the same movie as %s.", a.getTitle(), g.getTitle()));
        }
        if (b.equals(f)) {
            System.out.println(String.format("%s " +
                    "is the same movie as %s.", b.getTitle(), f.getTitle()));
        }
        else {
            System.out.println(String.format("%s is not the same movie as %s.", b.getTitle(), f.getTitle()));
        }

        System.out.println(String.format("%s is rated %s.", a.getTitle(), a.getRating()));
        System.out.println(String.format("%s is rated %s.", b.getTitle(), b.getRating()));
        System.out.println(String.format("%s is rated %s.", c.getTitle(), c.getRating()));
        System.out.println(String.format("%s is rated %s.", d.getTitle(), d.getRating()));
        System.out.println(String.format("%s is rated %s.", e.getTitle(), e.getRating()));
        System.out.println(String.format("%s is rated %s.", f.getTitle(), f.getRating()));
        System.out.println(String.format("%s is rated %s.", g.getTitle(), g.getRating()));

        System.out.println(String.format("The late fee for returning %s a week late is %.2f.", a.getTitle(), a.calculateLateFees(7)));
        System.out.println(String.format("The late fee for returning %s a week late is %.2f.", b.getTitle(), b.calculateLateFees(7)));
        System.out.println(String.format("The late fee for returning %s a week late is %.2f.", c.getTitle(), c.calculateLateFees(7)));
        System.out.println(String.format("The late fee for returning %s a week late is %.2f.", d.getTitle(), d.calculateLateFees(7)));
        System.out.println(String.format("The late fee for returning %s a week late is %.2f.", e.getTitle(), e.calculateLateFees(7)));
        System.out.println(String.format("The late fee for returning %s a week late is %.2f.", f.getTitle(), f.calculateLateFees(7)));
        System.out.println(String.format("The late fee for returning %s a week late is %.2f.", g.getTitle(), g.calculateLateFees(7)));

    }
}
